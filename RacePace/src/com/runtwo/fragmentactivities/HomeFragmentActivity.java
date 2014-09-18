package com.runtwo.fragmentactivities;

import com.runtwo.main.HomeFragment;
import com.runtwo.main.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class HomeFragmentActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.common_fragment_activity_lay);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new HomeFragment();
		ft.replace(R.id.contentintab,f);
		ft.commit();
	}
	
	

}
