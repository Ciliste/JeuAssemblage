package bot.interfaces;

import model.PlayBoard;

public interface IBot extends ITickable {

    public abstract PlayBoard getModel();
    public abstract void setStrategy(int strategy);
}
