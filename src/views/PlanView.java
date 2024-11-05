package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.GameBoard;
import models.Square;

import java.awt.*;

public class PlanView {
    AnchorPane pane;

    public void show(Stage stage) {
        VBox content = new VBox();
        Label title = new Label();
        final AnchorPane[] playArea = {new AnchorPane()};
//        Button refresh = new Button();
        Label refresh = new Label();
        TextArea textArea = new TextArea();
        content.setStyle("-fx-background-color:#272727;");
        content.setAlignment(Pos.TOP_CENTER);
        title.setText("Placement view");
        title.setFont(new Font("Z003", 32));
        title.setTextFill(Color.WHITE);
        playArea[0] = buildScene();
        refresh.setText("Redeploy");
        refresh.setStyle("-fx-padding:10px; -fx-text-fill:white; -fx-background-color:green; -fx-background-radius:10px;");
        refresh.setOnMouseClicked(event -> {
            show(stage);
        });
        content.getChildren().addAll(title, playArea[0], refresh);


        stage.setScene(new Scene(content));
    }

    public Scene planScene(Stage stage) {
        VBox vBox = new VBox();
        pane = buildScene();
        vBox.getChildren().add(pane);
        Button btn = new Button();
        btn.setText("Hello");
        btn.setOnAction(event -> {
            refresh(stage);
        });
        vBox.getChildren().add(btn);
        return new Scene(vBox);
    }

    private void refresh(Stage stage) {
        //  Actually awful
        stage.setScene(planScene(stage));
    }

    private AnchorPane buildScene() {
        GameBoard board = new GameBoard();
        board.generateField();
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color:blue;");
        int size = 30;
        int idx = 0;
        int ydx = 0;
        int gap = 2;
        String style = "-fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius:5px; -fx-background-radius:5px;";
//        for (int i = 0; i < board.getSize(); i++) {
//        String style = "";
        int i = 0;
        for (Square square : board.getSquares()) {
            if (i % 10 == 0) {
                if (idx != 0)
                    ydx++;
                idx = 0;
            }
            Label label = new Label();
            label.setPrefWidth(size);
            label.setPrefHeight(size);
            label.setStyle(style);
            label.setAlignment(Pos.CENTER);

            label.setText("" + square.getCoordinate());

//            Square square = board.getSquares()[i];
            label.setOnMouseClicked(event -> {
                square.setHit(true);
                label.setStyle(style + "-fx-background-color:" + square.getColor() + "; -fx-text-fill:white;");
            });
            label.setStyle(style + "-fx-background-color:" + square.getColor() + "; -fx-text-fill:white;");


            pane.getChildren().add(label);

            label.setLayoutX(gap + (size + gap) * idx);
            label.setLayoutY(gap + (size + gap) * ydx);
            idx++;
            i++;
        }
        return pane;
    }
}
