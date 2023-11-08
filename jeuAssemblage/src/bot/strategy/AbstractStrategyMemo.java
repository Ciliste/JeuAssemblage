package bot.strategy;

import java.util.HashMap;
import java.util.LinkedList;

import bot.interfaces.IStrategyBot;
import bot.Move;
import model.PlayBoard;

public class AbstractStrategyMemo implements IStrategyBot{
    protected int deep;
    protected LinkedList<Move> moves;
    protected PlayBoard model;
    
    protected AbstractStrategyMemo(PlayBoard model) {
        this.deep = -1;
        this.moves = new LinkedList<Move>();
        this.model = model;
    }
    
    private LinkedList<Move> getMoves() {
        return null;
    }
    
    @Override
    public Move nextMove() {
        return this.moves.pop();
    }
    
}
