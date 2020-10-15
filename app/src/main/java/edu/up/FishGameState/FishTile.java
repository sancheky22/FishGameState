package edu.up.FishGameState;


// Tile Class (Triple array of tiles is the board)
public class FishTile {
    /**
     * External Citation
     * Date: October 12, 2020
     * Problem: wanted to index a hexagonal tiling in a clever way.
     * Resource: https://www.redblobgames.com/grids/hexagons/ ("Map storage in axial coordinates")
     **/


    /**
     * Because there are three different independent directions player can move, we needed a third way to check if they
     * were moving in a straight line. So the solution to this is to just model it cleverly as a 2d array by adding some offsets.
     * The above resource explains it well, but basically it makes it very easy to check if you are moving in a striaght line.
     *
     */

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

    public boolean getExists(){
        return this.exists;
    }

    public boolean hasPenguin(){
        return hasPenguin;
    }

    public int getValue(){
        return this.value;
    }

    public void setExists(boolean b){
        this.exists = b;
    }

}
