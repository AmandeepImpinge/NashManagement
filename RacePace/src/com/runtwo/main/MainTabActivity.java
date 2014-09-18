 package com.runtwo.main;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.runtwo.fragmentactivities.AchievementFragmentActivity;
import com.runtwo.fragmentactivities.HomeFragmentActivity;
import com.runtwo.fragmentactivities.GoRunFragmentActivity;
import com.runtwo.fragmentactivities.NotificationFragmentActivity;
import com.runtwo.fragmentactivities.RunUsaFragmentActivity;
import com.runtwo.main.R;
import com.runtwo.slidingmenuleft.SlidingMenuLeftFunctions;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainTabActivity extends TabActivity{

	TabHost mTabHost;
	TabWidget mtabWidget;
	Resources res; 
	public static SlidingMenu menu;
	Globals global;
	int deviceHeight = 800;
	LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.tab_activity);
		
		Log.e("creating tabs","true");
		
		global = (Globals)getApplicationContext();
		
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		deviceHeight = global.getDeviceHeight();
		
		mTabHost = getTabHost();
		mtabWidget = getTabWidget();
		res = getResources();
		
		TabSpec tab1 = mTabHost.newTabSpec("home");
		//tab1.setIndicator("HOME",getResources().getDrawable(R.drawable.news_feed_icon_tab));
		View v1 = inflater.inflate(R.layout.tab_item,null);
		((ImageView)v1.findViewById(R.id.tab_img)).setImageResource(R.drawable.home_tab_icon);
		((TextView)v1.findViewById(R.id.tab_name)).setText("Home");
		tab1.setIndicator(v1);
		tab1.setContent(new Intent(MainTabActivity.this,HomeFragmentActivity.class));
		
		TabSpec tab2 = mTabHost.newTabSpec("profile");
		//tab2.setIndicator("PROFILE",getResources().getDrawable(R.drawable.achievements_icon_tab));
		View v2 = inflater.inflate(R.layout.tab_item,null);
		((ImageView)v2.findViewById(R.id.tab_img)).setImageResource(R.drawable.profile_tab_icon);
		((TextView)v2.findViewById(R.id.tab_name)).setText("Profile");
		tab2.setIndicator(v2);
		tab2.setContent(new Intent(MainTabActivity.this,AchievementFragmentActivity.class));
		
		TabSpec tab3 = mTabHost.newTabSpec("gorun");
		//tab3.setIndicator("GO RUN",getResources().getDrawable(R.drawable.run_icon_tab));
		View v3 = inflater.inflate(R.layout.tab_item,null);
		((ImageView)v3.findViewById(R.id.tab_img)).setImageResource(R.drawable.gorunicon);
		((TextView)v3.findViewById(R.id.tab_name)).setText("Go Run");
		tab3.setIndicator(v3);
		tab3.setContent(new Intent(MainTabActivity.this,GoRunFragmentActivity.class));
		
		
		TabSpec tab4 = mTabHost.newTabSpec("runusa");
		//tab4.setIndicator("RUN USA",getResources().getDrawable(R.drawable.run_usa_icon_tab));
		View v4 = inflater.inflate(R.layout.tab_item,null);
		((ImageView)v4.findViewById(R.id.tab_img)).setImageResource(R.drawable.run_usa_tab_icon);
		((TextView)v4.findViewById(R.id.tab_name)).setText("Run USA");
		tab4.setIndicator(v4);
		tab4.setContent(new Intent(MainTabActivity.this,RunUsaFragmentActivity.class));
		
		TabSpec tab5 = mTabHost.newTabSpec("notification");
		//tab5.setIndicator("ALERTS",getResources().getDrawable(R.drawable.photo_album_icon_tab));
		View v5 = inflater.inflate(R.layout.tab_item,null);
		((ImageView)v5.findViewById(R.id.tab_img)).setImageResource(R.drawable.notification_icon);
		((TextView)v5.findViewById(R.id.tab_name)).setText("Notifications");
		tab5.setIndicator(v5);
		tab5.setContent(new Intent(MainTabActivity.this,NotificationFragmentActivity.class));
		
		mTabHost.addTab(tab1);
		mTabHost.addTab(tab2);
		mTabHost.addTab(tab3);
		mTabHost.addTab(tab4);
		mTabHost.addTab(tab5);
		
		Log.e("creating tabs","Done");
		
		mTabHost.setCurrentTab(0);
		//To apply listener and change backgrounds
		handleTabs();
		
		menu = new SlidingMenuLeftFunctions(this).getTheMenu();
	}
	
	public void handleTabs(){
		Typeface typeface = Typeface.createFromAsset(getAssets(),"helvetica_neue_ltstd_lt_0.otf");
		
		for(int i=0;i<mtabWidget.getTabCount();i++){
			if(i == 0){
				mtabWidget.getChildAt(i).setBackgroundColor(res.getColor(R.color.bottom_back_selected_color));
			}else{
				mtabWidget.getChildAt(i).setBackgroundColor(res.getColor(R.color.bottom_bar_back_color));
			}
			
			/*ViewGroup vg = (ViewGroup)mtabWidget.getChildAt(i);
			
			TextView tv = (TextView)vg.getChildAt(1);
			ImageView im = (ImageView)vg.getChildAt(0);
			
			tv.setTextSize(9);
			tv.setTypeface(typeface);
			tv.setTextColor(Color.WHITE);
			
			if(deviceHeight > 800){
				tv.setPadding(0,0,0,6);
				im.setPadding(0,12,0,0);
			}else{
				tv.setPadding(0,0,0,4);
				im.setPadding(0,8,0,0);
			}
			tv.requestLayout();
			im.requestLayout();*/
		}
		 
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int curtab = (int) mTabHost.getCurrentTab();
				for(int i=0;i<mtabWidget.getTabCount();i++){
					if(i == curtab){
						mtabWidget.getChildAt(i).setBackgroundColor(res.getColor(R.color.bottom_back_selected_color));
					}else{
						mtabWidget.getChildAt(i).setBackgroundColor(res.getColor(R.color.bottom_bar_back_color));
					}
				}
			}
		});
	}
	
}