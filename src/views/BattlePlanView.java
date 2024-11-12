package views;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.GameBoard;
import models.Ship;
import models.Square;

public class BattlePlanView {
    private GameBoard board = new GameBoard();
    private int squareSize = 50;
    private int gap = 2;
    private boolean isVert = true;
    private String css = "-fx-border-color: gray; -fx-border-width: 2px;";
    //    private String css = "-fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius:5px; -fx-background-radius:5px;";
    String abc = "ABCDEFGHIJ";
    char[] chArr = abc.toCharArray();
    Stage pStage;
    AnchorPane pp;


    public void start(Stage stage) {
        pStage = stage;
        board.generateField();
        play();
    }

    void play() {
        pp = boardPane();
//        AnchorPane pane = playPane();
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        pStage.setScene(scene);
        pStage.setWidth(700);
        pStage.setHeight(800);
//        placeShips();
    }

    private void placeShips() {
        Runnable runner = () -> {
            boolean placed = false;
            while (!placed) {
                board.generateField();
                int s = 0;
                for (Node n : pp.getChildren()) {
                    n.setStyle("-fx-background-color:transparent;");
                    int idx = s;
                    n.setOnMouseClicked(event -> {
                        Square sq = board.getSquares()[idx];
                        if (sq.hitSquare().toLowerCase().contains("hit"))
                            n.setStyle("-fx-background-color:maroon;");
                        else
                            n.setStyle("-fx-background-color:blue;");
                    });
                    s++;
                }

                for (Ship ship : board.getShips()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    placed = board.generateShips(ship);
                    if (!placed) {
                        pp.setId("error");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        pp.setId("battleground");
                        break;
                    }
                    for (int i = 0; i < board.getSquares().length; i++) {
                        Square sq = board.getSquares()[i];
                        if (sq.getShip() != null) {
                            pp.getChildren().get(i).setStyle("-fx-background-color: " + sq.getColor() + ";-fx-text-fill:white;");
                        }

                    }
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
        Label playBtn = new Label();
//        pane.setPrefWidth(400);
//        pane.setPrefHeight(600);
        title.setText("BATTLEPLAN");
        title.setId("title");
        rndBtn.setId("rndBtn");
        rndBtn.setText("RANDOMIZE");
        rndBtn.setOnMouseClicked((e) -> placeShips());
        playBtn.setId("playBtn");
        playBtn.setText("READY UP");

        hBox.getChildren().add(playPane());
        hBox.setAlignment(Pos.CENTER);

        btnBox.getChildren().addAll(rndBtn, playBtn);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.setId("btnBox");

//        pane.setSpacing(50);
        pane.getChildren().addAll(title, hBox, btnBox);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setId("basePane");
        return pane;
    }

    private VBox playPane() {
        VBox pane = new VBox();
        VBox stack = new VBox();
        HBox rows = new HBox();
        stack.getChildren().add(topRow());
        rows.getChildren().add(sideRow());
        rows.getChildren().add(pp);
        rows.getChildren().add(sideRow());
        stack.getChildren().add(rows);
        stack.getChildren().add(topRow());
        pane.getChildren().add(stack);
        pane.setId("playPane");
        return pane;
    }

    private AnchorPane boardPane() {
        AnchorPane pane = new AnchorPane();
        pane.setId("battleground");
        int x = 0;
        int y = 0;
        int i = 0;
        for (Square square : board.getSquares()) {
            if (i % 10 == 0) {
                if (x != 0)
                    y++;
                x = 0;
            }
            Label boardCell = new Label();
            boardCell.setPrefWidth(squareSize);
            boardCell.setPrefHeight(squareSize);
//            boardCell.setStyle(css);
            boardCell.setAlignment(Pos.CENTER);
            boardCell.setId("boardCell");

            boardCell.setText("" + square.getCoordinate());


            pane.getChildren().add(boardCell);

            boardCell.setLayoutX((squareSize) * x);
            boardCell.setLayoutY((squareSize) * y);
            x++;
            i++;
        }
        pane.setCursor(Cursor.NONE);
        return pane;
    }

    private HBox topRow() {
        HBox box = new HBox();
        int i = 1;
        Rectangle rect = new Rectangle();
        rect.setWidth(squareSize / 2d);
//        rect.setFill(Color.web("#272727"));
        box.getChildren().add(rect);
        for (char c : chArr) {
            Label label = new Label();
            label.setPrefWidth(squareSize);
            label.setPrefHeight(squareSize / 2d);
            label.setLayoutX(gap + (squareSize + gap) * i);
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-text-fill:white;-fx-border-color:gray;-fx-border-width: 0 1 0 1;");
            label.setText("" + c);
            box.getChildren().add(label);
            i++;
        }
//        box.setStyle("-fx-background-color:#272727;");
        return box;
    }

    private VBox sideRow() {
        VBox box = new VBox();
        for (int i = 0; i < 10; i++) {
            Label label = new Label();
            label.setPrefWidth(squareSize / 2d);
            label.setPrefHeight(squareSize);
            label.setLayoutX(gap + (squareSize + gap) * i);
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-text-fill:white;-fx-border-color:gray;-fx-border-width: 1 0 1 0;");
            label.setText("" + i);
            box.getChildren().add(label);
        }
        return box;
    }
}
