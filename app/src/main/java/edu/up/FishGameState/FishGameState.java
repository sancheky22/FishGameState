package edu.up.FishGameState;

import android.util.Log;

import java.lang.Integer;
import static java.lang.Math.*;

/**
 * @author Kyle Sanchez
 * @author Ryan Enslow
 * @author Carina Pineda
 * @author Linda Nguyen
 **/


public class FishGameState {

    /** POSSIBLE INSTANCE VARS
     private int playerTurn;
     private int player1Score;
     private int player2Score;
     private int player3Score;
     private int player4Score;



     int for phase of game
        * 0 placement phase
        * 1 actual play
        * 2 Avengers: EndGame
     boolean validmoves
     **/

    final int BOARD_HEIGHT = 8;
    final int BOARD_LENGTH = 11;

    //store the current players turn (0,1,2,3)
    private int playerTurn;

    // number of players playing the game
    private int numPlayers;

    //store scores for all players
    //Note: if the game has fewer than 4 players, then the non-existent player scores will stay at 0
    private int player1Score;
    private int player2Score;
    private int player3Score;
    private int player4Score;
    //Stores the phase of the game (0: placement phase, 1: main game, 2: End game screen)
    private int gamePhase;
    //Check if there is still at least one valid move on the board.
    private boolean validMoves;

    //FishTile[][] is a 2d array that stores the location of the hexagons
    private FishTile[][] boardState;
    //FishPenguin[][] is a 2d array to stores each player's penguins.
    private FishPenguin[][] pieceArray;


    // Default constructor
    public FishGameState(){
        this.playerTurn = 0;
        this.numPlayers = 0;
        this.player1Score = 0;
        this.player2Score = 0;
        this.player3Score = 0;
        this.player4Score = 0;
        this.gamePhase = 0;
        this.validMoves = true;
        this.boardState = initializeBoard();
        this.pieceArray = null;
                //initializePieces();
    }

    // copy constructor. Copies values from o to a new instance of the game state
    public FishGameState(FishGameState o){
        this.playerTurn = o.getPlayerTurn();
        this.numPlayers = o.getNumPlayers();
        this.player1Score = o.getPlayer1Score();
        this.player2Score = o.getPlayer2Score();
        this.player3Score = o.getPlayer3Score();
        this.player4Score = o.getPlayer4Score();
        this.gamePhase = o.getGamePhase();
        this.validMoves = o.getValidMoves();
        this.boardState = o.getBoardState();
        this.pieceArray = o.getPieceArray();
    }

    @Override
    public String toString(){
        Log.d("toString","\n");
        return "Player Turn: " + this.playerTurn + "\n" +
                "Number of Players: " + this.numPlayers + "\n" +
                "Player 1 Score: " + this.player1Score + "\n" +
                "Player 2 Score: " + this.player2Score + "\n" +
                "Player 3 Score: " + this.player3Score + "\n" +
                "Player 4 Score: " + this.player4Score + "\n" +
                "Current Phase of the Game: " + this.gamePhase + "\n" +
                "Are there valid moves left:" + this.validMoves + "\n";

    }

    /**
     action methods will go underneath this comment.
     */

    //Action: When the player moves a penguin onto the board at the beginning of the game.
    public boolean placePenguin(FishPenguin p, int x, int y) {
        if (p.getOnBoard()){
            return false;
        }
        else {
            p.setxPos(x);
            p.setyPos(y);
            return true;
        }
    }

