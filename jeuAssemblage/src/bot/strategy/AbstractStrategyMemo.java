package bot.strategy;

import java.util.LinkedList;

import bot.interfaces.IStrategyBot;
import bot.Move;
import model.PlayBoard;

public class AbstractStrategyMemo implements IStrategyBot{
    protected int deep;
    protected LinkedList<Move> moves;
    protected PlayBoard model;
    
    public AbstractStrategyMemo(PlayBoard model) {
        this.deep = -1;
        this.moves = new LinkedList<Move>();
        this.model = model;
    }
    
    private LinkedList<Move> getMoves() {
        return null;
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }
    
}
