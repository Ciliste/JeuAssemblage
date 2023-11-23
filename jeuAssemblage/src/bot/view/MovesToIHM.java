package bot.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import bot.interfaces.IBot;
import bot.utils.Move;
import bot.view.interfaces.IMovableView;
import bot.view.interfaces.IMovesView;

public class MovesToIHM implements IMovesView {

    private IBot bot;
    private IMovableView view;

    public MovesToIHM(IBot bot, IMovableView view) {

        this.bot = bot;
        this.view = view;
    }

    @Override
    public void start() {
        List<Move> alMoves = (ArrayList<Move>) this.bot.getMoves();
        List<Move> alMovesNotCheck = new ArrayList<Move>();

        do {
            
            for (int i = 0; i < alMoves.size(); i++) {
                Move m = alMoves.get(i);

                this.view.moveMouseOn(m.pieceId);
                this.view.click();
                this.view.moveMouseOn(m.x, m.y);
                
                if ( !this.view.placePiece() ) {
                    alMovesNotCheck.add(m);
                }
            }

            alMoves = alMovesNotCheck;
            alMovesNotCheck = new ArrayList<Move>();

        } while( ! alMoves.isEmpty() );
    }
    
    
}
