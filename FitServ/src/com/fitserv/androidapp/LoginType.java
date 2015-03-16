/**
 * Author: Damilare Olowoniyi
 * Mobile application
 * Final year project 
 * */
package com.fitserv.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


//personal trainer AND users login choice 
public class LoginType extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.logintype);
	        
	        ImageButton loginUser = (ImageButton) findViewById(R.id.userlogin);
	        loginUser.setOnClickListener(new View.OnClickListener() {
	 			@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				startActivity(new Intent("com.fitserv.androidapp.USERLOGIN"));	
					}
				});
	        
	        ImageButton loginTrainer = (ImageButton) findViewById(R.id.trainerlogin);
	        loginTrainer.setOnClickListener(new View.OnClickListener() {
	 			@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				startActivity(new Intent("com.fitserv.androidapp.TRAINERLOGIN"));	
					}
				});
	        	{ 	
	        }
	    }


}
