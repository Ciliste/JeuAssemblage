package view.component;

import static view.utils.SwingUtils.*;

import javax.swing.JPanel;

import view.component.board.Finish;
import view.component.board.Grid;
import view.component.board.PieceBoard;
import view.utils.SwingUtils;

import java.awt.Graphics;
import java.awt.BorderLayout;


public class GamePanel extends JPanel{
	
	private final Grid grid;
	private final PieceBoard pieceBoard;
	private final Finish finish;

	public GamePanel() {
		super();
		this.setLayout(null);


		this.grid = new Grid();
		this.pieceBoard = new PieceBoard();
		this.finish = new Finish();

		this.add(this.grid);
		this.add(this.pieceBoard);
		this.add(this.finish);
	}

	@Override
	public void doLayout() {
		super.doLayout();

		this.grid.setBounds(
			0,
		 	0,
		 	getWidthTimesPourcent(this, .8f),
		 	this.getHeight()
		);

		this.pieceBoard.setBounds(
			this.grid.getWidth(),
		 	0,
		 	getWidthTimesPourcent (this, .2f),
		 	getHeightTimesPourcent(this, .4f)
		);

		this.finish.setBounds(
			this.grid.getWidth(),
		 	this.getHeight() - getHeightTimesPourcent(this, .4f),
		 	getWidthTimesPourcent (this, .2f),
		 	getHeightTimesPourcent(this, .4f)
		);
		
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
}
