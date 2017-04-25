package chat;

import java.util.HashMap;

import game.GameLoop;
import utils.ConsoleInterface;

public class ChatMaster {
	
	private PlayerChatBranch playerBranch;
	private CompanyChatBranch companyBranch;
	private GameChatBranch gameBranch;
	public GameLoop gameLoop;
	
	private int currentState = 0;
	
	private String[] stateDialogue = 
		{"Top Branch- type 'help' for all commands.", 
			"Player Branch- type 'help' for all commands.", 
			"Company Branch- type 'help' for all commands.",
			"Employee Branch- type 'help' for all commands.",
			"Game Branch- type 'help' for all commands."};
	
	public ChatMaster(String gameName, HashMap<String, String> data, HashMap<String, String> games, GameLoop game) {
		playerBranch = new PlayerChatBranch(data, game);
		companyBranch = new CompanyChatBranch(data, game);
		gameBranch = new GameChatBranch(data, games, game);
		this.gameLoop = game;
	}
	
	public void update(HashMap<String, String> games) {
		gameLoop.data = gameLoop.dm.addGameStat(gameLoop.data, "companyMoney", true);
		
		gameLoop.data = gameLoop.dm.addGameStat(gameLoop.data, "companyRep", true);
		
		gameLoop.data = gameLoop.dm.addGameStat(gameLoop.data, "currentOffice", true);
		
		gameLoop.data = gameLoop.dm.addGameStat(gameLoop.data, "playerLevel", true);
		
		gameLoop.data = gameLoop.dm.addGameStat(gameLoop.data, "playerUp", true);
		
		System.out.println();
		ConsoleInterface.print(stateDialogue[currentState], true);
		System.out.println();
		
		switch(currentState) {
		case 1:
			playerBranch.preChat();
			break;
		case 2:
			companyBranch.preChat();
			break;
		case 3:
			break;
		case 4:
			gameBranch.preChat(games);
			break;
		default:
			break;
		}
		
		try {
			
		String input = ConsoleInterface.getInput("> ", false);
		
		input = input.toLowerCase();
		input = input.substring(0, 2);
		
		System.out.println();
		runChat(input);
		
		} catch (Exception e) {
			ConsoleInterface.printError("Invalid input. Please type something with at least 2 characters.");
		}
	}
	
	private void runChat(String in) {
		ChatHelp chatHelp = new ChatHelp();
		
		if(in.equals("he")) {
			chatHelp.chatHelp(currentState);
			return;
		}
		
		if(in.equals("ba") && currentState != 0) {
			currentState = 0;
			System.out.println();
			return;
		}
		
		if(in.equals("ex")) {
			GameLoop.running = false;
			System.out.println("Exiting... ");
			return;
		}
		
		if(currentState == 0) {
			if(in.equals("pl")) {
				currentState = 1;
				return;
			}
			
			if(in.equals("co")) {
				currentState = 2;
				return;
			}
			
			if(in.equals("em")) {
				currentState = 3;
				return;
			}
			
			if(in.equals("ga")) {
				currentState = 4;
				return;
			}
		}
		
		switch(currentState) {
		case 1:
			playerBranch.runInput(in);
			break;
		case 2:
			companyBranch.runInput(in);
			break;
		case 3:
			
			break;
		case 4:
			gameBranch.runInput(in);
			break;
		default:
			
			break;
		}
		
	}
	
}
