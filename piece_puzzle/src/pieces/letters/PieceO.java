package pieces.letters;

import pieces.Piece;

public class PieceO extends Piece {

    public PieceO(int x, int y) {
        super(x, y);
    }

    @Override
    protected int[][] getInitialBounds(){
        return new int[][]
        {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
    }
}