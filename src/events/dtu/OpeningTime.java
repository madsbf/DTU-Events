package events.dtu;

import java.util.Calendar;
import java.util.Date;

public class OpeningTime {
	
	private Date open = new Date(0);
	private Date close = new Date(0);
	
	public OpeningTime(Date open, Date close) {
		this.open = open;
		this.close = close;
	}
	
	public OpeningTime(int openMinutes, int openHours, int openDay,
						int closeMinutes, int closeHours, int closeDay) {
		open.setDate(openDay);
		open.setHours(openHours);
		open.setMinutes(openMinutes);
		close.setDate(closeDay);
		close.setHours(closeHours);
		close.setMinutes(closeMinutes);
	}
	
	public OpeningTime(int openInt, int closeInt) {
		open = intToDate(openInt);
		close = intToDate(closeInt);
	}
	
	private Date intToDate(int dateInt) {
		
		dateInt = dateInt - 28800;
		int day = dateInt / 86400;
		dateInt = dateInt - 86400 * day;
		if(day < 3) {
			day = day + 5;
		}
		else {
			day = day - 2;
		}
		
		Date date = new Date(0);
		date.setDate(day);
		date.setHours(dateInt / 3600);
		date.setMinutes(dateInt % 60);
		
		return date;
	}
	
	public boolean isOpen(Date date) {
		if(date.after(open) && date.before(close)) {
			return true;
		}
		return false;
	}

}
