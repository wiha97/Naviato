package managers;

import models.GameBoard;
import models.Square;
import util.Print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AIManager {
    private static GameBoard board = GameManager.getTargetBoard();
    private static List<Square> targets = new ArrayList<>();

    public static void getPossibleTargets(int i){
        for(Square square : targets){
            square.setTarget(false);
        }
        targets.clear();

        if(i > 9)   // Checks above
            addTarget(board.getSquares()[i-10]);
        if(i % 10 != 0) // Checks left
            addTarget(board.getSquares()[i-1]);
        if((i+1) % 10 != 0) // Checks right
            addTarget(board.getSquares()[i+1]);
        if(i < board.getSquares().length - 10) // Checks below
            addTarget(board.getSquares()[i+10]);
    }

    private static void addTarget(Square square){
        if(!square.isHit()) {
            square.setTarget(true);
            targets.add(square);
        }
    }

    public static void fire(){
        if(!targets.isEmpty()){

        }
    }
}
