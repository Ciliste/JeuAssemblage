package view.screen;

import static view.utils.SwingUtils.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

import model.PlayBoard;
import utils.EDifficulty;
import view.MainFrame;
import view.component.Separator;
import view.component.board.Grid;
import view.utils.DocumentAdapter;

public class SoloGameCreation extends JPanel {
    
    private final JButton btnCancel = new JButton("<--");

    private final JLabel lblSeed = new JLabel("Seed :");
    private final JTextField txtSeed = new JTextField();

    private final JButton btnRandomSeed = new JButton("Random");

    private final Separator separator = new Separator();

    private final JLabel lblSizeX = new JLabel("Size X :");
    private final JTextField txtSizeX = new JTextField();

    private final JLabel lblSizeY = new JLabel("Size Y :");
    private final JTextField txtSizeY = new JTextField();

    private final JLabel lblNbPieces = new JLabel("Nb pièces :");
    private final JSpinner nbPiecesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

	private final JLabel lblDifficulty = new JLabel("Difficulté :");
	private final JList<String> difficultyList = new JList<String>(EDifficulty.getDifficultysName());

	private final JSpinner nbMinutesSpinner = new JSpinner();
	private final JSpinner nbSecondsSpinner = new JSpinner();
	private final JCheckBox timeLimitCheckBox = new JCheckBox("Temps limité");

	private JPanel gridPreview = new JPanel();

    private final JButton btnPlay = new JButton("Jouer");
    
	private final MainFrame mainFrame;

