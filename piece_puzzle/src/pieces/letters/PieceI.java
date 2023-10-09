package pieces.letters;

import pieces.Piece;

public class PieceI extends Piece {
    
    public PieceI(int x, int y) {
        super(x, y);
    }

    public int[][] getBounds() {
        return new int[][] 
        {
            {0,1,0},
            {0,1,0},
            {0,1,0}
        };
    }
}
