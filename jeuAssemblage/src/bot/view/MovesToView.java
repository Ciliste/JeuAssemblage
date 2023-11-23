package bot.view;

import java.util.LinkedList;
import java.util.List;

import bot.utils.Move;
import bot.view.interfaces.IMovableView;
import bot.view.interfaces.IMovesView;

public class MovesToView implements IMovesView {

    List<Move> moves;
    IMovableView view;

    public MovesToView(IMovableView view) {

        this(new LinkedList<Move>(), view);
    }

    public MovesToView(List<Move> moves, IMovableView view) {

        this.moves = moves;
        this.view = view;
    }

    @Override
    public void setMoves(List<Move> lstMoves) {
        
        this.moves = lstMoves;
    }

    @Override
    public void start() {
        
        for (Move m : this.moves) {
            //this.view.moveMouseOn(m.)
        }
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
    
    
}
