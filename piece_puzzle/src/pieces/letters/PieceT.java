package pieces.letters;

import pieces.Piece;

public class PieceT extends Piece{
    
    public PieceT(int x, int y) {
        super(x, y);
    }

    public int[][] getBounds() {
        return new int[][] 
        {
            {0,0,0},
            {1,1,1},
            {0,1,0}
        };
    }
}
