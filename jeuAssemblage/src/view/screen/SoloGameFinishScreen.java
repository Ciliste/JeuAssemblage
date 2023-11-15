package view.screen;

import static view.utils.SwingUtils.*;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controller;
import view.MainFrame;
import view.component.GameSummary;
import view.utils.SwingUtils;

public class SoloGameFinishScreen extends JPanel {

    private GameSummary gameSummary;

    private final JLabel lblArea       = new JLabel();
    private final JLabel lblSizeX      = new JLabel();
    private final JLabel lblSizeY      = new JLabel();
    private final JLabel lblPieceCount = new JLabel();
    private final JLabel lblSeed       = new JLabel();

    private final JButton btnRecord = new JButton("Enregistrer");
    private final JButton btnSolo = new JButton("Solo");
    private final JButton btnMenu = new JButton("Menu");

    private final Controller controller = Controller.getInstance();

    public SoloGameFinishScreen() {
        setLayout(null);

        this.gameSummary = new GameSummary();

        int[] areaInfo = controller.areaInfomartion();
        this.lblArea.setText("Aire : " + ((areaInfo[2] - areaInfo[0]) * (areaInfo[3] - areaInfo[1])));

        this.lblPieceCount.setText("Nombres de pièces : " + controller.getPieceCount());
        this.lblSizeX.setText("Taille X : " + controller.getWidthBoard());
        this.lblSizeY.setText("Taille Y : " + controller.getHeightBoard());
        this.lblSeed .setText("Seed : "     + controller.getSeeed());

        
        this.btnRecord.addActionListener(e -> {
            SoloGameFinishScreen.this.controller.registerArrangement();
        });

        this.btnSolo.addActionListener(e -> {
            SoloGameFinishScreen.this.controller.finishGame();
            MainFrame.getInstance().setContentPane(new SoloGameCreation());
        });

        this.btnMenu.addActionListener(e -> {
            SoloGameFinishScreen.this.controller.finishGame();
            MainFrame.getInstance().setContentPane(new MainScreen());
        });

        this.add(this.gameSummary);

        this.add(this.lblSizeX);
        this.add(this.lblSizeY);
        this.add(this.lblPieceCount);
        this.add(this.lblSeed);
        this.add(this.btnRecord);

        this.add(this.lblArea);

        this.add(this.btnMenu);
        this.add(this.btnSolo);

        super.doLayout();
    }
    
    @Override
    public void doLayout() {
        super.doLayout();

        final int BUTTON_HEIGHT = getHeightTimesPourcent(this, .06f);
        final int BUTTON_WIDTH  = getWidthTimesPourcent (this, .07f);
        
        final int TEXT_WIDTH  = getWidthTimesPourcent(this, .15f);
        final int TEXT_HEIGHT = getHeightTimesPourcent(this, .07f);

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
            TEXT_WIDTH,
            TEXT_HEIGHT
        );
        
        final int PADDING_LEFT_RECORD = PADDING_LEFT_GRID + this.lblArea.getWidth() * 2;

        this.lblSizeX.setBounds(
            PADDING_LEFT_RECORD ,
            PADDING_TOP,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblSizeY.setBounds(
            PADDING_LEFT_RECORD,
            PADDING_TOP + this.lblSizeX.getHeight(),
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblPieceCount.setBounds(
            PADDING_LEFT_RECORD,
            PADDING_TOP + this.lblSizeX.getHeight() * 2,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblSeed.setBounds(
            PADDING_LEFT_RECORD,
            PADDING_TOP + this.lblSizeX.getHeight() * 3,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.btnRecord.setBounds(
            PADDING_LEFT_RECORD,
            PADDING_TOP + this.lblSizeX.getHeight() * 4,
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        // Buttons
        this.btnMenu.setBounds(
            PADDING_LEFT_GRID,
            this.getHeight() - getHeightTimesPourcent(this, .11f),
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        this.btnSolo.setBounds(
            PADDING_LEFT_GRID + this.btnMenu.getWidth() + PADDING_LEFT,
            this.getHeight() - getHeightTimesPourcent(this, .11f),
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );
        
    }
    
    @Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		SwingUtils.drawDebugBounds(this, g);
	}
    
}
