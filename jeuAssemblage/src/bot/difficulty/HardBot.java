package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class HardBot extends Bot {
    
    public HardBot(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.HARD);
    }
}
