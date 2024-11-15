package models;


public class Square {
    private Ship ship;
    private boolean hit;
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
        }
    }

    public String hitSquare(){
        if(ship != null) {
            ship.damage();
            return "Hit ["+coordinate+"]";
        }
        return "Miss ["+coordinate+"]";
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
