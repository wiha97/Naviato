package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard {
    private int size = 100;
    private Square[] squares;
    private List<Ship> ships = new ArrayList<>();
    private final String chars = "ABCDEFGHIJ";
    private final char[] chArr = chars.toCharArray();

    public GameBoard() {
        fillShips();
    }

    public void generateField() {
        squares = new Square[size];
        int x = 0;
        int y = 0;
        for (int i = 0; i < size; i++) {
            if (i % 10 == 0) {
                if (x != 0)
                    y++;
                x = 0;
            }
            squares[i] = new Square(""+chArr[x] + y);
            x++;
        }
        for (Ship ship : ships) {
            int pos = new Random().nextInt(-1, size);
            int attempts = 0;
            while (!validate(pos, ship) && attempts < 1000000) {
                pos = new Random().nextInt(-1, size);
                if (pos == 0)
                    System.out.println(pos);
                attempts++;
            }
            if (attempts > 1000000) {
                System.out.println("Attempts full");
                break;
            }
            horizontal(pos, ship);
            System.out.println("" + ship.getName() + " deployed");
        }
    }

    private void vertical(int pos, Ship ship) {

    }
    private void horizontal(int pos, Ship ship) {
        for (int i = pos; i < pos + ship.getSize(); i++) {
            squares[i] = new Square(ship);
        }
    }

    //  TODO: Vertical: +10+10+10+10
    //
    private boolean validate(int pos, Ship ship) {
        if (pos + ship.getSize() > size - 1 || pos < 0)
            return false;

        for (int i = pos; i < pos + ship.getSize(); i++) {
            if ((i + 1) % 10 == 0 && i != 0 && i != pos+ship.getSize()-1)
                return false;
            if (squares[i].getShip() != null)
                return false;
            if (squares[i + 1].getShip() != null)
                return false;
            if (i != 0)
                if (squares[i - 1].getShip() != null)
                    return false;
            if (i > 9)
                if (!shipCheck(i - 10))
                    return false;
            if (i < size - 10)
                if (!shipCheck(i + 10))
                    return false;
        }
        return true;
    }

    private boolean shipCheck(int i) {
        if (squares[i].getShip() != null)
            return false;
        return true;
    }

    private void fillShips() {
        ships.add(new Carrier());

        ships.add(new Cruiser());
        ships.add(new Cruiser());

        ships.add(new Destroyer());
        ships.add(new Destroyer());
        ships.add(new Destroyer());

        ships.add(new Submarine());
        ships.add(new Submarine());
        ships.add(new Submarine());
        ships.add(new Submarine());
    }



    public int getSize() {
        return size;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Square[] getSquares() {
        return squares;
    }
}
