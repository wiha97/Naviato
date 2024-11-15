package managers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import views.BattlePlanView;
import views.ClientView;
import views.ServerView;
import views.WelcomeView;

public class ViewManager {
    private static Stage baseStage;
    public static void start(Stage stage){
        baseStage = stage;
        new WelcomeView().start(baseStage);
        stage.show();
    }

    public static void serverView(){
        baseStage.setScene(new ServerView().serverScene());
    }

    public static void clientView(){
        new ClientView().start(baseStage);
    }

    public static void planView(){
        new BattlePlanView().start(baseStage);
    }

}
