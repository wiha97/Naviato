package models;


public class Square {
    private Ship ship;
    private boolean hit;
    private String color;

    public Square(){

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
    }

    public String getColor() {
        return color;
    }
}
