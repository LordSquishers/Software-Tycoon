package chat;

import java.util.HashMap;

import game.GameLoop;
import utils.ConsoleInterface;

public class CompanyChatBranch {

	private HashMap<String, String> data;
	public GameLoop gameLoop;

	public CompanyChatBranch(HashMap<String, String> data, GameLoop game) {
		this.data = data;
		this.gameLoop = game;
	}

	public void runInput(String input) {
		
		if (input.equals("up")) {
			runChat();
			return;
		}
	}

	private void runChat() {
		ConsoleInterface.print("Money: " + data.get("companyMoney"), true);
		ConsoleInterface.print("Current Office: " + data.get("currentOffice"), true);
		
		int officeCost = (Integer.parseInt(data.get("currentOffice")) + 1) * 15000;
		
		ConsoleInterface.print("Upgrade Cost: " + officeCost, true);
		System.out.println();
		
		if (Integer.parseInt(data.get("companyMoney")) > officeCost) {
			System.out.println();
			String in = ConsoleInterface.getInput("Upgrade Office? ", false);
			
			in = in.toLowerCase();
			in = in.substring(0, 1);
			
			if(in.equals("y")) {
				gameLoop.data.put("currentOffice", String.valueOf(Integer.parseInt(data.get("currentOffice")) + 1) );
				
				gameLoop.data.put("companyMoney", String.valueOf(Integer.parseInt(data.get("companyMoney")) - officeCost));
				
				gameLoop.data.put("companyRep", String.valueOf(Integer.parseInt(data.get("companyRep")) + 2) );
			} else {
				ConsoleInterface.print("Cancelled.", true);
			}
			
		} else {
			ConsoleInterface.print("Not enough money to upgrade.", true);
			ConsoleInterface.print("Needed amount: " + (officeCost - Integer.parseInt(data.get("companyMoney"))), true);
		}
	}

	public void preChat() {
		gameLoop.data = gameLoop.dm.addGameStat(data, "companyMoney", true);
		data = gameLoop.dm.addGameStat(data, "companyMoney", true);

		gameLoop.data = gameLoop.dm.addGameStat(data, "companyRep", true);
		data = gameLoop.dm.addGameStat(data, "companyRep", true);
		
		gameLoop.data = gameLoop.dm.addGameStat(data, "currentOffice", true);
		data = gameLoop.dm.addGameStat(data, "currentOffice", true);
		
		ConsoleInterface.print("<" + data.get("companyName") + "> Money: " + data.get("companyMoney") + " | Rep: " + data.get("companyRep"), true);
		ConsoleInterface.print("Office: " + data.get("currentOffice"), true);
		System.out.println();
	}

}
