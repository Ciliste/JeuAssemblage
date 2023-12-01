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

    private final int DEFAULT_SLEEP = 500;

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

        int maxIte = 4;
        int actualIte = 0;

        do {

            if (actualIte == 2) {
                Move m = alMoves.get(alMoves.size() - 1);
                doMove(new Move(m.pieceId, model.getBoardWidth() - 3, model.getBoardHeight() - 3,
                        m.numberOfLeftRotation, m.numberOfReverse));
            }

            if (actualIte == 3) {
                Move m = alMoves.get(alMoves.size() - 1);
                doMove(new Move(m.pieceId, 2, 2, m.numberOfLeftRotation, m.numberOfReverse));
            }

            
            for (int i = 0; i < alMoves.size(); i++) {
                Move m = alMoves.get(i);

                boolean result = this.doMove(m);

                if (result == false) {
                    alMovesNotCheck.add(m);
                }
            }

            alMoves = alMovesNotCheck;
            alMovesNotCheck = new ArrayList<Move>();

            actualIte++;

        } while (!alMoves.isEmpty() && actualIte < maxIte);
    }
    
    private boolean doMove(Move m) {

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
        int numberOfLeftRotation = (m.numberOfLeftRotation - p.getNumberOfLeftRotation()) % 4;
        for (int j = 0; j < numberOfLeftRotation; j++) {
            this.view.rotateLeftPiece();
        }
        this.sleep();

        boolean ret = this.view.placePieceWithoutTranslate(m.x, m.y);

        this.view.clearSelectedPiece();
        this.sleep();

        return ret;
    }
    
    public void sleep() {
        try {
            int sleep = (int) ((Math.random() * (DEFAULT_SLEEP/2)) + DEFAULT_SLEEP);
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    
}
