package factory;

import piece.Piece;

public class OFactory implements PieceFactory {
	
	@Override
	public Piece createPiece(int size) {
		
		return new Piece(new boolean[][] {

			{true, true, true},
			{true, false, true},
			{true, true, true}
		});
	}
}
