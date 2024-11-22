package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import managers.GameManager;
import managers.ViewManager;

//Anna
public class GameOverView extends Application {

    private final ObservableList<models.Ship> deployable = FXCollections.observableArrayList();

    public void start (Stage primaryStage) {
//        deployable.filtered();
        String winner;
            if (GameManager.getGameBoard().getDeployable().isEmpty()) {
                winner = "Client is a winner!";
            }
            else {
                winner = "Server is a winner!";
            }
            util.Print.line(winner);
            showGameOverView(primaryStage, winner);
    }

    private void showGameOverView(Stage primaryStage, String winnerText) {

        javafx.scene.layout.AnchorPane anchorPane = new javafx.scene.layout.AnchorPane();
        anchorPane.setPrefSize(400, 400);
        anchorPane.setStyle("-fx-background-color: BLUE;");

        Label winnerLabel = new Label(winnerText + " is a winner!");
        winnerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        winnerLabel.setStyle("-fx-background-color: RED; -fx-text-fill: white;");
        winnerLabel.setLayoutX(100);
        winnerLabel.setLayoutY(100);

        Button restartButton = new Button("Restart");
        restartButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        restartButton.setStyle("-fx-background-color: RED; -fx-text-fill: white;");
        restartButton.setLayoutX(100);
        restartButton.setLayoutY(200);

        restartButton.setOnAction(e -> {
            System.out.println("The game is starting over!");
            startNewGame(primaryStage);
        });

        anchorPane.getChildren().addAll(winnerLabel, restartButton);
        Scene scene = new Scene(anchorPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Over");
        primaryStage.show();
    }
    private void startNewGame(Stage primaryStage) {
        ViewManager.welcomeView();
//        System.out.println("Start over!");
    }
    public static void gameOver(String[] args) {
        launch();
    }
}




