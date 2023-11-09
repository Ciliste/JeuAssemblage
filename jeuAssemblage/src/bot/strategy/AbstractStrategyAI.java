package bot.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import bot.interfaces.IStrategyBot;
import bot.Move;
import model.PlayBoard;
import piece.Piece;

public class AbstractStrategyAI implements IStrategyBot{
    protected int deep;
    protected LinkedList<Move> moves;
    protected PlayBoard model;
    
    protected AbstractStrategyAI(PlayBoard model) {
        this.deep = -1;
        this.moves = new LinkedList<Move>();
        this.model = model;
    }
    
    private LinkedList<Move> getMoves() {
        return null;
    }

    private ArrayList<PlayBoard> mutation(ArrayList<PlayBoard> toMutate, Random rand, double mutationRate) {

        ArrayList<PlayBoard> postMutation = new ArrayList<PlayBoard>();
        for (PlayBoard parent : toMutate) {
            postMutation.add(parent);

            if (rand.nextDouble() > mutationRate) {
                postMutation.add(individualMutation(parent));
            }
        }

        return postMutation;
    }
    
    private PlayBoard individualMutation(PlayBoard parent) {
        PlayBoard son = PlayBoard.constructEmptyPlayBoardCopy(parent);
        
        return null;
    }
    
    @Override
    public Move nextMove() {
        return this.moves.pop();
    }
    
}
