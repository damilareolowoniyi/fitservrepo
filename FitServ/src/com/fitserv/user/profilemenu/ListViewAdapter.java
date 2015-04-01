package com.fitserv.user.profilemenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fitserv.androidapp.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;

	LayoutInflater inflater;
	private List<TrainersDetailsPojo> trainersdetailspojolist = null;
	private ArrayList<TrainersDetailsPojo> arraylist;

	public ListViewAdapter(Context context, List<TrainersDetailsPojo> trainersdetailspojolist) {
		mContext = context;
		this.trainersdetailspojolist = trainersdetailspojolist;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<TrainersDetailsPojo>();
		this.arraylist.addAll(trainersdetailspojolist);
	}

	public class ViewHolder {
		TextView rating;
		TextView location;
		TextView details;
		TextView fullname;
	}

	@Override
	public int getCount() {
		return trainersdetailspojolist.size();
	}

	@Override
	public TrainersDetailsPojo getItem(int position) {
		return trainersdetailspojolist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.rating = (TextView) view.findViewById(R.id.rating);
			holder.location = (TextView) view.findViewById(R.id.location);
			holder.details = (TextView) view.findViewById(R.id.details);
			holder.fullname = (TextView) view.findViewById(R.id.fullname);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.rating.setText(trainersdetailspojolist.get(position).getRating());
		holder.location.setText(trainersdetailspojolist.get(position).getLocation());
		holder.details.setText(trainersdetailspojolist.get(position).getDetails());
		holder.fullname.setText(trainersdetailspojolist.get(position).getFullname());

		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(mContext, SingleItemView.class);
 				// Pass all data rating
				intent.putExtra("rating",(trainersdetailspojolist.get(position).getRating()));
				// Pass all data location
				intent.putExtra("location",(trainersdetailspojolist.get(position).getLocation()));
				// Pass all data details
				intent.putExtra("details",(trainersdetailspojolist.get(position).getDetails()));
				//Pass the fullname
				intent.putExtra("fullname",(trainersdetailspojolist.get(position).getFullname()));
				
				intent.putExtra("reviews",(trainersdetailspojolist.get(position).getReviews()));

				intent.putExtra("qualification",(trainersdetailspojolist.get(position).getQualification()));

				intent.putExtra("email",(trainersdetailspojolist.get(position).getEmail()));
				
				intent.putExtra("username",(trainersdetailspojolist.get(position).getUsername()));
				// Pass all data flag
				// Start SingleItemView Class
				mContext.startActivity(intent);
 
			}
		});

		return view;
	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		trainersdetailspojolist.clear();
		if (charText.length() == 0) {
			trainersdetailspojolist.addAll(arraylist);
		} 
		else 
		{
			for (TrainersDetailsPojo wp : arraylist) 
			{
				if (wp.getLocation().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					trainersdetailspojolist.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
