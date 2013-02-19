/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.sample.styled;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;

public class RoundedColourFragmentList extends SherlockListFragment {


	private View mView;
	private int mColour;
	private float mWeight;
	private int marginLeft, marginRight, marginTop, marginBottom;

	// need a public empty constructor for framework to instantiate
	public RoundedColourFragmentList() {
		


	}

	public RoundedColourFragmentList(int colour, float weight, int margin_left,
			int margin_right, int margin_top, int margin_bottom) {
		mColour = colour;
		mWeight = weight;
		marginLeft = margin_left;
		marginRight = margin_right;
		marginTop = margin_top;
		marginBottom = margin_bottom;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = new View(getActivity());

		GradientDrawable background = (GradientDrawable) getResources()
				.getDrawable(R.drawable.rounded_rect);
		background.setColor(mColour);

		

		mView.setBackgroundDrawable(background);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
				LayoutParams.FILL_PARENT, mWeight);
		lp.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		
		mView.setLayoutParams(lp);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
		

	    setListAdapter(new TwitterListAdapter(getActivity(), TweetReader.getJobs()));


		
		TwitterListAdapter mAdapter = new TwitterListAdapter(getActivity(), TweetReader.getJobs());
		setListAdapter(mAdapter);



		return mView;
	}

}
