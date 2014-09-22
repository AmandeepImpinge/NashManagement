package com.runtwo.fragmentactivities;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.Globals;
import com.runtwo.main.HomeFragment;
import com.runtwo.main.R;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class HomeFragmentActivity extends FragmentActivity{

	Globals global;
	AlertDialog ad;
	int total = 20;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.common_fragment_activity_lay);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new HomeFragment();
		ft.replace(R.id.contentintab,f);
		ft.commit();
		
		global = (Globals) getApplicationContext();
		
		try {
			total = Integer.parseInt(""+global.getUserData().get(GlobalConstants.TOTAL_PROFILE_COMPLETION));
			if(total < 100){
				makeProfileCompletionDialog();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void makeProfileCompletionDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragmentActivity.this);
		
		builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ad.dismiss();
			}
		});
		
		builder.setTitle("Profile Completion");
		builder.setMessage("Your Profile is "+total+"% Complete.");
		
		ad = builder.create();
		ad.show();
	}

}