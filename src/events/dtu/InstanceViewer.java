package events.dtu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class InstanceViewer extends Activity {

	private int id;
	private ArrayList<Event> events;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        setContentView(R.layout.instanceview);
        
        events = DTUEvents.events.getEvents(id);
        
        ListView lv = (ListView) findViewById(R.id.ListView03);
        lv.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events));
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        	{
        		eventDialog(events.get(position));
        	}
        });  
    }
    
    private void eventDialog(Event event) {
    	event.showEventInfo(this);
    }
    
    private class EventAdapter extends ArrayAdapter<Event> {

    	private ArrayList<Event> items;

    	public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
    		super(context, textViewResourceId, items);
    		this.items = items;
    	}

    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View v = convertView;
    		if (v == null) {
    			LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			v = vi.inflate(R.layout.row, null);
    		}
    		Event e = items.get(position);
    		if (e != null) {
    			TextView tt = (TextView) v.findViewById(R.id.toptext);
    			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
    			if (tt != null) {
    				tt.setText(e.name);                            
    			}
    			if(bt != null){
    				bt.setText("");
    			}
    		}
    		
    		ImageView img = (ImageView) v.findViewById(R.id.icon);
    		img.setImageResource(DTUEvents.instances.get(id).drawable);
    		return v;
    	}
    }
}
