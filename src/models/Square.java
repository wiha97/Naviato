package models;


public class Square {
    private Ship ship;
    private boolean hit;
    private String color;
    private String coordinate;

    public Square(String xy){
        coordinate = xy;
    }

    public Square(Ship ship){
        this.ship = ship;
        color = ship.getColor();
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        if(ship != null) {
            ship.damage();
            color = "red";
        }
    }

    public String getColor() {
        return color;
    }

    public String getCoordinate() {
        return coordinate;
    }
}
