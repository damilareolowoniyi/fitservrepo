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



public class NewTrainerDietPlan extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputDietName;
	EditText inputDietDate;
	EditText inputFoodName;
	EditText inputSize;
	EditText inputTotalCal;
	EditText inputProtein;
	EditText inputCarbs;
	EditText inputFat;
	EditText inputMealTime;
	EditText inputNotes;

	// url to create new product
	private static String url_create_dietplan = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/create_diet.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer_adddietplan);

		// Edit Text
		inputDietName = (EditText) findViewById(R.id.inputDietName);
		inputDietDate = (EditText) findViewById(R.id.inputDietDate);
		inputFoodName = (EditText) findViewById(R.id.inputFoodName);
		inputSize = (EditText) findViewById(R.id.inputSize);
		inputTotalCal = (EditText) findViewById(R.id.inputTotalCal);
		inputProtein = (EditText) findViewById(R.id.inputProtein);
 		inputCarbs = (EditText) findViewById(R.id.inputCarbs);
		inputFat = (EditText) findViewById(R.id.inputFat);
		inputMealTime = (EditText) findViewById(R.id.inputMealTime);
		inputNotes = (EditText) findViewById(R.id.inputDietNotes);

		// Create button
		Button btnCreateDiet = (Button) findViewById(R.id.btnCreateDiet);
		// button click event
		btnCreateDiet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new NewDiet().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class NewDiet extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewTrainerDietPlan.this);
			pDialog.setMessage("Creating Your Diet Plan..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String diet_name = inputDietName.getText().toString();
			String diet_date = inputDietDate.getText().toString();
			String food_name = inputFoodName.getText().toString();
			String size = inputSize.getText().toString();
			String total_cal = inputTotalCal.getText().toString();
			String protein = inputProtein.getText().toString();
			String carbs = inputCarbs.getText().toString();
			String fat = inputFat.getText().toString();
			String meal_time = inputMealTime.getText().toString();
			String notes = inputNotes.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("diet_name", diet_name));
			params.add(new BasicNameValuePair("diet_date", diet_date));
			params.add(new BasicNameValuePair("food_name", food_name));
			params.add(new BasicNameValuePair("size",size));
			params.add(new BasicNameValuePair("total_cal", total_cal));
			params.add(new BasicNameValuePair("protein", protein));
			params.add(new BasicNameValuePair("carbs", carbs));
			params.add(new BasicNameValuePair("fat", fat));
			params.add(new BasicNameValuePair("meal_time", meal_time));
			params.add(new BasicNameValuePair("notes", notes));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_dietplan,
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
