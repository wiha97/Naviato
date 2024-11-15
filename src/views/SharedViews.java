package views;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import managers.GameManager;
import models.Ship;
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

            boardCell.setText("" + square.getCoordinate().toUpperCase());


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

    public static HBox shipYard(ObservableList<Ship> shipStock) {

        FlowPane flowPane = new FlowPane();

        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(5);
        flowPane.setHgap(5);

        HBox shipBox = new HBox();
        shipBox.setPadding(new Insets(2));
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
            flowPane.getChildren().add(hull);
        }
        shipBox.setSpacing(15);
        shipBox.setSpacing(15);
        shipStock.addListener(new ListChangeListener<Ship>() {
            @Override
            public void onChanged(Change<? extends Ship> change) {
                try {
                    flowPane.getChildren().clear();
                    if(!change.getList().isEmpty()) {
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
                            flowPane.getChildren().add(hull);
                        }
                    }
                    else {
                        Label label = new Label("No more ships");
                        label.setId("txt");
                        flowPane.getChildren().add(label);
                    }
                } catch (Exception ignore) {
                    // Absolutely nothing to catch here, definitely no errors
                    // Go away now
                    // Shoo
                }
            }
        });
        shipBox.getChildren().add(flowPane);
        shipBox.setMouseTransparent(true);
        return shipBox;
    }
}
