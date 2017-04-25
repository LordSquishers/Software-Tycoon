package game;

import java.io.File;
import java.util.HashMap;

import io.FileIn;
import io.FileOut;
import utils.ConsoleInterface;
import utils.DataManager;

public class GameSetup {

	public static final String[] BLANK = { "" };
	public static final HashMap<String, String> BLANK_LIST = new HashMap<>();

	private String saveName;
	private HashMap<String, String> saveIn;

	public void prepare(String saveName) {
		this.saveName = saveName;
		DataManager dataM = new DataManager();
		boolean archiveExists = isArchiveExists();
		boolean gameKnown = false;

		if (archiveExists) {
			gameKnown = checkArchive(saveName);
		} else {
			FileOut fileOut = new FileOut();
			fileOut.writeToFile("gameArchive", BLANK, false);
		}

		boolean initialDataPresent = false;
		if (gameKnown) {
			saveIn = dataM.loadGameStats(saveName);
			initialDataPresent = checkInitData(saveIn);
		} else {
			writeGameToArchive(saveName);
			writeGameFile(saveName);
		}

		if (initialDataPresent) {
			ConsoleInterface.print("Found save data.", true);
		} else {
			saveIn = createBaseData();
		}

		//TODO- Find a way to create files if they do not exist yet.
		//As of right now, if the file doesn't exist nothing will be saved.
		
		ConsoleInterface.print("Intial game setup complete. Loading game...", true);

		System.out.println();
		System.out.println();

	}

	private HashMap<String, String> createBaseData() {
		HashMap<String, String> newSave = new HashMap<>();

		String companyName = ConsoleInterface.getInput("Name your company: ", false);
		String playerName = ConsoleInterface.getInput("What's your name: ", false);
		String artSkill = ConsoleInterface.getInput("Enter your art skill (0-30): ", false);

		newSave.put("companyName", companyName);
		newSave.put("playerName", playerName);
		newSave.put("companyMoney", "5000");

		int art = Integer.parseInt(artSkill);
		if (art < 0)
			art = 0;
		if (art > 30)
			art = 30;

		int prog = 40 - art;

		newSave.put("playerArt", String.valueOf(art));
		newSave.put("playerProg", String.valueOf(prog));

		return newSave;
	}

	private boolean checkInitData(HashMap<String, String> save) {
		boolean dataPresent = false;

		dataPresent = save.containsKey("companyName");

		return dataPresent;
	}

	public void start() {
		GameLoop game = new GameLoop(saveName, saveIn);
		game.runGame();
	}

	private void writeGameFile(String gameName) {
		FileOut fileOut = new FileOut();
		fileOut.writeToFile(gameName + "_00", BLANK, false);
		fileOut.writeToFile(gameName + "_02", BLANK, false);
	}

	private void writeGameToArchive(String game) {
		FileOut fileOut = new FileOut();
		String[] gameOut = { game };

		fileOut.writeToFile("gameArchive", gameOut, false);
	}

	private boolean checkArchive(String save) {
		boolean gameExists = false;

		FileIn fileIn = new FileIn();
		String[] games = fileIn.readFile(new File("saveData/gameArchive.stllc"));

		for (String game : games) {
			if (game.equals(save))
				gameExists = true;
		}

		return gameExists;
	}

	private boolean isArchiveExists() {
		FileIn fi = new FileIn();
		return fi.isFileExisting("gameArchive");
	}

}
