package bot.strategy;

import java.util.List;

import bot.interfaces.IStrategyBot;
import bot.Move;
import model.PlayBoard;

public class AbstractStrategyMemo implements IStrategyBot{
    protected int deep;
    protected PlayBoard model;
    
    public AbstractStrategyMemo(PlayBoard model) {
        this.deep = -1;
        this.model = model;
    }
    
    public List<Move> getMoves() {
        return null;
    }

    @Override
    public void tick() {
        
        throw new UnsupportedOperationException("Unimplemented method 'tick'");
    }
    
}
