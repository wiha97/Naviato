package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import managers.ViewManager;


public class WelcomeView {
//    public static void welcome(String[] args) {
//        launch();
//
//    }
    public void start(Stage primaryStage){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(400, 400);

        HBox hBox = new HBox(10);
        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        Button button = new Button("Server");
        button.setLayoutX(100);
        button.setLayoutY(100);
        button.setOnAction((e)->{
            ViewManager.serverView();
        });

        Button button2 = new Button("Client");
        button2.setLayoutX(100);
        button2.setLayoutY(200);
        button2.setOnAction((e)->{
            ViewManager.clientView();
        });

        anchorPane.getChildren().addAll(button, button2);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome!");
        primaryStage.show();

    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
