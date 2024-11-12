package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ButtonExample extends Application {
    public static void buttonExample(String[] args) {
        launch();

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(400, 400);

        HBox hBox = new HBox(10);
        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        Button button = new Button("Server");
        button.setLayoutX(100);
        button.setLayoutY(100);

        Button button2 = new Button("Client");
        button2.setLayoutX(100);
        button2.setLayoutY(200);

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
