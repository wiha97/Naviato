package app;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.GameManager;
import managers.ViewManager;

import java.util.Arrays;

public class Naviato extends Application {
    public static void main(String[] args) {
        GameManager.setAvailableSquares(Arrays.stream(GameManager.getGameBoard().getSquares()).toList());
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager.start(stage);
    }

}
