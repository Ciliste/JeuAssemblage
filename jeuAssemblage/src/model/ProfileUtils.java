package model;

import java.io.InputStream;

import main.App;

public final class ProfileUtils {
	
	private static final String DEFAULT_PROFILE_NAME_FILE = "default_names.txt";

	private ProfileUtils() {
		
	}

	public static String generateRandomProfileName() {

		try (InputStream fos = ProfileUtils.class.getResourceAsStream(App.RESSOURCES_PATH + DEFAULT_PROFILE_NAME_FILE)) {

			byte[] buffer = new byte[fos.available()];

			fos.read(buffer);

			String[] names = new String(buffer).split("\n");

			return names[(int) (Math.random() * names.length)];
		} 
		catch (Exception e) {

			return DEFAULT_PROFILE_NAME_FILE + " not found";
		}
	}
}
