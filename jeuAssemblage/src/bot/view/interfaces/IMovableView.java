package bot.view.interfaces;

import piece.Piece;

public interface IMovableView {
       
    public abstract void moveMouseOn(int pieceId);
    public abstract void moveMouseOn(int x, int y);
    public abstract void click();
    public abstract boolean placePiece();
}
