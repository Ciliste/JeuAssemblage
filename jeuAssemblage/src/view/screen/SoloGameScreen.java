package view.screen;

import javax.swing.JPanel;

import main.Controller;
import main.Difficulty;
import view.component.GameGrid;
import view.listener.ResizeListener;

import java.awt.Color;
import java.awt.Graphics;

public class SoloGameScreen extends JPanel {
	
	private final GameGrid grid;

	public SoloGameScreen(long seed, Difficulty difficulty) {
		
		super();

		this.setLayout(null);

		int sizeX = Controller.getInstance().getSizeX(seed, difficulty);
		int sizeY = Controller.getInstance().getSizeY(seed, difficulty);
		int piecesCount = Controller.getInstance().getPiecesCount(seed, difficulty);

		this.grid = new GameGrid(sizeX, sizeY);

		addComponentListener(new ResizeListener(createResizeCallback()));
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setColor(Color.RED);
		g.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
	}

	protected Runnable createResizeCallback() {

		return () -> {

			int width = getWidth();
			int height = getHeight();

			int size = Math.min(width, height);

			int x = (width - size) / 2;
			int y = (height - size) / 2;

			grid.setBounds(x, y, size, size);
		};
	}
}
