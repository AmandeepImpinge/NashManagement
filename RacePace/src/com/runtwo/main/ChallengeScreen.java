package com.runtwo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChallengeScreen extends Fragment{

	LayoutInflater inflater;
	
	TextView startChallengeBtn,joinChallengeBtn,myChallengeBtn;
	LinearLayout startLayout,joinLayout,myLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		
		this.inflater = inflater;
		container = (ViewGroup) inflater.inflate(R.layout.challange_screen,null);
		
		initViews(container);
		
		return container;
	}
	
	
	public void initViews(ViewGroup container){
		startChallengeBtn = (TextView)container.findViewById(R.id.start_challenge_btn);
		joinChallengeBtn = (TextView)container.findViewById(R.id.join_challenge_btn);
		myChallengeBtn = (TextView)container.findViewById(R.id.my_challenge_btn);
		
		startLayout = (LinearLayout)container.findViewById(R.id.start_challenge_lay);
		joinLayout = (LinearLayout)container.findViewById(R.id.join_challenge_lay);
		myLayout = (LinearLayout)container.findViewById(R.id.my_challenge_lay);
		
	}
}
