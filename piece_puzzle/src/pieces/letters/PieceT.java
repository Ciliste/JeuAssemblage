package pieces.letters;

import pieces.Piece;

public class PieceT extends Piece{
    
    public PieceT(int x, int y) {
        super(x, y);
    }

    @Override
    public int[][] getBounds() {
        return new int[][] 
        {
            {1,1,1},
            {0,1,0}
        };
    }
}
