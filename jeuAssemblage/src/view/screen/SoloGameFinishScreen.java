package view.screen;

import static view.utils.SwingUtils.*;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.PlayBoard;
import model.arrangement.Arrangement;
import model.arrangement.ArrangementList;
import view.MainFrame;
import view.component.GameSummary;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

public class SoloGameFinishScreen extends JPanel {

    private GameSummary gameSummary;

    private final JLabel lblArea = new JLabel();

    private final JLabel lblSizeX      = new JLabel();
    private final JLabel lblSizeY      = new JLabel();
    private final JLabel lblPieceCount = new JLabel();
    private final JLabel lblSeed       = new JLabel();
    
    private final JLabel lblUsername = new JLabel("Username : ");
    private final JTextField txtUsername = new JTextField();

    private final JLabel lblInfo       = new JLabel();


    private final JButton btnSolo = new JButton("Solo");
    private final JButton btnMenu = new JButton("Menu");
    private final JButton btnRecord = new JButton("Enregistrer");

	private final PlayBoard playBoard;

    public SoloGameFinishScreen(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor) {

		this.playBoard = playBoard;

        setLayout(null);

        this.gameSummary = new GameSummary(playBoard, piecesColor);

        this.lblArea      .setText("Aire : " + playBoard.getArea());
        this.lblPieceCount.setText("Nombres de pièces : " + playBoard.getPiecesCount());
        this.lblSizeX     .setText("Taille X : " + playBoard.getBoardWidth());
        this.lblSizeY     .setText("Taille Y : " + playBoard.getBoardHeight());
        this.lblSeed      .setText("Seed : "     + playBoard.getSeed());

        this.btnRecord.addActionListener(e -> {

            if (txtUsername.getText().equals("")) {

                this.lblInfo.setForeground(Color.RED);
                this.lblInfo.setText("L'username doit contenir au moins un caractère");
            } else {

                ArrangementList.addArrangement(playBoard, txtUsername.getText());
                this.btnRecord.setFocusPainted(false);

                this.lblInfo.setForeground(Color.GREEN);
                this.lblInfo.setText("Enregistré !");
            }
        });

        this.btnSolo.addActionListener(e -> {

            mainFrame.setContentPane(new SoloGameCreation(mainFrame));
        });

        this.btnMenu.addActionListener(e -> {
            
            mainFrame.setContentPane(new MainScreen(mainFrame));
        });

        this.add(this.lblSizeX);
        this.add(this.lblSizeY);
        this.add(this.lblPieceCount);
        this.add(this.lblSeed);
        this.add(this.lblUsername);
        this.add(this.txtUsername);

        this.add(this.btnRecord);

        this.add(this.lblInfo);


        this.add(this.gameSummary);
        this.add(this.lblArea);
        this.add(this.btnMenu);
        this.add(this.btnSolo);        

        super.doLayout();
    }
    
    @Override
    public void doLayout() {

        super.doLayout();

        final int TEXT_WIDTH  = getWidthTimesPourcent(this, .15f);
        final int TEXT_HEIGHT = getHeightTimesPourcent(this, .07f);

        final int BUTTON_HEIGHT = getHeightTimesPourcent(this, .06f);
        final int BUTTON_WIDTH  = getWidthTimesPourcent (this, .07f);

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

        this.lblSizeX.setBounds(
            PADDING_LEFT_GRID ,
            PADDING_TOP + TEXT_HEIGHT * 1,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblSizeY.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 2,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblPieceCount.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 3,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblSeed.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 4,
            TEXT_WIDTH,
            TEXT_HEIGHT
        );

        this.lblUsername.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 5,
            TEXT_WIDTH / 2,
            TEXT_HEIGHT
        );

        this.txtUsername.setBounds(
            PADDING_LEFT_GRID + TEXT_WIDTH / 2  ,
            PADDING_TOP + TEXT_HEIGHT * 5 + TEXT_HEIGHT / 3,
            TEXT_WIDTH,
            TEXT_HEIGHT / 2
        );

        this.btnRecord.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 6,
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        this.lblInfo.setBounds(
            PADDING_LEFT_GRID,
            PADDING_TOP + TEXT_HEIGHT * 7,
            TEXT_WIDTH * 2,
            TEXT_HEIGHT
        );

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
    
}
