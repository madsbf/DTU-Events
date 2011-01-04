package events.dtu;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.DrawFilter;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class BarOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context context;
	private Bar bar;

	public BarOverlay(Drawable defaultMarker, Context context, Bar bar) {
		super(boundCenter(defaultMarker));
		  this.context = context;
		  this.bar = bar;
	}
	
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  barView();
	  return true;
	}
	
	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, false);
	}
	
    private void barView() {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setClassName(context, InstanceViewer.class.getName());
    	intent.putExtra("id", bar.id);
    	context.startActivity(intent);
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
