package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import views.BoardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private int size = 100;
    private Square[] squares;
    private List<Ship> ships = new ArrayList<>();
    private ObservableList<Ship> deployable = FXCollections.observableList(ships);
    private final String chars = "abcdefghij";
    private final char[] chArr = chars.toCharArray();
    private BoardView view;

    public GameBoard() {
        generateField();
    }

    public void generateField() {
        squares = new Square[size];
        ships.clear();
        fillShips();
        int x = 0;
        int y = 0;
        for (int i = 0; i < size; i++) {
            if (i % 10 == 0) {
                if (x != 0)
                    y++;
                x = 0;
            }
            squares[i] = new Square("" + x + chArr[y]);
            x++;
        }
        //  Line removed = error on random placement but works
        //  Line exists  = No errors and works with random placement, but doesn't work with manual
//        deployable = FXCollections.observableList(ships);
    }

    public boolean placeShip(int pos, boolean isVert) {
        if (!deployable.isEmpty()) {
            Ship ship = deployable.get(0);
            if (validate(pos, ship, isVert)) {
                if (isVert)
                    horizontal(pos, ship);
                else
                    vertical(pos, ship);
                deployable.remove(0);
                return true;
            }
        }
        return false;
    }

    public void removeShip(int i) {
        Ship ship = squares[i].getShip();
        for (Square square : Arrays.stream(squares).filter(s -> s.getShip() == ship).toList()) {
            square.setShip(null);
        }
        deployable.add(0,ship);
    }

    public boolean generateShips(Ship ship) {
        int pos = new Random().nextInt(0, size);
        int attempts = 0;
        final int MAX = 1000000;
        boolean isSide = new Random().nextBoolean();

        //  Loops until valid position for the entire ship, gives up after 1 million tries
        while (!validate(pos, ship, isSide) && attempts < MAX) {
            pos = new Random().nextInt(0, size);
            attempts++;
            if (attempts >= MAX)
                return false;
        }
//        return placeShip(pos, isSide);
        if (isSide)
            horizontal(pos, ship);
        else
            vertical(pos, ship);
        return true;
    }

    private void vertical(int pos, Ship ship) {
        int y = pos;
        for (int i = 0; i < ship.getSize(); i++) {
            squares[y].setShip(ship);
            y += 10;
        }
    }

    private void horizontal(int pos, Ship ship) {
        for (int i = pos; i < pos + ship.getSize(); i++) {
            squares[i].setShip(ship);
        }
    }

    private boolean validate(int pos, Ship ship, boolean isSide) {
        if (pos + ship.getSize() > squares.length)
            return false;

        if (isSide) {
            return validateSidePos(pos, ship);
        } else {
            return validateVertical(pos, ship);
        }
//        return true;
    }

    private boolean validateSidePos(int pos, Ship ship) {
        if (pos - 1 >= 0) {
            if (containsShip(pos - 1) && (pos) % 10 != 0 )
                return false;
            if (topBotContainsShip(pos - 1) && (pos) % 10 != 0 )
                return false;
        }
        for (int i = pos; i < pos + ship.getSize(); i++) {
            if (pos != i && i % 10 == 0 && i >= 0 && i != pos + ship.getSize())
                return false;
            if (containsShip(i))
                return false;
            if (topBotContainsShip(i))
                return false;
        }
        int total = pos + ship.getSize();
        if (total < squares.length) {
            if (containsShip(total) && (total) % 10 != 0)
                return false;
            if (topBotContainsShip(total) && (total) % 10 != 0)
                return false;
        }
        return true;
    }

    private boolean validateVertical(int pos, Ship ship) {
        int x = pos;
        if (pos - 10 >= 0) {
            if (containsShip(pos - 10))
                return false;
            if (sideContainsShip(pos - 10))
                return false;
        }
        for (int i = 0; i < ship.getSize(); i++) {
            if (x > squares.length - 1) {
                return false;
            }
            if (containsShip(x))
                return false;
            if (sideContainsShip(x))
                return false;
            x += 10;
        }
        if (x < squares.length)
            if (containsShip(x))
                return false;
        if (sideContainsShip(x))
            return false;

        return true;
    }

    private boolean sideContainsShip(int i) {
        if (i < squares.length) {
            if (i + 1 < squares.length)
                if (containsShip(i + 1) && (i + 1) % 10 != 0)
                    return true;
            if (i - 1 >= 0)
                if (containsShip(i - 1) && i % 10 != 0)
                    return true;
        }
        return false;
    }

    private boolean topBotContainsShip(int i) {
        if (i + 10 < squares.length - 1)
            if (containsShip(i + 10))
                return true;
        if (i - 10 > 0)
            if (containsShip(i - 10))
                return true;
        return false;
    }

    private boolean containsShip(int i) {
        return squares[i].getShip() != null;
    }

    private void fillShips() {
        deployable.add(new Carrier());

        deployable.add(new Cruiser());
        deployable.add(new Cruiser());

        deployable.add(new Destroyer());
        deployable.add(new Destroyer());
        deployable.add(new Destroyer());

        deployable.add(new Submarine());
        deployable.add(new Submarine());
        deployable.add(new Submarine());
        deployable.add(new Submarine());
    }

    public ObservableList<Ship> getDeployable() {
        return deployable;
    }

    public Square[] getSquares() {
        return squares;
    }
}






