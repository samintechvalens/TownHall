package com.actionbarsherlock.sample.styled;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;

public class FilterFragment extends SherlockListFragment {
	boolean isDualPane;
	int mCurCheckPosition = 0;
	
	private final static String TAG = "FilterFragment";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
		setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.topics)));
		
//		setListAdapter(new ArrayAdapter<String>(getActivity(),
//                R.layout.my_list_item_icon_text, getResources().getStringArray(R.array.topics)));
		
		
		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.details);
		isDualPane = (detailsFrame != null) && (detailsFrame.getVisibility() == View.VISIBLE);

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}

		if (isDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			
			

			showDetails(mCurCheckPosition, "hi");
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
//		showDetails(position);
		
		String filter = getResources().getStringArray(R.array.topics)[position];
		
		showDetails(10, filter);
		
		Log.d(TAG, "The clicked text is: " + filter);
	}

	/**
	 * Helper function to show the details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a
	 * whole new activity in which it is displayed.
	 */
	void showDetails(int index, String filter) {
		mCurCheckPosition = index;

		if (isDualPane) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show the data.
			getListView().setItemChecked(index, true);

			// Check what fragment is currently shown, replace if needed.
			DetailsFragment details = (DetailsFragment)
					getActivity().getSupportFragmentManager().findFragmentById(R.id.details);
			if (details == null || details.getShownIndex() != index) {
				// Make new fragment to show this selection.
				details = DetailsFragment.newInstance(index);

				// Execute a transaction, replacing any existing fragment
				// with this one inside the frame.
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.details, details);
				
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}

		} else {
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
//			Intent intent = new Intent();
//			intent.setClass(getActivity(), DetailsActivity.class);
//			intent.putExtra("index", index);
//			startActivity(intent);
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(this).commit();
			
			ft = getActivity().getSupportFragmentManager().beginTransaction();
//			
			TownhallFragment newTownhall = new TownhallFragment();
			Bundle extras = new Bundle();
			extras.putString("filter", filter);
			newTownhall.setArguments(extras);
			
			ft.add(
					android.R.id.content, newTownhall, "townhall").commit();
		}
	}
}
