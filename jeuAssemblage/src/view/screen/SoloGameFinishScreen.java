package view.screen;

import static view.utils.SwingUtils.*;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.PlayBoard;
import view.MainFrame;
import view.component.GameSummary;
import view.utils.SwingUtils;

public class SoloGameFinishScreen extends JPanel {

    private GameSummary gameSummary;

    private final JLabel lblArea = new JLabel();

    private final JButton btnSolo = new JButton("Solo");
    private final JButton btnMenu = new JButton("Menu");

	private final PlayBoard playBoard;

    public SoloGameFinishScreen(PlayBoard playBoard) {

		this.playBoard = playBoard;

        setLayout(null);

        this.gameSummary = new GameSummary(playBoard);

        // int[] areaInfo = controller.areaInfomartion();
        // this.lblArea.setText("Aire : " + ((areaInfo[2] - areaInfo[0]) * (areaInfo[3] - areaInfo[1])));

        // this.btnSolo.addActionListener(e -> {
        //     Controller.getInstance().finishGame();
        //     MainFrame.getInstance().setContentPane(new SoloGameCreation());
        // });

        // this.btnMenu.addActionListener(e -> {
        //     Controller.getInstance().finishGame();
        //     MainFrame.getInstance().setContentPane(new MainScreen());
        // });

        // this.add(this.gameSummary);
        // this.add(this.lblArea);
        // this.add(this.btnMenu);
        // this.add(this.btnSolo);

        // super.doLayout();
    }
    
    @Override
    public void doLayout() {
        super.doLayout();

        final int PADDING_LEFT = getWidthTimesPourcent (this, .05f);
        final int PADDING_TOP  = getHeightTimesPourcent(this, .05f);

        this.gameSummary.setBounds(
            PADDING_LEFT,
            PADDING_TOP,
            getWidthTimesPourcent (this, .4f),
            getHeightTimesPourcent(this, .9f)
        );

        final int PADDING_LEFT_GRID = PADDING_LEFT * 2 + this.gameSummary.getWidth();
        this.lblArea.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP,
            getWidthTimesPourcent (this, .1f),
            getHeightTimesPourcent(this, .2f)
        );

        this.btnMenu.setBounds(
            PADDING_LEFT_GRID,
            this.getHeight() - getHeightTimesPourcent(this, .11f),
            getWidthTimesPourcent (this, .07f),
            getHeightTimesPourcent(this, .06f)
        );

        this.btnSolo.setBounds(
            PADDING_LEFT_GRID + this.btnMenu.getWidth() + PADDING_LEFT,
            this.getHeight() - getHeightTimesPourcent(this, .11f),
            getWidthTimesPourcent (this, .07f),
            getHeightTimesPourcent(this, .06f)
        );
        
    }
    
    @Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
    
}
