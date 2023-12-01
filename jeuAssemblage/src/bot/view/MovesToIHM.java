package bot.view;

import java.util.ArrayList;
import java.util.List;

import bot.interfaces.IBot;
import bot.utils.Move;
import bot.view.interfaces.IMovableView;
import bot.view.interfaces.IMovesView;
import model.PlayBoard;
import piece.Piece;

public class MovesToIHM implements IMovesView {

    private final int DEFAULT_SLEEP = 200;

    private IBot bot;
    private IMovableView view;
    private PlayBoard model;

    public MovesToIHM(IBot bot, IMovableView view, PlayBoard model) {

        this.bot = bot;
        this.view = view;
        this.model = model;
    }

    @Override
    public void run() {
        List<Move> alMoves = (ArrayList<Move>) this.bot.getMoves();
        List<Move> alMovesNotCheck = new ArrayList<Move>();

        int maxIte = 6;
        int actualIte = 0;

        do {

            if (actualIte == 2) {

                Move m = alMoves.get(getRandom(1, alMoves.size() - 1));
                doMove(new Move(m.pieceId, model.getBoardWidth() - 3, model.getBoardHeight() - 3,
                        0, m.numberOfReverse));
            }

            if (actualIte == 3) {

                Move m = alMoves.get(getRandom(1, alMoves.size() - 1));
                doMove(new Move(m.pieceId, 2, 2, 0, m.numberOfReverse));
            }

            if (actualIte == 4) {

                Move m = alMoves.get(getRandom(1, alMoves.size() - 1));
                doMove(new Move(m.pieceId, model.getBoardWidth() - 3, 2, 0, m.numberOfReverse));
            }
            
            if (actualIte == 5) {
                
                Move m = alMoves.get(getRandom(1, alMoves.size() - 1));
                doMove(new Move(m.pieceId, 2 , model.getBoardHeight() - 3, 0, m.numberOfReverse));
            }

            
            for (int i = 0; i < alMoves.size() && !Thread.currentThread().isInterrupted(); i++) {
                Move m = alMoves.get(i);

                boolean result = this.doMove(m);

                if (result == false) {
                    alMovesNotCheck.add(m);
                }
            }

            alMoves = alMovesNotCheck;
            alMovesNotCheck = new ArrayList<Move>();

            actualIte++;

        } while (!alMoves.isEmpty() && actualIte < maxIte && !Thread.currentThread().isInterrupted());
    }
    
    private boolean doMove(Move m) {

        System.out.println("shesh");

        Piece p = model.getPieceById(m.pieceId);
        
        boolean inversible = m.numberOfReverse != p.getNumberOfReverse();

        this.view.moveMouseOn(m.pieceId);
        this.sleep();

        this.view.clickOnPiece(m.pieceId);
        this.sleep();

        this.view.moveMouseOn(m.x, m.y);
        this.sleep();

        if (inversible) {
            this.view.reversePiece();
        }
        this.sleep();

        p = this.view.getSelectedPiece();
        while (p.getNumberOfLeftRotation() != m.numberOfLeftRotation) {
            this.view.rotateLeftPiece();
        }
        this.sleep();

        boolean ret = this.view.placePieceWithoutTranslate(m.x, m.y);

        this.view.clearSelectedPiece();
        this.sleep();

        return ret;
    }
    
    private void sleep() {
        try {
            int sleep = (int) ((Math.random() * (DEFAULT_SLEEP / 2)) + DEFAULT_SLEEP);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private int getRandom(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    
}
