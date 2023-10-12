package view.screen;

import main.Controller;
import view.screen.board.PieceBoardScreen;
import view.screen.board.PlayBoardScreen;

import javax.swing.*;
import java.awt.*;

public class SoloGameScreen extends JPanel {

    private final Controller controller;

    private PlayBoardScreen playBoardScreen;
    private PieceBoardScreen pieceBoardScreen;

    public SoloGameScreen( Runnable mainScreenCallBack ) {
        this.controller = Controller.getInstance();

        this.setLayout(new GridLayout(2,1));

        this.playBoardScreen = new PlayBoardScreen();
        this.pieceBoardScreen = new PieceBoardScreen();

        this.add(this.playBoardScreen);
        this.add(this.pieceBoardScreen);

        this.setVisible(true);
    }
}
