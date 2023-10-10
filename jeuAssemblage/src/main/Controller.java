package main;

import java.util.Random;

import model.PlayBoard;
import view.MainFrame;

public class Controller {
    
    private static Controller instance;
    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
			instance.model = new PlayBoard(0, 0, 0);
			instance.view = new MainFrame();
        }

        return instance;
    }

	private PlayBoard model;
	private MainFrame view;


	private Controller() {}

	public int getSizeX(long seed) {

		return new Random(seed).nextInt(10) + 5;
	}

	public int getSizeY(long seed) {

		return new Random(seed + 1).nextInt(10) + 5;
	}

	public int getPiecesCount(long seed) {

		return new Random(seed + 2).nextInt(10) + 5;
	}

	public boolean canBeAddedToBoard(Piece p) {
		return this.model.canBeAddedToBoard(
			p.getX(),
			p.getY(),
			p.getBounds()	
		);
	}
}
