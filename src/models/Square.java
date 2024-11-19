package models;


public class Square {
    private Ship ship;
    private boolean hit;
    private boolean target;
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

    public boolean hitSquare(){
        hit = true;
        if(ship != null) {
            ship.damage();
            return true;
        }
        return false;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isTarget() {
        return target;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }
}
