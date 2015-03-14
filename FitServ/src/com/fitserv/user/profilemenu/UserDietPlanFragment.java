package com.fitserv.user.profilemenu;

import com.fitserv.androidapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserDietPlanFragment extends Fragment  {
	
	public UserDietPlanFragment(){}
	
	 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.user_dietplan, container, false);
         
        return rootView;
    }
	
	
}
