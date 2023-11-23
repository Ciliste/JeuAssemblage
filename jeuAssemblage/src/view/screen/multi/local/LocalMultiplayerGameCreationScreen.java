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
import javax.swing.SwingWorker;

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

					buildWorker().execute();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					buildWorker().execute();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					buildWorker().execute();
				}
			});
		
			txtSizeX.getDocument().addDocumentListener(new DocumentAdapter() {

				@Override
				public void changedUpdate(DocumentEvent e) {

					buildWorker().execute();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					buildWorker().execute();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					buildWorker().execute();
				}
			});

			txtSizeY.getDocument().addDocumentListener(new DocumentAdapter() {

				@Override
				public void changedUpdate(DocumentEvent e) {

					buildWorker().execute();
				}

				@Override
				public void insertUpdate(DocumentEvent e) {

					buildWorker().execute();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {

					buildWorker().execute();
				}
			});
		
			nbPiecesSpinner.addChangeListener(e -> {

				buildWorker().execute();
			});

			nbMinutesSpinner.addChangeListener(e -> {

				buildWorker().execute();
			});

			nbSecondsSpinner.addChangeListener(e -> {

				buildWorker().execute();
			});

			timeLimitCheckBox.addActionListener(e -> {

				buildWorker().execute();
			});

			difficultyList.addListSelectionListener(e -> {

				if (e.getValueIsAdjusting()) {

					return;
				}

				buildWorker().execute();
			});

			hostClient.addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {

					if (false == object instanceof Requests) {

						return;
					}

					if (object instanceof Requests.CompleteGameSettingsRequest) {

						Logger.getGlobal().info("CompleteGameSettingsRequest received");

						buildWorker().execute();
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

	private SwingWorker<Void, Void> buildWorker() {

		return new SwingWorker<>() {

			@Override
			protected Void doInBackground() throws Exception {

				System.out.println("**************");
				System.out.println("doInBackground");
				System.out.println("**************");

				long seed = Long.parseLong(txtSeed.getText());
				int sizeX = Integer.parseInt(txtSizeX.getText());
				int sizeY = Integer.parseInt(txtSizeY.getText());

				int nbPieces = (int) nbPiecesSpinner.getValue();

				int nbMinutes = (int) nbMinutesSpinner.getValue();
				int nbSeconds = (int) nbSecondsSpinner.getValue();

				boolean timeLimit = timeLimitCheckBox.isSelected();

				EDifficulty difficulty = EDifficulty.values()[difficultyList.getSelectedIndex()];

				hostClient.sendTCP(new GameSettingsPacket.SeedUpdate(seed));
				hostClient.sendTCP(new GameSettingsPacket.SizeXUpdate(sizeX));
				hostClient.sendTCP(new GameSettingsPacket.SizeYUpdate(sizeY));
				hostClient.sendTCP(new GameSettingsPacket.NbPiecesUpdate(nbPieces));
				hostClient.sendTCP(new GameSettingsPacket.NbMinutesUpdate(nbMinutes));
				hostClient.sendTCP(new GameSettingsPacket.NbSecondsUpdate(nbSeconds));
				hostClient.sendTCP(new GameSettingsPacket.TimeLimitUpdate(timeLimit));
				hostClient.sendTCP(new GameSettingsPacket.DifficultyUpdate(difficulty));

				return null;
			}

			@Override
			protected void done() {

				System.out.println("**************");
				System.out.println("done");
				System.out.println("**************");
			}
		};
	};
}
