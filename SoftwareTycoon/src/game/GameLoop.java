package game;

import java.util.HashMap;

import chat.ChatMaster;
import games.GameMaster;
import utils.ConsoleInterface;
import utils.DataManager;
import utils.TimeKeeper;

public class GameLoop {

	public HashMap<String, String> data;
	public HashMap<String, String> games;
	
	public String gameName;
	public static boolean running = false;
	
	public DataManager dm;
	public TimeKeeper timeKeep;
	public GameMaster gmaster;

	public GameLoop(String gameName, HashMap<String, String> data) {
		this.gameName = gameName;
		this.data = data;
		
		dm = new DataManager();
		timeKeep = new TimeKeeper();
		
		if(data.containsKey("currentTime"))
		timeKeep.loadTime(Integer.parseInt(data.get("currentTime")));
		
		games = dm.loadCurrentGames(gameName);
		
		gmaster = new GameMaster(games, gameName, this);
	}

	public void runGame() {
		
		ChatMaster chatMaster = new ChatMaster(gameName, data, games, this);
		running = true;

		while (running) {
			System.out.println();
			games = gmaster.convertList(gmaster.getList());
			
			ConsoleInterface.print("Year: " + timeKeep.getYear() + " | Month: " + timeKeep.getMonth() + " | Week: " + timeKeep.getWeek() + " | Day: " + timeKeep.getDay() + " | Time: " + timeKeep.getTime(), true);
			
			chatMaster.update(games);
			
			timeKeep.increaseTime();
			gmaster.marketing();
			
		}
		
		data.put("currentTime", String.valueOf(timeKeep.getRealTime()));
		
		dm.saveGameStats(gameName, data);
		dm.saveCurrentGames(gameName, gmaster.convertList(gmaster.getList()));
	}

}
