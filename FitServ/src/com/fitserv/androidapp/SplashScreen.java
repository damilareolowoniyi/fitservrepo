package com.fitserv.androidapp;

 import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
 public class SplashScreen extends Activity {

	 //ref:  https://www.youtube.com/watch?v=AN5EFviJRR0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread logoTimer = new Thread(){
        	public void run(){
        		try{
        			//we want it to run for 1 seconds
        			sleep(1000);
        			Intent welcomeIntent = new Intent("com.fitserv.androidapp.LOGINTYPE");
        			startActivity(welcomeIntent);
        			
        		} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

        		finally{
        			finish();
        		}
        	}
        };
        logoTimer.start();
        
    }



}
