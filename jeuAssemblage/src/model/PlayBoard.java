package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

import pieces.Piece;
import pieces.PieceFactory;
import observer.AbstractListenable;
import view.utils.PieceRenderUtils;

public class PlayBoard extends AbstractListenable{

    private ArrayList<Piece>        alPieceOnBoard;
    private Piece                   selectedPiece;
    
    private HashMap<Integer, Image> hmPieceImage;
    private int[][]                 playBoard;
    
    private Long                    seed;


    public PlayBoard() {
        super();
        this.hmPieceImage = new HashMap<Integer, Image>();
        
        this.alPieceOnBoard = null;
        this.playBoard      = null;
        this.selectedPiece  = null;
        this.seed           = null;
    }

    public void initSeed(long seed) {
        this.seed = seed;
        PieceFactory.setSeed(seed);
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
    public Long             getSeed          () { return this.seed; }

    public Image getImageById (int id) { return this.hmPieceImage.get(id); }

    public Piece getPieceById(int id) {
        for (Piece p : this.alPieceOnBoard) {
            if (p.getInstanceId() == id) {
                return p;
            }
        }
        return null;
    }
    
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

        this.removePieceOnBoard(this.getPieceById(this.selectedPiece.getInstanceId()));
        this.addPieceOnBoard(this.selectedPiece, x - 1, y - 1);
        this.removeSelectedPiece();
    }

    /**
    * @param  x         int that represent of top left matrices
    * @param  y         int that represent of top left matrices
    * @return           True if the selected piece can be added on the board else False
    */
    public boolean selectedPieceCanBeAddedToBoard(int x, int y) {
        if (this.selectedPiece == null) return false;

        return this.canBeAddedToBoard(this.selectedPiece, x - 1, y - 1);
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
    *
    */
    public void registerArrangement() {
        String str = this.playBoard[0].length + ";" +
                this.playBoard.length         + ";" +
                this.alPieceOnBoard.size()    + ";" +
                this.seed;

        File f = new File(this.getPath() + "/maps.tetriste");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f, true));
            writer.append(str);
            writer.append('\n');
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
    * bla bla 
    *
    * @return   an ArrayList of String that represent the file
    */
    public ArrayList<String> getArrangement() {
        ArrayList<String> alRet = new ArrayList<String>();
        File f = new File(this.getPath() + "/maps.tetriste");
        if ( !f.exists() ) return alRet;
        
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                alRet.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return alRet;
    }

    /**
    * bla bla 
    * 
    */
    public void destroy() {
        while (!this.alPieceOnBoard.isEmpty()) {
            Piece p = this.alPieceOnBoard.get(0);
            p.destroy();
            this.alPieceOnBoard.remove(p);
        }
    }

    // Privates
    private String getPath() {
        Path path = Paths.get("");
        return path.toAbsolutePath().toString();
    }

	private void removeSelectedPiece() {
		if (this.selectedPiece == null) return;

        this.alPieceOnBoard.remove(this.getPieceById(this.selectedPiece.getInstanceId()));
        this.alPieceOnBoard.add(this.selectedPiece);
        this.selectedPiece = null;
	}

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
        Random rand = new Random(this.seed);
        for ( Piece p: this.alPieceOnBoard) {
            int x, y;
            do {
                x = rand.nextInt(this.playBoard[0].length - p.getWidth ());
                y = rand.nextInt(this.playBoard.length    - p.getHeight());
            } while( !canBeAddedToBoard(p, x, y) );

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
					this.playBoard[i][j] = p.getInstanceId();
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
		int[][] bounds = p.getBounds();
        int k = 0;
        for (int i = p.getY(); i < p.getY() + p.getHeight(); i++) {
            int l = 0;
            for (int j = p.getX(); j < p.getX() + p.getWidth(); j++) {
                if (bounds[k][l] != 0) {
                    this.playBoard[i][j] = 0;
                }
                l++;
            }
            k++;
        }
	}

    private boolean canBeAddedToBoard(Piece p, int x, int y) {

        System.out.println("x : " + x + " y : " + y);

        if ( x + p.getWidth()  > this.playBoard.length   ) return false;
        if ( y + p.getHeight() > this.playBoard[0].length) return false;
        if ( x < 0 ) return false;
        if ( y < 0 ) return false;

        int[][] bounds = p.getBounds();
        int k = 0;
        for (int i = y; i < y + p.getHeight(); i++) {
            int l = 0;
            for (int j = x; j < x + p.getWidth(); j++) {
                if ((this.playBoard[i][j] != 0 && this.playBoard[i][j] != p.getInstanceId()) && bounds[k][l] != 0) {
                    return false;
                }
                l++;
            }
            k++;
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
