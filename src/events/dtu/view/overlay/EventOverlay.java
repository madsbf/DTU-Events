package events.dtu.view.overlay;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.DrawFilter;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import events.dtu.model.event.Event;

public class EventOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;
	private Event event;

	public EventOverlay(Drawable defaultMarker, Context context, Event event) {
		super(boundCenterBottom(defaultMarker));
		  this.context = context;
		  this.event = event;
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  event.showEventInfo(context);
	  return true;
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

}
