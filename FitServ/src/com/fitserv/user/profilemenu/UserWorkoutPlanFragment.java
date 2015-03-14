package com.fitserv.user.profilemenu;

import com.fitserv.androidapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserWorkoutPlanFragment extends Fragment {
	
	public UserWorkoutPlanFragment(){}
	
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.user_workoutplan, container, false);
         
        return rootView;
    }
}
