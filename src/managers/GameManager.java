package managers;

import models.GameBoard;
import models.Square;

import java.util.Random;

public class GameManager {

    static GameBoard gameBoard = new GameBoard();

public static String gameMessage(String incomingShot) {

    String[] inputToArray = incomingShot.split(" ");
    String shotCoordinate = inputToArray[2];
    Square checkShip = checkSquare(shotCoordinate);
    String reply;
    String si = "i shot " + printRandomCoordinate(); //init
    String s1 = "h shot " + printRandomCoordinate(); //hit+koordinat
    String s2 = "m shot " + printRandomCoordinate(); //miss
    String s3 = "game over"; //Looooser!
    String s4 = "s shot " + printRandomCoordinate(); //Sänkt + koordinat

    if (checkShip.getShip() != null) {
        checkShip.setHit(true);

        if (checkShip.getShip().isSunk()) {
            reply = s4;
        } else {
            reply = s1;
            if (gameOver()) {
                reply = s3;
            }
        }

    } else {
        reply = s2;
    }
    return reply;
}

    public static String printRandomCoordinate() {
        Random random = new Random();
        int remaining = gameBoard.getSquares().length;
        Square[] squares = gameBoard.getSquares();

        // Första skottet...?
        while (remaining > 0) {
            int randomIndex = random.nextInt(squares.length);
            Square randomSquare = squares[randomIndex];

            if (squares[randomIndex] != null) {
                squares[randomIndex] = null;
                remaining--;
                return randomSquare.getCoordinate();

            }

        }

        return "No more coordinates available";
    }

    public static GameBoard getGameBoard() {
        return gameBoard;
    }
    private static Square checkSquare (String shotCoordinate) {
        for (Square square : gameBoard.getSquares()) {
            if (square.getCoordinate().equals(shotCoordinate)) {
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
    public static void firstShot(){
        System.out.println();
}
}
