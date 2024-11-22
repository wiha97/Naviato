package views;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import managers.GameManager;
import managers.ViewManager;
import models.GameBoard;
import models.Ship;
import models.Square;

import javax.swing.text.View;
import java.util.ArrayList;

// WH
public class BattlePlanView {
    private GameBoard board = GameManager.getGameBoard();
    private ObservableList<Ship> shipStock = board.getDeployable();
    private BoardView boardView = new BoardView(board, false);
    AnchorPane playPane = boardView.boardPane(50);


    public void start(Stage stage) {
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setWidth(1180);
        stage.setHeight(820);
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
            boardView.drawBoard();
        } else {
            error();
        }
    }

    private void clearBoard() {
        board.generateField();

        boardView.drawBoard();
    }

    public void placeShips() {
        Runnable runner = () -> {
            boolean placed = false;
            while (!placed) {
                board.generateField();
                boardView.drawBoard();
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
                boardView.drawBoard();
            }
        GameManager.setRunning(false);
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
        flowPane.setPrefWidth(400);
        flowPane.getChildren().addAll(rndBtn, clrBtn, extBtn, playBtn);

        rndBtn.setOnMouseClicked((e) -> {
            placeShips();
        });
        clrBtn.setOnMouseClicked((e) -> clearBoard());
        playBtn.setOnMouseClicked((e) -> ViewManager.getBattleView().start(ViewManager.getBaseStage()));


        hBox.getChildren().addAll(boardView.playPane(playPane), SharedViews.logView(520));
        hBox.setAlignment(Pos.CENTER);

        btnBox.getChildren().addAll(flowPane);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.setId("btnBox");

        pane.getChildren().addAll(title, hBox, listBox, btnBox);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setId("basePane");
        boardView.drawBoard();
        return pane;
    }
}
