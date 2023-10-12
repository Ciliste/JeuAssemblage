package view.component.board;

import main.Controller;
import view.component.board.utils.GrilleBoard;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    private final Controller controller;

    private GrilleBoard gb;

    public Grid() {
        this.controller = Controller.getInstance();
        this.setLayout(new BorderLayout());

        this.gb = new GrilleBoard();

        this.add(this.gb, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void updatePlayBoard() {
        this.gb.repaint();
    }

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		SwingUtils.drawDebugBounds(this, g);
	}
}
