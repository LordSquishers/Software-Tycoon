package chat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import game.GameLoop;
import games.Game;
import games.GameMaster;
import utils.ConsoleInterface;

public class GameChatBranch {

	private HashMap<String, String> data;
	private HashMap<String, String> games;
	public GameLoop gameLoop;

	public GameChatBranch(HashMap<String, String> data, HashMap<String, String> games, GameLoop game) {
		this.data = data;
		this.gameLoop = game;
		this.games = games;
	}

	public void runInput(String input) {
		if (input.equals("li")) {
			runList(games);
		}

		if (input.equals("cr")) {
			runCreation();
		}

		if (input.equals("ma")) {
			runMarket();
		}

	}

	public HashMap<String, String[]> runList(HashMap<String, String> gameList) {
		HashMap<String, Integer> gameMap = new HashMap<>();
		String[][] gameStats = new String[gameList.size()][4];
		HashMap<String, String[]> newList = new HashMap<>();

		Iterator it = gameList.entrySet().iterator();
		int id = 0;
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			String gameName = (String) pair.getKey();
			String stat = pair.getValue().toString();

			String stats[] = stat.split("SS");
			gameStats[id] = stats;
			gameMap.put(gameName, id++);

			int[] val = GameMaster.getTypeIDs(Integer.parseInt(stats[3]));
			int rating = Math.round(Float.parseFloat(stats[4]) * 100);

			String platform = GameMaster.PLATFORMS[val[0]];
			String genre = GameMaster.GENRES[val[1]];

			ConsoleInterface.print(gameName + "> Gain: " + stats[1] + " | Cost: " + stats[2] + " | Platform: "
					+ platform + " | Genre: " + genre + " | Rating: " + rating + "/100", true);

			String yon;
			if (stats[6].equals("true")) {
				yon = "Yes";
			} else {
				yon = "No";
			}

			ConsoleInterface.print(gameName + "> On the market: " + yon + " | Market Strength: " + stats[5], true);

			System.out.println();
			newList.put(gameName, stats);
		}

		return newList;
	}

	private void runMarket() {

		HashMap<String, String[]> gameData = runList(games);
		System.out.println();

		boolean gameFound = false;
		String gameName = "";
		while (!gameFound) {
			ConsoleInterface.print("Which game would you like to market/boost?", true);
			String input1 = ConsoleInterface.getInput("> ", false);

			Iterator it = gameData.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();

				String gn = (String) pair.getKey();

				if (gn.equalsIgnoreCase(input1)) {
					gameName = gn;
					gameFound = true;
				}
			}
		}

		Game marketedGame = gameLoop.gmaster.getGame(gameName);
		gameLoop.gmaster.removeGame(gameName);

		if (marketedGame.isMarketed()) {
			float boost = marketedGame.getMarketMultiplier() + 0.5f;

			marketedGame.setMarketMultiplier(boost);
		} else {
			marketedGame.setMarketed(true);
			marketedGame.setMarketMultiplier(2.0f);
		}

		gameLoop.gmaster.addGame(marketedGame);
	}

	private void runCreation() {
		ConsoleInterface.print("Current funds: " + data.get("companyMoney"), true);
		String name = ConsoleInterface.getInput("Name > ", false);

		ConsoleInterface.print("Platforms: " + GameMaster.getPlats(), true);
		String platform = ConsoleInterface.getInput("Available on > ", false);

		ConsoleInterface.print("Genres: " + GameMaster.getGenres(), true);
		String genre = ConsoleInterface.getInput("Type of game > ", false);

		int platID = 0, genreID = 0, typeID = 0;

		for (int i = 0; i < GameMaster.PLATFORMS.length; i++) {
			if (GameMaster.PLATFORMS[i].substring(0, 2).equalsIgnoreCase(platform.substring(0, 2))) {
				platID = i;
			}
		}

		for (int i = 0; i < GameMaster.GENRES.length; i++) {
			if (GameMaster.GENRES[i].substring(0, 2).equalsIgnoreCase(genre.substring(0, 2))) {
				genreID = i;
			}
		}

		typeID = GameMaster.TYPE_CONVERSION[platID][genreID];

		Game newGame = new Game(name, data.get("companyName"), 0, 0, typeID, 0.0f, 0.0f, false);

		newGame.setCost(newGame.calculateCost(name, platID, genreID, gameLoop));
		newGame.setRating(newGame.calculateRating(name, typeID, newGame.getCost(), gameLoop));

		System.out.println();

		ConsoleInterface.print(newGame.getName() + ": Cost: " + newGame.getCost() + " Platform: "
				+ GameMaster.PLATFORMS[platID] + " Genre: " + GameMaster.GENRES[genreID], true);
		String confirmation = ConsoleInterface.getInput("Do you want to release this game? (y/n) > ", false);
		confirmation = confirmation.toLowerCase();
		confirmation = confirmation.substring(0, 2);

		if (confirmation.equals("ye")) {
			if (Integer.parseInt(data.get("companyMoney")) >= newGame.getCost()) {
				gameLoop.data.put("companyMoney",
						String.valueOf(Integer.parseInt(data.get("companyMoney")) - newGame.getCost()));
				int rating = Math.round(newGame.getRating() * 100);
				ConsoleInterface.print("Released! The rating is: " + rating, true);
				System.out.println();
				gameLoop.gmaster.addGame(newGame);
			} else {
				confirmation = "no";
			}
		}
		
		if (confirmation.equals("no")) {
			System.out.println();
			System.out.println("Cancelling...");
			System.out.println();
			return;
		}
	}

	public void preChat(HashMap<String, String> games) {
		gameLoop.data = gameLoop.dm.addGameStat(data, "companyMoney", true);
		data = gameLoop.dm.addGameStat(data, "companyMoney", true);
		this.games = games;

		ConsoleInterface.print("<" + data.get("companyName") + "> Money: " + data.get("companyMoney"), true);
		ConsoleInterface.print("Office: " + data.get("currentOffice"), true);
		System.out.println();
	}

}
