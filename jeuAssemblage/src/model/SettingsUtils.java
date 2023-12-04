package model;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import main.App;

public final class SettingsUtils {
	
	private static final String DEFAULT_PROFILE_NAME_FILE = "default_names.txt";

	private static final char COMMENT_CHAR = '#';
	private static final char SEPARATOR_CHAR = '=';
	private static final char KEY_CHAR = '!';

	private static final String USERNAME_KEY = "USERNAME";

	private static String username = null;

	private SettingsUtils() {
		
	}

	public static String generateRandomProfileName() {

		try (InputStream fos = SettingsUtils.class.getResourceAsStream(App.RESSOURCES_PATH + DEFAULT_PROFILE_NAME_FILE)) {

			byte[] buffer = new byte[fos.available()];

			fos.read(buffer);

			String[] names = new String(buffer).split("\n");

			return names[(int) (Math.random() * names.length)];
		} 
		catch (Exception e) {

			return DEFAULT_PROFILE_NAME_FILE + " not found";
		}
	}

	public static void load() {

		// À des fins de tests, on supprime le fichier de settings
		new File("settings.conf").delete();

		File file = new File("settings.conf");
		if (!file.exists()) {

			createSettingsFile();
		}

		try {

			byte[] buffer = Files.readAllBytes(file.toPath());

			String[] lines = new String(buffer).split("\n");

			for (String line : lines) {

				if (line.length() == 0 || line.charAt(0) == COMMENT_CHAR) {

					continue;
				}

				String[] keyAndValue = line.split("" + SEPARATOR_CHAR);

				if (keyAndValue.length != 2) {

					continue;
				}

				switch (keyAndValue[0]) {

					case USERNAME_KEY:

						setUsername(keyAndValue[1]);
						break;
				}
			}
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static void createSettingsFile() {

		File file = new File("settings.conf");
		try {

			file.createNewFile();

			Files.write(file.toPath(), (KEY_CHAR + "USERNAME" + SEPARATOR_CHAR + generateRandomProfileName() + "\n").getBytes());
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static String getUsername() {


		return username;
	}

	public static void setUsername(String username) {

		SettingsUtils.username = username;
	}
}
