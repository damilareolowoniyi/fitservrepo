package com.fitserv.user.profilemenu;

import java.util.HashMap;

import com.fitserv.helper.SQLiteHandler;
import com.fitserv.helper.SessionManager;

import com.fitserv.androidapp.R;
import com.fitserv.androidapp.UserLogin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class UserHomeFragment extends Fragment {

	private TextView txtName;
	private TextView txtEmail;
	private TextView txtUserName;
	private Button btnLogout;
	private SQLiteHandler db;
	private SessionManager session;

	public UserHomeFragment() {
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.user_home, container, false);

		txtName = (TextView) rootView.findViewById(R.id.name);
		txtEmail = (TextView) rootView.findViewById(R.id.email);
		txtUserName = (TextView) rootView.findViewById(R.id.username);

		btnLogout = (Button) rootView.findViewById(R.id.btnLogout);

		// SqLite database handler
		db = new SQLiteHandler(getActivity().getApplicationContext());

		// session manager
		session = new SessionManager(getActivity().getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from sqlite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");
		String username = user.get("username");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);
		txtUserName.setText(username);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});

		// Button to add workout plan from profile
		Button diet = (Button) rootView.findViewById(R.id.diet);
		diet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(
						"com.fitserv.user.profilemenu.NEWDIETPLAN"));
			}
		});
		// Button to add workout plan from profile
		Button workout = (Button) rootView.findViewById(R.id.workout);
		workout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(
						"com.fitserv.user.profilemenu.NEWWORKOUT"));
			}
		});

		return rootView;

	}

	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(getActivity(), UserLogin.class);
		startActivity(intent);
		getActivity().finish();
	}
}
