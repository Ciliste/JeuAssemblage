package model.listener;

import piece.Piece;

public interface IPlayBoardListener {
	
	public abstract void pieceAdded(Object source, int pieceId);
	public abstract void pieceRemoved(Object source, int pieceId);

	public abstract void pieceMoved(Object source, int pieceId);
}
