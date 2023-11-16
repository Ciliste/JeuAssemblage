package bot.strategy;


import java.util.ArrayList;
import java.util.List;

import java.awt.Point;

import bot.interfaces.IStrategyBot;
import bot.utils.Move;
import model.PlayBoard;
import piece.Piece;

public abstract class AbstractStrategy implements IStrategyBot {

    protected PlayBoard model;
    protected PlayBoard bestSolution;
    
    protected AbstractStrategy(PlayBoard model) {
        this.model = model;
        this.bestSolution = model;
    }

    public List<Move> getMoves() {
        List<Move> ret = new ArrayList<Move>();

        ArrayList<Piece> pieceAl = new ArrayList<Piece>();

        for (int i = 0; i < bestSolution.getBoardHeight(); i++) {
            for (int j = 0; j < bestSolution.getBoardWidth(); j++) {
                int pieceId = bestSolution.getPieceIdAt(j, i);
                if (pieceId != 0) {
                    Piece p = bestSolution.getPieceById(pieceId);
                    if (!pieceAl.contains(p)) {
                        Point upperLeftCorner = bestSolution.getUpperLeftPieceCornerById(pieceId);
                        ret.add(new Move(pieceId, upperLeftCorner.x, upperLeftCorner.y));
                        pieceAl.add(p);
                    }
                }
            }
        }

        return ret;
    }
}
