package utils;

public class TimeKeeper {
	
	public int currentTime = 0;
	public int[] date, realDate;
	
	public TimeKeeper() {
		currentTime = 0;
		date = new int[5];
		realDate = new int[4];
		
		/* 0- Year
		 * 1- Month
		 * 2- Week
		 * 3- Day
		 * 4- Time
		 */
		
		/* 0- Month
		 * 1- Week
		 * 2- Day
		 * 3- Time
		 */
		
	}
	
	public void loadTime(int currentTime) {
		this.currentTime = currentTime;
		formatTime();
	}
	
	public void increaseTime() {
		currentTime++;
		formatTime();
	}
	
	public void increaseTime(int amt) {
		currentTime += amt;
		formatTime();
	}
	
	private void formatTime() {
		
		realDate[3] = currentTime;
		realDate[2] = realDate[3] / 3;
		realDate[1] = realDate[2] / 7;
		realDate[0] = realDate[1] / 4;
		
		date[0] = (realDate[0] / 12) + 1; 
		date[1] = (realDate[0] % 12) + 1;
		date[2] = (realDate[1] % 4) + 1; 
		date[3] = (realDate[2] % 7) + 1;
		date[4] = (realDate[3] % 3) + 1;

	}
	
	public int getDay() {
		return date[3];
	}
	
	public int getWeek() {
		return date[2];
	}
	
	public int getMonth() {
		return date[1];
	}
	
	public int getYear() {
		return date[0];
	}
	
	public int getTime() {
		return date[4];
	}
	
	public int getRealTime() {
		return currentTime;
	}
	
}
