package events.dtu.model.openingTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpeningTimes implements Iterable<OpeningTime> {

	private ArrayList<OpeningTime> openingTimes;
	
	public OpeningTimes() {
		openingTimes = new ArrayList<OpeningTime>();
	}
	
	public OpeningTimes(JSONArray json)
	{
		openingTimes = new ArrayList<OpeningTime>();
		for(int i = 0; i < json.length(); i++)
		{
			try {
				this.add(json.getJSONObject(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void add(OpeningTime openingTime) {
		openingTimes.add(openingTime);
	}
	
	public void add(JSONObject json) {
		openingTimes.add(new OpeningTime(json));
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
	
	public int size()
	{
		return openingTimes.size();
	}
	
	public JSONArray toJSON()
	{
		JSONArray json = new JSONArray();
		for(OpeningTime openingTime : openingTimes)
		{
			JSONObject openingTimeJSON = openingTime.toJSON();
			json.put(openingTimeJSON);
		}
		return json;
	}

	public Iterator<OpeningTime> iterator() {
		Iterator<OpeningTime> iter = openingTimes.iterator();
		return iter;
	}
}
