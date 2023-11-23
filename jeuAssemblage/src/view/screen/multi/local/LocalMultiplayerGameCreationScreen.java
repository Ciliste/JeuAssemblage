package view.screen.multi.local;

import java.util.logging.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.Box;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import model.SettingsUtils;
import net.packet.GameSettingsPacket;
import net.packet.request.Requests;
import net.packet.request.ServerRequests;
import net.server.HostClient;
import utils.EDifficulty;
import view.MainFrame;
import view.screen.AIGameCreation;
import view.utils.DocumentAdapter;

public class LocalMultiplayerGameCreationScreen extends AIGameCreation {
	
	protected final HostClient hostClient;
	protected final boolean isHost;

	public LocalMultiplayerGameCreationScreen(MainFrame mainFrame, HostClient hostClient, boolean isHost) {
		
		super(mainFrame);

		this.hostClient = hostClient;

		this.isHost = isHost;
	}

	public void setupListeners() {

		if (false == isHost) {

			for (JComponent component : getSettingsComponents()) {
				
				component.setEnabled(false);
			}

			remove(lblArrangement);
			remove(tableArrangement);
			remove(scrollArrangement);

			hostClient.addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {

					if (object instanceof GameSettingsPacket.SeedUpdate) {

						GameSettingsPacket.SeedUpdate seedUpdate = (GameSettingsPacket.SeedUpdate) object;

						txtSeed.setText(String.valueOf(seedUpdate.seed));

						updateGridPreview();
					}
					else if (object instanceof GameSettingsPacket.SizeXUpdate) {

						GameSettingsPacket.SizeXUpdate sizeXUpdate = (GameSettingsPacket.SizeXUpdate) object;

						txtSizeX.setText(String.valueOf(sizeXUpdate.sizeX));

						updateGridPreview();
					}
					else if (object instanceof GameSettingsPacket.SizeYUpdate) {

						GameSettingsPacket.SizeYUpdate sizeYUpdate = (GameSettingsPacket.SizeYUpdate) object;

						txtSizeY.setText(String.valueOf(sizeYUpdate.sizeY));

						updateGridPreview();
					}
					else if (object instanceof GameSettingsPacket.NbPiecesUpdate) {

						GameSettingsPacket.NbPiecesUpdate nbPiecesUpdate = (GameSettingsPacket.NbPiecesUpdate) object;

						nbPiecesSpinner.setValue(nbPiecesUpdate.nbPieces);

						updateGridPreview();
					}
					else if (object instanceof GameSettingsPacket.NbMinutesUpdate) {

						GameSettingsPacket.NbMinutesUpdate nbMinutesUpdate = (GameSettingsPacket.NbMinutesUpdate) object;

						nbMinutesSpinner.setValue(nbMinutesUpdate.nbMinutes);
					}
					else if (object instanceof GameSettingsPacket.NbSecondsUpdate) {

						GameSettingsPacket.NbSecondsUpdate nbSecondsUpdate = (GameSettingsPacket.NbSecondsUpdate) object;

						nbSecondsSpinner.setValue(nbSecondsUpdate.nbSeconds);
					}
					else if (object instanceof GameSettingsPacket.TimeLimitUpdate) {

						GameSettingsPacket.TimeLimitUpdate timeLimitUpdate = (GameSettingsPacket.TimeLimitUpdate) object;

						timeLimitCheckBox.setSelected(timeLimitUpdate.timeLimit);

						nbMinutesSpinner.setEnabled(false);
						nbSecondsSpinner.setEnabled(false);
					}
					else if (object instanceof GameSettingsPacket.DifficultyUpdate) {

						int index = ((GameSettingsPacket.DifficultyUpdate) object).difficulty.ordinal();

						difficultyList.setSelectedIndex(index);

						updateGridPreview();
					}
				}
			});

