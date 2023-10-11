package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

import main.Controller;
import pieces.Piece;
import pieces.PieceFactory;

public class PlayBoard {

    private final Controller controller;

    private ArrayList<Piece> alPiece;
    private ArrayList<Piece> alPieceOnBoard;
    private int[][]          playBoard;


    public PlayBoard() {
        this.controller = Controller.getInstance();

        this.alPieceOnBoard = new ArrayList<Piece>();

        this.playBoard = null;
        this.alPiece   = null;

    }

    public void initSizePB(int height, int width) {
        this.playBoard = createPlayBoard(height, width);
    }

    public void initNumberPiece(int numberPiece) {
        this.alPiece = createPieces(numberPiece);
    }

    private ArrayList<Piece> createPieces(int numberPiece) {
        ArrayList<Piece> tempAl = new ArrayList<Piece>();
        int samePieceLimit = numberPiece/PieceFactory.NUMBER_PIECE;
        
        while ( tempAl.size() < numberPiece) {
            Piece p = PieceFactory.createPiece();

            if ( PlayBoard.checkPiece(p, tempAl, samePieceLimit) ) {
                tempAl.add(p); 
            }
        }

        return tempAl;
    }

    private int[][] createPlayBoard(int height, int width) {
        int[][] tempBoard = new int[height][width];

        for ( int i = 0; i < height; i++) {
            for ( int j = 0; j < width; j++) {
                tempBoard[i][j] = 0;
            }
        }

        return tempBoard;
    }

    // Getters
    public int[][]          getPlayBoards  () { return this.playBoard; }
    public ArrayList<Piece> getPieces      () { return this.alPiece; }
    public ArrayList<Piece> getPieceOnBoard() { return this.alPieceOnBoard; }

    public int getHeight() {return this.playBoard.length; }
    public int getWidth () {return this.playBoard[0].length; }

    
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


    // Static methods
    private static boolean checkPiece( Piece p, ArrayList<Piece> alPiece, int samePieceLimit) {
        Class<?> c = p.getClass();
        int numberPieceSameClass = 1;
        for ( Piece piece: alPiece) {
            if (piece.getClass() == c) { numberPieceSameClass += 1; } 
        }

        return numberPieceSameClass <= samePieceLimit;
    }
}
