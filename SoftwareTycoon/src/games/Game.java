package games;

import java.util.Random;

import game.GameLoop;

public class Game {

	private int gain, cost, type;
	private float rating, marketMultiplier;
	private String name, creator;
	private boolean isMarketed;

	public Game(String name, String creator) {
		this.name = name;
		this.creator = creator;
	}

	public Game(String name, String creator, int gain, int cost, int type, float rating, float marketMultiplier, boolean isMarketed) {
		this.gain = gain;
		this.cost = cost;
		this.type = type;
		this.rating = rating;
		this.marketMultiplier = marketMultiplier;
		this.name = name;
		this.creator = creator;
		this.isMarketed = isMarketed;
	}
	
	public int calculateCost(String name, int platform, int genre, GameLoop gl) {
		int cost = 0;
		Random rand = new Random();
		
		cost = (rand.nextInt(name.length() * 2)) * 100;
		cost += (rand.nextInt((platform + 1) * (genre + 1))) * 100; 
		
		cost *= ((Integer.parseInt(gl.data.get("currentOffice")) / 5) + 1);
		
		return cost;
	}
	
	public float calculateRating(String name, int type, int cost, GameLoop gl) {
		float rating = 0.0f;
	
		double base = cost / ((name.length() * 2) + 25); // but only 80% of it.
		base /= 100;
		base *= 0.8;
		rating += base; // This takes the cost of the game and divides it by the maximum possible cost of the game
		
		Random r = new Random();
		float lastBit = r.nextFloat() * 0.2f;
		
		float playerProgM = Integer.parseInt(gl.data.get("playerProg")) / 100;
		float playerArtM = Integer.parseInt(gl.data.get("playerArt")) / 100;
		
		boolean isArt = r.nextBoolean();
		
		if(isArt) {
			playerArtM /= 2;
		} else {
			playerProgM /= 2;
		}
		
		float playerAddition = playerArtM + playerProgM;
		
		rating += lastBit;
		rating += playerAddition;
		
		rating /= 1f + playerAddition;	
		
		gl.data = gl.dm.addGameStat(gl.data, "playerUp", true);
		
		if(rating > 0.59f && rating < 0.80f) {
			   gl.data.put("playerUp", String.valueOf(Integer.parseInt(gl.data.get("playerUp")) + 1) );
			}
		
		if(rating > 0.79f && rating < 0.90f) {
		   gl.data.put("playerUp", String.valueOf(Integer.parseInt(gl.data.get("playerUp")) + 2) );
		}
		
		if(rating < 0.89f && rating < 1.01f) {
			gl.data.put("playerUp", String.valueOf(Integer.parseInt(gl.data.get("playerUp")) + 3) );
		}
		
		return rating;
	}

	public int getGain() {
		return gain;
	}

	public int getCost() {
		return cost;
	}

	public int getType() {
		return type;
	}

	public float getRating() {
		return rating;
	}

	public float getMarketMultiplier() {
		return marketMultiplier;
	}

	public String getName() {
		return name;
	}

	public String getCreator() {
		return creator;
	}

	public boolean isMarketed() {
		return isMarketed;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setMarketMultiplier(float marketMultiplier) {
		this.marketMultiplier = marketMultiplier;
	}

	public void setMarketed(boolean isMarketed) {
		this.isMarketed = isMarketed;
	}

	public void setGain(int gain) {
		this.gain = gain;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
