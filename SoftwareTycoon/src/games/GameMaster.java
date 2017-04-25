package games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import game.GameLoop;

public class GameMaster {

	public static final String[] PLATFORMS = { "PC", "Playbox", "ZBox", "Woo", "SwedePhone4" };
	public static final String[] GENRES = { "Action", "Adventure", "RPG", "Simulation", "Sandbox" };

	public static final int[][] TYPE_CONVERSION = new int[5][5];

	private int oldDay = 0;

	private GameLoop gl;

	private List<Game> games = new ArrayList<>();

	public GameMaster(HashMap<String, String> currentGames, String gameName, GameLoop gameLoop) {
		this.games = convertMap(currentGames);
		this.gl = gameLoop;
		this.oldDay = gl.timeKeep.getDay();

		int[] typeset0 = { 3, 4, 5, 6, 7 };
		int[] typeset1 = { 12, 13, 14, 15, 16 };
		int[] typeset2 = { 21, 22, 23, 24, 25 };
		int[] typeset3 = { 30, 31, 32, 33, 34 };
		int[] typeset4 = { 47, 48, 49, 50, 51 };

		TYPE_CONVERSION[0] = typeset0;
		TYPE_CONVERSION[1] = typeset1;
		TYPE_CONVERSION[2] = typeset2;
		TYPE_CONVERSION[3] = typeset3;
		TYPE_CONVERSION[4] = typeset4;
	}

	public static int[] getTypeIDs(int type) {
		int[] values = new int[2];

		for (int plat = 0; plat < PLATFORMS.length; plat++) {
			for (int genre = 0; genre < GENRES.length; genre++) {
				if (TYPE_CONVERSION[plat][genre] == type) {
					values[0] = plat;
					values[1] = genre;
				}
			}
		}

		return values;
	}

	private List<Game> convertMap(HashMap<String, String> games) {
		List<Game> newGames = new ArrayList<>();
		Iterator it = games.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			String gameName = (String) pair.getKey();
			String stat = pair.getValue().toString();
			String st[] = stat.split("SS");

			Game game = new Game(gameName, st[0], Integer.parseInt(st[1]), Integer.parseInt(st[2]),
					Integer.parseInt(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]),
					Boolean.parseBoolean(st[6]));
			newGames.add(game);
		}

		return newGames;
	}

	public HashMap<String, String> convertList(List<Game> games) {
		HashMap<String, String> newGames = new HashMap<>();

		for (Game game : games) {
			String save = game.getName();
			String data = game.getCreator() + "SS" + game.getGain() + "SS" + game.getCost() + "SS" + game.getType()
					+ "SS" + game.getRating() + "SS" + game.getMarketMultiplier() + "SS" + game.isMarketed();

			newGames.put(save, data);
		}
		return newGames;
	}

	public void addGame(Game game) {
		games.add(game);
	}

	public List<Game> getList() {
		return this.games;
	}

	public static String getPlats() {
		String list = "";

		for (String plat : PLATFORMS) {
			list = list.concat(plat + ", ");
		}

		list = list.substring(0, list.length() - 2);

		return list;
	}

	public static String getGenres() {
		String list = "";

		for (String genre : GENRES) {
			list = list.concat(genre + ", ");
		}

		list = list.substring(0, list.length() - 2);

		return list;
	}

	public void removeGame(String name) {
		Game game = findGameFromName(name);

		if (game != null) {
			games.remove(game);
		}
	}

	public Game getGame(String name) {
		return findGameFromName(name);
	}

	private Game findGameFromName(String name) {
		Game foundGame = null;

		for (Game game : games) {
			if (game.getName().equals(name)) {
				foundGame = game;
			}
		}

		return foundGame;
	}

	public void marketing() {
		if (gl.timeKeep.getDay() != oldDay) {
			marketRevenue();
			marketDecay();
		}
	}

	private void marketDecay() {
		List<Game> marketedGames = new ArrayList<>();

		for (Game game : games) {
			if (game.isMarketed())
				marketedGames.add(game);
		}

		for (Game game : marketedGames) {
			int cost = game.getCost(); //100
			float multi = game.getMarketMultiplier(); //2

			double subWCost = cost / multi; //50
			double sub = 100 * (multi / subWCost); //4
			
			sub -= ((0.025) * Integer.parseInt(gl.data.get("companyRep"))) / 6;

			if(sub >= multi) sub = 0;
			multi = (float) (multi - sub);
			
			game.setMarketMultiplier(multi);
			
			removeGame(game.getName());
			addGame(game);
		}
	}

	private void marketRevenue() {
		int marketRev = 0;
		List<Game> marketedGames = new ArrayList<>();
		
		for (Game game : games) {
			if (game.isMarketed())
				marketedGames.add(game);
		}

		for (Game game : marketedGames) {
			int endrev = 0;

			double[] eqp = new double[6];
			eqp[0] = 900;
			eqp[5] = game.getMarketMultiplier();
			eqp[4] = game.getCost() / 10;
			eqp[3] = eqp[4] / eqp[5];
			eqp[2] = game.getCost() * game.getMarketMultiplier() * (game.getRating() * game.getRating());
			eqp[1] = eqp[2] / eqp[3];

			endrev = (int) (eqp[0] * eqp[1]);
			
			game.setGain(endrev + game.getGain());
			removeGame(game.getName());
			addGame(game);
		
			marketRev += (endrev);
		}

		gl.data.put("companyMoney", String.valueOf(Integer.parseInt(gl.data.get("companyMoney")) + marketRev));
	}

}
