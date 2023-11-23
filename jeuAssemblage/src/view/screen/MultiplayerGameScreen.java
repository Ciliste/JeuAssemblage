package view.screen;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.ArrayList;
import java.util.List;

import bot.BotThread;
import bot.difficulty.Bot;
import bot.difficulty.EasyBot;
import bot.interfaces.IBot;
import model.PlayBoard;
import view.MainFrame;
import view.component.GamePanel;
import view.component.board.Grid;
import view.component.timer.Timer;
import view.screen.AIGameCreation.BotDescriptor;
import view.utils.SwingUtils;

public class MultiplayerGameScreen extends JPanel {

	private final GamePanel gamePanel;

	private final JScrollPane scrollPane = new JScrollPane();

	public MultiplayerGamePanel(MainFrame mainFrame, PlayBoard playBoard, Timer timer, Iterable<BotDescriptor> botDescriptors) {

		super();

		this.gamePanel = new GamePanel(mainFrame, playBoard, timer);

		JPanel bots = new JPanel();
		bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

		ArrayList<IBot> botsAl = new ArrayList<IBot>();
		for (BotDescriptor botDescriptor : botDescriptors) {

			Grid grid = new Grid(PlayBoard.constructCopyPlayBoard(playBoard), true);

			bots.add(grid);

			botsAl.add(new EasyBot(playBoard, Bot.AI_STRATEGY));
		}
		
		BotThread botT = null;//new BotThread(botsAl);

		this.gamePanel = new GamePanel(mainFrame, playBoard, piecesColor, timer, new MultiplayerFinishScreen(mainFrame, playBoard, piecesColor, botT, botDescriptors));

		
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
