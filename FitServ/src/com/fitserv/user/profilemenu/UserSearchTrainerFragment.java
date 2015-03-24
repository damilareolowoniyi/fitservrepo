package com.fitserv.user.profilemenu;

import org.json.JSONException;

import org.json.JSONObject;

import com.fitserv.androidapp.R;

 
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
 
public class UserSearchTrainerFragment extends Fragment  {
	 
	 private static String PROVIDER=LocationManager.GPS_PROVIDER;
	  private WebView browser;
	  private LocationManager myLocationManager=null;
	  
	public UserSearchTrainerFragment(){}
	
	@Override
  	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.user_find_trainer, container, false);
        
        browser=(WebView) rootView.findViewById(R.id.webview);
        myLocationManager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new Locater(), "locater");
        browser.loadUrl("http://leafletjs.com/examples/quick-start-example.html");
                
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
