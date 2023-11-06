package piece;

import observer.AbstractListenableAL;

public class Piece extends AbstractListenableAL implements Cloneable {
    
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
		this.rotateLeftWithoutEvent();

		this.fireAllEvents();
	}

	private void rotateLeftWithoutEvent() {

		boolean[][] rotated = new boolean[piece[0].length][piece.length];

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				rotated[j][piece.length - i - 1] = piece[i][j];
			}
		}

		piece = rotated;
		
	}

	public void rotateRight() {

		rotateLeftWithoutEvent();
		rotateLeftWithoutEvent();
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
		
		this.fireAllEvents();
	}

	public static Piece clone(Piece piece) {
		try {
			Piece p = (Piece) piece.clone();
			p.piece = piece.piece.clone();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		
		System.out.println("Piece.java");
	}
}
