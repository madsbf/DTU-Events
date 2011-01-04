package events.dtu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class EventsList 
{
	private ArrayList<Event> oldEvents;
	private ArrayList<Event> currentEvents;
	private ArrayList<Event> upcomingEvents;
	
	private ArrayList<Event> shownEvents = new ArrayList<Event>();
	
	private Date currentDate;
	
	public static final int ALL_EVENTS = 0;
	public static final int OLD_EVENTS = 1;
	public static final int CURRENT_EVENTS = 2;
	public static final int UPCOMING_EVENTS = 3;
	public static final int OLD_CURRENT_EVENTS = 4;
	public static final int CURRENT_UPCOMING_EVENTS = 5;
	
	public EventsList(ArrayList<Instance> instances)
	{
		oldEvents = new ArrayList<Event>();
		currentEvents = new ArrayList<Event>();
		upcomingEvents = new ArrayList<Event>();
		Calendar todayC = Calendar.getInstance();
		currentDate = todayC.getTime();
    	for(Instance instance : instances)
    	{
    		addEvents(instance);
    	}
    	createShownEvents();
	}
	
	private void createShownEvents() {
		shownEvents.clear();
		switch(Variables.SHOWN_EVENTS)
		{
		case ALL_EVENTS:
			shownEvents.addAll(oldEvents);
			shownEvents.addAll(currentEvents);
			shownEvents.addAll(upcomingEvents);
			break;
		case OLD_EVENTS:
			shownEvents.addAll(oldEvents);
			break;
		case CURRENT_EVENTS:
			shownEvents.addAll(currentEvents);
			break;
		case UPCOMING_EVENTS:
			shownEvents.addAll(upcomingEvents);
			break;
		case OLD_CURRENT_EVENTS:
			shownEvents.addAll(oldEvents);
			shownEvents.addAll(currentEvents);
			break;
		case CURRENT_UPCOMING_EVENTS:
			shownEvents.addAll(currentEvents);
			shownEvents.addAll(upcomingEvents);
			break;
		}
	}
	
	public int size()
	{
		return shownEvents.size();
	}
	
	public ArrayList<Event> getEvents(int pageId) {
		ArrayList<Event> barEvents = new ArrayList<Event>();
		for(Event event : shownEvents) {
			if(event.pageId == pageId) {
				barEvents.add(event);
			}
		}
		return barEvents;
	}
	
	public Event get(int i)
	{
		return shownEvents.get(i);
	}
	
	public void add(Event event)
	{
		ArrayList<Event> events;
		if(event.startDate.before(currentDate))
		{
			if(event.endDate.before(currentDate))
			{
				events = oldEvents;
			}
			else
			{
				events = currentEvents;
			}
		}
		else
		{
			events = upcomingEvents;
		}
		
		if(events.size() == 0)
		{
			events.add(event);
		}
		else
		{
			for(int i = 0; i < events.size(); i++)
			{
				Event e = events.get(i);
				if(event.startDate.compareTo(e.startDate) <= 0)
				{
					events.add(i,event);
					break;
				}
				if(i == events.size() - 1)
				{
					events.add(event);
				}
			}
		}
	}
	
	public void add(String name,
					String startDate,
					String endDate,
					String location,
					String id,
					int pageId) {
		this.add(new Event(name,
							startDate,
							endDate,
							location,
							id,
							pageId));
	}
	
	public void addEvents(Instance instance)
	{
		for(Event event : instance.loadEvents()) {
			this.add(event);
		}
	}
	
	public ArrayList<Event> getEvents() {
		return shownEvents;
	}
	
	public void setShown(int shownEvents) {
		Variables.SHOWN_EVENTS = shownEvents;
		createShownEvents();
	}
}
