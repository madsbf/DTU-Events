package events.dtu.model.openingTime;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class OpeningTime {
	
	private Date open = new Date(0);
	private Date close = new Date(0);
	
	public OpeningTime(Date open, Date close) {
		this.open = open;
		this.close = close;
	}
	
	public OpeningTime(JSONObject json)
	{
		try {
			open.setMinutes(json.getInt("open_minutes"));
			open.setHours(json.getInt("open_hours"));
			open.setDate(json.getInt("open_day"));
			close.setMinutes(json.getInt("close_minutes"));
			close.setHours(json.getInt("close_hours"));
			close.setDate(json.getInt("close_day"));
			
			if(close.before(open))
			{
				close.setDate(close.getDate() + 7);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("open_minutes", open.getMinutes());
			json.put("open_hours", open.getHours());
			json.put("open_day", open.getDate());
			json.put("close_minutes", close.getMinutes());
			json.put("close_hours", close.getHours());
			json.put("close_day", close.getDate() % 7);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
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
		Date d = (Date) date.clone();
		d.setDate(date.getDay());
		d.setYear(70);
		if(d.after(open) && d.before(close)) {
			return true;
		}
		return false;
	}

}
