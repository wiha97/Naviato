package views;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import managers.GameManager;
import models.Square;

public class SharedViews {
    private static final int SQUARE_SIZE = 50;
    static String abc = "ABCDEFGHIJ";
    static char[] chArr = abc.toCharArray();


    public static VBox playPane(AnchorPane pane) {
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

    public static AnchorPane boardPane() {
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
            boardCell.setPrefWidth(SQUARE_SIZE);
            boardCell.setPrefHeight(SQUARE_SIZE);
//            boardCell.setStyle(css);
            boardCell.setAlignment(Pos.CENTER);
            boardCell.setId("boardCell");
            boardCell.setFont(new Font(SQUARE_SIZE / 3d));

            boardCell.setText("" + square.getCoordinate());


            pane.getChildren().add(boardCell);

            boardCell.setLayoutX((SQUARE_SIZE) * x);
            boardCell.setLayoutY((SQUARE_SIZE) * y);
            x++;
            i++;
        }
        pane.setCursor(Cursor.NONE);
        return pane;
    }

    public static HBox topRow() {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            Label label = guideLabel();
            label.setPrefWidth(SQUARE_SIZE);
            label.setPrefHeight(SQUARE_SIZE / 2d);
            label.setId("hg");
            label.setText("" + i);
            box.getChildren().add(label);
        }
//        box.setStyle("-fx-background-color:#272727;");
        return box;
    }

    public static VBox sideRow() {
        VBox box = new VBox();
        for (int i = 0; i < 10; i++) {
            Label label = guideLabel();
            label.setPrefWidth(SQUARE_SIZE / 2d);
            label.setPrefHeight(SQUARE_SIZE);
            label.setId("vg");
            label.setText("" + chArr[i]);
            box.getChildren().add(label);
        }
        return box;
    }

    private static Label guideLabel() {
        Label label = new Label();
        label.setFont(new Font(SQUARE_SIZE / 3d));
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("guide");
        return label;
    }
}
