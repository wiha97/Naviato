package managers;

import models.GameBoard;
import models.Square;
import util.Print;

import java.util.*;

// WH
public class AIManager {
    private static GameBoard board = GameManager.getTargetBoard();
    private static Queue<Square> targets = new LinkedList<>();
    private static boolean isVert = false;
    private static boolean isHori = false;

    public static void getPossibleTargets(int i) {
        clearTargets();

        if (!isVert) {
            if (i % 10 != 0) // Checks left
                if (validateHori(i - 1))
                    isHori = true;
            if ((i + 1) % 10 != 0) // Checks right
                if (validateHori(i + 1))
                    isHori = true;
        }
        if (!isHori) {
            if (i > 9)   // Checks above
                if (validateVert(i - 10))
                    isVert = true;
            if (i < board.getSquares().length - 10) // Checks below
                if (validateVert(i + 10))
                    isVert = true;
        }
    }

    public static void clearTargets() {
        while (!targets.isEmpty())
            targets.poll().setTarget(false);
    }

    public static void resetTargets(){
        clearTargets();
        isVert = false;
        isHori = false;
    }

    private static boolean validateVert(int i) {
        Square square = board.getSquares()[i];
        if (square.isHit()) {
            isVert = true;
//            if (i - 10 > 0) {
////                validateVert(i - 10);
//                square = board.getSquares()[i - 10];
//                if (!square.isHit())
//                    addTarget(square);
//            } else if (i + 10 < board.getSquares().length) {
////                validateVert(i + 10);
//                square = board.getSquares()[i + 10];
//                if (!square.isHit())
//                    addTarget(square);
//            }
            return true;
        }
        if (!square.isMiss())
            addTarget(square);
        return false;
    }

    private static boolean validateHori(int i) {
        Square square = board.getSquares()[i];
        if (square.isHit()) {
            isHori = true;
            return true;
        }
        if (!square.isMiss())
            addTarget(square);
        return false;
    }

    private static void addTarget(Square square) {
        if (!square.isHit()) {
            square.setTarget(true);
            targets.add(square);
        }
    }

    public static Square calculateShot() {
        Square square = targets.poll();
        square.setTarget(false);
        return square;
    }

    public static Queue<Square> getTargets() {
        return targets;
    }
}
