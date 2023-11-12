package bot.difficulty;

import bot.strategy.AIStrategy;
import model.PlayBoard;

public class HardDifficulty extends AbstractDifficulty {
    
    public HardDifficulty(PlayBoard model, int strategy) {
        super(model, strategy, AIStrategy.HARD);
    }
}
