package bot.view.interfaces;

import piece.Piece;

public interface IMovableView {
       
    public abstract void rotateLeftPiece();
    public abstract void reversePiece();

    public abstract void moveMouseOn(int pieceId);
    public abstract void moveMouseOn(int x, int y);
    
    public abstract boolean clickOnPiece(int x, int y);
    public abstract boolean clickOnPiece(int pieceId);
    
    public abstract boolean placePiece(int x, int y);
    public abstract boolean placePieceWithoutTranslate(int x, int y);

    public abstract void clearSelectedPiece();

    public abstract Piece getSelectedPiece();
}
