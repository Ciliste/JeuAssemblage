package view.component.board.listener;

import piece.Piece;

public interface IPieceManipulationComponent {
	
	public abstract void selectPiece(int pieceId);
	public abstract Piece getSelectedPiece();
	public abstract void unSelectPiece();
}