			SwingUtilities.invokeLater(() -> {

				hostClient.sendTCP(new Requests.CompleteGameSettingsRequest());
				hostClient.sendTCP(new Requests.PlayerListRequest());
			});
		}
		else {

			txtSeed.getDocument().addDocumentListener(new DocumentAdapter() {

				@Override
				public void changedUpdate(DocumentEvent e) {

					sendSeed();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					sendSeed();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					sendSeed();
				}

				private void sendSeed() {

					try {
						
						long seed = Long.parseLong(txtSeed.getText());
						hostClient.sendTCP(new GameSettingsPacket.SeedUpdate(seed));
					} 
					catch (Exception ex) {
						// TODO: handle exception
					}
				}
			});
		
			txtSizeX.getDocument().addDocumentListener(new DocumentAdapter() {

				@Override
				public void changedUpdate(DocumentEvent e) {

					sendSizeX();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					sendSizeX();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					sendSizeX();
				}

				private void sendSizeX() {

					try {
						
						int sizeX = Integer.parseInt(txtSizeX.getText());
						hostClient.sendTCP(new GameSettingsPacket.SizeXUpdate(sizeX));
					} 
					catch (Exception ex) {

					}
				}
			});

			txtSizeY.getDocument().addDocumentListener(new DocumentAdapter() {

				@Override
				public void changedUpdate(DocumentEvent e) {

					sendSizeY();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					sendSizeY();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					sendSizeY();
				}

				private void sendSizeY() {

					try {
						
						int sizeY = Integer.parseInt(txtSizeY.getText());
						hostClient.sendTCP(new GameSettingsPacket.SizeYUpdate(sizeY));
					} 
					catch (Exception ex) {

					}
				}
			});
		
			nbPiecesSpinner.addChangeListener(e -> {

				int nbPieces = (int) nbPiecesSpinner.getValue();

				hostClient.sendTCP(new GameSettingsPacket.NbPiecesUpdate(nbPieces));
			});

			nbMinutesSpinner.addChangeListener(e -> {

				int nbMinutes = (int) nbMinutesSpinner.getValue();

				hostClient.sendTCP(new GameSettingsPacket.NbMinutesUpdate(nbMinutes));
			});

			nbSecondsSpinner.addChangeListener(e -> {

				int nbSeconds = (int) nbSecondsSpinner.getValue();

				hostClient.sendTCP(new GameSettingsPacket.NbSecondsUpdate(nbSeconds));
			});

			timeLimitCheckBox.addActionListener(e -> {

				boolean timeLimit = timeLimitCheckBox.isSelected();

				hostClient.sendTCP(new GameSettingsPacket.TimeLimitUpdate(timeLimit));
			});

			difficultyList.addListSelectionListener(e -> {

				if (e.getValueIsAdjusting()) {

					return;
				}

				EDifficulty difficulty = EDifficulty.values()[difficultyList.getSelectedIndex()];

				hostClient.sendTCP(new GameSettingsPacket.DifficultyUpdate(difficulty));
			});

			hostClient.addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {

					if (false == object instanceof Requests) {

						return;
					}

					if (object instanceof Requests.CompleteGameSettingsRequest) {

						Logger.getGlobal().info("CompleteGameSettingsRequest received");

						hostClient.sendTCP(new GameSettingsPacket.SeedUpdate(Long.parseLong(txtSeed.getText())));
						hostClient.sendTCP(new GameSettingsPacket.SizeXUpdate(Integer.parseInt(txtSizeX.getText())));
						hostClient.sendTCP(new GameSettingsPacket.SizeYUpdate(Integer.parseInt(txtSizeY.getText())));
						hostClient.sendTCP(new GameSettingsPacket.NbPiecesUpdate((int) nbPiecesSpinner.getValue()));
						hostClient.sendTCP(new GameSettingsPacket.NbMinutesUpdate((int) nbMinutesSpinner.getValue()));
						hostClient.sendTCP(new GameSettingsPacket.NbSecondsUpdate((int) nbSecondsSpinner.getValue()));
						hostClient.sendTCP(new GameSettingsPacket.TimeLimitUpdate(timeLimitCheckBox.isSelected()));
						hostClient.sendTCP(new GameSettingsPacket.DifficultyUpdate(EDifficulty.values()[difficultyList.getSelectedIndex()]));
					}
					else if (object instanceof Requests.PlayerListRequest) {

						
					}
				}
			});
		
			hostClient.addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {

					if (object instanceof ServerRequests.PseudonymRequest) {

						connection.sendTCP(new GameSettingsPacket.PlayerPseudonymPacket(SettingsUtils.getUsername()));
					}
				}
			});
		}
	
		hostClient.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof GameSettingsPacket.PlayerJoinPacket) {

					playerJoined((GameSettingsPacket.PlayerJoinPacket) object);
				}
			}
		});	
	}

	private final Map<Integer, GameSettingsPacket.PlayerJoinPacket> players = new ConcurrentHashMap<>();

	private final Map<Integer, JPanel> playerPanels = new ConcurrentHashMap<>();

	private void playerJoined(GameSettingsPacket.PlayerJoinPacket packet) {

		players.put(packet.id, packet);

		JPanel playerPanel = new JPanel();

		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));

		JLabel lblPlayer = new JLabel(packet.pseudonym);
		JLabel lblAddress = new JLabel(packet.ip);

		playerPanel.add(lblPlayer);

		playerPanel.add(Box.createHorizontalGlue());

		playerPanel.add(lblAddress);

		playerPanel.add(Box.createHorizontalGlue());

		JButton btnKick = new JButton("X");

		playerPanel.add(btnKick);

		playerPanels.put(packet.id, playerPanel);

		bots.add(playerPanel);

		bots.revalidate();
		bots.repaint();
	} 
}
