package model;

import java.util.ArrayList;

import main.Controller;
import pieces.Piece;

public class PlayBoard {
    
    private Controller       controller;
    private ArrayList<Piece> alPiece;
    private ArrayList<Piece> alPieceOnBoard;

    private int[][]          playBoard;

    public PlayBoard(int sizeX, int sizeY) {
        this.controller = Controller.getInstance();
        this.playBoard = createPlayBoard(sizeX, sizeY);
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
    
    /**
    * bla bla 
    * <p>
    * bla bla
    *
    * @param  piece is a Piece
    * @return       True if the piece is added on the 
    * @see          Piece
    */
    public void addPieceOnBoard(Piece piece) {
        this.alPieceOnBoard.add(piece);
    }

    /**
    * bla bla 
    * <p>
    * bla bla
    *
    * @param  x         is int of top left matrice
    * @param  y         is int of top left matrice
    * @param  bounds    is int[][] and represent the shape of an piece
    * @return           True if the piece can be added on the board else False
    */
    public boolean canBeAddedToBoard(int x, int y, int[][] bounds) {
        return true;
    }

}
