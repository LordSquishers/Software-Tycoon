package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIn {

	/**
	 * Reads the given file path and gets the data.
	 * 
	 * @param file
	 *            - The File data to find the file
	 * @return - A String array containing all of the data.
	 */
	public String[] readFile(File file) {
		String[] input = null;
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			List<String> lines = new ArrayList<>();

			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				lines.add(currentLine);
			}

			input = new String[lines.size()];
			int index = 0;
			for (String line : lines) {
				input[index++] = line;
			}

			fr.close();
			br.close();
		} catch (IOException e) {
			System.err.println("Could not read file " + file.getPath());
			input = new String[1];
			input[0] = "Error";
		}

		return input;
	}
	
	public boolean isFileExisting(String fileName) {
		boolean isFile = false;
		
		File file = new File("saveData/" + fileName + ".stllc");
		
		isFile = file.exists();
		
		return isFile;
	}

}
