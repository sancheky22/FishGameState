package edu.up.FishGameState;

import android.util.Log;

import java.lang.Integer;
import static java.lang.Math.*;
import java.lang.*;

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

    //FishTile[][] is a 2d array that stores the hexagons
    private FishTile[][] boardState;
    //FishPenguin[][] is a 2d array to store all the penguins.
    private FishPenguin[][] pieceArray;



    // Default constructor
    public FishGameState(){
        this.playerTurn = 0;
        //numPlayers is set to 2 for the purpose of this assignment, but in the final project it can be set from 2-4
        this.numPlayers = 2;
        this.player1Score = 0;
        this.player2Score = 0;
        this.player3Score = 0;
        this.player4Score = 0;
        this.gamePhase = 0;
        this.validMoves = true;
        this.boardState = initializeBoard();
        this.pieceArray = initializePieces(this.numPlayers);
    }

    // copy constructor. Copies values from o to a new instance of the game state
    public FishGameState(FishGameState o){
        this.playerTurn = o.playerTurn;
        this.numPlayers = o.numPlayers;
        this.player1Score = o.player1Score;
        this.player2Score = o.player2Score;
        this.player3Score = o.player3Score;
        this.player4Score = o.player4Score;
        this.gamePhase = o.gamePhase;
        this.validMoves = o.validMoves;
        this.boardState = o.boardState;
        this.pieceArray = o.pieceArray;
    }

    @Override
    public String toString(){
        Log.d("toString","\n");
        StringBuilder h = boardStateString();
        return "Player Turn: " + this.playerTurn + "\n" +
                "Number of Players: " + this.numPlayers + "\n" +
                "Player 1 Score: " + this.player1Score + "\n" +
                "Player 2 Score: " + this.player2Score + "\n" +
                "Player 3 Score: " + this.player3Score + "\n" +
                "Player 4 Score: " + this.player4Score + "\n" +
                "Current Phase of the Game: " + this.gamePhase + "\n" +
                "Are there valid moves left:" + this.validMoves + "\n" +
                "This is the hexagon array visualized (T's are tiles and N's are null): " + "\n" + h.toString()+"\n";

    }

    public StringBuilder boardStateString(){
    StringBuilder s = new StringBuilder();

        for(int i=0; i<boardState.length;i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (boardState[i][j] == null) {
                    s.append("N"); //null tile is on the board
                } else {
                    s.append("T"); //tile is present on the board
                }
            }
            s.append("\n");
        }
        s.append("\n This is the piece array visualized (Amount of penguins per player and N's are null): \n");
        for(int k=0; k<pieceArray.length; k++){
            for(int l=0; l<pieceArray[0].length;l++){
                if(pieceArray[k][l] == null){
                    s.append("N"); //null tile is on the board
                }
                else{
                    s.append(this.pieceArray[k][l].getPlayer()); //penguins present
                    }
                }
            s.append("\n");
        }

        return s;

    }

    /**
     Action methods will go underneath this comment.
     */

    //Action: When the player moves a penguin onto the board at the beginning of the game.
    public boolean placePenguin(FishPenguin p, int x, int y) {
        if (p.isOnBoard()){
            return false;
        }
        else {
            p.setXPos(x);
            p.setYPos(y);
            return true;
        }
    }

    //Action: When the player moves a penguin p to the coordinate (x,y) in the hex board.
    //After this action is taken, the original tile needs to be removed, the player's score needs to be updated, and the turn needs to be incremented.
    public boolean movePenguin(FishPenguin p, int x, int y){
        //Make sure the penguin is not moving to the same tile
        if(p.getX() == x && p.getY() == y){
            return false;
        }
        //Make sure that the space you are moving to exists (might be redundant later im not sure)
        if (!this.boardState[x][y].doesExist()){
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
        //If the new move is horizontal
        if (direction == 0){
            //s is the sign of (new coordinate - old coordinate)
            //if s is positive, then you are moving to the right
            int s = Integer.signum(x-p.getX());
            for (int i = p.getX()+s; i == x; i+=s){
                if (this.boardState[i][p.getY()].hasPenguin() || !this.boardState[i][p.getY()].doesExist()){
                    return false;
                }
            }
        }
        //If the new move is vertical (down right diag)
        else if (direction == 1){
            int s = Integer.signum(y-p.getY());
            for (int i = p.getY()+s; i == y; i+=s){
                if (this.boardState[p.getX()][i].hasPenguin() || !this.boardState[p.getX()][i].doesExist()){
                    return false;
                }
            }
        }
        //If the new move is up right diag
        else {
            int s = Integer.signum((y-x) - (p.getY()-p.getX()));
            for (int i = 0; i == abs(x-p.getX()); i++){
                if (this.boardState[p.getX()+i][p.getY()+i].hasPenguin() || !this.boardState[p.getX()+i][p.getY()+i].doesExist()){
                    return false;
                }
            }
        }

        //If the move is legal, then add to the player's score the fish on the tile and remove the tile from the game. Then pass the turn.
        addScore(playerTurn,this.boardState[p.getX()][p.getY()].getValue());
        this.boardState[p.getX()][p.getY()].setExists(false);
        this.playerTurn = (this.playerTurn+1)%this.numPlayers;
        return true;
    }


    //Helper method that is called whenever a player's score needs to be incremented
    //p = player's turn, s = score to be added
    private void addScore(int pT, int s){
        switch(pT){
            case 0:
                setPlayer1Score(getPlayer1Score()+s);
                break;
            case 1:
                setPlayer2Score(getPlayer2Score()+s);
                break;
            case 2:
                setPlayer3Score(getPlayer3Score()+s);
                break;
            case 3:
                setPlayer4Score(getPlayer4Score()+s);
                break;
        }
    }

    /**
     * This is what a 6x4 hexagon array looks like in our 2d array. 0's represent hexes and x's represent null spaces.
     *      x x 0 0 0 0
     *      x x 0 0 0 0
     *      x 0 0 0 0 x
     *      x 0 0 0 0 x
     *      0 0 0 0 x x
     *      0 0 0 0 x x
     *
     * The reason we choose to index them in this way is because we can easily check if a hexagon is adjacent to another hexagon.
     * Each hex has 6 neighbors and they lok like this:
     *
     *  x 0 0
     *  0 h 0
     *  0 0 x
     *
     *  where h's neighbors are the 0's.
     *  a nice consequence of this arrangement is that we can check if two hexagons lie on the same line very easily.
     *  There are three axes that they could lie on: horizontal, upper right, and lower right
     *  These correspond in the 2d array as: same x value, same y value, and same x+y value, respectively.
     *  For example, if we have two hexagons at p1 = (2,4) and p2 = (5,4), then we know those two hexagons are on the same upper right line because y1=y2
     */

    //Helper method to initialize the board at the beginning of the game.
    private FishTile[][] initializeBoard(){
        //n is the number of null cells you need at the beginning of the array
        int n;
        //c is the number of real tiles you have in a specific row. If the row is even, c = 8, if row is odd c = 7
        int c;
        FishTile t;
        FishTile[][] f = new FishTile[BOARD_HEIGHT][BOARD_LENGTH];

        //Loop through a 2d array and initialize each hexagon
        for (int i = 0; i <= BOARD_HEIGHT-1;i++)
        {
            //Basically this value of n is based on i and it tells you how many null cells you need at the start of the row.
            //(i,n): (1,3), (2,3), (3,2), (4,2), (5,1)....
            n = 4-((i+1)+(i+1)%2)/2;
            c = 0;
            for (int j = 0; j <= BOARD_LENGTH-1;j++)
            {
                //if n is not 0, then there are still null cells that must be placed into the array.
                //if i is even then c will hit 8 and then will start inputting null cells to finalize the array.
                //This all results in an array where the null cells are where they need to be, and an extra null cell for odd rows.
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

    /**
     Helper method to initialize the array of penguin pieces that belong to each player. The first coordinate represents
     the player number and the second coordinate represents the index of the penguin.
     The parameter n in this context is the number of players (2-4)
     The rules of the game state that for 2 player games, each player has 4 penguins. 3 players have 3 penguins each, and 4 players have 2 each.
     These numbers are pretty arbitrary but it works out nicely because the follow a simple pattern: PenguinsPerPlayer = 6-numPlayers
     */
    private FishPenguin[][] initializePieces(int n){
        int m = 6-n;
        FishPenguin tempguin;
        FishPenguin[][] p = new FishPenguin[n][m];
        for (int i=0;i<p.length;i++){
            for (int j=0;j<p[0].length;j++){
                tempguin = new FishPenguin(i);
                p[i][j] = tempguin;
            }
        }
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

    public void setValidMoves(boolean x){
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