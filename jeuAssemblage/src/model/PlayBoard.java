package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import pieces.Piece;
import pieces.PieceFactory;
import view.utils.PieceRenderUtils;

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
        this.placePieceOnBoard();
    }

    public void initNumberPiece(int numberPiece) {
        this.alPieceOnBoard = createPieces(numberPiece);
        this.createPieceImage();
    }

    // Getters
    public int[][]          getPlayBoard     () { return this.playBoard; }
    public ArrayList<Piece> getPieces        () { return this.alPieceOnBoard; }
    public Piece            getSelectedPiece () { return this.selectedPiece; }

    public Image getImageById (int id) { return this.hmPieceImage.get(id); }
    public Piece getPieceById (int id) { return this.alPieceOnBoard.get(id-1);}
    
    // Setters
    public void setPieceSelected(Piece p) { 

		if (p == null) {
			this.selectedPiece = null;
			return;
		}
		
		this.selectedPiece = (Piece) p.clone(); 
	}
    
    // Public 
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

		int index = this.alPieceOnBoard.indexOf(this.getPieceById(this.selectedPiece.getInstanceId()));
        this.alPieceOnBoard.remove(this.getPieceById(this.selectedPiece.getInstanceId()));
		this.alPieceOnBoard.add(index, this.selectedPiece);
        this.selectedPiece = null;

		printMatrice(playBoard);
    }

	public void removeSelectedPiece() {

		if (this.selectedPiece == null) return;

		this.removePieceOnBoard(this.selectedPiece);

		this.alPieceOnBoard.add(this.getPieceById(this.selectedPiece.getInstanceId()));
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

    /**
    * bla bla 
    *
    * @return   a int[], last one is a int whose the number of piece cube in the area and the 4 others 
    *           are representation of an rectangle,  contains respectively x, y, width, height 
    */
    public int[] rectangleArea() {
        int[] temp = this.getArea();
        int[] ret = new int[temp.length + 1];
        for (int i = 0; i < temp.length; i++) {
            ret[i] = temp[i];
        }
        ret[4] = this.getPreciseArea();
        return ret;
    }

    /**
    * bla bla 
    *          are representation of an rectangle,  contains respectively x, y, width, height 
    */
    public void destroy() {
        while (!this.alPieceOnBoard.isEmpty()) {
            Piece p = this.alPieceOnBoard.get(0);
            p.destroy();
            this.alPieceOnBoard.remove(p);
        }
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
            BufferedImage image = PieceRenderUtils.createCellImage();
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

    private void addPieceOnBoard(Piece p, int x, int y) {

        int[][] bounds = p.getBounds();
        int k = 0;
        int l = 0;

        for (int i = y; i < y + bounds.length; i++) {

			for (int j = x; j < x + bounds[i - y].length; j++) {

				if (bounds[k][l] == 1) {

					this.playBoard[i][j] = p.getInstanceId() + 1;
				}

				l++;
			}
			k++;
			l = 0;
		}

        p.setX(x);
        p.setY(y);
    }

	public void removePieceOnBoard(Piece p) {

		for (int i = p.getY(); i < p.getY() + p.getHeight(); i++) {

			for (int j = p.getX(); j < p.getX() + p.getWidth(); j++) {

				this.playBoard[i][j] = 0;
			}
		}
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

    private int getPreciseArea() {
        int sum = 0;
        for ( int i = 0; i < this.playBoard.length; i++) {
            for ( int j = 0; j < this.playBoard[i].length; j++) {
                if ( this.playBoard[i][j] != 0 ) sum += 1; 
            }   
        }

        return sum;
    }

    private int[] getArea() {
        int x = this.playBoard[0].length;
        int y = this.playBoard.length;
        int xEnd = 0;
        int yEnd = 0;
        for (int i = 0; i < this.playBoard.length; i++) {
            for (int j = 0; j < this.playBoard[i].length; j++) {
                if ( this.playBoard[i][j] != 0 ) {
                    if ( j < x ) x = j;
                    if ( i < y ) y = i; 
                    if ( j > xEnd ) xEnd = j; 
                    if ( i > yEnd ) yEnd = i;  
                }
            }
        }
        
        return new int[]{ x, y, xEnd + 1, yEnd + 1};
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
