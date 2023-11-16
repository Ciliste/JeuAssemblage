package view.component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.PlayBoard;
import view.MainFrame;
import view.component.board.Grid;
import view.component.board.TimerPanel.Timer;
import view.screen.AIGameCreation.BotDescriptor;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

public class MultiplayerGamePanel extends JPanel {

	private final GamePanel gamePanel;

	private final JScrollPane scrollPane = new JScrollPane();

	public MultiplayerGamePanel(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, Timer timer, Iterable<BotDescriptor> botDescriptors) {

		super();

		this.gamePanel = new GamePanel(mainFrame, playBoard, piecesColor, timer);

		JPanel bots = new JPanel();
		bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

		for (BotDescriptor botDescriptor : botDescriptors) {

			Grid grid = new Grid(PlayBoard.constructCopyPlayBoard(playBoard), true, piecesColor);

			bots.add(grid);
		}

		scrollPane.setViewportView(bots);

		setLayout(null);

		this.add(this.gamePanel);
		this.add(scrollPane);
	}

	@Override
	public void doLayout() {

		super.doLayout();

		this.gamePanel.setBounds(
			0,
		 	0,
		 	SwingUtils.getWidthTimesPourcent(this, .8f),
			getHeight()
		);

		this.scrollPane.setBounds(
			SwingUtils.getWidthTimesPourcent(this, .8f),
			0,
			SwingUtils.getWidthTimesPourcent(this, .2f),
			getHeight()
		);
	}
}
