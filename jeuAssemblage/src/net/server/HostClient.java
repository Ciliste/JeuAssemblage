package net.server;

import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryonet.Client;

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
	}
	
	private final List<Runnable> onConnect = new LinkedList<>();
	
	public void connect() {
		
		try {
			
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
}
