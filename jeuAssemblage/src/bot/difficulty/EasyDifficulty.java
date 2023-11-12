package bot.difficulty;

import bot.strategy.AIStrategy;
import model.PlayBoard;

public class EasyDifficulty extends AbstractDifficulty {

    public EasyDifficulty(PlayBoard model, int strategy) {
        super(model, strategy, AIStrategy.EASY);
    }

}
