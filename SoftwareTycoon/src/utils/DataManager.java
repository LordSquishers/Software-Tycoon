package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.FileIn;
import io.FileOut;

public class DataManager {

	public HashMap<String, String> addGameStat(HashMap<String, String> data, String prop, boolean isNumber) {
		if (data.containsKey(prop)) {
			return data;
		} else {
			String v = "";
			if (isNumber)
				v = "0";

			data.put(prop, v);
			return data;
		}

	}

	public HashMap<String, String> loadGameStats(String gameName) {
		FileIn in = new FileIn();
		String[] rawData = in.readFile(new File(FileOut.DATA_LOC + gameName + "_00.stllc"));
		HashMap<String, String> processedData = new HashMap<>();

		String[][] data1 = new String[rawData.length][2];
		for (int i = 0; i < rawData.length; i++) {
			String[] split = rawData[i].split("=");
			if (split[0] != null)
				data1[i][0] = split[0];
			if (split.length > 1)
				data1[i][1] = split[1];
		}

		for (int x = 0; x < data1.length; x++) {
			if (data1[x][0] != null && data1[x][1] != null)
				processedData.put(data1[x][0], data1[x][1]);
		}

		return processedData;
	}

	public HashMap<String, String> loadCurrentGames(String gameName) {
		FileIn in = new FileIn();
		String[] rawData = in.readFile(new File(FileOut.DATA_LOC + gameName + "_02.stllc"));
		HashMap<String, String> processedData = new HashMap<>();

		String[][] data1 = new String[rawData.length][2];
		for (int i = 0; i < rawData.length; i++) {
			String[] split = rawData[i].split("=");
			if (split[0] != null)
				data1[i][0] = split[0];
			if (split.length > 1)
				data1[i][1] = split[1];
		}

		for (int x = 0; x < data1.length; x++) {
			if (data1[x][0] != null && data1[x][1] != null)
				processedData.put(data1[x][0], data1[x][1]);
		}

		return processedData;
	}

	/**
	 * Saves all game stats to the file of the game (Requires File IO classes)
	 * 
	 * @param gameName
	 *            - The name of the game to save
	 * @param save
	 *            - All data to save to the file
	 */
	public void saveGameStats(String gameName, HashMap<String, String> save) {
		FileOut fileOut = new FileOut();

		// Conversion from HashMap to array
		String[] convertedData = new String[save.size()];

		int i = 0;
		Iterator it = save.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			String keyValue = pair.getKey() + "=" + pair.getValue();
			convertedData[i++] = keyValue;
		}

		fileOut.writeToFile(gameName + "_00", convertedData, true);
	}

	public void saveCurrentGames(String gameName, HashMap<String, String> save) {
		FileOut fileOut = new FileOut();

		// Conversion from HashMap to array
		String[] convertedData = new String[save.size()];

		int i = 0;
		Iterator it = save.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			String keyValue = pair.getKey() + "=" + pair.getValue();
			convertedData[i++] = keyValue;
		}

		fileOut.writeToFile(gameName + "_02", convertedData, true);
	}

}
