package com.fitserv.user.profilemenu;

 
 
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

 
import com.fitserv.androidapp.R;

 
 import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
 
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
  
public class UserSearchTrainerFragment extends Fragment  {
	
	// Declare Variables
		ListView list;
		ListViewAdapter adapter;
		EditText editsearch;
		String[] rating;
		String[] location;
		String[] details;
		String[] fullname;
		String[] reviews;
		String[] qualifcations;
		String[] email;
		String[] username;
		ArrayList<TrainersDetailsPojo> arraylist = new ArrayList<TrainersDetailsPojo>();

	 
  	public UserSearchTrainerFragment(){}
	
	@Override
  	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
          
         final ProgressDialog builder = new ProgressDialog(getActivity());
 		builder.setTitle("List based map view");
 		builder.setMessage("Checking your current location to load the personal trainers nearest to you..");
 		builder.setCancelable(true);
 		builder.show();

 		final Timer t = new Timer();
 		t.schedule(new TimerTask() {
 			public void run() {
 				builder.dismiss(); // when the task active then close the dialog
 				t.cancel(); // also just top the timer thread, otherwise, you
 							// may receive a crash report
 			}
 		}, 4000); // after 4 second (or 2000 miliseconds), the task will be
 					// active.

        View rootView = inflater.inflate(R.layout.user_find_trainer, container, false);

 		ImageButton map = (ImageButton) rootView.findViewById(R.id.map);

 		map.setOnClickListener(new View.OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				Intent i = new Intent(getActivity().getApplicationContext(),
 						WebMapActivity.class);
 				startActivity(i);
 				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 			}
 		});
 		// Generate sample data
 		rating = new String[] { "5", "5", "4", "4", "3", "2", "1", "1", "1",
 				"1" };

 		location = new String[] { "Glasnevin, Dublin ", "Ballymun, Dublin", "Finglas, Dublin",
 				"Whitehall Dublin", "Albert College Park, Dublin ", "Shanowen Road, Dublin", "Courtlands Road, Dublin", "Santry dublin",
 				"St Patricks College, Drumcondra, Dublin", "DCU Soccer Centre, Dublin" };

 		details = new String[] {
 				"A vastly  sales training to a wide range of Irish companies both large and small.",
 				"Orla has over 15 years experience in Customer Service, Sales, Account Management and 3rd Level Education.",
 				"Paula holds a degree in Business Studies (Hons) majoring in Marketing and Management ",
 				"Larry Power B.Comm F.C.M.A. is a financial consultant, and works with business owners and SME's",
 				"Prior to setting up his own practice in 2000, Larry had held senior financial positions across a wide sector of Irish industry.",
 				"Larry is an approved FÁS trainer, delivering courses to Start your own business",
 				"Our training team would be happy to meet with you to discuss your specific ",
 				"An experienced training professional, Karl is focused on bringing energy and an innovative learning",
 				"Being involved in professional sales for over 25 years",
 				" I believe in a very practical and people orientated approach to business. A genuine interest" };

 		reviews = new String[] { "Dav: Love this trainer ",
 				"AlexBest trainer going ", "tommy: amazing trainer ",
 				"Glen Hes the best ", "Chris Jones", "Davo Hes so good",
 				"Chris I love this guy", "Dacz helped me in weeks ",
 				"D Nger forget this guy", "John Hes perfect" };

 		qualifcations = new String[] { "DCU Education sport Honors ",
 				"NCI BA degree in Gym Health ", "Degree In Sport Science ",
 				"A1 in gym training course ",
 				"Professional trainer for 8 years", "UCD Trainers Educaton",
 				"DCU  Trainers Work Course ", "TCD Trainer Degree A1",
 				"DCU Mean Degree A ", "Trainers Live Degree B" };

 		fullname = new String[] { "Damilare Olowoniyi", "Sean White",
 				"Kevin Mustong ", "Chris Jones", "Alex Prorv",
 				"Jake Mike", "Links Dans", "Owen Smith", "Johnjo Meals","Alex Flex" };

 		email = new String[] { "damilareolowoniyi@yahoo.com",
 				"seanwhite@yahoo.com ", "kevinmustong@fisterv.com",
 				"chrisjones@gmail.com", "alexprov@gmail.com",
 				"jake@gmail.com", "owensmith@gymfit.com",
 				"owensmith@gymsmart.com", "alexf@mail.com", "johns@gmail.com" };

 		username = new String[] { "cashdaz", "seanwhite ", "kevin", "chrisjones",
 				"alexprov", "jman", "lanksIn", "OwenIt", "AlexFlex",
 				"MisterMuscle" };

 		// Locate the ListView in listview_main.xml
 		list = (ListView) rootView.findViewById(R.id.listview);

 		for (int i = 0; i < rating.length; i++) {
 			TrainersDetailsPojo wp = new TrainersDetailsPojo(rating[i],
 					location[i], details[i], reviews[i], qualifcations[i],
 					fullname[i], email[i], username[i]);
 			// Binds all strings into an array
 			arraylist.add(wp);
 		}

 		// Pass results to ListViewAdapter Class
 		adapter = new ListViewAdapter(getActivity(), arraylist);

 		// Binds the Adapter to the ListView
 		list.setAdapter(adapter);

 		// Locate the EditText in listview_main.xml
 		editsearch = (EditText) rootView.findViewById(R.id.search);

 		// Capture Text in EditText
 		editsearch.addTextChangedListener(new TextWatcher() {

 			@Override
 			public void afterTextChanged(Editable arg0) {
 				// TODO Auto-generated method stub
 				String text = editsearch.getText().toString()
 						.toLowerCase(Locale.getDefault());
 				adapter.filter(text);
 			}

 			@Override
 			public void beforeTextChanged(CharSequence arg0, int arg1,
 					int arg2, int arg3) {
 				// TODO Auto-generated method stub
 			}

 			@Override
 			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
 					int arg3) {
 				// TODO Auto-generated method stub
 			}
 		}); 
         
        return rootView;
    }
     
    
   }
