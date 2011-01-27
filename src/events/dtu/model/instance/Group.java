package events.dtu.model.instance;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;

public class Group extends Instance {
	
	public Group(int id, JSONObject json) {
		super(id, json);
	}
	
	public Group(int id, long fId, String name, String pictureUrl, boolean page, int drawable, int drawableEvent) {
		super(id, fId, name, pictureUrl, page, drawable, drawableEvent);
	}
	
	public boolean isBar() {
		return false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("type", Instance.GROUP);
			json.put("page", page);
			json.put("fId", fId);
			json.put("name", name);
			json.put("pictureUrl", pictureUrl);
			json.put("drawable", drawable);
			json.put("drawable_event", drawableEvent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
}