    public SoloGameCreation(MainFrame mainFrame) {

		super();

		this.mainFrame = mainFrame;

        this.setLayout(null);

        this.add(btnCancel);

        this.add(lblSeed);
        this.add(txtSeed);

        this.add(btnRandomSeed);

        this.add(separator);

        this.add(lblSizeX);
        this.add(txtSizeX);

        this.add(lblSizeY);
        this.add(txtSizeY);

        this.add(lblNbPieces);
        this.add(nbPiecesSpinner);

		this.add(lblDifficulty);
		this.add(difficultyList);
		difficultyList.setSelectedIndex(1);

		this.add(nbMinutesSpinner);
		this.add(nbSecondsSpinner);
		this.add(timeLimitCheckBox);

		this.add(gridPreview);

		this.add(btnPlay);

        // LISTENERS

		// ResizeListener resizeListener = new ResizeListener(createResizeCallback(this));
        // this.addComponentListener(resizeListener);

        btnCancel.addActionListener(e -> mainFrame.setContentPane(new MainScreen(mainFrame)));

        final Runnable randomSeedCallback = createRandomSeedCallback(this);
        btnRandomSeed.addActionListener(e -> randomSeedCallback.run());
        randomSeedCallback.run();

        txtSeed.getDocument().addDocumentListener(new SeedDocumentListener());

		txtSizeX.getDocument().addDocumentListener(new SizeDocumentListener());
		txtSizeY.getDocument().addDocumentListener(new SizeDocumentListener());

		difficultyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seedUpdated(Long.parseLong(txtSeed.getText()));
				updateGridPreview();
            }
        });

		nbPiecesSpinner.addChangeListener(e -> updateGridPreview());

		btnPlay.addActionListener(e -> {

			PlayBoard playBoard = PlayBoard.constructPlayBoard(
				Long.parseLong(txtSeed.getText()), 
				Integer.parseInt(txtSizeX.getText()),
				Integer.parseInt(txtSizeY.getText()), 
				(int) nbPiecesSpinner.getValue(), 
				EDifficulty.getDifficultyFromName(difficultyList.getSelectedValue())
			);

			mainFrame.setContentPane(new SoloGameScreen(mainFrame, playBoard));
		});

		updateGridPreview();

		revalidate();
    }

	@Override
	public void doLayout() {

		final int PADDING_LEFT = getWidthTimesPourcent(this, .05f);

		final int PADDING_TOP = getHeightTimesPourcent(this, .05f);

		final int BTN_CANCEL_WIDTH = Math.max(getWidthTimesPourcent(this, .03f), getHeightTimesPourcent(this, .03f));
		final int BTN_CANCEL_HEIGHT = BTN_CANCEL_WIDTH;

		btnCancel.setBounds(
			PADDING_LEFT, 
			PADDING_TOP, 
			BTN_CANCEL_WIDTH, 
			BTN_CANCEL_HEIGHT
		);

		final int PADDING_TOP_LBL_SEED = PADDING_TOP + BTN_CANCEL_HEIGHT + getHeightTimesPourcent(this, .05f);

		final int LBL_WIDTH = getWidthTimesPourcent(this, .1f);

		lblSeed.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED, 
			LBL_WIDTH, 
			BTN_CANCEL_HEIGHT
		);

		txtSeed.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT, 
			getWidthTimesPourcent(this, .2f), 
			BTN_CANCEL_HEIGHT
		);

		btnRandomSeed.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .2f),
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT,
			BTN_CANCEL_WIDTH,
			BTN_CANCEL_HEIGHT 
		);

		separator.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 2, 
			getWidthTimesPourcent(this, .9f), 
			BTN_CANCEL_HEIGHT
		);

		lblSizeX.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 3, 
			LBL_WIDTH, 
			BTN_CANCEL_HEIGHT
		);

		txtSizeX.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 4, 
			getWidthTimesPourcent(this, .2f), 
			BTN_CANCEL_HEIGHT
		);

		lblSizeY.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 5, 
			LBL_WIDTH, 
			BTN_CANCEL_HEIGHT
		);

		txtSizeY.setBounds(
			PADDING_LEFT, 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 6, 
			getWidthTimesPourcent(this, .2f), 
			BTN_CANCEL_HEIGHT
		);

		lblNbPieces.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .3f), 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 3, 
			LBL_WIDTH, 
			BTN_CANCEL_HEIGHT
		);

		nbPiecesSpinner.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .3f), 
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 4, 
			getWidthTimesPourcent(this, .2f), 
			BTN_CANCEL_HEIGHT
		);

		lblDifficulty.setBounds(
			PADDING_LEFT,
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 7,
			LBL_WIDTH,
			BTN_CANCEL_HEIGHT
		);

		difficultyList.setBounds(
			PADDING_LEFT,
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 8,
			getWidthTimesPourcent(this, .2f),
			BTN_CANCEL_HEIGHT * 3
		);

		nbMinutesSpinner.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .3f),
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 7,
			getWidthTimesPourcent(this, .1f),
			BTN_CANCEL_HEIGHT
		);

		nbSecondsSpinner.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .3f) + getWidthTimesPourcent(this, .1f),
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 7,
			getWidthTimesPourcent(this, .1f),
			BTN_CANCEL_HEIGHT
		);

		timeLimitCheckBox.setBounds(
			PADDING_LEFT + getWidthTimesPourcent(this, .3f),
			PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 8,
			getWidthTimesPourcent(this, .2f),
			BTN_CANCEL_HEIGHT
		);

		final int PADDING_RIGHT = PADDING_LEFT;
		final int PADDING_BOTTOM = PADDING_TOP;

		gridPreview.setBounds(
			getWidth() - PADDING_RIGHT - getWidthTimesPourcent(this, .3f),
			getHeightTimesPourcent(this, .33f),
			getWidthTimesPourcent(this, .3f),
			getWidthTimesPourcent(this, .3f)
		);

		btnPlay.setBounds(
			getWidth() - PADDING_RIGHT - BTN_CANCEL_WIDTH * 5,
			getHeight() - PADDING_BOTTOM - BTN_CANCEL_HEIGHT,
			BTN_CANCEL_WIDTH * 5,
			BTN_CANCEL_HEIGHT
		);
	}

    private void seedUpdated(long seed) {

		EDifficulty difficulty = EDifficulty.getDifficultyFromName(difficultyList.getSelectedValue());

        txtSizeX.setText(String.valueOf(PlayBoard.getSizeXBySeedAndDifficulty(seed, difficulty)));
        txtSizeY.setText(String.valueOf(PlayBoard.getSizeYBySeedAndDifficulty(seed, difficulty)));
        nbPiecesSpinner.setValue(PlayBoard.getPiecesCountBySeedAndDifficulty(seed, difficulty));
    }

	private void updateGridPreview() {

		EDifficulty difficulty = EDifficulty.getDifficultyFromName(difficultyList.getSelectedValue());

		remove(gridPreview);

		gridPreview = new Grid(PlayBoard.constructPlayBoard(
			Long.parseLong(txtSeed.getText()), 
			Integer.parseInt(txtSizeX.getText()),
			Integer.parseInt(txtSizeY.getText()), 
			(int) nbPiecesSpinner.getValue(), 
			difficulty
		), true);

		add(gridPreview);

		revalidate();
		repaint();
	}

    private static long generateRandomSeed() {

        return (long) (Math.random() * Long.MAX_VALUE);
    }

    private static Runnable createRandomSeedCallback(SoloGameCreation soloGameCreation) {

        return () -> {

            final long seed = generateRandomSeed();
            soloGameCreation.seedUpdated(seed);
            soloGameCreation.txtSeed.setText(String.valueOf(seed));
        };
    }

    private class SeedDocumentListener extends DocumentAdapter {

        @Override
        public void insertUpdate(DocumentEvent e) {

            callback(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {

            callback(e);
        }

        private void callback(DocumentEvent e) {

            try {

				String text = txtSeed.getText();
                long seed = Long.parseLong(text);
                seedUpdated(seed);
            } 
            catch (NumberFormatException ignored) {}
        }
    }

	private class SizeDocumentListener extends DocumentAdapter {

		@Override
		public void insertUpdate(DocumentEvent e) {

			callback(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {

			callback(e);
		}

		private void callback(DocumentEvent e) {

			try {

				Integer.parseInt(txtSizeX.getText());
				Integer.parseInt(txtSizeY.getText());
				
				updateGridPreview();
			} 
			catch (NumberFormatException ignored) {}
		}
	}
}
