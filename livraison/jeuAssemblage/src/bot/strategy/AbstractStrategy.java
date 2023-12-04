package bot.strategy;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Point;

import bot.interfaces.IStrategyBot;
import bot.utils.Move;
import model.PlayBoard;
import piece.Piece;

public abstract class AbstractStrategy implements IStrategyBot {

    protected PlayBoard model;
    protected PlayBoard bestSolution;

    protected long seed;
    protected int testSize;
    protected int testedSize;
    protected Random rand;

    protected List<PlayBoard> pop;
    
    protected AbstractStrategy(PlayBoard model, long seed, int testSize) {
        this.model = model;
        this.bestSolution = model;

        this.seed = seed;
        this.testSize = testSize;
        this.testedSize = 0;
        this.rand = new Random(this.seed);

        this.pop = new ArrayList<PlayBoard>();
    }

    @Override
    public boolean tick() {

        if (this.testedSize >= this.testSize) {
            return false;
        }

        int toTest = Math.min(this.testSize - this.testedSize,
                this.rand.nextInt(1, (int) Math.ceil(this.testSize / 25) + 1));
        for (int i = 0; i < toTest; i++) {
            this.tickMethod();
        }

        this.setBestSolution();
        this.testedSize += toTest;

        return true;
    }
    
    protected abstract void tickMethod();

    protected abstract void setBestSolution();

    public List<Move> getMoves() {
        List<Move> ret = new ArrayList<Move>();

        ArrayList<Piece> pieceAl = new ArrayList<Piece>();

        for (int i = 0; i < bestSolution.getBoardHeight(); i++) {
            for (int j = 0; j < bestSolution.getBoardWidth(); j++) {
                int pieceId = bestSolution.getPieceIdAt(j, i);
                if (pieceId != PlayBoard.EMPTY) {
                    Piece p = bestSolution.getPieceById(pieceId);
                    if (!pieceAl.contains(p)) {
                        Point upperLeftCorner = bestSolution.getUpperLeftPieceCornerById(pieceId);
                        ret.add(new Move(pieceId, upperLeftCorner.x, upperLeftCorner.y, p.getNumberOfLeftRotation(), p.getNumberOfReverse()));
                        pieceAl.add(p);
                    }
                }
            }
        }

        return ret;
    }
}
