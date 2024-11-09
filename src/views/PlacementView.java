package views;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import models.GameBoard;
import models.Ship;
import models.Square;

public class PlacementView {
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
        AnchorPane pane = playPane();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("style.css");
        pStage.setScene(scene);
        placeShips();
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
                        if(event.getButton() == MouseButton.SECONDARY)
                            placeShips();
                        else {
                            Square sq = board.getSquares()[idx];
                            if(sq.hitSquare().toLowerCase().contains("hit"))
                                n.setStyle("-fx-background-color:maroon;");
                            else
                                n.setStyle("-fx-background-color:blue;");
                        }
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
                    for (int i = 0; i < board.getSquares().length; i++) {
                        Square sq = board.getSquares()[i];
                        if (sq.getShip() != null) {
                            pp.getChildren().get(i).setStyle("-fx-background-color: " + sq.getColor() + ";-fx-text-fill:white;");
                        }

                    }
                }

                if (!placed) {
                    pp.setId("error");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    pp.setId("battleground");
                }
            }
        };
        new Thread(runner).start();
    }

    private AnchorPane playPane() {
        AnchorPane pane = new AnchorPane();
        VBox stack = new VBox();
        HBox rows = new HBox();
        stack.getChildren().add(topRow());
        rows.getChildren().add(sideRow());
        rows.getChildren().add(pp);
        rows.getChildren().add(sideRow());
        stack.getChildren().add(rows);
        stack.getChildren().add(topRow());
        pane.getChildren().add(stack);
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

            boardCell.setOnMouseClicked(event -> {
//                board.placeShip(board.getoSquares().indexOf(square), event.getButton() == MouseButton.SECONDARY);
//                play();
                if(event.getButton() == MouseButton.SECONDARY)
                    placeShips();
//                System.out.println(square.hitSquare());
            });
//            boardCell.setStyle(css + "-fx-background-color:" + square.getColor() + "; -fx-text-fill:transparent;");


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
        rect.setFill(Color.web("#272727"));
        box.getChildren().add(rect);
        for (char c : chArr) {
            Label label = new Label();
            label.setPrefWidth(squareSize);
            label.setPrefHeight(squareSize / 2d);
            label.setLayoutX(gap + (squareSize + gap) * i);
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-background-color:#272727;-fx-text-fill:white;-fx-border-color:gray;");
            label.setText("" + c);
            box.getChildren().add(label);
            i++;
        }
        box.setStyle("-fx-background-color:#272727;");
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
            label.setStyle("-fx-background-color:#272727;-fx-text-fill:white;-fx-border-color:gray;");
            label.setText("" + i);
            box.getChildren().add(label);
        }
        return box;
    }
}
