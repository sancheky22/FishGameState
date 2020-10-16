package edu.up.FishGameState;


public class FishTile {


    private boolean exists;
    private boolean hasPenguin;
    private int value;
    private int x;
    private int y;

    public FishTile(int x, int y){
        this.x = x;
        this.y = y;
        this.exists = true;
        this.hasPenguin = false;
        //needs to be [1-3] not just 3 but we can work on that later
        this.value = 3;
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean doesExist(){
        return this.exists;
    }

    public boolean hasPenguin(){
        return hasPenguin;
    }

    public int getValue(){
        return this.value;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setExists(boolean b){
        this.exists = b;
    }

    public void setHasPenguin(boolean b){
        this.hasPenguin = b;
    }

    public void setValue(int i){
        this.value = i;
    }

}
