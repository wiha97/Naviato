package managers;

import javafx.application.Platform;
import javafx.stage.Stage;
import views.*;

public class ViewManager {
    private static Stage baseStage;

    public static void start(Stage stage) {
        baseStage = stage;
        new WelcomeView().start(baseStage);
        stage.show();
    }

    public static void serverView() {
        baseStage.setScene(new ServerView().serverScene());
    }

    public static void clientView() {
        new ClientView().start(baseStage);
    }

    public static void planView() {
        new Thread(() -> {
            BattlePlanView view = new BattlePlanView();
            Platform.runLater(() -> view.start(baseStage));
            Platform.runLater(view::placeShips);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignore) {
            }

            Platform.runLater(() -> battleView());
        }).start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        new BattlePlanView().start(baseStage);
    }

    public static void battleView() {
        new BattleView().start(baseStage);
    }

}
