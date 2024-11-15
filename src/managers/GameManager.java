package managers;

import models.GameBoard;
import models.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private GameBoard gameBoard;
    private  List<Square>availableSquares;
    private  Random random;

    public GameManager() {
        this.gameBoard = new GameBoard();
        this.gameBoard.generateField();
        this.availableSquares = new ArrayList<>(List.of(gameBoard.getSquares()));
        this.random = new Random();
    }


    public String gameMessage(String incomingShot) {
    String[] inputToArray = incomingShot.split(" ");
    String shotCoordinate = inputToArray[2];
    System.out.println(shotCoordinate);
    Square checkShip = checkSquare(shotCoordinate);;
    String reply;
    String si = "i shot " + randomCoordinate(); //init
    String s1 = "h shot " + randomCoordinate(); //hit+koordinat
    String s2 = "m shot " + randomCoordinate(); //miss
    String s3 = "game over"; //Looooser!
    String s4 = "s shot " + randomCoordinate(); //Sänkt + koordinat

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

    public String randomCoordinate() {
        if(availableSquares.isEmpty()){
            return "No more coordinates";
        }

        int randomIndex = random.nextInt(availableSquares.size());
        Square randomSquare = availableSquares.get(randomIndex);
        availableSquares.remove(randomIndex);
        return randomSquare.getCoordinate().toString();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
    private Square checkSquare (String shotCoordinate) {
        for (Square square : gameBoard.getSquares()) {
            if (square.getCoordinate().equals(shotCoordinate)) {
                return square;
            }
        }
        return null;
    }

    private boolean gameOver(){
        for (Square square : gameBoard.getSquares()){
            if (square.getShip() != null && !square.getShip().isSunk()) {
                return false;
            }
        }
        return true;
    }
    public String firstShot() {
        return "i shot " + randomCoordinate();
    }




}
