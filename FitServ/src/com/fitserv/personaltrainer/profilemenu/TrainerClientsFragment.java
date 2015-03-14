package com.fitserv.personaltrainer.profilemenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitserv.androidapp.R;

public class TrainerClientsFragment extends Fragment {
	
	public TrainerClientsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.trainer_clients, container, false);
         
        return rootView;
    }
}