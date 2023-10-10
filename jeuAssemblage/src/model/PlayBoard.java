package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

import main.Controller;
import pieces.Piece;
import pieces.PieceFactory;

public class PlayBoard {
    
    public final static int SAME_PIECE_LIMIT = 3;

    private Controller       controller;
    private ArrayList<Piece> alPiece;
    private ArrayList<Piece> alPieceOnBoard;

    private int[][]          playBoard;


    public PlayBoard(int sizeX, int sizeY, int numberPiece) {
        this.controller = Controller.getInstance();

        this.alPieceOnBoard = new ArrayList<Piece>();

        this.playBoard = createPlayBoard(sizeX, sizeY);
        this.alPiece = createPieces(numberPiece);

    }

    private ArrayList<Piece> createPieces(int numberPiece) {
        ArrayList<Piece> tempAl = new ArrayList<Piece>();
        
        while ( tempAl.size() < numberPiece) {
            Piece p = PieceFactory.createPiece();

            if ( PlayBoard.checkPiece(p, tempAl) ) { 
                tempAl.add(p); 
            }
        }

        return tempAl;
    }

    private int[][] createPlayBoard(int sizeX, int sizeY) {
        int[][] tempBoard = new int[sizeX][sizeY];

        for ( int i = 0; i < sizeX; i++) {
            for ( int j = 0; j < sizeY; j++) {
                tempBoard[i][j] = 0;
            }
        }

        return tempBoard;
    }

    //GETTERS
    public int[][]          getPlayBoards  () { return this.playBoard; }
    public ArrayList<Piece> getPieces      () { return this.alPiece; }
    public ArrayList<Piece> getPieceOnBoard() { return this.alPieceOnBoard; }

    
    /**
    * bla bla 
    * <p>
    * bla bla
    *
    * @param  piece is a Piece thath is placed on the Board
    * @see          Piece
    */
    public void addPieceOnBoard(Piece piece) {

        // OPTIONNEL
        this.controller.canBeAddedToBoard(piece);
        // OPTIONNEL

        this.alPiece.remove(piece);
        this.alPieceOnBoard.add(piece);
    }

    /**
    * @param  x         is int of top left matrice
    * @param  y         is int of top left matrice
    * @param  bounds    is int[][] and represent the shape of an piece
    * @return           True if the piece can be added on the board else False
    */
    public boolean canBeAddedToBoard(int x, int y, int[][] bounds) {
        return true;
    }


    // STATIC METHODS
    private static boolean checkPiece( Piece p, ArrayList<Piece> alPiece) {
        Class<?> c = p.getClass();
        int numberPieceSameClass = 1;
        for ( Piece piece: alPiece) {
            if (piece.getClass() == c) { numberPieceSameClass += 1; } 
        }

        return numberPieceSameClass <= PlayBoard.SAME_PIECE_LIMIT;
    }
}
