package models;


public class Square {
    private Ship ship;
    private boolean hit;
    private boolean miss;
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
        if(ship != null) {
            ship.damage();
            hit = true;
            return true;
        }
        miss = true;
        return false;
    }

    public void hitSquare(boolean hit){
        if(hit)
            this.hit = true;
        else
            this.miss = true;
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

    public boolean isMiss() {
        return miss;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }
}
