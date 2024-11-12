package views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import managers.ViewManager;
import network.ServerHandler;

public class ServerView {
    private ServerHandler serverHandler;
    private Label connectionStatus;

    public ServerView() {
        this.serverHandler = null;
    }

    public Scene serverScene() {
        VBox content = new VBox();
        content.setSpacing(20);
        content.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox tBox = new HBox();
        Label title = new Label("NAVIATO");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Z003", 32));
        title.setAlignment(Pos.CENTER);
        tBox.getChildren().add(title);
        tBox.setAlignment(Pos.CENTER);

        Label serverStatus = new Label("Server status");
        serverStatus.setFont(new Font(24));
        serverStatus.setTextFill(Color.WHITE);
        serverStatus.setAlignment(Pos.CENTER);

        HBox portBox = new HBox();
        portBox.setSpacing(10);
        portBox.setAlignment(Pos.CENTER);
        Label portLabel = new Label("Port:");
        portLabel.setFont(new Font(16));
        portLabel.setTextFill(Color.WHITE);

        TextField portField = new TextField();
        portField.setPromptText("Enter a port higher than 1024");
        portField.setPrefWidth(210);
        portField.setStyle("-fx-prompt-text-fill: lightgray;");

        portBox.getChildren().addAll(portLabel, portField);

        HBox connectionBox = new HBox();
        connectionBox.setSpacing(10);
        connectionBox.setAlignment(Pos.CENTER);
        connectionStatus = new Label("Waiting for connection");
        connectionStatus.setFont(new Font(16));
        connectionStatus.setTextFill(Color.LIGHTGRAY);
        connectionStatus.setStyle("-fx-background-color: red; -fx-padding: 10px; -fx-background-radius: 5px;");

        Button startServerBtn = new Button("Start server");
        startServerBtn.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;");
        startServerBtn.setOnMouseEntered(e -> startServerBtn.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;"));
        startServerBtn.setOnMouseExited(e -> startServerBtn.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;"));

        startServerBtn.setOnAction(e -> {
            String portText = portField.getText();
            try {
                int port = Integer.parseInt(portText);
                if (port > 1024 && port <= 65535) {
                    connectionStatus.setText("Server started on port: " + port + ". Waiting for client...");
                    connectionStatus.setStyle("-fx-background-color: darkgreen; -fx-padding: 10px; -fx-background-radius: 5px;");
                    serverHandler = new ServerHandler(port, this);
                    serverHandler.startServer();
                    ViewManager.planView();
                } else {
                    connectionStatus.setText("Port must be above 1024 and below 65536");
                }
            } catch (NumberFormatException ex) {
                connectionStatus.setText("Invalid port number");
            }
        });

        Button stopServerBtn = new Button("Stop server");
        stopServerBtn.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;");
        stopServerBtn.setOnMouseEntered(e -> stopServerBtn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;"));
        stopServerBtn.setOnMouseExited(e -> stopServerBtn.setStyle("-fx-background-color: darkred; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5px; -fx-border-color: transparent;"));

        stopServerBtn.setOnAction(e -> {
            if (serverHandler != null) {
                serverHandler.stopServer();
                serverHandler = null;
                connectionStatus.setText("Server stopped.");
                connectionStatus.setStyle("-fx-background-color: darkred; -fx-padding: 10px; -fx-background-radius: 5px;");
            } else {
                connectionStatus.setText("No server is running.");
            }
        });

        connectionBox.getChildren().addAll(connectionStatus);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        spacer.setMinHeight(20);
        buttonBox.getChildren().addAll(startServerBtn, spacer, stopServerBtn);

        content.getChildren().addAll(title, serverStatus, connectionBox, portBox, buttonBox);
        content.setAlignment(Pos.CENTER);

        return new Scene(content, 500, 400);
    }

    public void updateConnectionStatus(String message) {
        Platform.runLater(() -> {
            connectionStatus.setText(message);
            connectionStatus.setStyle("-fx-background-color: green; -fx-padding: 10px; -fx-background-radius: 5px;");
        });
    }
}