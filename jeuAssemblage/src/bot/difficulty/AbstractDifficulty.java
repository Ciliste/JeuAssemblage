package bot.difficulty;

import bot.Move;
import bot.interfaces.IBot;
import bot.interfaces.IStrategyBot;
import bot.strategy.AIStrategy;
import bot.strategy.AbstractStrategyMemo;
import model.PlayBoard;

public abstract class AbstractDifficulty implements IBot {

    public final int AI_STRATEGY = 0;
    public final int MEMO_STRATEGY = 1;

    protected IStrategyBot strategyBot;
    protected PlayBoard model;

    private int AIsize;

    public AbstractDifficulty(PlayBoard model, int strategy, int AIsize) {
        this.model = model;
        this.AIsize = AIsize;

        this.setStrategy(strategy);
    }

    @Override
    public void setStrategy(int strategy) {
        switch (strategy) {
            case AI_STRATEGY -> {
                this.strategyBot = new AIStrategy(this.model, AIsize);
            }
            case MEMO_STRATEGY -> {
                this.strategyBot = new AbstractStrategyMemo(this.model);
            }
            default -> {
                throw new IllegalStateException("Strategy " + strategy + " doesn't exist");
            }
        }
    }

    @Override
    public void tick() {
        this.strategyBot.tick();
    }
   
    
}
