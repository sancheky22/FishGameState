package edu.up.FishGameState;

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

    //store the current players turn (0,1,2,3)
    private int playerTurn;

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
        this.player1Score = 0;
        this.player2Score = 0;
        this.player3Score = 0;
        this.player4Score = 0;
        this.gamePhase = 0;
        this.validMoves = true;
        this.boardState = initializeBoard();
        this.pieceArray = intializePieces();
    }

    // copy constructor. Copies values from o to a new instance of the game state
    public FishGameState(FishGameState o){
        this.playerTurn = o.playerTurn;
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
        return "";
    }

    /**
     action methods will go underneath this comment.
     */
    public boolean placePenguin(){
        return false;
    }

    public boolean movePenguin(){
        return false;
    }

    //Helper methods to initialize the board state
    private FishTile[][] initializeBoard(){
        FishTile[][] f = new FishTile[][]{};


        return f;
    }

    private FishPenguin[][] intializePieces(){
        FishPenguin[][] p = new FishPenguin[][]{};

        return p;
    }

    /**
     * Getter methods for instance variables
     */

    public int getPlayerTurn() {
        return this.playerTurn;
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
}