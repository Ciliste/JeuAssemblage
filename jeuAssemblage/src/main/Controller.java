package main;

import java.awt.*;
import java.util.Random;

import main.event.EventManager;
import model.PlayBoard;
import pieces.Piece;
import view.MainFrame;

public class Controller {
    
    private static Controller instance;
    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
			instance.model = new PlayBoard();
			instance.view = new MainFrame();
			instance.eventManager = new EventManager();
        }

        return instance;
    }

	private PlayBoard model;
	private MainFrame view;
	private EventManager eventManager;


	private Controller() {}
	
	public EventManager getEventManager() {
		return this.eventManager;
	}

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

	public boolean difficultyPossible(int sizeX, int sizeY, int nbPieces) {
		return sizeX * sizeY > nbPieces * 9;
	}

	// model methods
	public void setPlayBoard(int sizeX, int sizeY, int nbPieces) {
		this.model.initNumberPiece(nbPieces);
		this.model.initSizePB(sizeY, sizeX);
	}

	public int     getWidthBoard    () { return this.model.getPlayBoard()[0].length; }
	public int     getHeightBoard   () { return this.model.getPlayBoard().length; }
	public int     getPieceCount    () { return this.model.getPieces().size(); }
	public Piece   getSelectedPiece () { return this.model.getSelectedPiece(); }
	public int[][] getPlayBoard     () { return this.model.getPlayBoard(); }

	public Image getImageById(int id) { return this.model.getImageById(id); }
	public Piece getPieceById(int id) { return this.model.getPieceById(id); }

	public int[] areaInfomartion()    { return this.model.rectangleArea(); }

	public boolean canBeAddedToBoard(int x, int y) {
		return this.model.selectedPieceCanBeAddedToBoard(
			x,
			y
		);
	}

	public void addPieceOnBoard(int x, int y) {
		this.model.addSelectedPiece(x, y);
		this.view.repaint();
		this.eventManager.fireEvent();
	}
}
