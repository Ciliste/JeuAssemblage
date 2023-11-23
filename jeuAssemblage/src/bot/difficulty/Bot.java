package bot.difficulty;

import java.util.List;

import bot.interfaces.IBot;
import bot.interfaces.IStrategyBot;
import bot.strategy.AgStrategy;
import bot.strategy.NoobStrategy;
import bot.utils.Move;
import model.PlayBoard;

public abstract class Bot implements IBot {

    public static final int AI_STRATEGY = 0;
    public static final int MEMO_STRATEGY = 1;

    protected IStrategyBot strategyBot;
    protected PlayBoard model;

    private int AIdifficulty;

    public Bot(PlayBoard model, int strategy, int AIdifficulty) {
        this.model = model;
        this.AIdifficulty = AIdifficulty;

        this.setStrategy(strategy);
    }

    @Override
    public PlayBoard getModel() {
        return this.model;
    }

    @Override
    public void setStrategy(int strategy) {
        switch (strategy) {
            case AI_STRATEGY : {
                this.strategyBot = new AgStrategy(this.model, AIdifficulty);
                break;
            }
            case MEMO_STRATEGY : {
                this.strategyBot = new NoobStrategy(this.model);
                break;
            }
            default : {
                throw new IllegalStateException("Strategy " + strategy + " doesn't exist");
            }
        }
    }

    @Override
    public boolean tick() {
        return this.strategyBot.tick();
    }
    
    @Override
    public List<Move> getMoves() {
        return this.strategyBot.getMoves();
    }
}
