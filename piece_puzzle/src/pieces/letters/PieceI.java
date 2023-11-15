package pieces.letters;

import pieces.Piece;

public class PieceI extends Piece {
    
    public PieceI(int x, int y) {
        super(x, y);
    }

    @Override
    protected int[][] getInitialBounds(){
        return new int[][]
        {
            {1},
            {1},
            {1}
        };
    }
}