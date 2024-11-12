package models;


public class Square {
    private Ship ship;
    private boolean hit;
    private String color;
    private String coordinate;

    public Square(String xy){
        coordinate = xy;
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

    public String hitSquare(){
        if(ship != null) {
            ship.damage();
            color = "red";
            return "Hit ["+coordinate+"]";
        }
        return "Miss ["+coordinate+"]";
    }

    public String getColor() {
        return color;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        color = ship.getColor();
    }
}
