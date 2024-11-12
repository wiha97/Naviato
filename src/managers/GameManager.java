package managers;

import models.GameBoard;
import models.Square;

import java.util.Random;

public class GameManager {



    static GameBoard gameBoard = new GameBoard();

//Metod för att slumpa fram koordinater

    public static String printRandomCoordinate() {
    Random random = new Random();
    int remaining = gameBoard.getSquares().length;

    Square[] squares = gameBoard.getSquares();



    while (remaining > 0) {
        int randomIndex = random.nextInt(squares.length);

        if (squares[randomIndex] != null) {
            Square randomSquare = squares[randomIndex];
            System.out.println(randomSquare);

            squares[randomIndex] = null;
            return randomSquare.toString();
        }
        remaining--;
    }
    return "No more coordinates";
}
    public static GameBoard getGameBoard() {
        return gameBoard;
    }
}