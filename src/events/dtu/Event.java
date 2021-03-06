package events.dtu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Geocoder;
import android.location.Address;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.maps.GeoPoint;

public class Event 
{
	public String name;
	public Date startDate;
	public Date endDate;
	public String address = "";
	public Long id;
	public String description;
	public int pageId;
	public boolean atDTU = true;
	
	private boolean loaded = false;
	
	private int attendants = 0;
	public GeoPoint location;
	private float distance = 0f;

	public Event(String name,
				String startDate,
				String endDate,
				String location,
				String id,
				int pageId) {
		this.name = name;
		this.startDate = stringToDate(startDate);
		this.endDate = stringToDate(endDate);
		this.address = location;
		this.id = Long.parseLong(id);
		this.pageId = pageId;
	}
	
	private Date stringToDate(String dateString)
	{
		int pos = 0;
		int year = Integer.parseInt(dateString.substring(pos, pos = dateString.indexOf("-", pos)));
		int month = Integer.parseInt(dateString.substring(pos + 1, pos = dateString.indexOf("-", pos + 1))) - 1;
		int day = Integer.parseInt(dateString.substring(pos + 1, pos = dateString.indexOf("T", pos + 1)));
		int hours = Integer.parseInt(dateString.substring(pos + 1, pos = pos+3));
		int minutes = Integer.parseInt(dateString.substring(pos + 1, pos+3));

		hours = hours - 8;
		if(hours < 0) {
			hours = 24 + hours;
			day--;
		}
		
		return new Date(year - 1900, month, day, hours, minutes);
	}
	
	public boolean isActive(Date date) {
		if(date.after(startDate) && date.before(endDate)) {
			return true;
		}
		return false;
	}
	
	private void initializeLocation(JSONObject json, Context context) {
		String loc = json.optString("location");
		String newAddress = "";
		JSONObject venue = json.optJSONObject("venue");
		if(venue != null) {
			String street = venue.optString("street");
			String city = venue.optString("city");
			String country = venue.optString("country");
			if(!street.equals("")) {
				newAddress = newAddress + ", " + street;
			}
			if(!city.equals("")) {
				newAddress = newAddress + ", " + city;
			}
			if(!country.equals("")) {
				newAddress = newAddress + ", " + country;
			}
		}
		
		if(loc.contains("GeoPoint(")) {
			int index = loc.indexOf("GeoPoint(");
			int latitudeE6 = (int) (Float.parseFloat(loc.substring(index + 9, index + 18)) * 1E6);
			int longitudeE6 = (int) (Float.parseFloat(loc.substring(index + 19, index + 28)) * 1E6);
			location = new GeoPoint(latitudeE6, longitudeE6);
		}
		else if(description.contains("GeoPoint(")) {
			int index = description.indexOf("GeoPoint(");
			int latitudeE6 = (int) (Float.parseFloat(description.substring(index + 9, index + 18)) * 1E6);
			int longitudeE6 = (int) (Float.parseFloat(description.substring(index + 19, index + 28)) * 1E6);
			location = new GeoPoint(latitudeE6, longitudeE6);
		}
		else {
			if(!newAddress.equals("")) {
				Geocoder geocoder = new Geocoder(context);
				List<Address> addresses;
				try {
					addresses = geocoder.getFromLocationName(newAddress, 1);
					if(addresses != null) {
						if(!addresses.isEmpty()) {
							int latitudeE6 = (int) (addresses.get(0).getLatitude() * 1E6);
							int longitudeE6 = (int) (addresses.get(0).getLongitude() * 1E6);
							if(latitudeE6 < Statics.LOWER_BOUND_DOUBLE
									|| latitudeE6 >  Statics.UPPER_BOUND_DOUBLE
									|| longitudeE6 < Statics.LEFT_BOUND_DOUBLE
									|| longitudeE6 > Statics.RIGHT_BOUND_DOUBLE) {
								atDTU = false;
							}
							location = new GeoPoint(latitudeE6, longitudeE6);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Instance instance = DTUEvents.instances.get(pageId);
			if(instance.isBar()) {
				Bar bar = (Bar) instance;
				if(newAddress.equals(bar.address) || location == null || location == bar.location || loc.contains(bar.name)) {
					newAddress = bar.address;
					location = bar.location;
				}
			}
		}
		
		address = newAddress;
	}
	
	public void loadInfo(Context context) {
		String jsonString = "";
    	try {
			jsonString = DTUEvents.mFacebook.request(id.toString());
			JSONObject json = Util.parseJson(jsonString);
			description = json.optString("description");
			initializeLocation(json, context);
			
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
	
	public void showEventInfo(Context context) {
		if(!loaded) {
			loadInfo(context);
			loaded = true;
		}
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    	builder.setTitle(name)
    	       .setMessage(description)
    	       .setCancelable(false)
    	       .setPositiveButton(R.string.back, new DialogInterface.OnClickListener() 
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
