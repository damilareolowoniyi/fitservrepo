package com.fitserv.user.profilemenu;

import com.fitserv.androidapp.R;

import android.app.Fragment;
import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserCalendarFragment extends Fragment  {
	
	public UserCalendarFragment(){}
	
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.user_calender, container, false);
         
        return rootView;
    }
}
