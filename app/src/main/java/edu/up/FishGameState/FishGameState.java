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
        this.playerTurn = o.getPlayerTurn();
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
        return "";
    }

    /**
     action methods will go underneath this comment.
     */
    public boolean placePenguin(FishPenguin p, int x, int y){
        if (p.getOnBoard()){
            return false;
        }
        else{
            p.setxPos(x);
            p.setyPos(y);
            return true;
        }
    }

    public boolean movePenguin(FishPenguin p, int x, int y){
        //0 means horizontal, 1 means down right diag, 2 means up right diag
        int direction;
        if (p.getX() == x){
            direction = 1;
        }
        else if (p.getY() == y){
            direction = 0;
        }
        else if (p.getY()+p.getX() == x+y){
            direction = 2;
        }
        else{
            return false;
        }

        if (direction == 0){
            for (int i = p.getX()+1; i < x; i++){
                if (boardState[i][p.getY()].isHasPenguin() || !boardState[i][p.getY()].getExists()){
                    return false;
                }
            }
        }

        if (!this.boardState[x][y].getExists()){
            return false;
        }



        return false;
    }

    /**
     * so basically the way to board works is that you construct a 2d array but you add an offset after two rows.
     *      * It looks like this:
     *      * x x 0 0 0 0
     *      * x x 0 0 0 0
     *      * x 0 0 0 0 x
     *      * x 0 0 0 0 x
     *      * 0 0 0 0 x x
     *      * 0 0 0 0 x x
     *      * where the x's represent null cells in the array.
     *      *
     *      * Each hexagon is connected to 6 other hexagons. In this array a hexagons neighbors look like this:
     *      *
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
     *      * So we know exactly which spaces need to be null. Thus we have an array of
     */
    private FishTile[][] initializeBoard(){
        FishTile[][] f = new FishTile[8][12];
        for (int i = 0; i < 7;i++)
        {
            //if i is even, then j starts at 0. This means that for even rows, there will be 8 iterations and 7 for odd loops.
            for (int j = i%2; j < 11;i++)
            {
                f[i][j-(i/2)] = new FishTile(i,j);
            }
        }

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