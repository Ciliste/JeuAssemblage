package factory;

import piece.Piece;

public class ZFactory implements PieceFactory {
	
	@Override
	public Piece createPiece(int size) {
		
		return new Piece(new boolean[][] {

			{true, true, false},
			{false, true, true}
		});
	}
}
