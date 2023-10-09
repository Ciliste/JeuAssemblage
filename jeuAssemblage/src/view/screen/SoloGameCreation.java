package view.screen;

import static view.utils.SwingUtils.*;

import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.component.Separator;
import view.listener.ResizeListener;
import view.utils.DocumentAdapter;
import view.utils.SwingUtils;

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
    private final JSpinner nbPiecesSpinner = new JSpinner();

    private final JButton btnPlay = new JButton("Jouer");

    public SoloGameCreation(Runnable backCallback) {

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

        // LISTENERS
        //

        this.addComponentListener(new ResizeListener(createResizeCallback(this)));

        btnCancel.addActionListener(e -> backCallback.run());

        final Runnable randomSeedCallback = createRandomSeedCallback(this);
        btnRandomSeed.addActionListener(e -> randomSeedCallback.run());
        randomSeedCallback.run();

        txtSeed.getDocument().addDocumentListener(new SeedDocumentListener());
    }

    private void updateSeed(long seed) {

        Random random = new Random(seed);

        // TODO: Le controlleur devrait s'occuper de ça
        txtSizeX.setText(String.valueOf(random.nextInt(10) + 5));
        txtSizeY.setText(String.valueOf(random.nextInt(10) + 5));
        nbPiecesSpinner.setValue(random.nextInt(10) + 5);
    }

    private static Runnable createResizeCallback(SoloGameCreation soloGameCreation) {

        return () -> {

            final int PADDING_LEFT = getWidthTimesPourcent(soloGameCreation, .05f);

            final int PADDING_TOP = getHeightTimesPourcent(soloGameCreation, .05f);

            final int BTN_CANCEL_WIDTH = Math.max(getWidthTimesPourcent(soloGameCreation, .03f), getHeightTimesPourcent(soloGameCreation, .03f));
            final int BTN_CANCEL_HEIGHT = BTN_CANCEL_WIDTH;

            soloGameCreation.btnCancel.setBounds(
                PADDING_LEFT, 
                PADDING_TOP, 
                BTN_CANCEL_WIDTH, 
                BTN_CANCEL_HEIGHT
            );

            final int PADDING_TOP_LBL_SEED = PADDING_TOP + BTN_CANCEL_HEIGHT + getHeightTimesPourcent(soloGameCreation, .05f);

            final int LBL_WIDTH = getWidthTimesPourcent(soloGameCreation, .1f);

            soloGameCreation.lblSeed.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED, 
                LBL_WIDTH, 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.txtSeed.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT, 
                getWidthTimesPourcent(soloGameCreation, .2f), 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.btnRandomSeed.setBounds(
                PADDING_LEFT + getWidthTimesPourcent(soloGameCreation, .2f),
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT,
                BTN_CANCEL_WIDTH,
                BTN_CANCEL_HEIGHT 
            );

            soloGameCreation.separator.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 2, 
                getWidthTimesPourcent(soloGameCreation, .9f), 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.lblSizeX.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 3, 
                LBL_WIDTH, 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.txtSizeX.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 4, 
                getWidthTimesPourcent(soloGameCreation, .2f), 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.lblSizeY.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 5, 
                LBL_WIDTH, 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.txtSizeY.setBounds(
                PADDING_LEFT, 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 6, 
                getWidthTimesPourcent(soloGameCreation, .2f), 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.lblNbPieces.setBounds(
                PADDING_LEFT + getWidthTimesPourcent(soloGameCreation, .3f), 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 3, 
                LBL_WIDTH, 
                BTN_CANCEL_HEIGHT
            );

            soloGameCreation.nbPiecesSpinner.setBounds(
                PADDING_LEFT + getWidthTimesPourcent(soloGameCreation, .3f), 
                PADDING_TOP_LBL_SEED + BTN_CANCEL_HEIGHT * 4, 
                getWidthTimesPourcent(soloGameCreation, .2f), 
                BTN_CANCEL_HEIGHT
            );
        };
    }

    private static long generateRandomSeed() {

        return (long) (Math.random() * Long.MAX_VALUE);
    }

    private static Runnable createRandomSeedCallback(SoloGameCreation soloGameCreation) {

        return () -> {

            final long seed = generateRandomSeed();
            soloGameCreation.updateSeed(seed);
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

            String text = txtSeed.getText();

            try {

                long seed = Long.parseLong(text);
                updateSeed(seed);
            } 
            catch (NumberFormatException ignored) {}
        }
    }
}
