package com.fitserv.personaltrainer.profilemenu;

  
import java.util.HashMap;

import com.fitserv.helper.TrainerSQLiteHandler;
import com.fitserv.helper.TrainerSessionManager;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fitserv.androidapp.R;
import com.fitserv.androidapp.TrainerLogin;

public class TrainerHomeFragment extends Fragment {

	private TextView txtName;
	private TextView txtEmail;
	private TextView txtUserName;

	private Button btnLogout;
 	private TrainerSQLiteHandler db;
	private TrainerSessionManager session;

	public TrainerHomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.trainer_home, container, false);
        txtName = (TextView) rootView.findViewById(R.id.name);
		txtEmail = (TextView) rootView.findViewById(R.id.email);
		txtUserName = (TextView) rootView.findViewById(R.id.username);

		btnLogout = (Button) rootView.findViewById(R.id.btnLogout);

		// SqLite database handler
		db = new TrainerSQLiteHandler(getActivity().getApplicationContext());

		// session manager
		session = new TrainerSessionManager(getActivity().getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutTrainer();
		}

		// Fetching user details from sqlite
		HashMap<String, String> trainer = db.getTrainerDetails();

		String name = trainer.get("name");
		String email = trainer.get("email");
		String username = trainer.get("username");

 
		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);
		txtUserName.setText(username);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutTrainer();
			}
		});
        return rootView;
    }
	private void logoutTrainer() {
		session.setLogin(false);

		db.deleteTrainers();

		// Launching the login activity
		Intent intent = new Intent(getActivity(), TrainerLogin.class);
		startActivity(intent);
		getActivity().finish();
	}
}