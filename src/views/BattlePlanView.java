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
import models.GameBoard;
import models.Ship;
import models.ShipCell;
import models.Square;

import java.util.ArrayList;

public class BattlePlanView {
    private GameBoard board = GameManager.getGameBoard();
    private ObservableList<Ship> shipStock = board.getDeployable();
    AnchorPane playPane = SharedViews.boardPane();
    boolean vertPlace = true;


    public void start(Stage stage) {
        board.generateField();
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(800);
    }

    private void drawBoard() {
        int i = 0;
        for (Node n : playPane.getChildren()) {
            n.getStyleClass().clear();
            n.getStyleClass().add("boardCell");
            if(!shipStock.isEmpty()){
                n.setId("bcHori");
                if (vertPlace)
                    n.setId("bcVert");
            }
            else
                n.setId(null);
            int idx = i;
            n.setOnScroll((e) -> {
                scroll();
            });
            n.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY)
                    placeShip(idx, !vertPlace);
//                if (event.getButton() == MouseButton.SECONDARY) {
//                    board.removeShip(idx);
//                    drawBoard();
//                }
            });
            i++;
        }
        drawShips();
    }

    //  Fix for double-trigger from setOnScroll
    int sc = 0;

    private void scroll() {
        sc++;
        if (sc % 2 == 0)
            vertPlace = !vertPlace;
        drawBoard();
    }

    private void drawShips() {
        for (int i = 0; i < board.getSquares().length; i++) {
            Square sq = board.getSquares()[i];
            if (sq.getShip() != null) {
                Node node = playPane.getChildren().get(i);
                node.setId(sq.getShip().getName().toLowerCase());
                node.getStyleClass().add("ship");
                int idx = i;
                node.setOnMouseClicked(event -> {
//                    if (event.getButton() == MouseButton.PRIMARY)
//                        placeShip(idx, !vertPlace);
                    if (event.getButton() == MouseButton.SECONDARY) {
                        board.removeShip(idx);
                        drawBoard();
                    }
                });
            }
        }
    }

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
            drawBoard();
        } else {
            error();
        }
    }

    private void clearBoard() {
        board.generateField();

        drawBoard();
    }

    private void placeShips() {
        Runnable runner = () -> {
            boolean placed = false;
            while (!placed) {
                board.generateField();
                drawBoard();
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

        Label shipYard = new Label();
        shipYard.setText("Shipyard:");
//        shipYard.setAlignment(Pos.CENTER_LEFT);
        shipYard.setId("txt");

        HBox shipBox = new HBox();
        shipBox.setAlignment(Pos.CENTER);
        for (Ship ship : shipStock) {
            HBox hull = new HBox();
            for (int i = 0; i < ship.getSize(); i++) {
                        StackPane stack = new StackPane();
                        Rectangle rect = new Rectangle(10, 10);
                        rect.setFill(Color.TRANSPARENT);
                        stack.getChildren().add(rect);
                        stack.getStyleClass().add("ship");
                        stack.setId(ship.getName().toLowerCase());
                        hull.getChildren().add(stack);
            }
            shipBox.getChildren().add(hull);
        }
        shipBox.setSpacing(15);
        shipStock.addListener(new ListChangeListener<Ship>() {
            @Override
            public void onChanged(Change<? extends Ship> change) {
                shipBox.getChildren().clear();
                for (Ship ship : change.getList()) {
                    HBox hull = new HBox();
                    for (int i = 0; i < ship.getSize(); i++) {
                        StackPane pane = new StackPane();
                        Rectangle rect = new Rectangle(10, 10);
                        rect.setFill(Color.TRANSPARENT);
                        pane.getChildren().add(rect);
                        pane.getStyleClass().add("ship");
                        pane.setId(ship.getName().toLowerCase());
                        hull.getChildren().add(pane);
                    }
                    shipBox.getChildren().add(hull);
                }
            }
        });
        listBox.getChildren().addAll(shipBox);

        title.getStyleClass().add("btn");
        title.setText("BATTLEPLAN");
        title.setId("title");

        rndBtn.getStyleClass().add("btn");
        rndBtn.setId("rndBtn");
        rndBtn.setText("RANDOMIZE");

        clrBtn.getStyleClass().add("btn");
        clrBtn.setId("clrBtn");
        clrBtn.setText("CLEAR");

        extBtn.getStyleClass().add("btn");
        extBtn.setId("extBtn");
        extBtn.setText("GO BACK");

        playBtn.getStyleClass().add("btn");
        playBtn.setId("playBtn");
        playBtn.setText("READY UP");

        rndBtn.setOnMouseClicked((e) -> {
            placeShips();
        });
        clrBtn.setOnMouseClicked((e) -> clearBoard());


        hBox.getChildren().add(SharedViews.playPane(playPane));
//        hBox.getChildren().add(playPane());
        hBox.setAlignment(Pos.CENTER);

        btnBox.getChildren().addAll(rndBtn, playBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.setId("btnBox");

//        pane.setSpacing(50);
        pane.getChildren().addAll(title, hBox, listBox, btnBox);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setId("basePane");
        drawBoard();
        return pane;
    }
}
