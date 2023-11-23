package bot.view.interfaces;

import java.util.List;

import bot.utils.Move;

public interface IMovesView {
    
    public void setMoves(List<Move> lstMoves);
    public void start();
}
