package views;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import managers.GameManager;
import models.GameBoard;

//    WH
public class BattleView {
    private GameBoard opponentBoard = GameManager.getTargetBoard();
    private GameBoard playerBoard = GameManager.getGameBoard();
    private BoardView playerView = new BoardView(playerBoard, false);
    private BoardView targetView = new BoardView(opponentBoard, true);
    private AnchorPane playPane = playerView.boardPane(30);
    private AnchorPane targetPane = targetView.boardPane(60);


    public void start(Stage stage){
        Scene scene = new Scene(basePane());
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setWidth(1180);
        stage.setHeight(820);
    }

    public void update(){
        playerView.drawBoard();
        targetView.drawBoard();
    }

    private VBox basePane(){
//        opponentBoard.generateField();
        VBox vBox = new VBox();
        vBox.setId("basePane");
        vBox.setAlignment(Pos.TOP_CENTER);
        Label title = new Label("BATTLE");
        title.setId("title");

        VBox listBox = new VBox();
        listBox.getChildren().addAll(SharedViews.shipYard(GameManager.getTargetBoard().getDeployable()));
        listBox.setMinHeight(20);
        listBox.setAlignment(Pos.CENTER);
        listBox.setMaxWidth(400);
        listBox.setId("shipYard");


        HBox content = new HBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(5);

        VBox trgtBox = new VBox();
        trgtBox.setAlignment(Pos.CENTER);
//        trgtBox.getChildren().add(SharedViews.playPane(targetPane));
        trgtBox.getChildren().addAll(targetView.playPane(targetPane), listBox);

        targetView.drawBoard();

        VBox playBox = new VBox();
        playBox.setAlignment(Pos.CENTER);
        playBox.setSpacing(5);
        playBox.getChildren().addAll(playerView.playPane(playPane), SharedViews.logView(300));

        playerView.drawBoard();
        playPane.setMouseTransparent(true);
        playPane.setCursor(Cursor.DEFAULT);

        content.getChildren().addAll(trgtBox, playBox);



        vBox.getChildren().addAll(title, content);
        return vBox;
    }
}
