package com.fitserv.personaltrainer.profilemenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitserv.androidapp.R;

public class TrainerCalenderFragment extends Fragment {
	
	public TrainerCalenderFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.trainer_calender, container, false);
         
        return rootView;
    }
}