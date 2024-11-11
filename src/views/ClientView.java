package views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.GameBoard;
import network.ClientHandler;


public class ClientView extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        GameBoard gameBoard = new GameBoard();
        gameBoard.generateField();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        anchorPane.setPrefSize(1000,800);

        VBox titleBox = new VBox();
        Label title = new Label("Client");
        title.setTextFill(Color.WHITE);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);


        Label enterIP = new Label("Enter IP:");
        TextField numberIP = new TextField();
        enterIP.setTextFill(Color.WHITE);

        Label enterPort = new Label("Enter Port:");
        TextField  numberPort = new TextField();
        enterPort.setTextFill(Color.WHITE);


        Button button = new Button("Start");
        button.setOnAction(e-> {
            String IP = numberIP.getText();
            int port = Integer.parseInt(numberPort.getText());
            ClientHandler clientHandler = new ClientHandler(IP,port);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();


        });



        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(titleBox, enterIP, numberIP, enterPort, numberPort, button);

        anchorPane.getChildren().addAll(vBox);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        stage.show();




    }


}
