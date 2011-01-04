package events.dtu;

import java.util.ArrayList;
import java.util.Date;

public class OpeningTimes {

	private ArrayList<OpeningTime> openingTimes;
	
	public OpeningTimes() {
		openingTimes = new ArrayList<OpeningTime>();
	}
	
	public void add(OpeningTime openingTime) {
		openingTimes.add(openingTime);
	}
	
	public void add(int openMinutes, int openHours, int openDay,
					int closeMinutes, int closeHours, int closeDay) {
		openingTimes.add(new OpeningTime(openMinutes, openHours, openDay,
											closeMinutes, closeHours, closeDay));
	}
	
	public void add(Date open, Date close) {
		openingTimes.add(new OpeningTime(open, close));
	}
	
	public void add(int openInt, int closeInt) {
		openingTimes.add(new OpeningTime(openInt, closeInt));
	}
	
	public boolean isOpen() {
		Date now = new Date();
		for(OpeningTime openingTime : openingTimes) {
			if(openingTime.isOpen(now)) {
				return true;
			}
		}
		return false;
	}
}
