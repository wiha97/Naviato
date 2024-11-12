package app;

import javafx.application.Application;
import javafx.stage.Stage;
import managers.ViewManager;

public class Naviato extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager.start(stage);
    }

}
