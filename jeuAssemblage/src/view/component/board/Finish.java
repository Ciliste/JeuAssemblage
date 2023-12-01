package view.component.board;

import static view.utils.SwingUtils.*;

import java.awt.Graphics;
import java.awt.Point;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bot.BotThread;
import model.PlayBoard;
import model.listener.IPlayBoardListener;
import view.MainFrame;
import view.screen.SoloGameFinishScreen;
import view.utils.PiecesColor;
import view.utils.SwingUtils;

public class Finish extends JPanel {

    private final JLabel lblPreciseArea = new JLabel();
    private final JLabel lblArea = new JLabel();

    private final JButton btnFinish = new JButton("Finir");

    private final MainFrame mainFrame;
    private final PlayBoard playBoard;

    public Finish(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor) {

        this(mainFrame, playBoard, piecesColor, null, new SoloGameFinishScreen(mainFrame, playBoard, piecesColor));
    }

    public Finish(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, JPanel finishScreen) {

        this(mainFrame, playBoard, piecesColor, null, finishScreen);
    }

    public Finish(MainFrame mainFrame, PlayBoard playBoard, PiecesColor piecesColor, BotThread botThread, JPanel finishScreen) {

        super();

        this.mainFrame = mainFrame;
        this.playBoard = playBoard;

        this.playBoard.addPlayBoardListener(new IPlayBoardListener() {

            @Override
            public void pieceAdded(Object source, int pieceId) {

                update();
            }

            @Override
            public void pieceRemoved(Object source, int pieceId) {

                update();
            }

            @Override
            public void pieceMoved(Object source, int pieceId) {

                update();
            }
        });

        this.setLayout(null);

        this.btnFinish.addActionListener(e -> {

            if (botThread != null) { botThread.stop(); }
            mainFrame.setContentPane(finishScreen);
        });

        this.add(this.lblPreciseArea);
        this.add(this.lblArea);
        this.add(this.btnFinish);

        this.setVisible(true);
    }

    @Override
    public void doLayout() {

        final int PADDING = getWidthTimesPourcent(this, .05f);

        this.lblArea.setBounds(
                PADDING,
                PADDING,
                getWidthTimesPourcent(this, .9f),
                getHeightTimesPourcent(this, .2f));

        final int PADDING_AREA = PADDING + getHeightTimesPourcent(this, .3f);
        this.lblPreciseArea.setBounds(
                PADDING,
                PADDING_AREA,
                getWidthTimesPourcent(this, .9f),
                getHeightTimesPourcent(this, .2f));

        final int PADDING_BUTTON = PADDING_AREA + getHeightTimesPourcent(this, .3f);
        this.btnFinish.setBounds(
                PADDING,
                PADDING_BUTTON,
                getWidthTimesPourcent(this, .9f),
                getHeightTimesPourcent(this, .2f));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        SwingUtils.drawDebugBounds(this, g);
    }

    // @Override
    // public void update() {
    // 	this.repaint();
    // }

    public void update() {

        Point upperLeft = playBoard.getUpperLeftPieceCorner();
        Point lowerRight = playBoard.getLowerRightPieceCorner();

        int area = (lowerRight.x - upperLeft.x) * (lowerRight.y - upperLeft.y);

        lblArea.setText("Aire : " + area);

        //int[] areaInfo = this.controller.areaInfomartion();
        //lblPreciseArea.setText("Nombre de carrés : " + areaInfo[4]);
        //lblArea.setText("Aire : " + ((areaInfo[2] - areaInfo[0]) * (areaInfo[3] - areaInfo[1])));
    }
}
