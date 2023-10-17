package view.component;

import javax.swing.JPanel;

import view.component.board.Grid;
import view.component.board.PieceBoard;
import view.utils.SwingUtils;

import java.awt.Graphics;
import java.awt.BorderLayout;


public class GamePanel extends JPanel{
	
	private final Grid grid;
	private final PieceBoard pieceBoard;

	public GamePanel() {
		super();
		this.setLayout(new BorderLayout());


		this.grid = new Grid();
		this.pieceBoard = new PieceBoard();

		this.add(this.grid, BorderLayout.CENTER);
		this.add(this.pieceBoard, BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
}
