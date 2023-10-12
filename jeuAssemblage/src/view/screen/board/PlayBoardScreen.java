package view.screen.board;

import main.Controller;
import view.screen.board.utils.GrilleBoard;

import javax.swing.*;
import java.awt.*;

public class PlayBoardScreen extends JPanel {
    private final Controller controller;

    private GrilleBoard gb;

    public PlayBoardScreen() {
        this.controller = Controller.getInstance();
        this.setLayout(new BorderLayout());

        this.gb = new GrilleBoard();

        this.add(this.gb, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void updatePlayBoard() {
        this.gb.repaint();
    }
}
