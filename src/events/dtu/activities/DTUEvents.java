package events.dtu.activities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.android.Facebook.DialogListener;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import events.dtu.R;
import events.dtu.R.drawable;
import events.dtu.R.id;
import events.dtu.R.layout;
import events.dtu.R.menu;
import events.dtu.R.string;
import events.dtu.model.InitialData;
import events.dtu.model.event.Event;
import events.dtu.model.event.EventsList;
import events.dtu.model.facebook.BaseRequestListener;
import events.dtu.model.facebook.SessionEvents;
import events.dtu.model.facebook.SessionStore;
import events.dtu.model.facebook.SessionEvents.AuthListener;
import events.dtu.model.facebook.SessionEvents.LogoutListener;
import events.dtu.model.instance.Bar;
import events.dtu.model.instance.Group;
import events.dtu.model.instance.Instance;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.ZoomButtonsController.OnZoomListener;

public class DTUEvents extends TabActivity {
	
    public static final String APP_ID = "155925761094385";
    
    private static final String[] PERMISSIONS =
        new String[] {"publish_stream", "read_stream", "offline_access"};
    private ListView eventsLV;
    private ListView instancesLV;
    public static EventsList events;
    private TabHost mTabHost;
    
    public static ArrayList<Instance> instances = new ArrayList<Instance>();
    
    private ImageView splash;
    
    private Handler mHandler;
    private SessionListener mSessionListener;
    public static Facebook mFacebook;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        splash = new ImageView(this);
        splash.setImageResource(R.drawable.splash0);
        splash.setScaleType(ImageView.ScaleType.CENTER_CROP);
       
        mTabHost = getTabHost();
        mTabHost.addView(splash);
        
       	mFacebook = new Facebook(APP_ID);
       	mSessionListener = new SessionListener();
       	mHandler = new Handler();
       
        SessionStore.restore(mFacebook, getBaseContext());
        SessionEvents.addAuthListener(mSessionListener);
        SessionEvents.addLogoutListener(mSessionListener);
        
