package chat;

import utils.ConsoleInterface;

public class ChatHelp {
	
	public void chatHelp(int id) {
		if(id == 0) {
			ConsoleInterface.print("Commands: player, company, employees, games, exit", true);
		}
		
		if(id == 1) {
			ConsoleInterface.print("Commands: upgrade, back, exit", true);
		}
		
		if(id == 2) {
			ConsoleInterface.print("Commands: upgrade office, back, exit", true);
		}
		
		if(id == 3) {
			ConsoleInterface.print("Commands: hire, fire, back, exit", true);
		}
		
		if(id == 4) {
			ConsoleInterface.print("Commands: create game, list games, market", true);
		}
	}
	
}
