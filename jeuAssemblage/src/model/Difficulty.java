package model;

/**
 * Difficulty
 */
public enum Difficulty {

	// TODO: Faire en sorte que les difficultés soient chargées depuis un fichier de configuration
	EASY(1, "Facile", 6, 6, 10, 10, 4, 5, 2),
	MEDIUM(2, "Intermédiaire", 10, 10, 15, 15, 6, 7, 2),
	HARD(3, "Difficile", 15, 15, 20, 20, 8, 10, 3);

	private final int value;
	private final String name;

	private final int minSizeX;
	private final int minSizeY;

	private final int maxSizeX;
	private final int maxSizeY;

	private final int minNbPieces;
	private final int maxNbPieces;

	private final int maxPieceSize;

	private Difficulty(int value, String name, int minSizeX, int minSizeY, int maxSizeX, int maxSizeY, int minNbPieces, int maxNbPieces, int maxPieceSize) {

		this.value = value;
		this.name = name;

		this.minSizeX = minSizeX;
		this.minSizeY = minSizeY;

		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;

		this.minNbPieces = minNbPieces;
		this.maxNbPieces = maxNbPieces;

		this.maxPieceSize = maxPieceSize;
	}

	public int getValue() {

		return this.value;
	}

	public String getName() {

		return this.name;
	}

	public int getMinSizeX() {

		return this.minSizeX;
	}

	public int getMinSizeY() {

		return this.minSizeY;
	}

	public int getMaxSizeX() {

		return this.maxSizeX;
	}

	public int getMaxSizeY() {

		return this.maxSizeY;
	}

	public int getMinNbPieces() {

		return this.minNbPieces;
	}

	public int getMaxNbPieces() {

		return this.maxNbPieces;
	}

	public int getMaxPieceSize() {

		return this.maxPieceSize;
	}

	public static String[] getDifficultysName() {

		String[] names = new String[Difficulty.values().length];

		for (int i = 0; i < Difficulty.values().length; i++) {

			names[i] = Difficulty.values()[i].getName();
		}

		return names;
	}

	public static Difficulty getDifficultyFromName(String name) {

		for (Difficulty difficulty : Difficulty.values()) {

			if (difficulty.getName().equals(name)) {

				return difficulty;
			}
		}

		return null;
	}
}