        // instances = InitialData.initialize();
		// saveConfig();
        new Initializer().execute("");
    }
    
    private void saveConfig()
    {
    	File cacheDir = getCacheDir();
    	try 
		{
			BufferedWriter output = new BufferedWriter(new FileWriter(cacheDir.getAbsolutePath() + "/config"));
			
			JSONArray pagesJSON = new JSONArray();
			
			for(Instance instance : instances)
			{
				pagesJSON.put(instance.toJSON());
			}
			
			JSONObject json = new JSONObject();
			try {
				json.put("pages", pagesJSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			output.write(json.toString());
			output.close();
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void loadConfig()
    {
    	File cacheDir = getCacheDir();
    	File f = new File(cacheDir.getAbsolutePath() + "/config");
    	if(f.exists())
    	{
    	    try {
    	    	byte[] buffer = new byte[(int) f.length()];
        	    FileInputStream input = new FileInputStream(cacheDir.getAbsolutePath() + "/config");
				input.read(buffer);
				
				JSONObject json = Util.parseJson(new String(buffer));
	    		JSONArray pagesJSON = json.getJSONArray("pages");
				
				for(int i = 0; i < pagesJSON.length(); i++)
				{
					JSONObject pageJSON = pagesJSON.getJSONObject(i);
					switch(pageJSON.getInt("type")) {
					case Instance.GROUP:
						instances.add(new Group(i,pageJSON));
						break;
					case Instance.BAR:
						instances.add(new Bar(i,pageJSON));
						break;
					default:
						break;
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacebookError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    	else
    	{
    		instances = InitialData.initialize();
    		saveConfig();
    	}
    }
    
    private void removePage(int i)
    {
    	// TODO
    	saveConfig();
    }
    
    private void removePageDialog()
    {   
    	/* TODO
		final ListView lv = new ListView(this);
		lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1 , pageStrings));
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        	{
        		removePage(position);
        		lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1 , pageStrings));
        		// Update!
        	}
        });
		
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Add Page")
    		   .setCancelable(false)
    		   .setView(lv)
    	       .setNegativeButton("Back", new DialogInterface.OnClickListener() 
    	       {
    	           public void onClick(DialogInterface dialog, int id) 
    	           {
    	        	   dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();    	
    	alert.show();
    	*/
    }
    
    private void addPageDialog()
    {
    	/* TODO
    	final EditText input = new EditText(this);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Add Page")  
    			.setView(input)
    	       .setCancelable(false)
    	       .setPositiveButton("Add", new DialogInterface.OnClickListener() 
    	       {
    	           public void onClick(DialogInterface dialog, int id) 
    	           {
    	        	   Long l = Long.parseLong(input.getText().toString());
    	        	   Long[] pages2 = new Long[pages.length + 1];
    	        	   for(int i = 0; i < pages.length; i++)
    	        	   {
    	        		   pages2[i] = pages[i];
    	        	   }
    	        	   pages2[pages.length] = l;
    	        	   pages = pages2;
    	        		   
    	        	   // Update
    	        	   saveConfig();
    	        	   dialog.cancel();
    	           }
    	       })
    	       .setNegativeButton("Back", new DialogInterface.OnClickListener() 
    	       {
    	           public void onClick(DialogInterface dialog, int id) 
    	           {
    	        	   dialog.cancel();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    	*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    private void loginFacebook() {
    	// TODO: Fjern Facebook.FORCE_DIALOG_AUTH og få det til at virke på mobil
		mFacebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH,
                new LoginDialogListener());
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) 
        {
        // TODO: Update
        case R.id.all:
        	events.setShown(EventsList.ALL_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.old:
        	events.setShown(EventsList.OLD_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.current:
        	events.setShown(EventsList.CURRENT_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.upcoming:
        	events.setShown(EventsList.UPCOMING_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.old_current:
        	events.setShown(EventsList.OLD_CURRENT_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.current_upcoming:
        	events.setShown(EventsList.CURRENT_UPCOMING_EVENTS);
        	eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
        	return true;
        case R.id.addpage:
        	addPageDialog();
        	return true;
        case R.id.removepage:
        	removePageDialog();
        	return true;
		default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private final class LoginDialogListener implements DialogListener {
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
        }

        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
        }
        
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        public void onCancel() {
            SessionEvents.onLoginError(stringFromId(R.string.action_canceled));
        }
    }
    
    public String stringFromId(int id) {
    	return getString(id);
    }
    
    private class itemListener implements OnItemClickListener
    {
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    	{
    		eventDialog(events.get(position));
    	}
    }
    	
    private void eventDialog(Event event) {
    	event.showEventInfo(this);
    }
    
    private class LogoutRequestListener extends BaseRequestListener {
        public void onComplete(String response) {
            // callback should be run in the original thread, 
            // not the background thread
            mHandler.post(new Runnable() {
                public void run() {
                    SessionEvents.onLogoutFinish();
                }
            });
        }
    }
    
    private class SessionListener implements AuthListener, LogoutListener {
        
        public void onAuthSucceed() 
        {
            SessionStore.save(mFacebook, getBaseContext());
            new Initializer().execute("");
        }

        public void onAuthFail(String error) 
        {
        }
        
        public void onLogoutBegin() 
        {           
        }
        
        public void onLogoutFinish() 
        {
            SessionStore.clear(getBaseContext());
        }
    }
    
    private void loadData() {
    	events = new EventsList(instances);
    }
    
    private class Initializer extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			loadConfig();
			publishProgress(1);
	        
	        if(mFacebook.isSessionValid()) {
	        	loadData();
	        	publishProgress(2);
	            return true;
	        }
			return false;
		}
   	
        protected void onProgressUpdate(Integer... progress) {
        	switch(progress[0]) {
        	case 1:
        		splash.setImageResource(R.drawable.splash1);
        		break;
        	case 2:
        		splash.setImageResource(R.drawable.splash2);
        		break;
        	case 3:
        		splash.setImageResource(R.drawable.splash3);
        		break;
        	}
        }

        protected void onPostExecute(Boolean result) {
        	if(result) {
                setContentView(R.layout.main);
                
    	        instancesLV = (ListView) findViewById(R.id.ListView01);
    	        instancesLV.setAdapter(new PageAdapter(getBaseContext(), R.layout.row, instances));
    	        instancesLV.setOnItemClickListener(new itemListener() {
    	        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    	        	{
    	        		instanceView(position);
    	        	}
    	        });  
                
                eventsLV = (ListView) findViewById(R.id.ListView02);
    	        eventsLV.setAdapter(new EventAdapter(getBaseContext(), R.layout.row, events.getEvents()));
                eventsLV.setOnItemClickListener(new OnItemClickListener() {
                	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
                	{
                		eventDialog(events.get(position));
                	}
                });  
                
                mTabHost = getTabHost();
                
                mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getString(R.string.instances)).setContent(R.id.ListView01));
                mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getString(R.string.events)).setContent(R.id.ListView02));
                mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(getString(R.string.map)).setContent(new Intent(getBaseContext(), MapViewer.class)));
                
                mTabHost.setCurrentTab(1);
        	}
        	else {
        		loginFacebook();
        	}
        }
    }
    
    private void instanceView(int id) {
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setClassName(this, InstanceViewer.class.getName());
    	intent.putExtra("id", id);
    	startActivity(intent);
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
    				bt.setText(instances.get(e.pageId).name);
    			}
    		}
    		
    		ImageView img = (ImageView) v.findViewById(R.id.icon);
    		img.setImageResource(instances.get(e.pageId).drawable);
    		return v;
    	}
    }
    
    private class PageAdapter extends ArrayAdapter<Instance> {

    	private ArrayList<Instance> items;

    	public PageAdapter(Context context, int textViewResourceId, ArrayList<Instance> items) {
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
    		Instance instance = items.get(position);
    		if (instance != null) {
    			TextView tt = (TextView) v.findViewById(R.id.toptext);
    			TextView bt = (TextView) v.findViewById(R.id.bottomtext);
    			if (tt != null) {
    				tt.setText(instance.name);                            
    			}
    			if(bt != null){
    				String openText = "";
    				if(instance.isOpen()) {
    					openText = getString(R.string.open);
    				}
    				bt.setText(openText);
    			}
    		}
    		
    		ImageView img = (ImageView) v.findViewById(R.id.icon);
    		img.setImageResource(instance.drawable);
    		return v;
    	}
    }
}