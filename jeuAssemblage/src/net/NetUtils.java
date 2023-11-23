package net;

import com.esotericsoftware.kryo.Kryo;

import net.packet.GameSettingsPacket;
import net.packet.request.Requests;
import net.packet.request.ServerRequests;
import utils.EDifficulty;

public final class NetUtils {
	
	private NetUtils() {
		
	}

	public static Kryo registerClasses(Kryo kryo) {
		
		kryo.register(GameSettingsPacket.class);
		kryo.register(GameSettingsPacket.SeedUpdate.class);
		kryo.register(GameSettingsPacket.SizeXUpdate.class);
		kryo.register(GameSettingsPacket.SizeYUpdate.class);
		kryo.register(GameSettingsPacket.NbPiecesUpdate.class);
		kryo.register(GameSettingsPacket.NbMinutesUpdate.class);
		kryo.register(GameSettingsPacket.NbSecondsUpdate.class);
		kryo.register(GameSettingsPacket.TimeLimitUpdate.class);
		kryo.register(GameSettingsPacket.PlayerPseudonymPacket.class);
		kryo.register(GameSettingsPacket.PlayerJoinPacket.class);

		kryo.register(EDifficulty.class);
		kryo.register(GameSettingsPacket.DifficultyUpdate.class);



		kryo.register(Requests.class);
		kryo.register(Requests.CompleteGameSettingsRequest.class);
		kryo.register(Requests.PlayerListRequest.class);



		kryo.register(ServerRequests.class);
		kryo.register(ServerRequests.PseudonymRequest.class);
		


		return kryo;
	}
}
