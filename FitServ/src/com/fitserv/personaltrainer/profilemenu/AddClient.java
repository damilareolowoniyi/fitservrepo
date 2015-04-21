package com.fitserv.personaltrainer.profilemenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.fitserv.androidapp.R;
import com.fitserv.user.profilemenu.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddClient extends Activity{

	// Progress Dialog
		private ProgressDialog pDialog;

		JSONParser jsonParser = new JSONParser();
		EditText inputClientEmail;
		EditText inputClientPassword;
		EditText inputCurrentClient;
		EditText inputClientFullname;
		EditText inputClientAddress;
		EditText inputClientGender;
		EditText inputClientAge;
		EditText inputClientWeight;
		EditText inputClientHeight;
		EditText inputClientGoal;
		EditText inputClientInjury;
		
		private static final String TAG_SUCCESS = "success";

		// url to create new client
		private static String url_create_clients = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/create_client.php";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer_addclient);
		// Edit Text
		inputClientEmail = (EditText) findViewById(R.id.inputClientEmail);
		inputClientPassword = (EditText) findViewById(R.id.inputClientPassword);
		inputCurrentClient = (EditText) findViewById(R.id.inputCurrentClient);
		inputClientFullname = (EditText) findViewById(R.id.inputClientFullname);
		inputClientAddress = (EditText) findViewById(R.id.inputClientAddress);
		inputClientGender = (EditText) findViewById(R.id.inputClientGender);
		inputClientAge = (EditText) findViewById(R.id.inputClientAge);
		inputClientHeight = (EditText) findViewById(R.id.inputClientHeight);
		inputClientWeight = (EditText) findViewById(R.id.inputClientWeight);
		inputClientGoal = (EditText) findViewById(R.id.inputClientGoal);
		inputClientInjury = (EditText) findViewById(R.id.inputClientInjury);

		// Create button
		Button btnCreateClient = (Button) findViewById(R.id.btnCreateClient);
		// button click event
		btnCreateClient.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new AddClientPlan().execute();
			}
		});
		
	}
	
	/**
	 * Background Async Task to Create new product
	 * */
	class AddClientPlan extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddClient.this);
			pDialog.setMessage("Creating a new Client ..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String email = inputClientEmail.getText().toString();
			String password = inputClientPassword.getText().toString();
			String current_client = inputCurrentClient.getText().toString();
			String fullname = inputClientFullname.getText().toString();
			String address = inputClientAddress.getText().toString();
			String gender = inputClientGender.getText().toString();
			String age = inputClientAge.getText().toString();
			String height = inputClientHeight.getText().toString();
			String weight = inputClientWeight.getText().toString();
			String goal = inputClientGoal.getText().toString();
			String injuries = inputClientInjury.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("current_client", current_client));
			params.add(new BasicNameValuePair("fullname", fullname));
			params.add(new BasicNameValuePair("address", address));
			params.add(new BasicNameValuePair("age", age));
			params.add(new BasicNameValuePair("gender", gender));			
			params.add(new BasicNameValuePair("height", height));
			params.add(new BasicNameValuePair("weight", weight));
			params.add(new BasicNameValuePair("goal", goal));
			params.add(new BasicNameValuePair("injuries", injuries));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_clients,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), TrainerProfileActivity.class);
					startActivity(i);
					 //closing this screen
					finish();
					
					// Fragment newFragment = new UserWorkoutPlanFragment();
					   // FragmentTransaction transaction = getFragmentManager().beginTransaction();
					   // transaction.replace(R.id.g, newFragment);
					   // transaction.commit();	
				   
					
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
