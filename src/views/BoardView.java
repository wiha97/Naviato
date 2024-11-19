package views;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import managers.AIManager;
import managers.GameManager;
import models.GameBoard;
import models.Ship;
import models.Square;
import util.App;

public class BoardView {
    int size = 50;
    private boolean isOpponent;
    private boolean isSide;
    AnchorPane pane;
    GameBoard board;

    public BoardView(GameBoard board, boolean isOp){
        this.board = board;
        isOpponent = isOp;
    }

    public VBox playPane(AnchorPane pane) {
        this.pane = pane;
        VBox content = new VBox();
        VBox stack = new VBox();
        HBox rows = new HBox();
//        pane = boardPane();
        stack.getChildren().add(topRow());
        rows.getChildren().add(sideRow());
        rows.getChildren().add(pane);
        rows.getChildren().add(sideRow());
        stack.getChildren().add(rows);
        stack.getChildren().add(topRow());
        content.getChildren().add(stack);
        content.setId("playPane");
        return content;
    }

    public AnchorPane boardPane(int size) {
        this.size = size;
        AnchorPane pane = new AnchorPane();
        pane.setId("battleground");
        int x = 0;
        int y = 0;
        int i = 0;
        for (Square square : GameManager.getGameBoard().getSquares()) {
            if (i % 10 == 0) {
                if (x != 0)
                    y++;
                x = 0;
            }
            Label boardCell = new Label();
            boardCell.setPrefWidth(size);
            boardCell.setPrefHeight(size);
//            boardCell.setStyle(css);
            boardCell.setAlignment(Pos.CENTER);
            boardCell.setId("boardCell");
            boardCell.setFont(new Font(size / 3d));

            boardCell.setText("" + square.getCoordinate().toUpperCase());


            pane.getChildren().add(boardCell);

            boardCell.setLayoutX((size) * x);
            boardCell.setLayoutY((size) * y);
            x++;
            i++;
        }
        pane.setCursor(Cursor.NONE);
        return pane;
    }

    public HBox topRow() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            Label label = guideLabel();
            label.setPrefWidth(size);
            label.setPrefHeight(size / 2d);
            label.setId("hg");
            label.setText("" + i);
            box.getChildren().add(label);
        }
//        box.setStyle("-fx-background-color:#272727;");
        return box;
    }

    public VBox sideRow() {
        VBox box = new VBox();
        for (int i = 0; i < 10; i++) {
            Label label = guideLabel();
            label.setPrefWidth(size / 2d);
            label.setPrefHeight(size);
            label.setId("vg");
            label.setText("" + SharedViews.getChArr()[i]);
            box.getChildren().add(label);
        }
        return box;
    }

    private Label guideLabel() {
        Label label = new Label();
        label.setFont(new Font(size / 3d));
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("guide");
        return label;
    }

    public void loop(){
        new Thread(() -> {
            while (GameManager.isRunning()) {
                Platform.runLater(() -> drawBoard());
                App.sleep(100);
            }
        }).start();
    }

    public void drawBoard() {
        int i = 0;
        for (Node n : pane.getChildren()) {
            int idx = i;
            Square square = board.getSquares()[idx];
            n.getStyleClass().clear();
            n.getStyleClass().add("boardCell");
            if(!isOpponent && !board.getDeployable().isEmpty()) {
                if(isSide)
                    n.setId("bcHori");
                else
                    n.setId("bcVert");
            }
            else {
                if(square.isHit())
                    n.setId("splash");
                else if(square.isTarget())
                    n.setId("targetCell");
                else
                    n.setId(null);
            }
            n.setOnScroll((e) -> {
                scroll();
                drawBoard();
            });
            n.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY)
                    if(isOpponent) {
                        GameManager.getLogList().add(0, "Shot " + board.getSquares()[idx].getCoordinate());

                        //  Confirm with opponent if hit
                        AIManager.getPossibleTargets(idx);
                        square.hitSquare();
                        if(square.isHit())
                            n.setId("shipHit");
                        else
                            n.setId("splash");
                        drawBoard();
                    }
                    else
                        placeShip(idx);
            });
            n.setOnMouseMoved(mouseEvent -> {
                if(isOpponent) {
                    AIManager.getPossibleTargets(idx);
                    drawBoard();
                }
            });
            i++;
        }
        if(!isOpponent)
            drawShips();
    }

    int scrollCount = 0;
    public void scroll() {
        if(scrollCount++ % 2 == 0)
            isSide = !isSide;
    }

    private void placeShip(int pos) {
        if (board.placeShip(pos, isSide)) {
            drawBoard();
        } else {
            SharedViews.error(pane);
        }
    }

    private void drawShips() {
        for (int i = 0; i < board.getSquares().length; i++) {
            Square sq = board.getSquares()[i];
            if (sq.getShip() != null) {
                Node node = pane.getChildren().get(i);
                node.setId(sq.getShip().getName().toLowerCase());
                node.getStyleClass().add("ship");
                if(sq.isHit())
                    node.setId("shipHit");
                int idx = i;
                node.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        board.removeShip(idx);
                        drawBoard();
                    }
                });
            }
        }
    }
}
