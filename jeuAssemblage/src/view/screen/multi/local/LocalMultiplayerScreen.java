package view.screen.multi.local;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.server.HostClient;
import net.server.PartyServer;
import view.MainFrame;

public class LocalMultiplayerScreen extends JPanel {
	
	private final JButton btnBack = new JButton("Retour");

	private final JButton btnCreate = new JButton("Créer une partie");
	private final JButton btnJoin = new JButton("Rejoindre une partie");

	public LocalMultiplayerScreen(MainFrame mainFrame) {
		
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createVerticalGlue());

		add(btnCreate);

		add(Box.createVerticalStrut(50));

		add(btnJoin);

		add(Box.createVerticalStrut(50));

		add(btnBack);

		add(Box.createVerticalGlue());

		btnCreate.setAlignmentX(CENTER_ALIGNMENT);
		btnJoin.setAlignmentX(CENTER_ALIGNMENT);
		btnBack.setAlignmentX(CENTER_ALIGNMENT);

		btnCreate.addActionListener(e -> {

			// Show popup to enter port
			String ports = JOptionPane.showInputDialog(new JDialog(mainFrame, "Créer une partie"), "Entrez les ports de la partie :\nExemple: 7777:7778", "Créer une partie", JOptionPane.QUESTION_MESSAGE, null, null, "7777:7778").toString();

			final AtomicReference<PartyServer> serverRef = new AtomicReference<>();
			try {
				
				final int tcpPort = Integer.parseInt(ports.split(":")[0]);
				final int udpPort = Integer.parseInt(ports.split(":")[1]);

				PartyServer server = new PartyServer(tcpPort, udpPort);
				serverRef.set(server);
			} 
			catch (Exception ex) {
				
				JOptionPane.showMessageDialog(new JDialog(mainFrame, "Créer une partie"), "Formatage des ports incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			serverRef.get().onStart(() -> {

				Logger.getGlobal().info("Server started on ports " + serverRef.get().getTcpPort() + ":" + serverRef.get().getUdpPort());

				HostClient client = new HostClient("localhost", serverRef.get().getTcpPort(), serverRef.get().getUdpPort());

				client.onConnect(() -> {

					LocalMultiplayerGameCreationScreen gameCreationScreen = new LocalMultiplayerGameCreationScreen(mainFrame, client, true);
					gameCreationScreen.setupListeners();

					client.onDisconnect(() -> {

						JOptionPane.showMessageDialog(new JDialog(mainFrame, "Créer une partie"), "Une erreur est survenue...", "Erreur", JOptionPane.ERROR_MESSAGE);

						mainFrame.setContentPane(new LocalMultiplayerScreen(mainFrame));

						serverRef.get().stop();
					});

					mainFrame.setContentPane(gameCreationScreen);

				});

				client.connect();
			});

			serverRef.get().start();
		});

		btnJoin.addActionListener(e -> {

			// Show popup to enter ip and port
			String input = JOptionPane.showInputDialog(new JDialog(mainFrame, "Rejoindre une partie"), "Entrez l'adresse IP et le port de la partie séparés par un ':'\nExemple: localhost:7777:7778", "Rejoindre une partie", JOptionPane.QUESTION_MESSAGE, null, null, "localhost:7777:7778").toString();

			final AtomicReference<HostClient> clientRef = new AtomicReference<>();
			try {
				
				final String host = input.split(":")[0];
				final int tcpPort = Integer.parseInt(input.split(":")[1]);
				final int udpPort = Integer.parseInt(input.split(":")[2]);

				HostClient client = new HostClient(host, tcpPort, udpPort);
				clientRef.set(client);
			} 
			catch (Exception ex) {
				
				JOptionPane.showMessageDialog(new JDialog(mainFrame, "Rejoindre une partie"), "Formatage de l'adresse IP et des ports incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			clientRef.get().onConnect(() -> {

				LocalMultiplayerGameCreationScreen gameCreationScreen = new LocalMultiplayerGameCreationScreen(mainFrame, clientRef.get(), false);
				gameCreationScreen.setupListeners();

				mainFrame.setContentPane(gameCreationScreen);
			});

			clientRef.get().onDisconnect(() -> {

				JOptionPane.showMessageDialog(new JDialog(mainFrame, "Rejoindre une partie"), "Connexion perdue...", "Erreur", JOptionPane.ERROR_MESSAGE);

				mainFrame.setContentPane(new LocalMultiplayerScreen(mainFrame));
			});

			clientRef.get().connect();
		});
	}
}
