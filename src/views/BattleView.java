package views;

import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import managers.GameManager;
import models.GameBoard;

public class BattleView {
    private GameBoard opponentBoard = new GameBoard();
    private GameBoard playerBoard = GameManager.getGameBoard();
    private BoardView playerView = new BoardView(playerBoard);
    private BoardView targetView = new BoardView(opponentBoard);
    private AnchorPane playPane = playerView.boardPane(30);
    private AnchorPane targetPane = targetView.boardPane(60);


    public void start(Stage stage){
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
    }

    private VBox basePane(){
        opponentBoard.generateField();
        VBox vBox = new VBox();
        vBox.setId("basePane");
        vBox.setAlignment(Pos.TOP_CENTER);
        Label title = new Label("BATTLE");
        title.setId("title");

        VBox listBox = new VBox();
        listBox.getChildren().addAll(SharedViews.shipYard(opponentBoard.getDeployable()));
        listBox.setMinHeight(20);
        listBox.setAlignment(Pos.CENTER);
        listBox.setMaxWidth(400);
        listBox.setId("shipYard");
//        listBox.setStyle("-fx-background-color:#272727;");


        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        VBox trgtBox = new VBox();
        trgtBox.setAlignment(Pos.CENTER);
//        trgtBox.getChildren().add(SharedViews.playPane(targetPane));
        trgtBox.getChildren().addAll(targetView.playPane(targetPane), listBox);
        targetView.drawBoard();

        VBox playBox = new VBox();
        playBox.setAlignment(Pos.TOP_CENTER);

        VBox logBox = new VBox();
        logBox.setPrefHeight(300);
        logBox.setPrefWidth(300);
        logBox.setId("shipYard");
        logBox.setPadding(new Insets(20));
        opponentBoard.getLogList().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                logBox.getChildren().clear();
                for(String log : change.getList()){
                    Label label = new Label(log);
                    label.setId("txt");
                    logBox.getChildren().add(label);
                }
            }
        });

        //        playBox.getChildren().add(SharedViews.playPane(playPane));
        playBox.getChildren().addAll(playerView.playPane(playPane), logBox);
        playerView.drawBoard();
        playPane.setMouseTransparent(true);
        playPane.setCursor(Cursor.DEFAULT);


        content.getChildren().addAll(trgtBox, playBox);



        vBox.getChildren().addAll(title, content);
        return vBox;
    }
}
