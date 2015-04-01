package com.fitserv.user.profilemenu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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



public class EditWorkouts extends Activity {

	EditText inputWorkoutName;
	EditText inputWorkoutDate;
	EditText inputExceriseName;
	EditText inputSets;
	EditText inputKg;
	EditText inputReps;
	EditText inputNotes;
	EditText txtCreatedAt;
	Button btnSave;
	Button btnDelete;

	String wid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_workout_detials = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_workout_details.php";

	// url to update product
	private static final String url_update_workout = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/update_workout.php";
	
	// url to delete product
	private static final String url_delete_workout = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/delete_workout.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_WORKOUT = "workoutplan";
	private static final String TAG_WID = "wid";
	private static final String TAG_WORKOUT_NAME = "workout_name";
	private static final String TAG_WORKOUT_DATE = "workout_date";
	private static final String TAG_EXECRCISE_NAME = "exercise_name";
	private static final String TAG_SETS = "sets";
	private static final String TAG_WEIGHT_KG = "weight_kg";
	private static final String TAG_REPS = "reps";
	private static final String TAG_NOTES = "notes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_editworkout);

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		// getting product details from intent
		Intent i = getIntent();
		
		// getting product id (pid) from intent
		wid = i.getStringExtra(TAG_WID);

		// Getting complete product details in background thread
		new GetProductDetails().execute();

		// save button click event
				btnSave.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// starting background task to update product
						new SaveProductDetails().execute();
					}
				});

				// Delete button click event
				btnDelete.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// deleting product in background thread
						new DeleteProduct().execute();
					}
				});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, JSONObject> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditWorkouts.this);
			pDialog.setMessage("Loading product details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting product details in background thread
		 * */
		
		protected JSONObject doInBackground(String... args) {
	        JSONObject json = null;

	        try {
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("wid", wid));

	            json = jsonParser.makeHttpRequest(
	                    url_workout_detials, "GET", params);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return json;
	    }
		
		 
		  protected void onPostExecute(JSONObject json) {

		        pDialog.dismiss();
		        if (json == null) return; // check if json is null too! if null something went wrong (handle it, i used return as example)

		        int success = 0;
				try {
					success = json.getInt(TAG_SUCCESS);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};

		        if (success == 1) {
		            JSONArray productObj = null;
					try {
						productObj = json.getJSONArray(TAG_WORKOUT);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        JSONObject product = null;
				try {
					product = productObj.getJSONObject(0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        inputWorkoutName = (EditText) findViewById(R.id.inputWorkoutName);
				inputWorkoutDate = (EditText) findViewById(R.id.inputWorkoutDate);
				inputExceriseName = (EditText) findViewById(R.id.inputExceriseName);
				inputSets = (EditText) findViewById(R.id.inputSets);
				inputKg = (EditText) findViewById(R.id.inputKg);
				inputReps = (EditText) findViewById(R.id.inputReps);
				inputNotes = (EditText) findViewById(R.id.inputNotes);

				// display product data in EditText
				try {
					inputWorkoutName.setText(product.getString(TAG_WORKOUT_NAME));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputWorkoutDate.setText(product.getString(TAG_WORKOUT_DATE));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputExceriseName.setText(product.getString(TAG_EXECRCISE_NAME));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputSets.setText(product.getString(TAG_SETS));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputKg.setText(product.getString(TAG_WEIGHT_KG));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputReps.setText(product.getString(TAG_REPS));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputNotes.setText(product.getString(TAG_NOTES));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        }else{

		        }
		    }
	}
	/**
	 * Background Async Task to  Save product Details
	 * */
	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditWorkouts.this);
			pDialog.setMessage("Saving product ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String workout_name = inputWorkoutName.getText().toString();
			String workout_date = inputWorkoutDate.getText().toString();
			String excerise_name = inputExceriseName.getText().toString();
			String sets = inputSets.getText().toString();
			String weight_kg = inputKg.getText().toString();
			String reps = inputReps.getText().toString();
			String notes = inputNotes.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_WID, wid));
			params.add(new BasicNameValuePair(TAG_WORKOUT_NAME, workout_name));
			params.add(new BasicNameValuePair(TAG_WORKOUT_DATE, workout_date));
			params.add(new BasicNameValuePair(TAG_EXECRCISE_NAME, excerise_name));
			params.add(new BasicNameValuePair(TAG_SETS, sets));
			params.add(new BasicNameValuePair(TAG_WEIGHT_KG, weight_kg ));
			params.add(new BasicNameValuePair(TAG_REPS, reps ));
			params.add(new BasicNameValuePair(TAG_NOTES, notes ));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_workout,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
				} else {
					// failed to update product
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
			// dismiss the dialog once product uupdated
			pDialog.dismiss();
		}
	}

	/*****************************************************************
	 * Background Async Task to Delete Workout
	 * */
	class DeleteProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditWorkouts.this);
			pDialog.setMessage("congratulations workout completed...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting product
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("wid", wid));

				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_workout, "POST", params);

				// check your log for json response
				Log.d("Delete Workout", json.toString());
				
				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// product successfully deleted
					// notify previous activity by sending code 100
					Intent i = getIntent();
					// send result code 100 to notify about product deletion
					setResult(100, i);
					finish();
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
			// dismiss the dialog once product deleted
			pDialog.dismiss();

		}

	}
	}
