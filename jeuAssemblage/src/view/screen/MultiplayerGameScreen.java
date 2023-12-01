package view.screen;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.ArrayList;
import java.util.List;

import bot.BotThread;
import bot.difficulty.Bot;
import bot.difficulty.EasyBot;
import bot.difficulty.HardBot;
import bot.difficulty.MediumBot;
import bot.interfaces.IBot;
import bot.view.MovesToIHM;
import bot.view.interfaces.IMovesView;
import model.PlayBoard;
import view.MainFrame;
import view.component.GamePanel;
import view.component.board.Grid;
import view.component.timer.Timer;
import view.screen.AIGameCreation.BotDescriptor;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

public class MultiplayerGameScreen extends JPanel {

	private final GamePanel gamePanel;

	private final JScrollPane scrollPane = new JScrollPane();

	public MultiplayerGameScreen(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, Timer timer, List<BotDescriptor> botDescriptors) {

		super();

		JPanel bots = new JPanel();
		bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

		ArrayList<IMovesView> movesViews = new ArrayList<IMovesView>();
		ArrayList<IBot> botsAl = new ArrayList<IBot>();
		ArrayList<PlayBoard> botPlayboard = new ArrayList<PlayBoard>();

		for (BotDescriptor botDescriptor : botDescriptors) {

			PlayBoard p = PlayBoard.constructPlayBoard(playBoard);
			Grid grid = new Grid(p, false, true, piecesColor);

			botPlayboard.add(p);
			bots.add(grid);

			IBot bot = null;
			int strategy = botDescriptor.getStrategy();
			if (botDescriptor.getDifficulty() == 1) {
				bot = new EasyBot(playBoard, strategy);
			} else if (botDescriptor.getDifficulty() == 2) {
				bot = new MediumBot(playBoard, strategy);
			} else {
				bot = new HardBot(playBoard, strategy);
			}

			movesViews.add(new MovesToIHM(bot, grid, playBoard));
			botsAl.add(bot);
		}
		
		BotThread botT = new BotThread(botsAl, movesViews);

		this.gamePanel = new GamePanel(mainFrame, playBoard, piecesColor, timer, botT,
				new MultiplayerFinishScreen(mainFrame, playBoard, piecesColor, botT, botPlayboard));
		
		new Thread(botT).start();
		
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
