package com.fitserv.personaltrainer.profilemenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import com.fitserv.androidapp.R;
import com.fitserv.user.profilemenu.JSONParser;
 
public class TrainerClientsFragment extends ListFragment {
	
	Button CreateWorkout;
	//Progress Dialog
 	private ProgressDialog pDialog;

 	// Creating JSON Parser object
 	JSONParser jParser = new JSONParser();

 	ArrayList<HashMap<String, String>> clientsList;

 	// url to get all workouts list
 	private static String url_all_clients = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_all_clients.php";

 	// JSON Node names
 	private static final String TAG_SUCCESS = "success";
 	private static final String TAG_CLIENT = "client";
 	private static final String TAG_CID = "cid";
 	private static final String TAG_CLIENT_FULLNAME = "fullname";
 	private static final String TAG_CLIENT_GOAL = "goal";
 	private static final String TAG_CLIENT_ADDRESS = "address";

 	 //workouts JSONArray
 	  JSONArray workout = null;
 	 
	public TrainerClientsFragment(){}
	Button AddClient;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.trainer_clients, container, false);
         
        
		// Buttons
        AddClient = (Button) rootView.findViewById(R.id.addclient);
        
		// create clients click event
        AddClient.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new client activity
				Intent i = new Intent(getActivity(), AddClient.class);
				startActivity(i);
			}
		});
        
        return rootView;
    }
	
	@Override 
	 public void onViewCreated (View view, Bundle savedInstanceState) {
 	
		//List workout 
         clientsList = new ArrayList<HashMap<String, String>>();

		// Loading workouts in Background Thread
		new LoadAllClients().execute();

 		// Get listview
			ListView lv = getListView();
			
			// on seleting single product
			// launching Edit Product Screen
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// getting values from selected ListItem
					String cid = ((TextView) view.findViewById(R.id.cid)).getText()
							.toString();

					// Starting new intent
					Intent in = new Intent(getActivity().getApplicationContext(),
							EditClient.class);
					// sending cid to next activity
					in.putExtra(TAG_CID, cid);
					
					// starting new activity and expecting some response back
					startActivityForResult(in, 100);
				}
			});
	}	
	// Response from Edit Client Activity
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
	class LoadAllClients extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading your clients, Please wait...");
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
			JSONObject json = jParser.makeHttpRequest(url_all_clients, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All clients: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Workout
					workout = json.getJSONArray(TAG_CLIENT);

					// looping through All Workout
					for (int i = 0; i < workout.length(); i++) {
						JSONObject c = workout.getJSONObject(i);

						// Storing each json item in variable
						String cid = c.getString(TAG_CID);
						String fullname = c.getString(TAG_CLIENT_FULLNAME);
						String goal = c.getString(TAG_CLIENT_GOAL);
						String address = c.getString(TAG_CLIENT_ADDRESS);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_CID, cid);
						map.put(TAG_CLIENT_FULLNAME, fullname);
						map.put(TAG_CLIENT_GOAL, goal);
						map.put(TAG_CLIENT_ADDRESS, address);

						// adding HashList to ArrayList
						clientsList.add(map);
					}
				} else {
					// no workout found
					// Launch Add New workout Activity
					Intent i = new Intent(getActivity(),
							AddClient.class);
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
					ListAdapter adapter = new SimpleAdapter( getActivity(), clientsList, R.layout.clients_list_item,
							new String[] { TAG_CID, TAG_CLIENT_FULLNAME, TAG_CLIENT_GOAL, TAG_CLIENT_ADDRESS},
							new int[] { R.id.cid, R.id.client_fullname, R.id.client_goal , R.id.client_address});
					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
	
	
	
}