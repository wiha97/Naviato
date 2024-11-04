package models;

public abstract class Ship {
    protected int health;
    protected String name;
    protected int size;
    protected String color;

    public void damage(){
        health--;
        if(health <= 0){

        }
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return size;
    }

    public int getHealth(){
        return health;
    }

    public String getColor() {
        return color;
    }
}
