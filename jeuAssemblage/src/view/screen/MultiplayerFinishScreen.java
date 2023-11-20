package view.screen;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bot.BotThread;
import bot.interfaces.IBot;
import model.PlayBoard;
import view.MainFrame;
import view.component.GameSummary;
import view.utils.PiecesColor;

public class MultiplayerFinishScreen extends JPanel {
    
    private SoloGameFinishScreen finishScreen;

    private final JLabel lblFinishGame = new JLabel();
    private final ArrayList<GameSummary> botPanels = new ArrayList<GameSummary>();
    
    
    public MultiplayerFinishScreen(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, BotThread botThread) {

        super();

        this.setLayout(null);

        botThread.stop();

        for (IBot bot : botThread.getBots()) {
            new GameSummary(bot.getModel(), piecesColor);
        }

        this.finishScreen = new SoloGameFinishScreen(mainFrame, playBoard, piecesColor);

        this.add(this.finishScreen);
    }
    
    @Override
    public void doLayout() {
        this.finishScreen.setBounds(
            0,
            0,
            this.getWidth(),
            this.getHeight()
        );
    }
}
