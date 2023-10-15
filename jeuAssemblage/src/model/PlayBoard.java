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

    public static final String PATH_IMG = "resources/run/";

    private final Controller controller;

    private ArrayList<Piece> alPieceOnBoard;
    private HashMap<Integer, Image> hmPieceImage;
    private int[][]          playBoard;


    public PlayBoard() {
        this.controller = Controller.getInstance();

        this.hmPieceImage = new HashMap<Integer, Image>();
        this.alPieceOnBoard = null;
        this.playBoard = null;
    }

    public void initSizePB(int height, int width) {
        this.playBoard = createPlayBoard(height, width);
        this.addPieceOnBoard(this.alPieceOnBoard.get(0), 0, 0);
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
            }
        }
        return tempAl;
    }
    
    private void createPieceImage() {
        for (Piece p : this.alPieceOnBoard) {
            BufferedImage image = PieceRenderUtils.createPieceImage(p.getBounds());

            try {
                File output = new File(PATH_IMG + "/" + p.getInstanceId() + ".png");
                output.createNewFile();
                ImageIO.write(image, "png", output);
                this.hmPieceImage.put(p.getInstanceId(), ImageIO.read(output));
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    // Getters
    public int[][]          getPlayBoard () { return this.playBoard; }
    public ArrayList<Piece> getPieces    () { return this.alPieceOnBoard; }

    public Image getImageById (int id) { return this.hmPieceImage.get(id); }
    public Piece getPieceById (int id) { return this.alPieceOnBoard.get(id-1);}
    
    /**
    * bla bla 
    * <p>
    * bla bla
    *
    * @param  p     is a Piece that is placed on the Board
    * @see          Piece
    */
    public void addPieceOnBoard(Piece p, int x, int y) {

        int[][] bounds = p.getBounds();
        int k = 0;
        int l = 0;
        for (int i = x; i < x + bounds.length; i++) {
            l = 0;
            for (int j = y; j < y + bounds[i].length; j++) {
                this.playBoard[i][j] = bounds[k][l] * (p.getInstanceId() + 1);
                l++;
            }
            k++;
        }

        p.setX(x);
        p.setY(y);

    }

    /**
    * @param  x         is int of top left matrices
    * @param  y         is int of top left matrices
    * @param  bounds    is int[][] and represent the shape of a piece
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
    
    public static void printMatrice(int[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            System.out.println();
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
        }
    }

    public static void cleaningRunSpace() {
        File dir = new File(PATH_IMG);

        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}
