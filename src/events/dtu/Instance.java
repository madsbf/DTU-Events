package events.dtu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.maps.GeoPoint;

public abstract class Instance {

	public String name;
	public long fId;
	public int id;
	public String pictureUrl;
	protected boolean page;
	public int drawable;
	public int drawableEvent;
	
	public static final int GROUP = 0;
	public static final int BAR = 1;
	
	public Instance(int id,
				long fId,
				String name,
				String pictureUrl,
				boolean page,
				int drawable,
				int drawableEvent) {
		this.id = id;
		this.fId = fId;
		this.name = name;
		this.pictureUrl = pictureUrl;
		this.page = page;
		this.drawable = drawable;
		this.drawableEvent = drawableEvent;
	}
	
	public Instance(int id, JSONObject json) {
		try {
			this.id = id;
			fId = json.getLong("fId");
			name = json.getString("name");
			pictureUrl = json.getString("pictureUrl");
			page = json.getBoolean("page");
			drawable = json.getInt("drawable");
			drawableEvent = json.getInt("drawable_event");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public abstract JSONObject toJSON();
	
	public abstract boolean isBar();
	
	public abstract boolean isOpen();
	
	public ArrayList<Event> loadEvents() {
		ArrayList<Event> events = new ArrayList<Event>();
		if(page) {
			String jsonString = "";
	    	try 
	    	{
				jsonString = DTUEvents.mFacebook.request(fId + "/events");
				JSONObject json = Util.parseJson(jsonString);
				JSONArray eventsArray = json.getJSONArray("data");
				for(int i = 0; i < eventsArray.length(); i++)
				{
					JSONObject event = eventsArray.getJSONObject(i);
					
					events.add(new Event(event.optString("name"),
							event.optString("start_time"),
							event.optString("end_time"),
							event.optString("location"),
							event.optString("id"),
							id));
				}
		        
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacebookError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return events;
	}
	
	public void showInstanceInfo(Context context) {			
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle(name)
    	       .setMessage(context.getString(R.string.opening_times) + ":")
    	       .setCancelable(false)
    	       .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() 
    	       {
    	           public void onClick(DialogInterface dialog, int id) 
    	           {
    	                dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
	}
}
