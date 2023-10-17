package view.component.board;

import main.Controller;
import view.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PieceBoard extends JPanel {

	private final Controller controller = Controller.getInstance();

	public PieceBoard() {

		// controller.getPlayBoard().

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(Box.createHorizontalGlue());
		this.add(new JLabel("PieceBoard"));
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);

	}
}
