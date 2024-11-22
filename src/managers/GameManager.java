package managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.GameBoard;
import models.Square;
import util.Print;
import views.BoardView;

import java.util.*;

public class GameManager {

    private static boolean running = true;
    private static GameBoard gameBoard = new GameBoard();
    private static GameBoard targetBoard = new GameBoard();
    private static BoardView playerView;
    private static BoardView targetView;
    private static List<Square> availableSquares;
    private static Random random = new Random();
    private static List<Integer> hitSquares = new ArrayList<>();
    private static ObservableList<String> logList = FXCollections.observableList(new ArrayList<>());

    public static void start() {
        playerView = new BoardView(gameBoard, false);
        targetView = new BoardView(targetBoard, true);
    }

    // JJ & FP & WH
    public static String gameMessage(String incomingShot) {
//        String shotCoordinate = incomingShot.substring(7).trim();
//        String[] msg = incomingShot.split(" ");
//        if (msg.length == 1) {
        if (incomingShot.startsWith("i"))
            incomingShot = incomingShot.substring(incomingShot.lastIndexOf(' ') + 1);
        String shotCoordinate = incomingShot;
        Square checkShip = checkSquare(shotCoordinate);

        if (checkShip.hitSquare()) {
            if (gameOver()) {
                receiveStatus("s shot " + shotCoordinate);
                return "game over";
            }
            if (checkShip.getShip().isSunk()) {
                return "s shot " + shotCoordinate;
            }
            return "h shot " + shotCoordinate;
        }
        return "m shot " + shotCoordinate;
    }

    //    WH
    public static void receiveStatus(String message) {
        try {
            String[] msg = message.split(" ");
            Square square = Arrays.stream(targetBoard.getSquares()).filter(s -> s.getCoordinate().equals(msg[2])).findFirst().get();
//            square = Arrays.stream(targetBoard.getSquares()).findFirst();
            switch (msg[0]) {
                case "h":
                    square.hitSquare(true);
                    AIManager.getPossibleTargets(Arrays.stream(targetBoard.getSquares()).toList().indexOf(square));
                    break;
                case "m":
                    square.hitSquare(false);
//                AIManager.clearTargets();
                    break;
                case "s":
                    square.hitSquare(true);
                    square.setSunk(true);
                    AIManager.resetTargets();

                    targetBoard.getDeployable().remove(0);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Print.line(e.getMessage());
        }

    }

    //    JJ & WH
    public static String randomCoordinate() {
//        if (hitSquares.size() == gameBoard.getSquares().length) {
//            return "No more coordinates";
//        }

        if (AIManager.getTargets().isEmpty()) {
            AIManager.resetTargets();
            int randomIndex = random.nextInt(100);
            Square square = targetBoard.getSquares()[randomIndex];
            while (square.isHit() || square.isMiss()) {
                randomIndex = random.nextInt(100);
                square = targetBoard.getSquares()[randomIndex];
            }

//            if(square.isHit()){
//                System.out.println("wtf");
//            }

//        availableSquares.remove(randomIndex);

            return square.getCoordinate().toLowerCase();
        }
        Square square = AIManager.calculateShot();
        hitSquares.add(Arrays.stream(targetBoard.getSquares()).toList().indexOf(square));
        return square.getCoordinate();
    }

    public static GameBoard getGameBoard() {
        return gameBoard;
    }

    public static GameBoard getTargetBoard() {
        return targetBoard;
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

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        GameManager.running = running;
    }

    public static BoardView getPlayerView() {
        return playerView;
    }

    public static BoardView getTargetView() {
        return targetView;
    }
}
