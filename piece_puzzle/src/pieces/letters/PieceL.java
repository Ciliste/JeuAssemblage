package pieces.letters;

import pieces.Piece;

public class PieceL extends Piece {
    
    public PieceL(int x, int y) {
        super(x, y);
    }

    protected int[][] getInitialBounds(){
        return new int[][]
        {
            {1, 0},
            {1, 0},
            {1, 1}
        };
    }
}
