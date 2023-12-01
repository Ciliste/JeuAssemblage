package view.screen;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bot.BotThread;
import bot.interfaces.IBot;
import model.PlayBoard;
import view.MainFrame;
import view.component.GameSummary;
import view.screen.AIGameCreation.BotDescriptor;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

public class MultiplayerFinishScreen extends JPanel {
    
    private SoloGameFinishScreen finishScreen;

	private final JScrollPane scrollPane = new JScrollPane();   
    
    public MultiplayerFinishScreen(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, BotThread botThread, List<PlayBoard> botPlayboard) {

        super();

        this.setLayout(null);

        this.finishScreen = new SoloGameFinishScreen(mainFrame, playBoard, piecesColor);
        JPanel bots = new JPanel();
        bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

        List<IBot> botLst = botThread.getBots();
        for (int i = 0; i < botLst.size(); i++) {

            PlayBoard p = botPlayboard.get(i);

            GameSummary gTemp = new GameSummary(p, piecesColor);
            bots.add(gTemp);

        }

        this.scrollPane.setViewportView(bots);

        this.add(this.finishScreen);
        this.add(this.scrollPane);
    }
    
    @Override
    public void doLayout() {
        super.doLayout();

		this.finishScreen.setBounds(
			0,
		 	0,
		 	SwingUtils.getWidthTimesPourcent(this, .8f),
			SwingUtils.getHeightTimesPourcent(this, 1f)
		);

		this.scrollPane.setBounds(
			SwingUtils.getWidthTimesPourcent(this, .8f),
			0,
			SwingUtils.getWidthTimesPourcent(this, .2f),
			getHeight()
		);
    }
}
