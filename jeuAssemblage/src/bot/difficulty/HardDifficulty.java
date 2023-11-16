package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class HardDifficulty extends AbstractDifficulty {
    
    public HardDifficulty(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.HARD);
    }
}
