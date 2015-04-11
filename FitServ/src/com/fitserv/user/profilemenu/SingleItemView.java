package com.fitserv.user.profilemenu;

import com.fitserv.androidapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleItemView extends Activity {
	// Declare Variables
	TextView txtrating;
	TextView txtlocation;
	TextView txtdetails;
	TextView txtfullname;
	TextView txtreviews;
	TextView txtqualifcations;
	TextView txtemail;
	TextView txtusername;
	String rating;
	String location;
	String details;
	String fullname;
	String reviews;
	String qualifcation;
	String email;
	String username;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleitemview);
		// Retrieve data from MainActivity on item click event
		Intent i = getIntent();
		// Get the results of rank
		rating = i.getStringExtra("rating");
		// Get the results of country
		location = i.getStringExtra("location");
		// Get the results of population
		details = i.getStringExtra("details");
		fullname = i.getStringExtra("fullname");
		reviews = i.getStringExtra("reviews");
		qualifcation = i.getStringExtra("qualifcation");
		email = i.getStringExtra("email");
		username = i.getStringExtra("username");
 
		// Locate the TextViews in singleitemview.xml
		txtrating = (TextView) findViewById(R.id.rating);
		txtlocation = (TextView) findViewById(R.id.location);
		txtdetails = (TextView) findViewById(R.id.details);
 		txtfullname = (TextView) findViewById(R.id.fullname);
		txtreviews = (TextView) findViewById(R.id.reviews);
		txtqualifcations = (TextView) findViewById(R.id.qualification);
		txtemail = (TextView) findViewById(R.id.email);
		txtusername = (TextView) findViewById(R.id.username);
 
		// Load the results into the TextViews
		txtrating.setText(rating);
		txtlocation.setText(location);
		txtdetails.setText(details);
		txtfullname.setText(fullname);
		txtreviews.setText(reviews);
		txtqualifcations.setText(qualifcation);
		txtemail.setText(email);
		txtusername.setText(username);
		  Button booktrainer = (Button) findViewById(R.id.bookbt);

	        booktrainer.setOnClickListener(new View.OnClickListener() {
	 			@Override
		public void onClick(View v) {
				 //TODO Auto-generated method stub
	 		Intent i = new Intent(getApplicationContext(),
						UserBookTrainer.class);
				startActivity(i);
				finish();
				}
			});
	}
}