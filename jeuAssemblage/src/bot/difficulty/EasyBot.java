package bot.difficulty;

import bot.strategy.AgStrategy;
import model.PlayBoard;

public class EasyBot extends Bot {

    public EasyBot(PlayBoard model, int strategy) {
        super(model, strategy, AgStrategy.EASY);
    }

}
