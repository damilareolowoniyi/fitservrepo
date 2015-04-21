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

public class EditClient extends Activity {

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
	Button btnSave;
	Button btnDelete;

	String cid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_client_detials = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/get_client_details.php";

	// url to update product
	private static final String url_update_client = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/update_client.php";
	
	// url to delete product
	private static final String url_delete_client = "http://ec2-54-77-51-119.eu-west-1.compute.amazonaws.com/android_connect/delete_client.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_CLIENT = "client";
	private static final String TAG_CID = "cid";
	private static final String TAG_EMAIL= "email";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_CURRENT_CLIENT = "current_client";
	private static final String TAG_FULLNAME = "fullname";
	private static final String TAG_ADDRESS= "address";
	private static final String TAG_AGE = "age";
	private static final String TAG_GENDER= "gender";
	private static final String TAG_HEIGHT= "height";
	private static final String TAG_WEIGHT= "weight";
	private static final String TAG_GOAL= "goal";
	private static final String TAG_INJURIES= "injuries";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer_editclient);
 		// save button
				btnSave = (Button) findViewById(R.id.ClientbtnSave);
				btnDelete = (Button) findViewById(R.id.ClientbtnDelete);
				// getting product details from intent
				Intent i = getIntent();
				
				// getting product id (pid) from intent
				cid = i.getStringExtra(TAG_CID);

				// Getting complete product details in background thread
				new GetClientDetails().execute();

				// save button click event
						btnSave.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// starting background task to update product
								new SaveClientDetails().execute();
							}
						});

						// Delete button click event
						btnDelete.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// deleting product in background thread
								new DeleteClient().execute();
							}
						});
			}
	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetClientDetails extends AsyncTask<String, String, JSONObject> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditClient.this);
			pDialog.setMessage("Loading your clients details. Please wait...");
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
	            params.add(new BasicNameValuePair("cid", cid));

	            json = jsonParser.makeHttpRequest(
	                    url_client_detials, "GET", params);

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
		            JSONArray clientObj = null;
					try {
						clientObj = json.getJSONArray(TAG_CLIENT);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        JSONObject client = null;
				try {
					client = clientObj.getJSONObject(0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

				// display product data in EditText
				try {
					inputClientEmail.setText(client.getString(TAG_EMAIL));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputClientPassword.setText(client.getString(TAG_PASSWORD));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputCurrentClient.setText(client.getString(TAG_CURRENT_CLIENT));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputClientFullname.setText(client.getString(TAG_FULLNAME));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputClientAddress.setText(client.getString(TAG_ADDRESS));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputClientGender.setText(client.getString(TAG_GENDER));
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					inputClientAge.setText(client.getString(TAG_AGE));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inputClientHeight.setText(client.getString(TAG_HEIGHT));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					inputClientWeight.setText(client.getString(TAG_WEIGHT));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					inputClientGoal.setText(client.getString(TAG_GOAL));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}try {
					inputClientInjury.setText(client.getString(TAG_INJURIES));
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
	class SaveClientDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditClient.this);
			pDialog.setMessage("Saving Client ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
 		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
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
			params.add(new BasicNameValuePair(TAG_CID, cid));
			params.add(new BasicNameValuePair(TAG_EMAIL, email));
			params.add(new BasicNameValuePair(TAG_PASSWORD, password));
			params.add(new BasicNameValuePair(TAG_CURRENT_CLIENT, current_client));
			params.add(new BasicNameValuePair(TAG_FULLNAME, fullname));
			params.add(new BasicNameValuePair(TAG_ADDRESS, address ));
			params.add(new BasicNameValuePair(TAG_GENDER, gender ));
			params.add(new BasicNameValuePair(TAG_AGE, age ));
			params.add(new BasicNameValuePair(TAG_HEIGHT, height ));
			params.add(new BasicNameValuePair(TAG_WEIGHT, weight ));
			params.add(new BasicNameValuePair(TAG_GOAL, goal ));
			params.add(new BasicNameValuePair(TAG_INJURIES, injuries ));
			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_client,
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
		 * Background Async Task to Delete Client
		 * */
		class DeleteClient extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(EditClient.this);
				pDialog.setMessage("congratulations client completed...");
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
					params.add(new BasicNameValuePair("cid", cid));

					// getting product details by making HTTP request
					JSONObject json = jsonParser.makeHttpRequest(
							url_delete_client, "POST", params);

					// check your log for json response
					Log.d("Delete Client", json.toString());
					
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
