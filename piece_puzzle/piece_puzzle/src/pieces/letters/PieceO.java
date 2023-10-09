package pieces.letters;

import pieces.Piece;

public class PieceO extends Piece {

    public PieceO(int x, int y) {
        super(x, y);
    }

    public int[][] getBounds() {
        return new int[][] 
        {
            {1,1,1},
            {1,0,1},
            {1,1,1}
        };
    }
}