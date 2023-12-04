package piece;

import observer.AbstractListenableAL;
import java.awt.Point;

public class Piece extends AbstractListenableAL implements Cloneable {

	private boolean[][] piece;

	private int numberOfLeftRotation;
	private int numberOfReverse;

	public Piece(boolean[][] piece) {
		
		this.numberOfLeftRotation = 0;

		int width = piece[0].length;
		for (int i = 1; i < piece.length; i++) {

			if (piece[i].length != width) {
				//this is impossible but in case if we do it in another langauge later.
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

	public int getNumberOfLeftRotation() {

		return this.numberOfLeftRotation;
	}

	public int getNumberOfReverse() {

		return this.numberOfReverse;
	}

	public void rotateLeft() {
		this.rotateLeftWithoutEvent();

		this.fireAllEvents();
	}

	private void rotateLeftWithoutEvent() {

		this.numberOfLeftRotation = (numberOfLeftRotation + 1) % 4;

		boolean[][] rotated = new boolean[piece[0].length][piece.length];

		for (int i = 0; i < piece.length; i++) {

			for (int j = 0; j < piece[i].length; j++) {

				rotated[j][piece.length - i - 1] = piece[i][j];
			}
		}

		piece = rotated;

	}
	
	public Point getPieceMiddle() {
		
		int width = this.getWidth() / 2 - ((this.getWidth() % 2 != 0) ? 0 : 1);
		int height = this.getHeight() / 2 - ((this.getHeight() % 2 != 0) ? 0 : 1);

		if (this.piece[height][width]) {
			return new Point(width, height);
		}

		for (int i = 0; i < height; i++) {
			if (this.piece[i][width]) {
				return new Point(width, i);
			}
		}

		for (int i = 0; i < width; i++) {
			if (this.piece[height][i]) {
				return new Point(i, height);
			}
		}

		for (int i = 0; i < this.getHeight(); i++) {
			for ( int j = 0; j < this.getWidth(); j++) {
				if (this.piece[i][j]) {
					return new Point(j, i);
				}
			}
		}

		return null;
	}

	public void rotateRight() {

		rotateLeftWithoutEvent();
		rotateLeftWithoutEvent();
		rotateLeft();
	}

	public void reverse() {

		this.numberOfReverse = (numberOfReverse + 1) % 2;
		
		if (this.numberOfLeftRotation % 2 != 0) {
			
			this.numberOfLeftRotation = (numberOfLeftRotation + 2) % 4;
		}

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

	@Override
	public String toString() {

		String str = super.toString() + "\n";

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
