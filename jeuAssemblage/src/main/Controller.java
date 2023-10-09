package main;

import java.util.Random;

public class Controller {
    
    private static Controller instance;
    public static Controller getInstance() {

        if (instance == null) {

            instance = new Controller();
        }

        return instance;
    }

	private Controller() {

	}

	public int getSizeX(long seed) {

		return new Random(seed).nextInt(10) + 5;
	}

	public int getSizeY(long seed) {

		return new Random(seed + 1).nextInt(10) + 5;
	}

	public int getPiecesCount(long seed) {

		return new Random(seed + 2).nextInt(10) + 5;
	}
}
