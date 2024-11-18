package views;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import managers.GameManager;
import managers.ViewManager;
import models.GameBoard;
import models.Ship;
import models.ShipCell;
import models.Square;

import java.util.ArrayList;

public class BattlePlanView {
    private GameBoard board = GameManager.getGameBoard();
    private ObservableList<Ship> shipStock = board.getDeployable();
    AnchorPane playPane = SharedViews.boardPane(50);
    boolean vertPlace = true;


    public void start(Stage stage) {
        board.generateField();
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setWidth(800);
//        stage.setHeight(800);
    }

//    private void drawBoard() {
//        int i = 0;
//        for (Node n : playPane.getChildren()) {
//            n.getStyleClass().clear();
//            n.getStyleClass().add("boardCell");
//            if(!shipStock.isEmpty()){
//                n.setId("bcHori");
//                if (vertPlace)
//                    n.setId("bcVert");
//            }
//            else
//                n.setId(null);
//            int idx = i;
//            n.setOnScroll((e) -> {
//                scroll();
//            });
//            n.setOnMouseClicked(event -> {
//                if (event.getButton() == MouseButton.PRIMARY)
//                    placeShip(idx, !vertPlace);
////                if (event.getButton() == MouseButton.SECONDARY) {
////                    board.removeShip(idx);
////                    drawBoard();
////                }
//            });
//            i++;
//        }
//        drawShips();
//    }
//
//    //  Fix for double-trigger from setOnScroll
//    int sc = 0;
//
//    private void scroll() {
//        sc++;
//        if (sc % 2 == 0)
//            vertPlace = !vertPlace;
//        drawBoard();
//    }
//
//    private void drawShips() {
//        for (int i = 0; i < board.getSquares().length; i++) {
//            Square sq = board.getSquares()[i];
//            if (sq.getShip() != null) {
//                Node node = playPane.getChildren().get(i);
//                node.setId(sq.getShip().getName().toLowerCase());
//                node.getStyleClass().add("ship");
//                int idx = i;
//                node.setOnMouseClicked(event -> {
////                    if (event.getButton() == MouseButton.PRIMARY)
////                        placeShip(idx, !vertPlace);
//                    if (event.getButton() == MouseButton.SECONDARY) {
//                        board.removeShip(idx);
//                        drawBoard();
//                    }
//                });
//            }
//        }
//    }

    private void error() {
        new Thread(() -> {

            playPane.setId("error");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            playPane.setId("battleground");
        }).start();
    }

    private void placeShip(int pos, boolean isSide) {
        if (board.placeShip(pos, isSide)) {
            SharedViews.drawBoard(playPane, board);
        } else {
            error();
        }
    }

    private void clearBoard() {
        board.generateField();

        SharedViews.drawBoard(playPane, board);
    }

    private void placeShips() {
        Runnable runner = () -> {
            boolean placed = false;
            while (!placed) {
                board.generateField();
                SharedViews.drawBoard(playPane, board);
                for (Ship ship : new ArrayList<>(shipStock)) {
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    placed = board.generateShips(ship);
                    if (!placed) {
                        playPane.setId("error");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        playPane.setId("battleground");
                        break;
                    }
                    for (int i = 0; i < board.getSquares().length; i++) {
                        Square sq = board.getSquares()[i];
                        if (sq.getShip() != null) {
                            Node node = playPane.getChildren().get(i);
                            node.setId(sq.getShip().getName().toLowerCase());
                            node.getStyleClass().add("ship");
                        }
                    }
                    Platform.runLater(() -> {
                        shipStock.remove(ship);
                    });
                }
            }
        };
        new Thread(runner).start();
    }

    private VBox basePane() {
        VBox pane = new VBox();
        Label title = new Label();
        HBox hBox = new HBox();
        HBox btnBox = new HBox();
        Label rndBtn = new Label();
        Label clrBtn = new Label();
        Label extBtn = new Label();
        Label playBtn = new Label();

//        ListView<Ship> listView = new ListView<>();
//        listView.setItems(shipStock);
//        listView.setCellFactory(p -> new ShipCell(10));
//        listView.setOrientation(Orientation.HORIZONTAL);
//        listView.setId("listView");
//        listView.setMouseTransparent(true);
//        listView.setPrefHeight(30);
//        listView.setMinHeight(30);
//        listView.setMaxWidth(465);

        VBox listBox = new VBox();

        listBox.getChildren().addAll(SharedViews.shipYard(shipStock));
        listBox.setMinHeight(20);
        listBox.setAlignment(Pos.CENTER);
        listBox.setMaxWidth(400);
        listBox.setId("shipYard");
        listBox.setStyle("-fx-background-color:#272727;");

        title.setText("BATTLEPLAN");
        title.setId("title");

        FlowPane flowPane = new FlowPane();

        rndBtn.getStyleClass().add("btn");
        rndBtn.setId("rndBtn");
        rndBtn.setText("RANDOMIZE");

        clrBtn.getStyleClass().add("btn");
        clrBtn.setId("clrBtn");
        clrBtn.setText("CLEAR BOARD");

        extBtn.getStyleClass().add("btn");
        extBtn.setId("extBtn");
        extBtn.setText("GO BACK");

        playBtn.getStyleClass().add("btn");
        playBtn.setId("playBtn");
        playBtn.setText("READY UP");

        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(15);
        flowPane.setHgap(15);
//        flowPane.setPrefWrapLength(500);
        flowPane.setPrefWidth(400);
        flowPane.getChildren().addAll(rndBtn, clrBtn, extBtn, playBtn);

        rndBtn.setOnMouseClicked((e) -> {
            placeShips();
        });
        clrBtn.setOnMouseClicked((e) -> clearBoard());
        playBtn.setOnMouseClicked((e) -> ViewManager.battleView());


        hBox.getChildren().add(SharedViews.playPane(playPane));
//        hBox.getChildren().add(playPane());
        hBox.setAlignment(Pos.CENTER);

        btnBox.getChildren().addAll(flowPane);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.setId("btnBox");

//        pane.setSpacing(50);
        pane.getChildren().addAll(title, hBox, listBox, btnBox);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setId("basePane");
        SharedViews.drawBoard(playPane, board);
        return pane;
    }
}
