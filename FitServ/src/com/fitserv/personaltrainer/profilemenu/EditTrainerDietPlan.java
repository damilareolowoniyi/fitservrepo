package com.fitserv.personaltrainer.profilemenu;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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



public class EditTrainerDietPlan extends Activity {

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
	Button btnSave;
	Button btnDelete;

	String id;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_diet_detials = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_trainersdietplans_details.php";

	// url to update product
	private static final String url_update_diet = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/update_trainersdiet.php";
	
	// url to delete product
	private static final String url_delete_diet = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/delete_trainerdietplan.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DIET = "diet";
	private static final String TAG_ID = "id";
	private static final String TAG_DIET_NAME = "diet_name";
	private static final String TAG_DIET_DATE = "diet_date";
	private static final String TAG_FOOD_NAME = "food_name";
	private static final String TAG_SIZE = "size";
	private static final String TAG_TOTAL_CAL = "total_cal";
	private static final String TAG_PROTEIN = "protein";
	private static final String TAG_CARBS = "carbs";
	private static final String TAG_FAT = "fat";
	private static final String TAG_MEAL_TIME = "meal_time";
	private static final String TAG_NOTES = "notes";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer_editdietplan);

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		// getting product details from intent
		Intent i = getIntent();
		
		// getting product id (pid) from intent
		id = i.getStringExtra(TAG_ID);

		// Getting complete product details in background thread
		new GetDietDetails().execute();

		// save button click event
				btnSave.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// starting background task to update product
						new SaveDietDetails().execute();
					}
				});

				// Delete button click event
				btnDelete.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// deleting product in background thread
						new DeleteDiet().execute();
					}
				});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetDietDetails extends AsyncTask<String, String, JSONObject> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditTrainerDietPlan.this);
			pDialog.setMessage("Loading Diet details. Please wait...");
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
	            params.add(new BasicNameValuePair("id", id));

	            json = jsonParser.makeHttpRequest(
	                    url_diet_detials, "GET", params);

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
						productObj = json.getJSONArray(TAG_DIET);
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
				
		        inputDietName = (EditText) findViewById(R.id.inputDietName);
		        inputDietDate = (EditText) findViewById(R.id.inputDietDate);
		        inputFoodName = (EditText) findViewById(R.id.inputFoodName);
		        inputSize = (EditText) findViewById(R.id.inputSize);
		        inputTotalCal = (EditText) findViewById(R.id.inputTotalCal);
		        inputProtein = (EditText) findViewById(R.id.inputProtein);
		        inputCarbs = (EditText) findViewById(R.id.inputCarbs);
		        inputFat = (EditText) findViewById(R.id.inputFat);
		        inputMealTime = (EditText) findViewById(R.id.inputMealTime);
		        inputNotes = (EditText) findViewById(R.id.inputNotes);

				// display product data in EditText
				try {
					inputDietName.setText(product.getString(TAG_DIET_NAME));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputDietDate.setText(product.getString(TAG_DIET_DATE));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputFoodName.setText(product.getString(TAG_FOOD_NAME));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputSize.setText(product.getString(TAG_SIZE));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputTotalCal.setText(product.getString(TAG_TOTAL_CAL));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputProtein.setText(product.getString(TAG_PROTEIN));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputCarbs.setText(product.getString(TAG_CARBS));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputFat.setText(product.getString(TAG_FAT));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputMealTime.setText(product.getString(TAG_MEAL_TIME));
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
	class SaveDietDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditTrainerDietPlan.this);
			pDialog.setMessage("Saving Diet Plan ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
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
			params.add(new BasicNameValuePair(TAG_ID, id));
			params.add(new BasicNameValuePair(TAG_DIET_NAME, diet_name));
			params.add(new BasicNameValuePair(TAG_DIET_DATE, diet_date));
			params.add(new BasicNameValuePair(TAG_FOOD_NAME, food_name));
			params.add(new BasicNameValuePair(TAG_SIZE, size));
			params.add(new BasicNameValuePair(TAG_TOTAL_CAL, total_cal ));
			params.add(new BasicNameValuePair(TAG_PROTEIN, protein ));
			params.add(new BasicNameValuePair(TAG_CARBS, carbs ));
			params.add(new BasicNameValuePair(TAG_FAT, fat ));
			params.add(new BasicNameValuePair(TAG_MEAL_TIME, meal_time ));
			params.add(new BasicNameValuePair(TAG_NOTES, notes ));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_diet,
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
	class DeleteDiet extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditTrainerDietPlan.this);
			pDialog.setMessage("workout completed...");
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
				params.add(new BasicNameValuePair("id", id));

				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_diet, "POST", params);

				// check your log for json response
				Log.d("Delete diet", json.toString());
				
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
