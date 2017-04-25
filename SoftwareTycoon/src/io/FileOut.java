package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOut {

	public static final String DATA_LOC = "saveData/";

	/**
	 * A method to write text to a file.
	 * 
	 * @param path
	 *            - The file path for the text.
	 * @param text
	 *            - The text array to be written.
	 * @return - boolean, If the file writing was a success.
	 */
	public boolean writeToFile(String path, String[] text, boolean overwrite) {
		File file = new File("saveData/" + path + ".stllc");
		File saveDir = new File("saveData");
		FileIn fileIn = new FileIn();

		String[] in = null;
		boolean success = false;
		boolean fileExists = file.exists();

		if (!overwrite) {
			if (fileExists)
				in = fileIn.readFile(file);
		}

		try {
			if (!saveDir.exists())
				saveDir.mkdirs();
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			if (!overwrite) {
				if (fileExists) {
					for (String line : in)
						printWriter.println(line);
				}
			}

			for (String line : text)
				printWriter.println(line);

			printWriter.close();
			success = true;
		} catch (IOException e) {
			System.err.println("Error writing to file " + file.getPath());
		}

		return success;

	}

}
