package edu.up.FishGameState;


public class FishPenguin {

    /**
     * keep track of position x,y,z
     * if they can make a valid move (boolean) loop through array to check if each piece has valid move if not
        return false and EndGame
     **/
    private int player;
    private int xPos;
    private int yPos;
    private boolean onBoard;
    private boolean legalMoves;

    //Constructor for a penguin piece. Needs a player who placed it and the location it was placed in.
    public FishPenguin(int playerNumber, int x, int y){
        this.player = playerNumber;
        this.xPos = x;
        this.yPos = y;
        this.onBoard = false;
        this.legalMoves = true;
    }

    public int getX(){
        return this.xPos;
    }

    public int getY(){
        return this.yPos;
    }

    public boolean getOnBoard(){
        return this.onBoard;
    }

    public void setxPos(int x){
        this.xPos = x;
    }

    public void setyPos(int y){
        this.yPos = y;
    }
}
