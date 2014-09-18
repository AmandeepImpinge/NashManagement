package com.runtwo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.runtwo.main.R;

public class ProfileSetupFirst extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		container = (LinearLayout)inflater.inflate(R.layout.profile_setup_first,null);
		
		return container;
	}
	
}
