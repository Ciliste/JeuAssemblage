package view.screen;

import static view.utils.SwingUtils.*;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;

import model.PlayBoard;
import model.arrangement.ArrangementList;
import utils.EDifficulty;
import utils.SeedUtils;
import view.MainFrame;
import view.component.Separator;
import view.component.board.Grid;
import view.component.timer.Timer;
import view.utils.DocumentAdapter;

public class SoloGameCreation extends JPanel {
    
    private final JButton btnCancel = new JButton("<--");

    private final JLabel lblSeed = new JLabel("Seed :");
    protected final JTextField txtSeed = new JTextField();

    private final JButton btnRandomSeed = new JButton("Random");

    private final Separator separator = new Separator();

    private final JLabel lblSizeX = new JLabel("Size X :");
    protected final JTextField txtSizeX = new JTextField();

    private final JLabel lblSizeY = new JLabel("Size Y :");
    protected final JTextField txtSizeY = new JTextField();

    private final JLabel lblNbPieces = new JLabel("Nb pièces :");
    protected final JSpinner nbPiecesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

	private final JLabel lblDifficulty = new JLabel("Difficulté :");
	protected final JList<String> difficultyList = new JList<String>(EDifficulty.getDifficultysName());

	protected final JLabel lblArrangement = new JLabel("Parties Enregistrées :");
	protected final JTable tableArrangement = new JTable(new ArrangementList());
	protected final JScrollPane scrollArrangement = new JScrollPane(tableArrangement);

	protected final JSpinner nbMinutesSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
	protected final JSpinner nbSecondsSpinner = new JSpinner(new SpinnerNumberModel(0, -1, 60, 1));
	protected final JCheckBox timeLimitCheckBox = new JCheckBox("Temps limité");

	private JPanel gridPreview = new JPanel();

    private final JButton btnPlay = new JButton("Jouer");
    
	private final MainFrame mainFrame;

    public SoloGameCreation(MainFrame mainFrame) {

		super();

		Logger.getGlobal().info("Solo game creation");

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

		txtSizeX.setText("10");
		txtSizeY.setText("10");

        this.add(lblNbPieces);
        this.add(nbPiecesSpinner);

		this.add(lblDifficulty);
		this.add(difficultyList);
		difficultyList.setSelectedIndex(1);

		this.add(nbMinutesSpinner);
		this.add(nbSecondsSpinner);
		this.add(timeLimitCheckBox);

		this.add(lblArrangement);
		this.add(scrollArrangement);

		this.add(gridPreview);

		this.add(btnPlay);

		this.nbMinutesSpinner.setValue(5);
		this.nbSecondsSpinner.setValue(0);
		this.timeLimitCheckBox.setSelected(true);

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
				(int) nbPiecesSpinner.getValue()
			);

			Timer timer = null;
			if (timeLimitCheckBox.isSelected()) {

				timer = new Timer((int) nbMinutesSpinner.getValue(), (int) nbSecondsSpinner.getValue());
			}
			else {

				timer = Timer.NO_TIMER;
			}

