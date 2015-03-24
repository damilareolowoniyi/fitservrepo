package com.fitserv.user.profilemenu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fitserv.androidapp.R;

import android.app.ListFragment;
import android.app.ProgressDialog;
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

public class UserWorkoutPlanFragment extends ListFragment {
	
	
	Button CreateWorkout;
	// Progress Dialog
 	private ProgressDialog pDialog;

 	// Creating JSON Parser object
 	JSONParser jParser = new JSONParser();

 	ArrayList<HashMap<String, String>> workoutsList;

 	// url to get all workouts list
 	private static String url_all_workouts = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_all_workouts.php";

 	// JSON Node names
 	private static final String TAG_SUCCESS = "success";
 	private static final String TAG_WORKOUT = "workoutplan";
 	private static final String TAG_WID = "wid";
 	private static final String TAG_WORKOUT_NAME = "workout_name";
 	private static final String TAG_WORKOUT_DATE = "workout_date";

 // workouts JSONArray
 	JSONArray workout = null;
 	
	public UserWorkoutPlanFragment(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.user_workoutplan, container, false);
    	
		// Buttons
         CreateWorkout = (Button) rootView.findViewById(R.id.createworkout);
         
 		// create workouts click event
         CreateWorkout.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View view) {
 				// Launching create new workouts activity
 				Intent i = new Intent(getActivity(), NewWorkout.class);
 				startActivity(i);
 				
 			}
 		});
         return rootView;
	}
	@Override 
	 public void onViewCreated (View view, Bundle savedInstanceState) {
  	
		//List workout 
         
         workoutsList = new ArrayList<HashMap<String, String>>();

 		// Loading workouts in Background Thread
 		new LoadAllWorkouts().execute();

 		// Get listview
 	// Get listview
 			ListView lv = getListView();
 			
 			// on seleting single product
 			// launching Edit Product Screen
 			lv.setOnItemClickListener(new OnItemClickListener() {

 				@Override
 				public void onItemClick(AdapterView<?> parent, View view,
 						int position, long id) {
 					// getting values from selected ListItem
 					String wid = ((TextView) view.findViewById(R.id.wid)).getText()
 							.toString();

 					// Starting new intent
 					Intent in = new Intent(getActivity().getApplicationContext(),
 							EditWorkouts.class);
 					// sending pid to next activity
 					in.putExtra(TAG_WID, wid);
 					
 					// starting new activity and expecting some response back
 					startActivityForResult(in, 100);
 				}
 			});
	}
	// Response from Edit Product Activity
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if result code 100
		if (resultCode == 100) {
			// if result code 100 is received 
			// means user edited/deleted product
			// reload this screen again
			Intent intent = getActivity().getIntent();
			getActivity().finish();
			startActivity(intent);
		}

	}
	
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllWorkouts extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading your workouts, Please wait...");
			//pDialog.setMessage("almost done...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_workouts, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Workouts: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Workout
					workout = json.getJSONArray(TAG_WORKOUT);

					// looping through All Workout
					for (int i = 0; i < workout.length(); i++) {
						JSONObject c = workout.getJSONObject(i);

						// Storing each json item in variable
						String id = c.getString(TAG_WID);
						String name = c.getString(TAG_WORKOUT_NAME);
						String date = c.getString(TAG_WORKOUT_DATE);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_WID, id);
						map.put(TAG_WORKOUT_NAME, name);
						map.put(TAG_WORKOUT_DATE, date);

						// adding HashList to ArrayList
						workoutsList.add(map);
					}
				} else {
					// no workout found
					// Launch Add New workout Activity
					Intent i = new Intent(getActivity(),
							NewWorkout.class);
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
			// dismiss the dialog after getting all workouts
			pDialog.dismiss();
			// updating UI from Background Thread
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter( getActivity(), workoutsList, R.layout.list_item,
							new String[] { TAG_WID, TAG_WORKOUT_NAME, TAG_WORKOUT_DATE},
							new int[] { R.id.wid, R.id.workout_name , R.id.workout_date});
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	
}
