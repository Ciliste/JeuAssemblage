package view.component.board;

import main.Controller;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PieceBoard extends JPanel {

	private final Controller controller = Controller.getInstance();

	public PieceBoard() {

		// controller.getPlayBoard().

		this.setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		SwingUtils.drawDebugBounds(this, g);

	}
}
