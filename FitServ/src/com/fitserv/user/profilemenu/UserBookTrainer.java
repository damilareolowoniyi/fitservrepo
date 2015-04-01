package com.fitserv.user.profilemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.fitserv.androidapp.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class UserBookTrainer extends Activity {
 	
	// Progress Dialog
		private ProgressDialog pDialog;
		JSONParser jsonParser = new JSONParser();
		EditText inputSubject;
		EditText inputName;
		EditText inputLocation;
		EditText inputStartTime;
		EditText inputEndTime;
		EditText inputDescription;
		// url to create new booking
		private static String url_create_booking = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/bookings.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";

		
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.userbooktrainer);
		
 		
 	    final ProgressDialog builder = new ProgressDialog(this);
  	    builder.setMessage("loading your details before booking this trainer ");
 	    builder.setCancelable(true);
 	       builder.show();

 	    final Timer t = new Timer();
 	    t.schedule(new TimerTask() {
 	        public void run() {
 	        	builder.dismiss(); // when the task active then close the dialog
 	            t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
 	        }
 	    }, 4000); // after 4 second (or 4000 miliseconds), the task will be active.
 	 
 		inputSubject = (EditText) findViewById(R.id.subject);
 		inputName = (EditText) findViewById(R.id.name);
 		inputLocation = (EditText) findViewById(R.id.location);
 		inputStartTime = (EditText) findViewById(R.id.starttime);
 		inputEndTime = (EditText) findViewById(R.id.endtime);
 		inputDescription = (EditText) findViewById(R.id.description);
 		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.btnRegister);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new NewBooking().execute();
			}
		});
	}
	
	/**
	 * Background Async Task to Create new product
	 * */
	class NewBooking extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UserBookTrainer.this);
			pDialog.setMessage("Confirming your bookings now..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String subject = inputSubject.getText().toString();
			String name = inputName.getText().toString();
			String location = inputLocation.getText().toString();
			String starttime = inputStartTime.getText().toString();
			String endtime = inputEndTime.getText().toString();
			String description = inputDescription.getText().toString();
 
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Subject", subject));
			params.add(new BasicNameValuePair("Name", name));
			params.add(new BasicNameValuePair("Location", location));
			params.add(new BasicNameValuePair("StartTime", starttime));
			params.add(new BasicNameValuePair("EndTime", endtime));
			params.add(new BasicNameValuePair("Description", description));
	 

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_booking,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
					startActivity(i);
					 //closing this screen
					finish();
					
					
				} else {
					// failed to create product
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
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
	
 			
}
	

