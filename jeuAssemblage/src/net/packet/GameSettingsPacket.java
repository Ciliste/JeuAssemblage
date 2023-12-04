package net.packet;

import utils.EDifficulty;

public class GameSettingsPacket {
	
	private GameSettingsPacket() {
		
	}

	public static class SeedUpdate extends GameSettingsPacket {

		public long seed;

		public SeedUpdate() {

		}

		public SeedUpdate(long seed) {

			this.seed = seed;
		}
	}

	public static class SizeXUpdate extends GameSettingsPacket {

		public int sizeX;

		public SizeXUpdate() {

		}

		public SizeXUpdate(int sizeX) {

			this.sizeX = sizeX;
		}
	}

	public static class SizeYUpdate extends GameSettingsPacket {

		public int sizeY;

		public SizeYUpdate() {

		}

		public SizeYUpdate(int sizeY) {

			this.sizeY = sizeY;
		}
	}

	public static class NbPiecesUpdate extends GameSettingsPacket {

		public int nbPieces;

		public NbPiecesUpdate() {

		}

		public NbPiecesUpdate(int nbPieces) {

			this.nbPieces = nbPieces;
		}
	}

	public static class NbMinutesUpdate extends GameSettingsPacket {

		public int nbMinutes;

		public NbMinutesUpdate() {

		}

		public NbMinutesUpdate(int nbMinutes) {

			this.nbMinutes = nbMinutes;
		}
	}

	public static class NbSecondsUpdate extends GameSettingsPacket {

		public int nbSeconds;

		public NbSecondsUpdate() {

		}

		public NbSecondsUpdate(int nbSeconds) {

			this.nbSeconds = nbSeconds;
		}
	}

	public static class TimeLimitUpdate extends GameSettingsPacket {

		public boolean timeLimit;

		public TimeLimitUpdate() {

		}

		public TimeLimitUpdate(boolean timeLimit) {

			this.timeLimit = timeLimit;
		}
	}

	public static class DifficultyUpdate extends GameSettingsPacket {

		public EDifficulty difficulty;

		public DifficultyUpdate() {

		}

		public DifficultyUpdate(EDifficulty difficulty) {

			this.difficulty = difficulty;
		}
	}

	public static class PlayerJoinPacket extends GameSettingsPacket {

		public int id;

		public String pseudonym;
		public String ip;

		public PlayerJoinPacket() {

		}

		public PlayerJoinPacket(int id, String pseudonym, String ip) {

			this.id = id;
			this.pseudonym = pseudonym;
			this.ip = ip;
		}
	}

	public static class PlayerPseudonymPacket extends GameSettingsPacket {

		public String pseudonym;

		public PlayerPseudonymPacket() {

		}

		public PlayerPseudonymPacket(String pseudonym) {

			this.pseudonym = pseudonym;
		}
	}

}
