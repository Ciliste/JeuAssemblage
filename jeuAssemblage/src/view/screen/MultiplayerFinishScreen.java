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
import view.utils.SwingUtils;

public class MultiplayerFinishScreen extends JPanel {
    
    private SoloGameFinishScreen finishScreen;

    private final JLabel lblFinishGame = new JLabel("Le gagnant est le ");
	private final JScrollPane scrollPane = new JScrollPane();   
    
    public MultiplayerFinishScreen(MainFrame mainFrame, PlayBoard playBoard, BotThread botThread, List<BotDescriptor> botDescriptor) {

        super();

        this.setLayout(null);

        botThread.stop();

        this.finishScreen = new SoloGameFinishScreen(mainFrame, playBoard);
        JPanel bots = new JPanel();
        bots.setLayout(new BoxLayout(bots, BoxLayout.Y_AXIS));

        String winnerName = "Joueur";
        int minArea = playBoard.getArea();

        List<IBot> botLst = botThread.getBots();
        for (int i = 0; i < botLst.size(); i++) {

            IBot bot = botLst.get(i);
            String name = botDescriptor.get(i).getName();

            GameSummary gTemp = new GameSummary(bot.getModel());
            bots.add(gTemp);

            if (bot.getModel().getArea() < minArea) {
                winnerName = name;
            }
        }

        this.lblFinishGame.setText(this.lblFinishGame.getText() + winnerName);
        
        this.scrollPane.setViewportView(bots);

        this.add(this.lblFinishGame);
        this.add(this.finishScreen);
        this.add(this.scrollPane);
    }
    
    @Override
    public void doLayout() {
        super.doLayout();

        this.lblFinishGame.setBounds(
            0,
		 	0,
		 	SwingUtils.getWidthTimesPourcent(this, .8f),
			SwingUtils.getHeightTimesPourcent(this, .1f)
        );

		this.finishScreen.setBounds(
			0,
		 	SwingUtils.getHeightTimesPourcent(this, .1f),
		 	SwingUtils.getWidthTimesPourcent(this, .8f),
			SwingUtils.getHeightTimesPourcent(this, .9f)
		);

		this.scrollPane.setBounds(
			SwingUtils.getWidthTimesPourcent(this, .8f),
			0,
			SwingUtils.getWidthTimesPourcent(this, .2f),
			getHeight()
		);
    }
}
