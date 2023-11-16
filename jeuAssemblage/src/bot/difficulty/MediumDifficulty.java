package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class MediumDifficulty extends AbstractDifficulty {

    public MediumDifficulty(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.MEDIUM);
    }
}
