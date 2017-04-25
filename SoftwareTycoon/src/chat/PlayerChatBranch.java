package chat;

import java.util.HashMap;

import game.GameLoop;
import utils.ConsoleInterface;

public class PlayerChatBranch {

	private HashMap<String, String> data;
	public GameLoop gameLoop;

	public PlayerChatBranch(HashMap<String, String> data, GameLoop game) {
		this.data = data;

		this.gameLoop = game;
	}

	public void runInput(String input) {
		gameLoop.data = gameLoop.dm.addGameStat(data, "playerLevel", true);
		data = gameLoop.dm.addGameStat(data, "playerLevel", true);

		gameLoop.data = gameLoop.dm.addGameStat(data, "playerUp", true);
		data = gameLoop.dm.addGameStat(data, "playerUp", true);
		
		if (input.equals("up")) {
			runChat();
			return;
		}
	}

	private void runChat() {
		ConsoleInterface.print("Level: " + data.get("playerLevel"), true);
		ConsoleInterface.print("Available Upgrade Points: " + data.get("playerUp"), true);

		if (Integer.parseInt(data.get("playerUp")) > 0) {
			System.out.println();
			String in = ConsoleInterface.getInput("Upgrade Art or Programming? ", false);
			
			in = in.toLowerCase();
			in = in.substring(0, 1);
			
			if(in.equals("a")) {
				gameLoop.data.put("playerLevel", String.valueOf(Integer.parseInt(data.get("playerLevel")) + 1) );
				
				gameLoop.data.put("playerArt", String.valueOf(Integer.parseInt(data.get("playerArt")) + 3) );
				
				gameLoop.data.put("playerUp", String.valueOf(Integer.parseInt(data.get("playerUp")) - 1) );
			}
			
			if(in.equals("p")) {
				gameLoop.data.put("playerLevel", String.valueOf(Integer.parseInt(data.get("playerLevel")) + 1) );
				
				gameLoop.data.put("playerProg", String.valueOf(Integer.parseInt(data.get("playerProg")) + 3) );
				
				gameLoop.data.put("playerUp", String.valueOf(Integer.parseInt(data.get("playerUp")) - 1) );
			}
		}
	}

	public void preChat() {
		ConsoleInterface.print("<" + data.get("playerName") + "> Art: " + data.get("playerArt") + " | Programming: "
				+ data.get("playerProg"), true);
		ConsoleInterface.print("Upgrade Points: " + data.get("playerUp"), true);
		System.out.println();
	}

}
