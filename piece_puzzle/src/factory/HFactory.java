package factory;

import piece.Piece;

public class HFactory implements PieceFactory {
	
	@Override
	public Piece createPiece(int size) {
		
		return new Piece(new boolean[][] {

			{true, false, true},
			{true, true, true},
			{true, false, true}
		});
	}
}
