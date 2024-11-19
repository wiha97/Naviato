package managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.GameBoard;
import models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private static GameBoard gameBoard = new GameBoard();
    private static List<Square> availableSquares;
    private static Random random = new Random();
    private static List<Integer> hitSquares = new ArrayList<>();
    private static ObservableList<String> logList = FXCollections.observableList(new ArrayList<>());

//    public static  GameManager() {
//        this.gameBoard = new GameBoard();
//        this.gameBoard.generateField();
//        this.gameBoard.getSquares();
//        this.availableSquares = new ArrayList<>(List.of(gameBoard.getSquares()));
//        this.random = new Random();
//
//    }

    // JJ & FP
    public static String gameMessage(String incomingShot) {
//        String shotCoordinate = incomingShot.substring(7).trim();
        String[] msg = incomingShot.split(" ");
        if(msg.length == 1) {
            String shotCoordinate = msg[0];
            Square checkShip = checkSquare(shotCoordinate);

            System.out.println(shotCoordinate);
            if (checkShip.getShip() != null) {
                checkShip.setHit(true);

                if (checkShip.getShip().isSunk()) {
                    if (gameOver()) {
                        return "game over";
                    } else {
                        return "s shot " + shotCoordinate;
                    }
                } else {
                    return "h shot " + shotCoordinate;
                }
            }
            if (checkShip != null && checkShip.getShip() == null) {
                return "m shot " + shotCoordinate;
            }
        }   else{
            switch (msg[0]){
                case "i":
                    break;
                case "h":
                    break;
                case "m":
                    break;
                case "s":
                    break;
                default:
                    break;
            }
            return randomCoordinate();
            // Update board
        }
        return "No more coordinates";


    }

    public static  String randomCoordinate() {
        if(hitSquares.size() == gameBoard.getSquares().length){
            return "No more coordinates";
        }

        int randomIndex = random.nextInt(availableSquares.size());
        while (hitSquares.contains(randomIndex)) {
            randomIndex = random.nextInt(availableSquares.size());
        }

        Square randomSquare = availableSquares.get(randomIndex);
//        availableSquares.remove(randomIndex);
        hitSquares.add(randomIndex);

        return randomSquare.getCoordinate().toLowerCase();
    }

    public static GameBoard getGameBoard() {
        return gameBoard;
    }

    private static Square checkSquare(String shotCoordinate) {
        for (Square square : gameBoard.getSquares()) {
            if (square.getCoordinate().toLowerCase().equals(shotCoordinate)) {
                return square;
            }
        }
        return null;
    }

    private static boolean gameOver() {
        for (Square square : gameBoard.getSquares()) {
            if (square.getShip() != null && !square.getShip().isSunk()) {
                return false;
            }
        }
        return true;
    }

    public static String firstShot() {
        return "i shot " + randomCoordinate();
    }

    public static void setAvailableSquares(List<Square> availableSquares) {
        GameManager.availableSquares = availableSquares;
    }

    public static ObservableList<String> getLogList() {
        return logList;
    }
}
