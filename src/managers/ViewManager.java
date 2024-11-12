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

public class ViewManager {
    private static Stage baseStage;
    public static void start(Stage stage){
        // TODO: Replace with WelcomeView

        baseStage = stage;
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Label server = new Label();
        Label client = new Label();
        Label title = new Label("NAVIATO");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Z003", 32));
        title.setAlignment(Pos.CENTER);


        server.setId("rndBtn");
        server.setText("Host");
        server.setOnMouseClicked((e) -> {
            baseStage.setScene(new ServerView().serverScene());
        });

        client.setId("playBtn");
        client.setText("Join");
        client.setOnMouseClicked((e) ->{
            new ClientView().start(baseStage);
        });

        hBox.setAlignment(Pos.CENTER);
        hBox.setId("btnBox");
        hBox.getChildren().addAll(server, client);
        hBox.setSpacing(15);

        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setId("basePane");
        vBox.getChildren().addAll(title, hBox);

        Scene scene = new Scene(vBox);
        stage.setWidth(700);
        stage.setHeight(800);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);

        stage.show();
    }

    public static void planView(){
        new BattlePlanView().start(baseStage);
    }
}
