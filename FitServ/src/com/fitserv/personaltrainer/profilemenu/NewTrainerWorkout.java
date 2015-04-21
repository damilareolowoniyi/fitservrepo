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



public class NewTrainerWorkout extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputWorkoutName;
	EditText inputWorkoutDate;
	EditText inputExceriseName;
	EditText inputSets;
	EditText inputKg;
	EditText inputReps;
	EditText inputNotes;


	// url to create new product
	private static String url_create_workout = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/create_trainerworkoutplan.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer_addworkout);

		// Edit Text
		inputWorkoutName = (EditText) findViewById(R.id.inputWorkoutName);
		inputWorkoutDate = (EditText) findViewById(R.id.inputWorkoutDate);
		inputExceriseName = (EditText) findViewById(R.id.inputExceriseName);
		inputSets = (EditText) findViewById(R.id.inputSets);
		inputKg = (EditText) findViewById(R.id.inputKg);
		inputReps = (EditText) findViewById(R.id.inputReps);
		inputNotes = (EditText) findViewById(R.id.inputNotes);

		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.btnCreateWorkout);
		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new NewWorkoutPlan().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class NewWorkoutPlan extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewTrainerWorkout.this);
			pDialog.setMessage("Creating Your Workout Plan..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String workout_name = inputWorkoutName.getText().toString();
			String workout_date = inputWorkoutDate.getText().toString();
			String exercise_name = inputExceriseName.getText().toString();
			String sets = inputSets.getText().toString();
			String weight_kg = inputKg.getText().toString();
			String reps = inputReps.getText().toString();
			String notes = inputNotes.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("workout_name", workout_name));
			params.add(new BasicNameValuePair("workout_date", workout_date));
			params.add(new BasicNameValuePair("exercise_name", exercise_name));
			params.add(new BasicNameValuePair("sets", sets));
			params.add(new BasicNameValuePair("weight_kg", weight_kg));
			params.add(new BasicNameValuePair("reps", reps));
			params.add(new BasicNameValuePair("notes", notes));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_workout,
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
	