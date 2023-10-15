package pieces.letters;

import pieces.Piece;

public class PieceI extends Piece {
    
    public PieceI(int x, int y) {
        super(x, y);
    }

    @Override
    public int getWidth() {
        if (rotate == 0 || rotate == 2) {
            return 1;
        }

        return 3;
    }

    @Override
    public int getHeight() {
        if (rotate == 1 || rotate == 3) {
            return 1;
        }

        return 3;
    }

    @Override
    protected int[][] getInitialBounds(){
        return new int[][]
        {
            {1, 0, 0},
            {1, 0, 0},
            {1, 0, 0}
        };
    }
}
