package pieces.letters;

import pieces.Piece;

public class PieceT extends Piece{
    
    public PieceT(int x, int y) {
        super(x, y);
    }

    @Override
    public int getWidth() {
        if (rotate == 0 || rotate == 2) {
            return 3;
        }

        return 2;
    }

    @Override
    public int getHeight() {
        if (rotate == 1 || rotate == 3) {
            return 3;
        }

        return 2;
    }

    @Override
    protected int[][] getInitialBounds(){
        return new int[][]
        {
            {1, 1, 1},
            {0, 1, 0}
        };
    }
}
