package net.server;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.NetUtils;

public class HostClient extends Client {
	
	private final String host;
	private final int tcpPort;
	private final int udpPort;

	public HostClient(String host, int tcpPort, int udpPort) {
		
		super();
		
		this.host = host;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;

		NetUtils.registerClasses(getKryo());

		addListener(new Listener() {

			@Override
			public void received(Connection connection, Object object) {

				Logger.getGlobal().info("Received: " + object);
			}
		});
	}
	
	private final List<Runnable> onConnect = new LinkedList<>();
	private final List<Runnable> onDisconnect = new LinkedList<>();

	public void connect() {
		
		try {
			
			addListener(new Listener() {

				@Override
				public void received(Connection connection, Object object) {

					System.out.println("*** HOST CLIENT ***");
					System.out.println("Received: " + object);
				}

				@Override
				public void disconnected(Connection connection) {
					
					onDisconnect.forEach(Runnable::run);
				}
			});

			super.start();

			super.connect(5000, host, tcpPort, udpPort);
			
			onConnect.forEach(Runnable::run);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HostClient onConnect(Runnable runnable) {
		
		onConnect.add(runnable);
		
		return this;
	}

	public HostClient onDisconnect(Runnable runnable) {
		
		onDisconnect.add(runnable);
		
		return this;
	}
}
