package view.component.board.listener;

/**
 * Interface qui permet de rendre écoutable les composants qui peuvent sélectionner une pièce.
 * 
 * @see view.component.board.listener.IPieceClickedListener
 */
public interface IPieceClickedListenable {
	
	public abstract void addPieceClickedListener(IPieceClickedListener listener);
	public abstract void removePieceClickedListener(IPieceClickedListener listener);

	public abstract void unselectPiece();
}
