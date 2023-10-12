package view.component;

import javax.swing.JPanel;

import view.component.board.Grid;
import view.component.board.PieceBoard;
import view.utils.SwingUtils;

import java.awt.Graphics;

public class GamePanel extends JPanel{
	
	private final Grid grid;
	private final PieceBoard pieceBoard;

	public GamePanel() {
		
		super();

		this.grid = new Grid();
		this.pieceBoard = new PieceBoard();

		this.setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
}
