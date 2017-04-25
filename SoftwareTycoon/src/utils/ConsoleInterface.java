package utils;

import java.util.Scanner;

public class ConsoleInterface {
	
	
	public static String getInput(String preText, boolean newLine) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		if(newLine) {
			System.out.println(preText);
		} else {
			System.out.print(preText);
		}
		
		String input = sc.nextLine();
		
		return input;
	}
	
	public static void print(Object output, boolean newLine) {
		if(newLine) {
			System.out.println(output);
		} else {
			System.out.print(output);
		}
	}
	
	public static void printError(Object output) {
		System.err.println(output);
		System.out.println();
	}
	
}
