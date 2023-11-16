package view.screen;

import javax.swing.JPanel;

import model.PlayBoard;
import view.MainFrame;
import view.component.GamePanel;
import view.component.board.TimerPanel.Timer;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

import java.awt.Graphics;

public class SoloGameScreen extends JPanel {

	private final GamePanel gamePanel;

    public SoloGameScreen(MainFrame mainFrame, PlayBoard playboard, PiecesColor piecesColor, Timer timer) {

        setLayout(null);

		this.gamePanel = new GamePanel(mainFrame, playboard, piecesColor, timer);

		this.add(this.gamePanel);

		super.doLayout();
    }

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}

	@Override
	public void doLayout() {

		super.doLayout();

		final int PADDING = SwingUtils.getHeightTimesPourcent(this, 0.05f);

		gamePanel.setBounds(PADDING, PADDING, getWidth() - (PADDING * 2), getHeight() - (PADDING * 2));
	}
}
