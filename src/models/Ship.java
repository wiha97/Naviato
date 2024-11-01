package models;

import java.awt.*;
import java.util.Arrays;

public class Ship {
    private int health;
    private ShipClass shipClass;
    private Point position;

    public Ship(int type){
        shipClass = ShipClass.getShipClass(type);
        health = type;
    }

    public ShipClass getShipClass(){
        return shipClass;
    }

    private enum ShipClass {
        CARRIER(5),
        CRUISER(4),
        DESTROYER(3),
        SUBMARINE(2);

        private int size;

        ShipClass(int size){
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public static ShipClass getShipClass(int type){
//            for (ShipClass c : ShipClass.values()){
//                if(c.size == type)
//                    return c;
//            }
            Arrays.stream(ShipClass.values()).filter(s -> s.size == type).findFirst();
            return null;
        }
    }
}
