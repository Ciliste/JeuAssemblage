package factory;

import piece.Piece;

public class TFactory implements PieceFactory {
	
	@Override
	public Piece createPiece(int size) {
		
		return new Piece(new boolean[][] {

			{true, true, true},
			{false, true, false}
		});
	}
}
