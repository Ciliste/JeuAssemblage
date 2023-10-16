package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import main.Controller;
import pieces.Piece;
import pieces.PieceFactory;
import view.utils.PieceRenderUtils;

import javax.imageio.ImageIO;

public class PlayBoard {

    private ArrayList<Piece>        alPieceOnBoard;
    private Piece                   selectedPiece;
    
    private HashMap<Integer, Image> hmPieceImage;
    private int[][]                 playBoard;


    public PlayBoard() {
        this.hmPieceImage = new HashMap<Integer, Image>();
        
        this.alPieceOnBoard = null;
        this.playBoard      = null;
        this.selectedPiece  = null;

    }

    public void initSizePB(int height, int width) {
        this.playBoard = createPlayBoard(height, width);
        placePieceOnBoard();
    }

    public void initNumberPiece(int numberPiece) {
        this.alPieceOnBoard = createPieces(numberPiece);
        this.createPieceImage();
    }

    // Privates
    private ArrayList<Piece> createPieces(int numberPiece) {
        ArrayList<Piece> tempAl = new ArrayList<Piece>();
        int samePieceLimit = (int) Math.ceil(numberPiece / (PieceFactory.NUMBER_PIECE * 1d));
        
        while (tempAl.size() < numberPiece) {

            Piece p = PieceFactory.createPiece();

            if (PlayBoard.checkPiece(p, tempAl, samePieceLimit)) {
                tempAl.add(p);
            } else {
                p.destroy();
            }
        }
        return tempAl;
    }
    
    private void createPieceImage() {
        for (Piece p : this.alPieceOnBoard) {
            BufferedImage image = PieceRenderUtils.createPieceImage(p.getBounds());
            this.hmPieceImage.put(p.getInstanceId(), image);
        }
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

    private void placePieceOnBoard() {
        // TODO : changer ça en vrai fonction qui vérifie les possibilités avant d'ajouter, backtracking ?
        for ( Piece p: this.alPieceOnBoard) {
            int x, y;
            do {
                x = (int) (Math.random() * (this.playBoard[0].length - p.getWidth ()));
                y = (int) (Math.random() * (this.playBoard.length    - p.getHeight()));
            } while( !canBeAddedToBoard(x, y, p.getWidth(), p.getHeight()) );

            addPieceOnBoard(p, x, y);
        }
    }

    // Getters
    public int[][]          getPlayBoard     () { return this.playBoard; }
    public ArrayList<Piece> getPieces        () { return this.alPieceOnBoard; }
    public Piece            getSelectedPiece () { return this.selectedPiece; }

    public Image getImageById (int id) { return this.hmPieceImage.get(id); }
    public Piece getPieceById (int id) { return this.alPieceOnBoard.get(id-1);}
    
    // Setters
    public void setPieceSelected(Piece p) { this.selectedPiece = p; }
    
    /**
    * bla bla 
    * <p>
    * bla bla
    * @param  x     int that represent of top left matrices
    * @param  y     int that represent of top left matrices
    * @see          Piece
    */
    public void addSelectedPiece(int x, int y) {
        if (this.selectedPiece == null) return;

        this.addPieceOnBoard(this.selectedPiece, x, y);
    }

    /**
    * @param  x         int that represent of top left matrices
    * @param  y         int that represent of top left matrices
    * @return           True if the selected piece can be added on the board else False
    */
    public boolean selectedPieceCanBeAddedToBoard(int x, int y) {
        if (this.selectedPiece == null) return false;

        return this.canBeAddedToBoard(x, y, this.selectedPiece.getWidth(), this.selectedPiece.getHeight());
    }

    private void addPieceOnBoard(Piece p, int x, int y) {

        int[][] bounds = p.getBounds();
        int k = 0;
        int l = 0;
        for (int i = y; i < y + p.getHeight(); i++) {
            l = 0;
            for (int j = x; j < x + p.getWidth(); j++) {
                this.playBoard[i][j] = bounds[k][l] * (p.getInstanceId() + 1);
                l++;
            }
            k++;
        }

        p.setX(x);
        p.setY(y);

    }

    private boolean canBeAddedToBoard(int x, int y, int width, int height) {
        if ( x + width  > this.playBoard.length   ) return false;
        if ( y + height > this.playBoard[0].length) return false;

        for (int i = y; i < y + height; i++) {
            for (int j = x; j < x + width; j++) {
                if (this.playBoard[i][j] != 0 ) return false;
            }
        }
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
    
    public static void printMatrice(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            System.out.println();
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
        }
    }
}
