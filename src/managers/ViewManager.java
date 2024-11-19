package managers;

import javafx.stage.Stage;
import views.*;

public class ViewManager {
    private static Stage baseStage;
    public static void start(Stage stage){
        baseStage = stage;
        welcomeView();
//        new WelcomeView().start(baseStage);
//        stage.show();
    }

    public static void welcomeView(){
        new Welcome().start(baseStage);
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

    public static void battleView(){
        new BattleView().start(baseStage);
    }

}
