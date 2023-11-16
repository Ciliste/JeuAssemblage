package bot.interfaces;

import java.util.List;

import bot.Move;

public interface IStrategyBot extends ITickable{

    public abstract List<Move> getMoves();
}
