package main;

import game.GameSetup;
import utils.ConsoleInterface;

public class Main {
	
	public static void main(String args[]) {
		
		ConsoleInterface.printError("Warning! If the save you enter is in the archive but does not have a file, nothing will be saved!");
		String gm = ConsoleInterface.getInput("Enter your save name: ", false);
		
		System.out.println();
		System.out.println();
		
		GameSetup setup = new GameSetup();
		setup.prepare(gm);
		setup.start();
	
	}
	
}
