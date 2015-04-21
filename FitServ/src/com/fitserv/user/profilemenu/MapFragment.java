package com.fitserv.user.profilemenu;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.fitserv.androidapp.R;
import com.fitserv.user.profilemenu.WebMapActivity.Locater;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MapFragment extends Fragment  {
	private static String PROVIDER=LocationManager.GPS_PROVIDER;
	  private WebView browser;
	  private LocationManager myLocationManager=null;
	  
	public MapFragment(){}
	 
    @SuppressLint("JavascriptInterface")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_map_home, container, false);
         
        final ProgressDialog builder = new ProgressDialog(getActivity());
        builder.setTitle("Location based map view");
        builder.setMessage("Hi, Welcome to FitServ. We are now" +
        		" checking your current location to load the personal trainers nearest to you..." +
        		" please wait, thank you");
        builder.setCancelable(true);
           builder.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
            	builder.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 30000); // after 30 second (or 9000 miliseconds), the task will be active.
     
        browser=(WebView)rootView.findViewById(R.id.homewebview);
        
        myLocationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new Locater(), "locater");
        browser.loadUrl("http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/trainermap2.html");
        
        return rootView;
    }
    @Override
    public void onResume() {
      super.onResume();
      myLocationManager.requestLocationUpdates(PROVIDER, 10000,
                                                100.0f,
                                                onLocation);
    }
    
    @Override
    public void onPause() {
      super.onPause();
      myLocationManager.removeUpdates(onLocation);
    }
    
    LocationListener onLocation=new LocationListener() {
      public void onLocationChanged(Location location) {
        // ignore...for now
      }
      
      public void onProviderDisabled(String provider) {
        // required for interface, not used
      }
      
      public void onProviderEnabled(String provider) {
        // required for interface, not used
      }
      
      public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
        // required for interface, not used
      }
    };
    
    public class Locater {
      public String getLocation() throws JSONException {
        Location loc=myLocationManager.getLastKnownLocation(PROVIDER);
        
        if (loc==null) {
          return(null);
        }
        
        JSONObject json=new JSONObject();

        json.put("lat", loc.getLatitude());
        json.put("lon", loc.getLongitude());
        
        return(json.toString());
      }
    }
	
	
}
