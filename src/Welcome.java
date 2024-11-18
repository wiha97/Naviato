package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Background;


//Anna
public class Welcome extends Application {
    public static void welcome(String[] args) {
        launch();

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(400, 400);

        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        Button button = new Button("Server");
        button.setLayoutX(100);
        button.setLayoutY(100);
        button.setPrefWidth(200);
        button.setPrefHeight(50);

        Button button2 = new Button("Client");
        button2.setLayoutX(100);
        button2.setLayoutY(200);
        button2.setPrefWidth(200);
        button2.setPrefHeight(50);

        button.setOnAction(e -> {
            button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        });

        button2.setOnAction(e -> {
            button2.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                });

        anchorPane.getChildren().addAll(button, button2);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome!");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
