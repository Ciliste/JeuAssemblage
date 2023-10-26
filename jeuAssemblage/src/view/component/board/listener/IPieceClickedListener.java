package view.component.board.listener;

/**
 * Interface qui permet d'écouter les clics sur les pièces.
 * 
 * @see view.component.board.listener.IPieceClickedListenable
 */
public interface IPieceClickedListener {
	
	public abstract void pieceClicked(Object source, int pieceId);
}
