package main;

import java.util.Random;

import model.PlayBoard;
import pieces.Piece;
import pieces.PieceColor;
import view.MainFrame;

public class Controller {
    
    private static Controller instance;
    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
			instance.model = new PlayBoard();
			instance.view = new MainFrame();
        }

        return instance;
    }

	private PlayBoard model;
	private MainFrame view;


	private Controller() {}


	// seed methods

	// difficulty methods
	public int getSizeXBySeed(long seed, Difficulty difficulty) {

		int minSizeX = difficulty.getMinSizeX();
		int maxSizeX = difficulty.getMaxSizeX();

		return new Random(seed).nextInt(maxSizeX - minSizeX) + minSizeX;
	}

	public int getSizeYBySeed(long seed, Difficulty difficulty) {

		int minSizeY = difficulty.getMinSizeY();
		int maxSizeY = difficulty.getMaxSizeY();

		return new Random(seed + 1).nextInt(maxSizeY - minSizeY) + minSizeY;
	}

	public int getPiecesCountBySeed(long seed, Difficulty difficulty) {

		int minNbPieces = difficulty.getMinNbPieces();
		int maxNbPieces = difficulty.getMaxNbPieces();

		return new Random(seed + 2).nextInt(maxNbPieces - minNbPieces) + minNbPieces;
	}

	// play board methods
	public void setPlayBoard(int sizeX, int sizeY, int nbPieces) {

		this.model.initNumberPiece(nbPieces);
		this.model.initSizePB(sizeY, sizeX);
	}

	public int getWidthBoard() {
		return this.model.getPlayBoard()[0].length;
	}

	public int getHeightBoard() {
		return this.model.getPlayBoard().length;
	}

	public int getPieceCount() {
		return this.model.getPieceOnBoard().size();
	}

	public int[][] getPlayBoard() {
		return this.model.getPlayBoard();
	}

	public boolean canBeAddedToBoard(PieceColor pc) {
		Piece p = pc.piece;
		return this.model.canBeAddedToBoard(
			p.getX(),
			p.getY(),
			p.getBounds()
		);
	}

	public void cleaningRunSpace() {
		this.model.cleaningRunSpace();
	}
}
