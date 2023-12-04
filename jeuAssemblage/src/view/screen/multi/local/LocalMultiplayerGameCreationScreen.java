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

		Logger.getGlobal().info("LocalMultiplayerGameCreationScreen");

		this.hostClient = hostClient;
		this.isHost = isHost;
	}

	public void setupListeners() {

		if (isHost) {

			setupHostBehaviour();
		}
		else {

			setupClientBehaviour();
		}
	}

	private void setupHostBehaviour() {

		setupComponentsListeners();

		setupRequestsListener();
	}

	private void setupComponentsListeners() {

		setupSeedListener();
		setupSizeXListener();
		setupSizeYListener();
		setupDifficultyListener();
		setupNbPiecesListener();
		setupNbMinutesListener();
		setupNbSecondsListener();
		setupTimeLimitListener();
	}

	private void setupSeedListener() {

		txtSeed.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			public void changedUpdate(DocumentEvent e) {

				callback();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				callback();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				callback();
			}

			private void callback() {

				long seed = -1;
				try {

					seed = Long.parseLong(txtSeed.getText());
				}
				catch (NumberFormatException e) {

					return;
				}

				if (seed < 0) {

					return;
				}

				sendSeed();
			}
		});
	}

	private void setupSizeXListener() {

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
		});
	}

	private void setupSizeYListener() {

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
		});
	}

	private void setupDifficultyListener() {

		difficultyList.addListSelectionListener(e -> {

			sendDifficulty();
		});
	}

	private void setupNbPiecesListener() {

		nbPiecesSpinner.addChangeListener(e -> {

			sendNbPieces();
		});
	}

	private void setupNbMinutesListener() {

		nbMinutesSpinner.addChangeListener(e -> {

			sendNbMinutes();
		});
	}

	private void setupNbSecondsListener() {

		nbSecondsSpinner.addChangeListener(e -> {

			sendNbSeconds();
		});
	}

	private void setupTimeLimitListener() {

		timeLimitCheckBox.addActionListener(e -> {

			sendTimeLimit();
		});
	}

	private void setupRequestsListener() {

		hostClient.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof Requests.CompleteGameSettingsRequest) {

					SwingUtilities.invokeLater(() -> {

						sendAll();
					});
				}
			}
		});
	}

	private void sendAll() {

		sendSeed();
		sendSizeX();
		sendSizeY();
		sendDifficulty();
		sendNbPieces();
		sendNbMinutes();
		sendNbSeconds();
		sendTimeLimit();
	}

	private void sendSeed() {

		hostClient.sendTCP(new GameSettingsPacket.SeedUpdate(Long.parseLong(txtSeed.getText())));
	}

	private void sendSizeX() {

		hostClient.sendTCP(new GameSettingsPacket.SizeXUpdate(Integer.parseInt(txtSizeX.getText())));
	}

	private void sendSizeY() {

		hostClient.sendTCP(new GameSettingsPacket.SizeYUpdate(Integer.parseInt(txtSizeY.getText())));
	}

	private void sendDifficulty() {

		hostClient.sendTCP(new GameSettingsPacket.DifficultyUpdate(EDifficulty.values()[difficultyList.getSelectedIndex()]));
	}

	private void sendNbPieces() {

		hostClient.sendTCP(new GameSettingsPacket.NbPiecesUpdate((Integer) nbPiecesSpinner.getValue()));
	}

	private void sendNbMinutes() {

		hostClient.sendTCP(new GameSettingsPacket.NbMinutesUpdate((Integer) nbMinutesSpinner.getValue()));
	}

	private void sendNbSeconds() {

		hostClient.sendTCP(new GameSettingsPacket.NbSecondsUpdate((Integer) nbSecondsSpinner.getValue()));
	}

	private void sendTimeLimit() {

		hostClient.sendTCP(new GameSettingsPacket.TimeLimitUpdate(timeLimitCheckBox.isSelected()));
	}

	private void setupClientBehaviour() {

		disableAllSettingsComponents();

		hostClient.addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				if (object instanceof GameSettingsPacket.PlayerJoinPacket) {

					SwingUtilities.invokeLater(() -> playerJoined((GameSettingsPacket.PlayerJoinPacket) object));
				}
				else if (object instanceof GameSettingsPacket.SeedUpdate) {

					SwingUtilities.invokeLater(() -> seedReceived((GameSettingsPacket.SeedUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.SizeXUpdate) {

					SwingUtilities.invokeLater(() -> sizeXReceived((GameSettingsPacket.SizeXUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.SizeYUpdate) {

					SwingUtilities.invokeLater(() -> sizeYReceived((GameSettingsPacket.SizeYUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.DifficultyUpdate) {

					SwingUtilities.invokeLater(() -> difficultyReceived((GameSettingsPacket.DifficultyUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.NbPiecesUpdate) {

					SwingUtilities.invokeLater(() -> nbPiecesReceived((GameSettingsPacket.NbPiecesUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.NbMinutesUpdate) {

					SwingUtilities.invokeLater(() -> nbMinutesReceived((GameSettingsPacket.NbMinutesUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.NbSecondsUpdate) {

					SwingUtilities.invokeLater(() -> nbSecondsReceived((GameSettingsPacket.NbSecondsUpdate) object));
				}
				else if (object instanceof GameSettingsPacket.TimeLimitUpdate) {

					SwingUtilities.invokeLater(() -> timeLimitReceived((GameSettingsPacket.TimeLimitUpdate) object));
				}
			}
		});

		SwingUtilities.invokeLater(() -> {

			hostClient.sendTCP(new Requests.CompleteGameSettingsRequest());
		});
	}

	private void disableAllSettingsComponents() {

		for (JComponent component : getSettingsComponents()) {

			component.setEnabled(false);
		}

		remove(tableArrangement);
		remove(scrollArrangement);
		remove(lblArrangement);
	}

	private final Map<Integer, GameSettingsPacket.PlayerJoinPacket> players = new ConcurrentHashMap<>();

	private final Map<Integer, JPanel> playerPanels = new ConcurrentHashMap<>();

	private void playerJoined(GameSettingsPacket.PlayerJoinPacket packet) {


	}

	private void seedReceived(GameSettingsPacket.SeedUpdate packet) {

		txtSeed.setText(String.valueOf(packet.seed));
	}

	private void sizeXReceived(GameSettingsPacket.SizeXUpdate packet) {

		txtSizeX.setText(String.valueOf(packet.sizeX));
	}

	private void sizeYReceived(GameSettingsPacket.SizeYUpdate packet) {

		txtSizeY.setText(String.valueOf(packet.sizeY));
	}

	private void difficultyReceived(GameSettingsPacket.DifficultyUpdate packet) {

		difficultyList.setSelectedIndex(packet.difficulty.ordinal());
	}

	private void nbPiecesReceived(GameSettingsPacket.NbPiecesUpdate packet) {

		nbPiecesSpinner.setValue(packet.nbPieces);
	}

	private void nbMinutesReceived(GameSettingsPacket.NbMinutesUpdate packet) {

		nbMinutesSpinner.setValue(packet.nbMinutes);
	}

	private void nbSecondsReceived(GameSettingsPacket.NbSecondsUpdate packet) {

		nbSecondsSpinner.setValue(packet.nbSeconds);
	}

	private void timeLimitReceived(GameSettingsPacket.TimeLimitUpdate packet) {

		timeLimitCheckBox.setSelected(packet.timeLimit);
	}
}
