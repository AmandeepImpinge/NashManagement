package com.runtwo.slidingmenuleft;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.Globals;
import com.runtwo.main.LoginActivity;
import com.runtwo.main.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlidingMenuLeftFunctions implements OnClickListener{

	LinearLayout home,profile,activity,challenges,community,shop,upgrade,settings,help,logout;
	SlidingMenu menu;
	Globals global;
	SharedPreferences prefs;
	Context c;
	
	public SlidingMenuLeftFunctions(Context c){

		Activity a = (Activity)c;
		this.c = c;
		
		global = (Globals) c.getApplicationContext();
		
		prefs = c.getSharedPreferences(GlobalConstants.PREF_NAME,c.MODE_PRIVATE);
		
		menu = new SlidingMenu(c);
		menu.attachToActivity(a,SlidingMenu.SLIDING_CONTENT);
		menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        menu.setFadeDegree(0.35f);
        menu.setBehindOffset(120);
        menu.setMenu(R.layout.left_sliding_menu);
		
		home = (LinearLayout)menu.findViewById(R.id.home_btn);
		shop = (LinearLayout)menu.findViewById(R.id.shop_btn);
		upgrade = (LinearLayout)menu.findViewById(R.id.upgrade_btn);
		settings = (LinearLayout)menu.findViewById(R.id.settings_btn);
		help = (LinearLayout)menu.findViewById(R.id.help_btn);
		logout = (LinearLayout)menu.findViewById(R.id.logout_btn);
		
		((TextView)menu.findViewById(R.id.username)).setText(""+global.getUserName());
		
		
		logout.setOnClickListener(this);
		
	}
	
	public SlidingMenu getTheMenu(){
		return menu;
	}
	
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.home_btn:
			
			break;
		case R.id.shop_btn:
		
			break;
		case R.id.upgrade_btn:
			
			break;
		case R.id.settings_btn:
			
			break;
		case R.id.help_btn:
			
			break;
		case R.id.logout_btn:
				Editor edit = prefs.edit();
				edit.putString(GlobalConstants.USERNAME_SAVE,"");
				edit.putString(GlobalConstants.PASSWORD_SAVE,"");
				edit.commit();
				
				Intent intent = new Intent(c,LoginActivity.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    intent.setAction(Intent.ACTION_MAIN);
			    intent.addCategory(Intent.CATEGORY_LAUNCHER);
			    c.startActivity(intent);
			    
			    android.os.Process.killProcess(android.os.Process.myPid());
			    
			break;
		}
	}
}