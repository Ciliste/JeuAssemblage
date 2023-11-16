package bot.difficulty;

import bot.interfaces.IBot;
import bot.interfaces.IStrategyBot;
import bot.strategy.AgStrategy;
import bot.strategy.AbstractStrategyMemo;
import model.PlayBoard;

public abstract class AbstractDifficulty implements IBot {

    public final int AI_STRATEGY = 0;
    public final int MEMO_STRATEGY = 1;

    protected IStrategyBot strategyBot;
    protected PlayBoard model;

    private int AIdifficulty;

    public AbstractDifficulty(PlayBoard model, int strategy, int AIdifficulty) {
        this.model = model;
        this.AIdifficulty = AIdifficulty;

        this.setStrategy(strategy);
    }

    @Override
    public void setStrategy(int strategy) {
        switch (strategy) {
            case AI_STRATEGY -> {
                this.strategyBot = new AgStrategy(this.model, AIdifficulty);
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
