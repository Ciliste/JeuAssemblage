package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class MediumBot extends Bot {

    public MediumBot(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.MEDIUM);
    }
}
