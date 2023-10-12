package view.component.board;

import main.Controller;
import view.component.board.utils.GrilleBoard;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PieceBoard extends JPanel {

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		SwingUtils.drawDebugBounds(this, g);
	}
}
