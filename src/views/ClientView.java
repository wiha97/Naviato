package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import managers.ViewManager;
import models.GameBoard;
import network.ClientHandler;

//JJ
public class ClientView {

    private static Slider slider;
    public static double getSliderValue() {
        return slider.getValue();
    }

    public void start(Stage stage) {

        GameBoard gameBoard = new GameBoard();
        gameBoard.generateField();

        VBox mainBox = new VBox();
        mainBox.setSpacing(40);
        mainBox.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE,null,null)));
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPrefSize(400,800);

        VBox titleBox = new VBox();
        Label title = new Label("Client Page");
        title.setFont(Font.font(16));
        title.setTextFill(Color.WHITE);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);

        VBox insideBox = new VBox(10);
        insideBox.setAlignment(Pos.CENTER);

        Label enterIP = new Label("Enter IP:");
        TextField numberIP = new TextField();
        enterIP.setTextFill(Color.WHITE);
        numberIP.setMaxWidth(200);

        Label enterPort = new Label("Enter Port:");
        TextField  numberPort = new TextField();
        enterPort.setTextFill(Color.WHITE);
        numberPort.setMaxWidth(200);

        VBox statusBox = new VBox();
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setMaxWidth(200);
        statusBox.setBackground(new Background(new BackgroundFill(Color.YELLOW,null,null)));

        Label statusLabel = new Label("Waiting for server...");
        statusLabel.setTextFill(Color.BLACK);
        statusBox.getChildren().add(statusLabel);

        VBox sliderBox = new VBox();
        slider = new Slider(0, 5, 1);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setShowTickLabels(true);
        slider.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        sliderBox.setMaxWidth(200);

        Label sliderLebel = new Label("Adjust fire delay in seconds:");
        sliderLebel.setTextFill(Color.WHITE);
        sliderLebel.setAlignment(Pos.CENTER);
        sliderBox.getChildren().addAll(sliderLebel,slider);

        Button button = new Button("Start");
        button.setOnAction(e-> {
            String IP = numberIP.getText();
            int port = Integer.parseInt(numberPort.getText());
            ClientHandler clientHandler = new ClientHandler(IP,port);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
            ViewManager.planView();

        });

        insideBox.getChildren().addAll(enterIP, numberIP, enterPort, numberPort, statusBox,sliderBox, button);

        mainBox.getChildren().addAll(titleBox,insideBox);

        Scene scene = new Scene(mainBox);
        stage.setScene(scene);

        stage.show();




    }


}
