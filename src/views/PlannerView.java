package views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import models.Ship;

import java.awt.*;
import java.util.Random;

public class PlannerView {
    public Scene planScene() {
//        StackPane rootPane = new StackPane();
        VBox content = new VBox();
        content.setBackground(new Background(bg(Color.DODGERBLUE)));
//        content.setBackground(new Background(bg("#272727")));
        HBox tBox = new HBox();
//        content.setAlignment(Pos.CENTER);
        Label title = new Label();
        title.setText("NAVIATO");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Z003", 32));
        title.setAlignment(Pos.CENTER);
        tBox.getChildren().add(title);
        tBox.setAlignment(Pos.CENTER);
        HBox playGrounds = new HBox();
        playGrounds.getChildren().add(battleGround(1));
        playGrounds.getChildren().add(battleGround(2));
        playGrounds.setSpacing(50);
        playGrounds.setAlignment(Pos.CENTER);

        content.getChildren().addAll(tBox, playGrounds);
//        rootPane.getChildren().add(content);
//        StackPane.setAlignment(rootPane, Pos.CENTER);
        return new Scene(content);
    }

    private VBox battleGround(int p) {
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #101010; -fx-padding:5px; -fx-background-radius:5px;");
        AnchorPane aPane = new AnchorPane();
        aPane.setBackground(new Background(bg(Color.NAVY)));
        aPane.setPadding(new Insets(2.5));

        AnchorPane.setLeftAnchor(aPane, 50d);

        Ship ship = new Ship(5);

        int s = 30;
        Point size = new Point(s, s);
        int idx = 0;
        int ydx = 0;
        int gap = 5;
        String chars = "ABCDEFGHIJK";
        char[] chArr = chars.toCharArray();
        String style = "-fx-border-color: gray; -fx-border-width: 2px; -fx-border-radius:5px; -fx-background-radius:5px; -fx-color:transparent;";
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                if (idx != 0)
                    ydx++;
                idx = 0;
            }
            Rectangle rect = new Rectangle();
            rect.setWidth(size.x);
            rect.setHeight(size.y);
            rect.setFill(Color.WHITE);

            Label label = new Label();
            label.setPrefWidth(size.x);
            label.setPrefHeight(size.y);
            label.setBackground(new Background(bg(Color.TRANSPARENT)));
            label.setStyle(style);
            label.setAlignment(Pos.CENTER);
            label.setTextFill(Color.TRANSPARENT);
            label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    label.setStyle(style+"-fx-background-color: dodgerblue; -fx-text-fill:white;");
                }
            });
            final boolean[] clicked = {false};
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    label.setStyle(style+"-fx-background-color:maroon;");
                    clicked[0] = true;
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(!clicked[0])
                        label.setStyle(style+"-fx-text-fill:transparent;");
                }
            });
            if (new Random().nextInt(15) < 4) {
//                label.setBackground(new Background(bg(Color.MAROON)));
            }

                label.setText("" + chArr[idx] + (ydx));


            rect.setLayoutX(gap + (size.x + gap) * idx);
            rect.setLayoutY(gap + (size.y + gap) * ydx);
            label.setLayoutX(gap + (size.x + gap) * idx);
            label.setLayoutY(gap + (size.y + gap) * ydx);
            idx++;
            aPane.getChildren().add(label);
        }
//        rootPane.getChildren().add(aPane);
        Label label = new Label();
        label.setText("Player " + p);
        label.setTextFill(Color.WHITE);
        vBox.getChildren().addAll(label, aPane);
        return vBox;
    }

    static BackgroundFill bg(String hex) {
        return new BackgroundFill(Color.web(hex), null, null);
    }

    static BackgroundFill bg(Color color) {
        return new BackgroundFill(color, new CornerRadii(5), null);
    }
}
