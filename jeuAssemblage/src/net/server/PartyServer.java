package net.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import net.NetUtils;
import net.packet.GameSettingsPacket;
import net.packet.request.Requests;
import net.packet.request.ServerRequests;

public class PartyServer extends Server {
	
	private final int tcpPort;
	private final int udpPort;

	private Connection owner = null;

	private final Map<Integer, Tuple<Connection, String>> connections = new ConcurrentHashMap<>();

	public PartyServer(int tcpPort, int udpPort) {
		
		super();

		this.tcpPort = tcpPort;
		this.udpPort = udpPort;

		NetUtils.registerClasses(getKryo());
	}

	public int getTcpPort() {
		
		return tcpPort;
	}

	public int getUdpPort() {
		
		return udpPort;
	}

	private final List<Runnable> onStart = new LinkedList<>();

	public void start() {
		
		try {
			
			bind(tcpPort, udpPort);
			
			addListener(new Listener() {

				@Override
				public void connected(Connection connection) {
					
					owner = connection;

					Logger.getGlobal().info("Owner connected: " + connection.getRemoteAddressTCP().getAddress().getHostAddress());

					removeListener(this);

					registerPlayer(connection);

					addListener(new Listener() {

						@Override
						public void connected(Connection connection) {

							int id = registerPlayer(connection);

							sendToAllExceptTCP(connection.getID(), new GameSettingsPacket.PlayerJoinPacket(id, connections.get(id).b, connection.getRemoteAddressTCP().getAddress().getHostAddress()));
						}

						@Override
						public void received(Connection connection, Object object) {

							if (object instanceof GameSettingsPacket.PlayerPseudonymPacket) {

								GameSettingsPacket.PlayerPseudonymPacket packet = (GameSettingsPacket.PlayerPseudonymPacket) object;

								for (Tuple<Connection, String> tuple : connections.values()) {

									if (tuple.a == connection) {

										tuple.b = packet.pseudonym;

										int id = connections.entrySet().stream().filter(entry -> entry.getValue().a == connection).findFirst().get().getKey();

										sendToAllExceptTCP(connection.getID(), new GameSettingsPacket.PlayerJoinPacket(id, packet.pseudonym, connection.getRemoteAddressTCP().getAddress().getHostAddress()));

										break;
									}
								}
							}
						}
					});
				}
			});

			addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {
					
					if (owner != connection) {
						
						return;
					}

					if (object instanceof GameSettingsPacket) {

						sendToAllExceptTCP(connection.getID(), object);
					}
				}
			});

			addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {
					
					if (owner == connection) {
						
						return;
					}

					if (object instanceof Requests) {

						owner.sendTCP(object);
					}
				}
			});

			addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {
					
					
				}
			});

			super.start();

			onStart.forEach(Runnable::run);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PartyServer onStart(Runnable runnable) {
		
		onStart.add(runnable);
		
		return this;
	}

	private int registerPlayer(Connection connection) {

		int id = registerPlayer(connection, "???");

		connection.sendTCP(new ServerRequests.PseudonymRequest());

		return id;
	}

	private int registerPlayer(Connection connection, String pseudonym) {

		int id = connections.size();

		connections.put(id, new Tuple<>(connection, pseudonym));

		return id;
	}

	private static class Tuple<A, B> {
	
		public A a;
		public B b;

		public Tuple(A a, B b) {
			
			this.a = a;
			this.b = b;
		}
	}
}
