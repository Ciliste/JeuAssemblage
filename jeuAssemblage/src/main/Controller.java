package main;

import model.PlayBoard;
import pieces.Piece;
import view.MainFrame;

public class Controller {
    
    private static Controller instance;

    private PlayBoard model;
    private MainFrame view;

    private Controller() {}

    public static Controller getInstance() {
        if (instance == null) { 
            instance = new Controller();
            instance.model = new PlayBoard(3, 3, 6);
            instance.view = new MainFrame();
        }

        return instance;
    }

    public boolean canBeAddedToBoard(Piece piece) {
        return model.canBeAddedToBoard(
            piece.getX(), 
            piece.getY(), 
            piece.getBounds()
        );
    } 
}
