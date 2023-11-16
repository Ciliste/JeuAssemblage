package bot.utils;

import piece.Piece;

public class Move {
    
    protected int pieceId;
    protected int x;
    protected int y;

    public Move(int pieceId, int x, int y) {
        this.pieceId = pieceId;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Piece : " + this.pieceId + " x : " + this.x + " y : " + this.y;
    }
}
