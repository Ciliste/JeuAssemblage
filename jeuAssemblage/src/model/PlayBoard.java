package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import main.Controller;
import pieces.Piece;
import pieces.PieceColor;
import pieces.PieceFactory;
import view.utils.PieceRenderUtils;

import javax.imageio.ImageIO;

public class PlayBoard {

    private static final String PATH_IMG = "resources/run/";

    private final Controller controller;

    private ArrayList<PieceColor> alPieceOnBoard;
    private int[][]          playBoard;


    public PlayBoard() {
        this.controller = Controller.getInstance();

        this.alPieceOnBoard = null;
        this.playBoard = null;
    }

    public void initSizePB(int height, int width) {
        this.playBoard = createPlayBoard(height, width);
    }

    public void initNumberPiece(int numberPiece) {
        this.alPieceOnBoard = createPieces(numberPiece);
        createImageForPiece();
    }

    private ArrayList<PieceColor> createPieces(int numberPiece) {
        ArrayList<PieceColor> tempAl = new ArrayList<PieceColor>();
        int samePieceLimit = (int) Math.ceil(numberPiece/(PieceFactory.NUMBER_PIECE*1d));

        while ( tempAl.size() < numberPiece) {
			
            Piece p = PieceFactory.createPiece();

            if ( PlayBoard.checkPiece(p, tempAl, samePieceLimit) ) {
                tempAl.add(new PieceColor(p, PieceRenderUtils.getRandomColor()));
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

    private void createImageForPiece() {

        for( int i = 0; i < this.alPieceOnBoard.size(); i++) {
            PieceColor pc = this.alPieceOnBoard.get(i);

            BufferedImage image = PieceRenderUtils.createPieceImage(pc.piece.getBounds(), pc.color);

            try {
                File output = new File(PATH_IMG + i + ".png");
                output.createNewFile();
                ImageIO.write(image, "png", output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public int[][]               getPlayBoard   () { return this.playBoard; }
    public ArrayList<PieceColor> getPieceOnBoard() { return this.alPieceOnBoard; }

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
    public void addPieceOnBoard(PieceColor piece) {

        // OPTIONNEL
        this.controller.canBeAddedToBoard(piece);
        // OPTIONNEL

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
    private static boolean checkPiece( Piece p, ArrayList<PieceColor> alPiece, int samePieceLimit) {
        Class<?> c = p.getClass();
        int numberPieceSameClass = 1;
        for ( PieceColor piece: alPiece) {
            if (piece.piece.getClass() == c) { numberPieceSameClass += 1; }
        }

        return numberPieceSameClass <= samePieceLimit;
    }

    public void cleaningRunSpace() {
        File dir = new File(PATH_IMG);
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
    }
}
