package events.dtu;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.GpsStatus.Listener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ZoomButtonsController;
import android.widget.ZoomButtonsController.OnZoomListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class MapViewer extends MapActivity implements LocationListener {
	
	private MyLocationOverlay locationOverlay;
	private boolean initialized = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadview);
        new Initializer().execute();
    }
    
    public void onPause() {
    	if(initialized) {
    		locationOverlay.disableMyLocation();
    	}
    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	lm.removeUpdates(this);
    	super.onPause();
    }
    
    public void onResume() {
    	super.onResume();
    	if(initialized) {
    		initializeLocationManager();
    	}
    }
    
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
    private MapView mapView;
    private MapController mapControl;
    private final GeoPoint mapCenter = new GeoPoint(55785804, 12521152);
	
    private ArrayList<ItemizedOverlay> createOverlays() {
		ArrayList<ItemizedOverlay> overlays = new ArrayList<ItemizedOverlay>();
		for(Instance instance : DTUEvents.instances) {
			if(instance.isBar()) {
				overlays.add(createOverlay((Bar) instance));
			}
		}
		for(Event event : DTUEvents.events.getEvents()) {
			if(event.location != null) {
				Instance instance = DTUEvents.instances.get(event.pageId);
				if(instance.isBar()) {
					if(((Bar) instance).location != event.location) {
						overlays.add(createOverlay(event,instance));
					}
				}
				else {
					overlays.add(createOverlay(event,instance));
				}
			}
		}
		return overlays;
	}
    
    private Drawable drawableToGrayscale(Drawable drawable) {
    	Bitmap colorBitmap = ((BitmapDrawable)drawable).getBitmap();
    	Bitmap grayscaleBitmap = Bitmap.createBitmap(
    			colorBitmap.getWidth(), colorBitmap.getHeight(),
    			Bitmap.Config.ARGB_8888);

    	Canvas c = new Canvas(grayscaleBitmap);
    	Paint p = new Paint();
    	ColorMatrix cm = new ColorMatrix();

    	cm.setSaturation(0);
    	ColorMatrixColorFilter filter = new ColorMatrixColorFilter(cm);
    	p.setColorFilter(filter); 
    	c.drawBitmap(colorBitmap, 0, 0, p);
    	return new BitmapDrawable(grayscaleBitmap);
    }
    
    private EventOverlay createOverlay(Event event, Instance instance) {
    	Drawable drawable;
    	EventOverlay eventOverlay;
		
        drawable = this.getResources().getDrawable(instance.drawableEvent).mutate();
        if(!event.isActive(new Date())) {
            drawable.setAlpha(95);
        }
        eventOverlay = new EventOverlay(drawable, this, event);
		OverlayItem overlayitem = new OverlayItem(event.location, "", "");
		
		eventOverlay.addOverlay(overlayitem);
		return eventOverlay;
    }
    
	private BarOverlay createOverlay(Bar bar) {
		Drawable drawable;
		BarOverlay barOverlay;
		
        drawable = this.getResources().getDrawable(bar.drawableMap).mutate();
        if(!bar.isOpen()) {
            drawable = drawableToGrayscale(drawable);
        	drawable.setAlpha(95);
        }
        barOverlay = new BarOverlay(drawable, this, bar);
		
		OverlayItem overlayitem = new OverlayItem(bar.location, "", "");
		
		barOverlay.addOverlay(overlayitem);
		return barOverlay;
	}
	
	private class ZoomListen implements OnZoomListener {

		public void onVisibilityChanged(boolean visible) {
			// TODO Auto-generated method stub
			
		}

		public void onZoom(boolean zoomIn) {
			if(zoomIn) {
				mapControl.zoomIn();
			}
			else {
				if(mapView.getZoomLevel() > 16) {
					mapControl.zoomOut();
				}
			}
			if(mapView.getZoomLevel() == 16) {
				mapControl.animateTo(mapCenter);
				mapView.setLongClickable(false);
			} else {
				mapView.setLongClickable(true);
			}
			
		}
		
	}
	
	private boolean isPositionAtDTU(GeoPoint position) {
		if(position.getLatitudeE6() > Statics.LOWER_BOUND_INT &&
			position.getLatitudeE6() < Statics.UPPER_BOUND_INT &&
			position.getLongitudeE6() > Statics.LEFT_BOUND_INT &&
			position.getLongitudeE6() < Statics.RIGHT_BOUND_INT) {
			return true;
		}
		return false;
	}
	
	private void initializeLocationManager() {
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			checkLocation(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		}
	}
	
	private void initializeLocationOverlay() {
		locationOverlay = new MyLocationOverlay(this, mapView);
		locationOverlay.enableMyLocation();
	}
	
	private class Initializer extends AsyncTask<Void, Void, ArrayList<ItemizedOverlay>> {

		@Override
		protected ArrayList<ItemizedOverlay> doInBackground(Void... params) {  
			loadEventInfo();
			return createOverlays();
		}
   	
        protected void onProgressUpdate(Void... progress) {

        }

        protected void onPostExecute(ArrayList<ItemizedOverlay> result) {
        	setContentView(R.layout.mapview);
        	mapView = (MapView) findViewById(R.id.mapview);
            mapView.setBuiltInZoomControls(true);
            mapControl = mapView.getController();
            mapControl.setCenter(mapCenter);
            mapControl.setZoom(16);

            ZoomButtonsController zoomButton = mapView.getZoomButtonsController();
            zoomButton.setOnZoomListener(new ZoomListen());
            
            initializeLocationOverlay();
			initializeLocationManager();
            
            for(ItemizedOverlay overlay : result) {
                mapView.getOverlays().add(overlay);
            }
            
            initialized = true;
        }
    }
	
	private void loadEventInfo() {
        for(Event event : DTUEvents.events.getEvents()) {
        	event.loadInfo(this);
        }
	}

	private GeoPoint locationToGP(Location location) {
		GeoPoint geoPoint = null;
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			geoPoint = new GeoPoint((int) (lat * 1000000), (int) (lng * 1000000));
		}
		return geoPoint;
	}
	
	public void onLocationChanged(Location location) {
		if(initialized) {
			checkLocation(location);
		}
	}
	
	private void checkLocation(Location location) {
		if (location != null) {
	    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			GeoPoint position = locationToGP(location);
			if(isPositionAtDTU(position)) {
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 5.0f, this);
				if(mapView.getZoomLevel() > 16) {
					mapControl.animateTo(position);
				}
				locationOverlay.enableMyLocation();
			}
			else {
	    		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, this);
	    		locationOverlay.disableMyLocation();
			}
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		if(initialized) {
			if(provider.equals(LocationManager.GPS_PROVIDER)) {
				LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				checkLocation(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));
			}
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
