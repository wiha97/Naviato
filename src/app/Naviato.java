package app;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.GameManager;
import managers.ViewManager;

public class Naviato extends Application {
    public static void main(String[] args) {
        GameManager.getGameBoard().generateField();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager.start(stage);
    }

}
