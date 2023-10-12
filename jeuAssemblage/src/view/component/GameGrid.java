package view.component;

import javax.swing.JPanel;

public class GameGrid extends JPanel {
	
	private final int sizeX;
	private final int sizeY;

	public GameGrid(int sizeX, int sizeY) {
		
		super();

		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.setLayout(null);
	}
}
