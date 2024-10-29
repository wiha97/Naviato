package app;

import javafx.application.Application;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Naviato extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label label = new Label();
        label.setText("Hello Space");
        label.setTextFill(Color.WHITE);
        label.setBackground(new Background(new BackgroundFill(Color.web("#272727"), null, null)));
        label.setPrefWidth(400);
        label.setPrefHeight(200);
        label.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(label));
        stage.setTitle("Naviato");
        stage.show();
    }
}
