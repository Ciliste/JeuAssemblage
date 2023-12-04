package factory;

import piece.Piece;

public class LFactory implements PieceFactory {
	
	@Override
	public Piece createPiece(int size) {
		
		return new Piece(new boolean[][] {

			{true, false},
			{true, false},
			{true, true}
		});
	}
}
