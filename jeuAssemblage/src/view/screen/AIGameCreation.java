package view.screen;

import static view.utils.SwingUtils.getHeightTimesPourcent;
import static view.utils.SwingUtils.getWidthTimesPourcent;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ProfileUtils;
import view.MainFrame;
import view.utils.SwingUtils;

public class AIGameCreation extends SoloGameCreation {
	
	private final JList<BotDescriptor> bots = new JList<>();

	private final JButton btnAddBot = new JButton("Ajouter un robot");

	private final JScrollPane scrollPane = new JScrollPane(bots);

	public AIGameCreation(MainFrame mainFrame) {
		
		super(mainFrame);

		add(scrollPane);
		add(btnAddBot);

		btnAddBot.addActionListener(e -> {

			final String botName = ProfileUtils.generateRandomProfileName();

			BotDescriptor bot = new BotDescriptor(botName, 1);

			BotDescriptor[] botDescriptors = new BotDescriptor[bots.getModel().getSize() + 1];
			for (int i = 0; i < bots.getModel().getSize(); i++) {

				botDescriptors[i] = bots.getModel().getElementAt(i);
			}

			botDescriptors[bots.getModel().getSize()] = bot;

			bots.setListData(botDescriptors);
		});
	}

	@Override
	public void doLayout() {
		
		super.doLayout();

		final int PADDING_LEFT = getWidthTimesPourcent(this, .05f);
		final int PADDING_TOP = getHeightTimesPourcent(this, .05f);
		
		final int SCROLLPANE_WIDTH = getWidthTimesPourcent(this, .15f);
		final int SCROLLPANE_HEIGHT = getHeightTimesPourcent(this, .15f);

		scrollPane.setBounds(
			getWidth() - SCROLLPANE_WIDTH - PADDING_LEFT,
			PADDING_TOP,
			SCROLLPANE_WIDTH,
			SCROLLPANE_HEIGHT
		);

		btnAddBot.setBounds(
			scrollPane.getX(),
			scrollPane.getY() + scrollPane.getHeight() + SwingUtils.getHeightTimesPourcent(this, .01f),
			scrollPane.getWidth(),
			SwingUtils.getHeightTimesPourcent(this, .05f)
		);
	}

	private static class BotDescriptor {

		private String name;
		private int difficulty;

		public BotDescriptor(String name, int difficulty) {

			this.name = name;
			this.difficulty = difficulty;
		}

		public String getName() {

			return name;
		}

		public int getDifficulty() {

			return difficulty;
		}

		public void setName(String name) {

			this.name = name;
		}

		public void setDifficulty(int difficulty) {

			this.difficulty = difficulty;
		}

		@Override
		public String toString() {

			return name + " (" + difficulty + ")";
		}
	}
}