    //Action: When the player moves a penguin p to the coordinate (x,y) in the hex board.
    public boolean movePenguin(FishPenguin p, int x, int y){
        //Make sure the penguin is not moving to the same tile
        if(p.getX() == x && p.getY() == y){
            return false;
        }
        //Make sure that the space you are moving to exists (might be redundant later im not sure)
        if (!this.boardState[x][y].getExists()){
            return false;
        }
        //0 means horizontal, 1 means down right diag, 2 means up right diag
        int direction;
        if (p.getY() == y){
            direction = 0;
        }
        else if (p.getX() == x){
            direction = 1;
        }
        else if (p.getY()+p.getX() == x+y){
            direction = 2;
        }
        else{
            return false;
        }

        //TODO make sure that the action is a legal move
        //If the new move is horizontal
        if (direction == 0){
            //s is the sign of (new coordinate - old coordinate)
            //if s is positive, then you are moving to the right
            int s = Integer.signum(x-p.getX());
            for (int i = p.getX()+s; i == x; i+=s){
                if (boardState[i][p.getY()].isHasPenguin() || !boardState[i][p.getY()].getExists()){
                    return false;
                }
            }
            return true;
        }
        //If the new move is vertical (down right diag)
        else if (direction == 1){
            int s = Integer.signum(y-p.getY());
            for (int i = p.getY()+s; i == y; i+=s){
                if (boardState[p.getX()][i].isHasPenguin() || !boardState[p.getX()][i].getExists()){
                    return false;
                }
            }
            return true;
        }
        //If the new move is up right diag
        else {
            int s = Integer.signum((y-x) - (p.getY()-p.getX()));
            for (int i = 0; i == abs(x-p.getX()); i++){
                if (boardState[p.getX()+i][p.getY()+i].isHasPenguin() || !boardState[p.getX()+i][p.getY()+i].getExists()){
                    return false;
                }
            }
            return true;
        }
    }

    /** so basically the way to board works is that you construct a 2d array but you add an offset after two rows.
    *      * It looks like this:
    *      * x x 0 0 0 0
    *      * x x 0 0 0 0
    *      * x 0 0 0 0 x
    *      * x 0 0 0 0 x
    *      * 0 0 0 0 x x
    *      * 0 0 0 0 x x
    *      * where the x's represent null cells in the array
    *      *
    *      * Each hexagon is connected to 6 other hexagons. In this array a hexagons neighbors look like this:
    *      *
    *      * x 0 0
    *      * 0 H 0
    *      * 0 0 x
    *      *
    *      * With this arrangement, increments along the columns correspond to movements down to the right along a straight line in the tiling.
    *      * To find straight lines along the other axis, you just go diagonally up to the left.
    *      * This makes it very easy to find out if a path traced along the array is in a straight line or not.
    *      *
    *      * The nice thing about Hey That's My Fish is that the board size is constant: 4 lines of 8 hexes and 4 lines of 7 hexes alternating
     *     */
    private FishTile[][] initializeBoard(){
        int n;
        int c;
        FishTile t;
        FishTile[][] f = new FishTile[BOARD_HEIGHT][BOARD_LENGTH];
        for (int i = 0; i <= BOARD_HEIGHT-1;i++)
        {
            //(i,n): (1,3), (2,3), (3,2), (4,2), (5,1)....
            n = 4-((i+1)+(i+1)%2)/2;
            c = 0;
            for (int j = 0; j <= BOARD_LENGTH-1;j++)
            {
                if (n!=0 || c == (8 - i%2)) {
                    t = null;
                    n--;
                }
                else {
                    t = new FishTile(i, j);
                    c++;
                }
                f[i][j] = t;
            }
        }
        return f;
    }

    private FishPenguin[][] initializePieces(){
        FishPenguin[][] p = new FishPenguin[][]{};

        return p;
    }

    /**
     * Getter methods for instance variables
     */

    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public int getNumPlayers() {
        return this.numPlayers;
    }

    public int getPlayer1Score(){
        return this.player1Score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
    }

    public int getPlayer3Score(){
        return this.player3Score;
    }

    public int getPlayer4Score(){
        return this.player4Score;
    }

    public int getGamePhase(){
        return this.gamePhase;
    }

    public boolean getValidMoves(){
        return this.validMoves;
    }

    public FishTile[][] getBoardState(){
        return this.boardState;
    }

    public FishPenguin[][] getPieceArray(){
        return this.pieceArray;
    }

    /**
     * Setter methods for instance variables
     */

    public void setPlayerTurn(int x) {
        this.playerTurn = x;
    }

    public void setNumPlayers(int x) {
       this.numPlayers = x;
    }

    public void setPlayer1Score(int x){
        this.player1Score = x;
    }

    public void setPlayer2Score(int x) {
        this.player2Score = x;
    }

    public void setPlayer3Score(int x){
        this.player3Score = x;
    }

    public void setPlayer4Score(int x){
        this.player4Score = x;
    }

    public void setGamePhase(int x){
        this.gamePhase = x;
    }

    public void getValidMoves(boolean x){
        this.validMoves = x;
    }

    /*
    public void setBoardState(int x, int y){
        this.boardState = FishTile[y][x];
    }

    public void setPieceArray(){
        this.pieceArray = FishPenguin[][];
    }*/
}