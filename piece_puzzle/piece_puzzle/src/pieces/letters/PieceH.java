package pieces.letters;

import pieces.Piece;

public class PieceH extends Piece {
    
    public PieceH(int x, int y) {
        super(x, y);
    }

    public int[][] getBounds() {
        return new int[][] 
        {
            {1,0,1},
            {1,1,1},
            {1,0,1}
        };
    }
}
