package pieces;

import java.util.Random;

import pieces.letters.*;

public class PieceFactory {

    public static final int NUMBER_PIECE = 5;
    private static Random random;

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public static Piece createPiece() {
        if ( random == null ) return null;
        
        int rand = random.nextInt(NUMBER_PIECE);
        
        switch ( rand ) {
            case 0: {
                return createPieceH();
            }
            case 1: {
                return createPieceI();
            }
            case 2: {
                return createPieceL();
            }
            case 3: {
                return createPieceO();
            }
            case 4: {
                return createPieceT();
            }
        }

        return null;
    }

    public static PieceH createPieceH() {
        return new PieceH(0, 0);
    }

    public static PieceI createPieceI() {
        return new PieceI(0, 0);
    }

    public static PieceL createPieceL() {
        return new PieceL(0, 0);
    }

    public static PieceO createPieceO() {
        return new PieceO(0, 0);
    }

    public static PieceT createPieceT() {
        return new PieceT(0, 0);
    }
    
}