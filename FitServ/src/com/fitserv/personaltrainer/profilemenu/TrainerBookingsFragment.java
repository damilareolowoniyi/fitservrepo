package com.fitserv.personaltrainer.profilemenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
 import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
 import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.fitserv.androidapp.R;
import com.fitserv.user.profilemenu.JSONParser;

public class TrainerBookingsFragment extends ListFragment {
	
	// Progress Dialog
		 	private ProgressDialog pDialog;
		 	Button popupButton;
		 	final Context context = getActivity();
		 	
		 	// Creating JSON Parser object
		 	JSONParser jParser = new JSONParser();

		 	ArrayList<HashMap<String, String>> bookingsList;

		 	// url to get all bookings list
		 	private static String url_all_bookings = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_all_bookings.php";
		 	
		 	// JSON Node names
		 	private static final String TAG_SUCCESS = "success";
		 	private static final String TAG_BOOKINGS = "bookings";
		 	private static final String TAG_ID = "id";
		 	private static final String TAG_SUBJECT = "Subject";
		 	private static final String TAG_NAME = "Name";
		 	private static final String TAG_LOCATION = "Location";
		 	private static final String TAG_DESCRIPTION = "Description";
		 	private static final String TAG_STARTTIME = "StartTime";
		 	private static final String TAG_ENDTIME = "EndTime";
			 
		 // bookings JSONArray
		 	JSONArray bookings = null;
		 	
	
	public TrainerBookingsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.trainer_calender, container, false);
         
        return rootView;
    }
	@Override 
	 public void onViewCreated (View view, Bundle savedInstanceState) {
	
		//List bookings 
       
       bookingsList = new ArrayList<HashMap<String, String>>();

		// Loading bookings in Background Thread
		new LoadAllBookings().execute();

 			ListView lv = getListView();
   
 			
			// on seleting single product
			// launching Edit Product Screen
			lv.setOnItemClickListener(new OnItemClickListener() {


				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					String bid = ((TextView) view.findViewById(R.id.id)).getText().toString();
 					// Starting new intent
					Intent in = new Intent(getActivity().getApplicationContext(),
							TrainerProfileActivity.class);
					// sending pid to next activity
					in.putExtra(TAG_ID, bid);
					
 					// starting new activity and expecting some response back
					startActivityForResult(in, 100);

				}
				
 				
 			});
			
 			
	}  
	// Response from Edit TrainerBookingFragment Activity
	 	@Override
	 	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	 		super.onActivityResult(requestCode, resultCode, data);
	 		// if result code 100
	 		if (resultCode == 100) {
	 			// if result code 100 is received 
	 			// means trainer edited/deleted product
	 			// reload this screen again
	 			Intent intent = getActivity().getIntent();
	 			getActivity().finish();
	 			startActivity(intent);
	 		}
	 	}
		/**
		 * Background Async Task to Load all product by making HTTP Request
		 * */
		class LoadAllBookings extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Loading your confirmed Bookings, Please wait...");
				//pDialog.setMessage("almost done...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(false);
				pDialog.show();
			}

			/**
			 * getting All bookings from url
			 * */
			protected String doInBackground(String... args) {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_all_bookings, "GET", params);
				
				// Check your log cat for JSON reponse
				Log.d(" Confirmed Bookings: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Bookings
						bookings = json.getJSONArray(TAG_BOOKINGS);

						// looping through All bookings
						for (int i = 0; i < bookings.length(); i++) {
							JSONObject c = bookings.getJSONObject(i);

							// Storing each json item in variable
							String id = c.getString(TAG_ID);
							String subject = c.getString(TAG_SUBJECT);
							String name = c.getString(TAG_NAME);
							String location = c.getString(TAG_LOCATION);
							String description =  c.getString(TAG_DESCRIPTION);
							String starttime = c.getString(TAG_STARTTIME);
							String endtime = c.getString(TAG_ENDTIME);

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put(TAG_ID, id);
							map.put(TAG_SUBJECT, subject);
							map.put(TAG_NAME, name);
							map.put(TAG_LOCATION, location);
							map.put(TAG_DESCRIPTION, description);
							map.put(TAG_STARTTIME, starttime);
							map.put(TAG_ENDTIME, endtime);

							// adding HashList to ArrayList
							bookingsList.add(map);
						
						}
					} else {
						// no booking found
						// Launch Add New bookings Activity
						Intent i = new Intent(getActivity(),
								TrainerProfileActivity.class);
						// Closing all previous activities
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}
			
			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog after getting all bookings
				pDialog.dismiss();
				// updating UI from Background Thread
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 **/
						ListAdapter adapter = new SimpleAdapter( getActivity(), bookingsList, R.layout.bookingstrainer_list_item,
								new String[] { TAG_ID, TAG_SUBJECT, TAG_NAME, TAG_LOCATION, TAG_DESCRIPTION,TAG_STARTTIME,TAG_ENDTIME },
								new int[] { R.id.id, R.id.Subject , R.id.Name, R.id.Location, R.id.Description, R.id.StartTime,R.id.EndTime});

 						// updating listview
						setListAdapter(adapter);
 						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						
  						//need the popupButton to trigger this dialog box
 						builder.setTitle("Notification - You have New Bookings");
 			            builder.setMessage("You can cancel or confirm these bookings");
			            builder.setPositiveButton("Ok! No Problem",null);
			            final AlertDialog alert = builder.create();
			          
			            getActivity().runOnUiThread(new java.lang.Runnable(){
			            	public void run(){
			                    //show AlertDialog
 			            		alert.show();
			                }
			            });
						
  						/**
 						popupButton = (Button) getActivity().findViewById(R.id.CancelBookings);
						 
						// add button listener
						popupButton.setOnClickListener(new OnClickListener() {
				 
						  @Override
						  public void onClick(View arg0) {
				 
							// custom dialog
							final Dialog dialog = new Dialog(context);
							dialog.setContentView(R.layout.cancel_booking);
							dialog.setTitle("Title...");
				 
							// set the custom dialog components - text, image and button
							 
							Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
							// if button is clicked, close the custom dialog
							dialogButton.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									dialog.dismiss();
								}
							});
				 
							dialog.show();
						  }
						});

				**/
			            
 					  }
				});
		
			}

		}
	
}