			startGame(playBoard, timer);
		});

		nbMinutesSpinner.setEnabled(timeLimitCheckBox.isSelected());
		nbSecondsSpinner.setEnabled(timeLimitCheckBox.isSelected());

		timeLimitCheckBox.addActionListener(e -> {
			nbMinutesSpinner.setEnabled(timeLimitCheckBox.isSelected());
			nbSecondsSpinner.setEnabled(timeLimitCheckBox.isSelected());
		});

		nbSecondsSpinner.addChangeListener(e -> {

			if ((int) nbSecondsSpinner.getValue() == 60) {

				nbSecondsSpinner.setValue(0);
				nbMinutesSpinner.setValue((int) nbMinutesSpinner.getValue() + 1);
			}
			else if ((int) nbSecondsSpinner.getValue() == -1) {

				nbSecondsSpinner.setValue(59);
				nbMinutesSpinner.setValue((int) nbMinutesSpinner.getValue() - 1);
			}
		});

		tableArrangement.getSelectionModel().addListSelectionListener(e->{
			if (e.getValueIsAdjusting()) return;
			SoloGameCreation.this.setParams(tableArrangement.getSelectedRow());
		});

		updateGridPreview();

		revalidate();
    }

	protected void startGame(PlayBoard playBoard, Timer timer) {

		Logger.getGlobal().info("Start game");

		mainFrame.setContentPane(new SoloGameScreen(mainFrame, playBoard, timer));
	}

	@Override
	public void doLayout() {

		Logger.getGlobal().info("Do layout");

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

		lblArrangement.setBounds(
			PADDING_LEFT,
			difficultyList.getY() + difficultyList.getHeight() + BTN_CANCEL_HEIGHT,
			LBL_WIDTH,
			BTN_CANCEL_HEIGHT
		);

		scrollArrangement.setBounds(
			PADDING_LEFT,
			difficultyList.getY() + difficultyList.getHeight() + BTN_CANCEL_HEIGHT * 2,
			getWidthTimesPourcent(this, .35f),
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

		Logger.getGlobal().info("Seed updated: " + seed);

		// EDifficulty difficulty = EDifficulty.getDifficultyFromName(difficultyList.getSelectedValue());

		// txtSizeX.setText(String.valueOf(PlayBoard.getSizeXBySeedAndDifficulty(seed, difficulty)));
		// txtSizeY.setText(String.valueOf(PlayBoard.getSizeYBySeedAndDifficulty(seed, difficulty)));
		// nbPiecesSpinner.setValue(PlayBoard.getPiecesCountBySeedAndDifficulty(seed, difficulty));

		updateGridPreview();
	}
	
	private void setParams(int index) {

		Logger.getGlobal().info("Set params: " + index);

		int sizeX = (Integer) this.tableArrangement.getValueAt(index, 0);
		int sizeY = (Integer) this.tableArrangement.getValueAt(index, 1);
		int pieceCount = (Integer) this.tableArrangement.getValueAt(index, 2);
		long seed = (Long) this.tableArrangement.getValueAt(index, 3);

		this.txtSeed.setText(seed + "");
		this.txtSizeX.setText(sizeX + "");
		this.txtSizeY.setText(sizeY + "");
		this.nbPiecesSpinner.setValue(pieceCount);
	}

	protected void updateGridPreview() {

		Logger.getGlobal().info("Update grid preview");

		JPanel temp = null;
		try {
			PlayBoard p = PlayBoard.constructPlayBoard(
				Long.parseLong(txtSeed.getText()), 
				Integer.parseInt(txtSizeX.getText()),
				Integer.parseInt(txtSizeY.getText()), 
					(int) nbPiecesSpinner.getValue());
			temp = new Grid(p, true);	
		} 
		catch (Exception ignored) {}

		if (temp != null) {

			remove(gridPreview);
			gridPreview = temp;
			add(gridPreview);

			revalidate();
			repaint();
		}
	}

	public List<JComponent> getSettingsComponents() {

		Logger.getGlobal().info("Get settings components");

		List<JComponent> components = new LinkedList<>();

		components.add(txtSeed);
		components.add(btnRandomSeed);
		components.add(txtSizeX);
		components.add(txtSizeY);
		components.add(nbPiecesSpinner);
		components.add(difficultyList);
		components.add(nbMinutesSpinner);
		components.add(nbSecondsSpinner);
		components.add(timeLimitCheckBox);
		components.add(tableArrangement);
		components.add(btnPlay);

		return components;
	}

    private static Runnable createRandomSeedCallback(SoloGameCreation soloGameCreation) {

        return () -> {

			Logger.getGlobal().info("Random seed callback");

            final long seed = SeedUtils.generateRandomSeed();
			System.out.println(seed);
//            soloGameCreation.seedUpdated(seed);
            soloGameCreation.txtSeed.setText("" + seed);
        };
    }

    private class SeedDocumentListener extends DocumentAdapter {

		@Override
		public void changedUpdate(DocumentEvent e) {

			callback(e);
		}

		@Override
        public void insertUpdate(DocumentEvent e) {

            callback(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {

            callback(e);
        }

        private void callback(DocumentEvent e) {

			Logger.getGlobal().info("Seed document listener callback");

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

			Logger.getGlobal().info("Size document listener callback");

			try {

				Integer.parseInt(txtSizeX.getText());
				Integer.parseInt(txtSizeY.getText());
				
				updateGridPreview();
			} 
			catch (NumberFormatException ignored) {}
		}
	}
}
