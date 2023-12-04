package view.screen;

import static view.utils.SwingUtils.getHeightTimesPourcent;
import static view.utils.SwingUtils.getWidthTimesPourcent;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bot.difficulty.Bot;
import model.PlayBoard;
import model.SettingsUtils;
import view.MainFrame;
import view.component.timer.Timer;
import view.utils.SwingUtils;

public class AIGameCreation extends SoloGameCreation {

	protected final JPanel bots = new JPanel();
	protected final List<BotDescriptor> botDescriptors = new LinkedList<>();

	private final JButton btnAddBot = new JButton("Ajouter un robot");

	private final JScrollPane scrollPane = new JScrollPane(bots);

	private final MainFrame mainFrame;

	public AIGameCreation(MainFrame mainFrame) {

		super(mainFrame);

		Logger.getGlobal().info("AIGameCreation");

		this.mainFrame = mainFrame;

		bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

		add(scrollPane);
		add(btnAddBot);

		btnAddBot.addActionListener(e -> {

			Logger.getGlobal().info("AIGameCreation.btnAddBot");

			final String botName = SettingsUtils.generateRandomProfileName();

			BotDescriptor bot = new BotDescriptor(botName, 1, Bot.AI_STRATEGY);

			JPanel renderer = createBotDescriptorRenderer(bot);

			bots.add(renderer);
			botDescriptors.add(bot);

			bots.revalidate();
			bots.repaint();
		});
	}

	@Override
	public void doLayout() {

		super.doLayout();

		Logger.getGlobal().info("AIGameCreation.doLayout");

		final int PADDING_LEFT = getWidthTimesPourcent(this, .05f);
		final int PADDING_TOP = getHeightTimesPourcent(this, .05f);

		final int SCROLLPANE_WIDTH = getWidthTimesPourcent(this, .25f);
		final int SCROLLPANE_HEIGHT = getHeightTimesPourcent(this, .12f);

		final int HEIGHT = Math.max(getWidthTimesPourcent(this, .03f), getHeightTimesPourcent(this, .03f));
		final int BTN_ADD_HEIGHT = HEIGHT/2 + HEIGHT/3;

		scrollPane.setBounds(
				getWidth() - SCROLLPANE_WIDTH - PADDING_LEFT,
				PADDING_TOP,
				SCROLLPANE_WIDTH,
				SCROLLPANE_HEIGHT);

		btnAddBot.setBounds(
				scrollPane.getX(),
				scrollPane.getY() + scrollPane.getHeight() + getHeightTimesPourcent(this, .01f),
				scrollPane.getWidth(),
				BTN_ADD_HEIGHT);
	}

	@Override
	protected void startGame(PlayBoard playBoard, Timer timer) {

		Logger.getGlobal().info("AIGameCreation.startGame");

		mainFrame.setContentPane(new MultiplayerGameScreen(mainFrame, playBoard, timer, botDescriptors));
	}

	@Override
	public List<JComponent> getSettingsComponents() {

		Logger.getGlobal().info("AIGameCreation.getSettingsComponents");

		List<JComponent> components = super.getSettingsComponents();

		components.add(bots);

		components.add(btnAddBot);

		return components;
	}

	private JPanel createBotDescriptorRenderer(BotDescriptor botDescriptor) {

		Logger.getGlobal().info("AIGameCreation.createBotDescriptorRenderer");

		JPanel panel = new JPanel();

		panel.setMaximumSize(new java.awt.Dimension(32767, 20));

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel label = new JLabel(botDescriptor.getName());
		label.setAlignmentX(LEFT_ALIGNMENT);
		panel.add(label);

		JComboBox<Integer> comboDiff = new JComboBox<>(new Integer[] { 1, 2, 3 });
		JComboBox<String> comboStrategy = new JComboBox<>(Bot.strategies);

		comboDiff.setSelectedItem(botDescriptor.getDifficulty());
		comboStrategy.setSelectedIndex(botDescriptor.getStrategy());

		comboDiff.addActionListener(e -> {

			botDescriptor.setDifficulty((int) comboDiff.getSelectedItem());
		});

		comboStrategy.addActionListener(e -> {

			botDescriptor.setStrategy((int) comboStrategy.getSelectedIndex());
		});

		panel.add(comboDiff);
		panel.add(comboStrategy);


		JButton btnRemove = new JButton("X");

		btnRemove.addActionListener(e -> {

			botDescriptors.remove(botDescriptor);

			bots.remove(panel);

			bots.revalidate();
			bots.repaint();
		});

		panel.add(btnRemove);

		return panel;
	}

	public static class BotDescriptor {

		private String name;
		private int difficulty;
		private int strategy;

		public BotDescriptor(String name, int difficulty, int strategy) {

			Logger.getGlobal().info("BotDescriptor");

			this.name = name;
			this.difficulty = difficulty;
			this.strategy = strategy;
		}

		public String getName() {

			Logger.getGlobal().info("BotDescriptor.getName");

			return name;
		}

		public int getDifficulty() {

			Logger.getGlobal().info("BotDescriptor.getDifficulty");

			return difficulty;
		}

		public int getStrategy() {

			return strategy;
		}

		public void setName(String name) {

			Logger.getGlobal().info("BotDescriptor.setName");

			this.name = name;
		}

		public void setDifficulty(int difficulty) {

			Logger.getGlobal().info("BotDescriptor.setDifficulty");

			this.difficulty = difficulty;
		}

		public void setStrategy(int strategy) {

			this.strategy = strategy;
		}

		@Override
		public String toString() {

			Logger.getGlobal().info("BotDescriptor.toString");

			return name + " (" + difficulty + ")";
		}
	}
}
