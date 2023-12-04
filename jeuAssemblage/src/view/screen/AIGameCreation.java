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

			BotDescriptor bot = new BotDescriptor(botName, 1);

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
		final int SCROLLPANE_HEIGHT = getHeightTimesPourcent(this, .15f);

		scrollPane.setBounds(
				getWidth() - SCROLLPANE_WIDTH - PADDING_LEFT,
				PADDING_TOP,
				SCROLLPANE_WIDTH,
				SCROLLPANE_HEIGHT);

		btnAddBot.setBounds(
				scrollPane.getX(),
				scrollPane.getY() + scrollPane.getHeight() + SwingUtils.getHeightTimesPourcent(this, .01f),
				scrollPane.getWidth(),
				SwingUtils.getHeightTimesPourcent(this, .05f));
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

		JComboBox<Integer> comboBox = new JComboBox<>(new Integer[] { 1, 2, 3, 4, 5 });
		comboBox.setSelectedItem(botDescriptor.getDifficulty());

		comboBox.addActionListener(e -> {

			botDescriptor.setDifficulty((int) comboBox.getSelectedItem());
		});

		panel.add(comboBox);

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

		public BotDescriptor(String name, int difficulty) {

			Logger.getGlobal().info("BotDescriptor");

			this.name = name;
			this.difficulty = difficulty;
		}

		public String getName() {

			Logger.getGlobal().info("BotDescriptor.getName");

			return name;
		}

		public int getDifficulty() {

			Logger.getGlobal().info("BotDescriptor.getDifficulty");

			return difficulty;
		}

		public void setName(String name) {

			Logger.getGlobal().info("BotDescriptor.setName");

			this.name = name;
		}

		public void setDifficulty(int difficulty) {

			Logger.getGlobal().info("BotDescriptor.setDifficulty");

			this.difficulty = difficulty;
		}

		@Override
		public String toString() {

			Logger.getGlobal().info("BotDescriptor.toString");

			return name + " (" + difficulty + ")";
		}
	}
}
