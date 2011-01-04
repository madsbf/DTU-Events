package events.dtu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.maps.GeoPoint;

public class Bar extends Instance {
	
	public GeoPoint location;
	public String address;
	private OpeningTimes openingTimes;
	
	public Bar(int id, JSONObject json) {
		super(id, json);
		try {
			location = new GeoPoint(json.getInt("locationLatitude"),json.getInt("locationLongitude"));
			address = json.getString("address");
			
			openingTimes = new OpeningTimes();
			// TODO
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Bar(int id, long fId, String name, String pictureUrl, boolean page,
			GeoPoint location, String address,
			OpeningTimes openingTimes, int drawable, int drawableEvent) {
		super(id, fId, name, pictureUrl, page, drawable, drawableEvent);
		this.location = location;
		this.address = address;
		this.openingTimes = openingTimes;
	}
	
	public boolean isBar() {
		return true;
	}

	@Override
	public boolean isOpen() {
		return openingTimes.isOpen();
	}

	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("type", Instance.BAR);
			json.put("page", page);
			json.put("fId", fId);
			json.put("name", name);
			json.put("pictureUrl", pictureUrl);
			json.put("locationLatitude", location.getLatitudeE6());
			json.put("locationLongitude", location.getLongitudeE6());
			json.put("address", address);
			json.put("drawable", drawable);
			json.put("drawable_event", drawableEvent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
}
