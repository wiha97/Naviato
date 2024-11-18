package managers;

import models.GameBoard;
import models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private static GameBoard gameBoard = new GameBoard();
    private static  List<Square>availableSquares;
    private static  Random random;

//    public static  GameManager() {
//        this.gameBoard = new GameBoard();
//        this.gameBoard.generateField();
//        this.gameBoard.getSquares();
//        this.availableSquares = new ArrayList<>(List.of(gameBoard.getSquares()));
//        this.random = new Random();
//
//    }

// JJ & FP
    public static  String gameMessage(String incomingShot) {
        String shotCoordinate = incomingShot.substring(7).trim();
    System.out.println(shotCoordinate);
    Square checkShip = checkSquare(shotCoordinate);;

    if (checkShip.getShip() != null) {
        checkShip.setHit(true);

        if (checkShip.getShip().isSunk()) {
            if (gameOver()){
                return "game over";
            }else{
                return "s shot " + randomCoordinate();
            }
        } else {
            return "h shot " + randomCoordinate();
            }
        }
    if (checkShip != null && checkShip.getShip() == null) {
        return "m shot "+randomCoordinate();
    }
    return "No more coordinates";


}

    public static  String randomCoordinate() {
        if(availableSquares.isEmpty()){
            return "No more coordinates";
        }

        int randomIndex = random.nextInt(availableSquares.size());
        Square randomSquare = availableSquares.get(randomIndex);
        availableSquares.remove(randomIndex);

        return randomSquare.getCoordinate().toLowerCase();
    }

    public static  GameBoard getGameBoard() {
        return gameBoard;
    }
    private static Square checkSquare (String shotCoordinate) {
        for (Square square : gameBoard.getSquares()) {
            if (square.getCoordinate().toLowerCase().equals(shotCoordinate)) {
                return square;
            }
        }
        return null;
    }

    private static boolean gameOver(){
        for (Square square : gameBoard.getSquares()){
            if (square.getShip() != null && !square.getShip().isSunk()) {
                return false;
            }
        }
        return true;
    }
    public static  String firstShot() {
        return "i shot " + randomCoordinate();
    }




}
