package main;

import java.util.Random;

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
        }

        return instance;
    }

	private PlayBoard model;
	private MainFrame view;


	private Controller() {}


	// seed methods
	public int getSizeX(long seed) {
		return new Random(seed).nextInt(10) + 5;
	}

	public int getSizeY(long seed) {
		return new Random(seed + 1).nextInt(10) + 5;
	}

	public int getPiecesCount(long seed) {
		return new Random(seed + 2).nextInt(10) + 5;
	}

	// play board methods
	public void setPlayBoard(long seed, int difficulty ) {
		this.model.initNumberPiece(getPiecesCount(seed));
		this.model.initSizePB(getSizeX(seed), getSizeY(seed));
	}

	public int getWidth() {
		return this.model.getWidth();
	}

	public int getHeight() {
		return this.model.getHeight();
	}

	public boolean canBeAddedToBoard(Piece p) {
		return this.model.canBeAddedToBoard(
			p.getX(),
			p.getY(),
			p.getBounds()
		);
	}
}
