package piece;

public class Piece {
    
	private boolean[][] piece;

	public Piece(boolean[][] piece) {

		int width = piece[0].length;
		for (int i = 1; i < piece.length; i++) {

			if (piece[i].length != width) {

				throw new IllegalArgumentException("Piece must be rectangular");
			}
		}

		this.piece = piece;
	}

	public boolean[][] getPiece() {

		return piece.clone();
	}

	public int getWidth() {

		return piece[0].length;
	}

	public int getHeight() {

		return piece.length;
	}

	public void rotateLeft() {

		boolean[][] rotated = new boolean[piece[0].length][piece.length];

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				rotated[j][piece.length - i - 1] = piece[i][j];
			}
		}

		piece = rotated;
	}

	public void rotateRight() {

		rotateLeft();
		rotateLeft();
		rotateLeft();
	}

	public void reverse() {

		boolean[][] reversed = new boolean[piece.length][piece[0].length];

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				reversed[i][piece[i].length - j - 1] = piece[i][j];
			}
		}

		piece = reversed;
	}

	public static Piece clone(Piece piece) {

		return new Piece(piece.piece.clone());
	}

	@Override
	public String toString() {

		String str = "";

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[0].length; j++) {

				str += (piece[i][j] ? "█" : " ");
			}

			str += "\n";
		}

		return str;
	}

	public static void main(String[] args) {
		
		System.out.println("Piece.java");
	}
}
