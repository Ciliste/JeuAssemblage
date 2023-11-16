package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class EasyDifficulty extends AbstractDifficulty {

    public EasyDifficulty(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.EASY);
    }

}
