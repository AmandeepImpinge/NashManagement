package com.runtwo.fragmentactivities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.datatype.Duration;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.AddPictureActivity;
import com.runtwo.main.Globals;
import com.runtwo.main.MusicTypeActivity;
import com.runtwo.main.R;
import com.runtwo.utils.Utils;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GoRunFragmentActivity extends FragmentActivity{

	GoogleMap mMap;
	ImageView mediaBox, mapBox;
	View mediaBottomDiv, mapBottomDiv;
	RelativeLayout mediaTab, mapTab, mediaContainer, mapContainer;
	ImageView musicBtn;
	TextView goRun,startBtn,resumeBtn,stopBtn;
	LinearLayout slideBtn,resumeStopContainer;
	boolean firstTime = false;
	final int MEDIA_TAB_CHANGE = 111;
	final int MAP_TAB_CHANGE = 100;
	boolean mediaTabOpen = false;
	boolean mapTabOpened = true;
	Resources res;
	FragmentTransaction ft;
	float downx = 0,upx = 0;
	long downtime,uptime;
	double loclat = 0.0,loclng = 0.0;
	RelativeLayout map_container;
	LinearLayout map_cover;
	View actionBar;
	Globals global;
	int hours =0;
	int minutes = 0;
	int seconds = 0;
	String toSet = "";
	String hourset = "",minset = "",secset = "";
	Timer myTimer;
	TextView timerText,milesText,paceText,caloriesText;
	double oldLat = 0.0,oldLng = 0.0;
	boolean working = false,running = false;
	float oldMiles = 0.00f;
	int hitSeconds = 0;
	//Inerval of No of seconds between the hit to calculate the distance between the Locations. 
	int distanceCalculateHitInterval = 4;
	float currentSpeed = 0.0f;
	float oldCalories = 0;
	long oldHitTime = 0;

	//For route and Markers on the map along the path of running
	ArrayList<LatLng> locCoodList = new ArrayList<LatLng>();
	PolylineOptions routeOptions;
	Polyline trackPolyline;
	Marker startMarker;
	ArrayList<Integer> markerPositions = new ArrayList<Integer>();
	ArrayList<Marker> mapMarkers = new ArrayList<Marker>();
	//=========================
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		/*setContentView(R.layout.common_fragment_activity_lay);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment f = new GoRunFragment();
		ft.replace(R.id.contentintab,f);
		ft.commit();*/
		
		setContentView(R.layout.run_two_frag_lay);
		
		global = (Globals) getApplicationContext();
		
		actionBar = findViewById(R.id.action_bar);
		((TextView)actionBar.findViewById(R.id.user_name)).setText(""+global.getUserName());
		
		res = getResources();
		ft = getSupportFragmentManager().beginTransaction();
		
		routeOptions = new PolylineOptions();
		routeOptions.color(getResources().getColor(R.color.topbar_blue_back_color));
		routeOptions.width(3);
		
		//for use in map 
		firstTime = true;
		map_container = (RelativeLayout)findViewById(R.id.map_container);
		//map_cover = (LinearLayout)findViewById(R.id.map_cover);
		
		initMap();
		initViews();
		handleClickActions();
		//getDistanceCalulated();
		
	}
	
	private void initMap() {
		try {
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_run)).getMap();
			mMap.setMyLocationEnabled(true);
			mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
				public void onMyLocationChange(Location loc) {
					loclat = loc.getLatitude();
					loclng = loc.getLongitude();
					/*if(firstTime){
						firstTime = false;
						try {
							mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loclat, loclng),17));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}*/
					
					//For Calculating Pace
					//if(loc.hasSpeed()){
						currentSpeed = loc.getSpeed();
						Log.e("Speed",""+currentSpeed);
						//getPaceUpdated(currentSpeed);
						//Toast.makeText(GoRunFragmentActivity.this,"location changed has speed : "+currentSpeed,Toast.LENGTH_SHORT);
						/*}else{
						try {
							paceText.setText("00:00");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}*/
					
					//To calculate the Distance====
					//if not paused or stopped Running
					if(running){
						//if it is not the first Location
						if(oldLat != 0){
							//if the older calculation request is still in progress
							if(!working){
								//if in case hitseconds are zero
								if(hitSeconds > 0){
									Log.e("hit seconds","greater");
									//if time interval is more than specified in the variable
									if((seconds - hitSeconds) > distanceCalculateHitInterval){
										Log.e("interval passed","true");
										new getDistanceUpdated().execute("");
										//Toast.makeText(GoRunFragmentActivity.this,"Getting Distance and speed",Toast.LENGTH_SHORT);
									}
								}else{
									hitSeconds = seconds;
								}
								
								LatLng ltln = new LatLng(loclat, loclng);
								locCoodList.add(ltln);
								updateMapRoute();
								
							}
						}else{
							oldLat = loclat;
							oldLng = loclng;
							hitSeconds = seconds;
						}
					}
					//==============================
					
				}
			}); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMapRoute(){
		routeOptions.getPoints().clear();
		routeOptions.addAll(locCoodList);
		//remove if already added 
		if(trackPolyline != null){
			trackPolyline.remove();
		}
		
		trackPolyline = mMap.addPolyline(routeOptions);
		//for the start marker to show on map
		if(startMarker == null){
			updateMarkersOnMap();
		}
	}
	
	public void updateMarkersOnMap(){
		if(startMarker == null && locCoodList.size() > 0){
			startMarker = mMap.addMarker(new MarkerOptions().position(
						  new LatLng(locCoodList.get(0).latitude,locCoodList.get(0).longitude))
						  .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
		}
		
		//remove the markers if any
		if(mapMarkers.size() > 0){
			for(int i=0;i<mapMarkers.size();i++){
				mapMarkers.get(i).remove();
			}
			mapMarkers.clear();
		}
		//==========================
		
		int upto = markerPositions.size();
		for(int i=0;i<upto;i++){
			LatLng ltlg = locCoodList.get(markerPositions.get(i));
			Marker mk;
			if(i == (upto-1)){
				if(!running){
					mk = mMap.addMarker(new MarkerOptions().position(new LatLng(ltlg.latitude,ltlg.longitude))
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red)));	
				}else{
					mk = mMap.addMarker(new MarkerOptions().position(new LatLng(ltlg.latitude,ltlg.longitude))
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
				}
			}else{
				mk = mMap.addMarker(new MarkerOptions().position(new LatLng(ltlg.latitude,ltlg.longitude))
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_green)));
			}
			mapMarkers.add(mk);
		}
	}
	
	
	class getDistanceUpdated extends AsyncTask<String,Integer,String>{
		float distance = 0;
		float calories = 0;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			working = true;
		}
		
		@Override
		protected String doInBackground(String... params) {
			distance = Utils.getDistance(global,oldLat, oldLng, loclat, loclng);
			
			//speed work
			long curtime = System.currentTimeMillis();
			long diff = curtime-oldHitTime;
			oldHitTime = curtime;
			currentSpeed = Utils.getSpeed(global.getDistanceInMeters(),diff);
			//==========
			float weight = 65;
			try {
				String wwt = global.getUserData().get(GlobalConstants.WEIGHT);
				if(wwt.length() > 0){
					weight =  Float.parseFloat(wwt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentSpeed > 0){
				calories = Utils.getBurnedCaloriesCount(GoRunFragmentActivity.this,currentSpeed,weight,global.getDistanceInMeters());
			}
			return ""; 
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(distance > 0){
				oldMiles += distance;
				milesText.setText(""+oldMiles);
				oldLat = loclat;
				oldLng = loclng;
				
				if(calories > 0){
					oldCalories += calories;
					caloriesText.setText(""+oldCalories);
				}
				
				if(currentSpeed > 0){
					getPaceUpdated(currentSpeed);
				}
			}
			working = false;
		}
	} 
	
	public void getPaceUpdated(float speed){
		try {
			paceText.setText(""+Utils.convertToMinutesPerMile(speed));
			//caloriesText.setText(""+Utils.convertToKilometerPerHour(speed));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initViews() {
		mediaBox = (ImageView) findViewById(R.id.media_check);
		mapBox = (ImageView) findViewById(R.id.map_check);
		mediaBottomDiv = (View) findViewById(R.id.media_bottom_line);
		mapBottomDiv = (View) findViewById(R.id.map_bottom_line);
		mediaTab = (RelativeLayout) findViewById(R.id.media_lay);
		mapTab = (RelativeLayout) findViewById(R.id.map_lay);
		mapContainer = (RelativeLayout) findViewById(R.id.map_container);
		mediaContainer = (RelativeLayout) findViewById(R.id.media_container);
		  
		musicBtn = (ImageView)findViewById(R.id.music_btn);
	
		timerText = (TextView)findViewById(R.id.duration);
		milesText = (TextView)findViewById(R.id.no_miles);
		paceText = (TextView)findViewById(R.id.curr_pace);
		caloriesText = (TextView)findViewById(R.id.calories);
		
		goRun = (TextView)findViewById(R.id.gorun_btn);
		startBtn = (TextView)findViewById(R.id.start_btn);
		resumeBtn = (TextView)findViewById(R.id.resume_btn);
		stopBtn = (TextView)findViewById(R.id.stop_btn);
		
		slideBtn = (LinearLayout)findViewById(R.id.slide_btn);
		resumeStopContainer = (LinearLayout)findViewById(R.id.resume_stop_container);
	}

	private void handleClickActions() {
		
		mediaTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mediaTabOpen){
					mediaBox.setImageResource(R.drawable.music_player_icon_selected);
					mapBox.setImageResource(R.drawable.map_icon_unselected);
				 	changeTab(MEDIA_TAB_CHANGE);
					
					mediaTabOpen = true;
					mapTabOpened = false;
				}
			} 
		});

		mapTab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mapTabOpened){
					mediaBox.setImageResource(R.drawable.music_player_icon_unselected);
					mapBox.setImageResource(R.drawable.map_icon_selected);
					changeTab(MAP_TAB_CHANGE);
					
					mediaTabOpen = false;
					mapTabOpened = true;
				}
			}
		});
		
		musicBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GoRunFragmentActivity.this,MusicTypeActivity.class));
			}
		});
		
		goRun.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleGoRunClick();
			}
		});
		
		startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//handleStartClick();
			}
		});

		resumeBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleResumeClick();
			}
		});
		
		stopBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleStopClick();
			}
		});
		
		slideBtn.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				
				if(action == MotionEvent.ACTION_DOWN){
					downx = event.getX();
					downtime = System.currentTimeMillis();
					upx = 0;					
					
					return true;
				}else if(action == MotionEvent.ACTION_UP){
					upx = event.getX();
					uptime = System.currentTimeMillis();
					//Log.e("up","val d: "+downx+" val u :"+upx);
					if(upx > downx){
						if((uptime-downtime) < 1800){
							if((upx-downx)>150){
								//make resume stop buttons visible
								pauseMyTimer();
								handleVisibilityOfButtons(3);
							}
						}
					}
					return false;
				}else{
					return true;
				}
			}
		});
	}
	
	private void changeTab(int which) {
		
		if (which == MAP_TAB_CHANGE) {
			mapContainer.setVisibility(View.VISIBLE);
			mediaContainer.setVisibility(View.GONE);
		 	mapBottomDiv.setBackgroundColor(res.getColor(R.color.topbar_blue_back_color));
			mediaBottomDiv.setBackgroundColor(res.getColor(R.color.dark_grey_text_color));
		} else if (which  == MEDIA_TAB_CHANGE) {
			mapContainer.setVisibility(View.GONE);
			mediaContainer.setVisibility(View.VISIBLE);
			mediaBottomDiv.setBackgroundColor(res.getColor(R.color.topbar_blue_back_color));
			mapBottomDiv.setBackgroundColor(res.getColor(R.color.dark_grey_text_color));
		}
	}
	
	//Timer Classes and methods======
	public void startMyTimer(){
		myTimer = new Timer();
		myTimer.scheduleAtFixedRate(new MyTimerTask(),1000,1000);
		running = true;
	}
	
	class MyTimerTask extends TimerTask{
		public void run() {
			seconds += 1;
			//seconds work
			if(seconds == 60){
				seconds = 0;
				minutes += 1;
				secset = "00"; 
			}else{
				if(seconds < 10){
					secset = "0"+seconds;
				}else{
					secset = ""+seconds;
				}
			}
			//========

			//minutes work=====
			if(minutes == 60){
				hours += 1;
				minutes = 0;
				minset = "00";
			}else{
				if(minutes < 10){
					minset = "0"+minutes;
				}else{
					minset = ""+minutes;
				}
			}
			//to be shown in timer
			toSet = minset+":"+secset;

			//Hour Work===========
			if(hours > 0){
				if(hours < 10){
					hourset = "0"+hours;
				}else{
					hourset = ""+hours;
				}
				toSet = hourset+":"+toSet;
			}
			//====================
			try {
				runOnUiThread(new Runnable() {
					public void run() {
						timerText.setText(toSet);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pauseMyTimer(){
		running = false;
		if(locCoodList.size() > 0){
			markerPositions.add(locCoodList.size()-1);
		}
		updateMarkersOnMap();
		myTimer.cancel();
	}
	
	public void stopMyTimer(){
		running = false;
		oldLat = 0.0;
		oldLng = 0.0;
		try {
			if(myTimer != null){
				myTimer.cancel();
				myTimer.purge();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		seconds = 0;
		minutes = 0;
		hours = 0;
		toSet = "00:00";
		hourset = "00";
		minset = "00";
		secset = "00";
		
		timerText.setText("00:00");
		milesText.setText("0.00");
		paceText.setText("00:00");
		caloriesText.setText("0");
	}
	//==============================
	
	private void handleGoRunClick(){
		if(loclat != 0 && loclng != 0){
			try {
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loclat, loclng),17));
				//make start btn visible
				//handleVisibilityOfButtons(1);
				goRun.setVisibility(View.GONE);
				handleStartDelay.postDelayed(null,1500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Toast.makeText(GoRunFragmentActivity.this,"Unable to find your Location,check GPS and Internet connection.",Toast.LENGTH_SHORT).show();
		}
	}
	
	/*private void handleStartClick(){
		//make slide btn visible
		handleVisibilityOfButtons(2);
		playStartSound();
		startMyTimer();
	}*/
	
	Handler handleStartDelay = new Handler(){
		public void handleMessage(android.os.Message msg) {
			handleStart();
		}
	};
	
	public void handleStart(){
		handleVisibilityOfButtons(2);
		playStartSound();
		startMyTimer();
	}
	
	private void handleResumeClick(){
		//make slide btn visible
		handleVisibilityOfButtons(2);
		updateMarkersOnMap();
		startMyTimer();
	}
	
	private void handleStopClick(){
		Intent in = new Intent(this,AddPictureActivity.class);
		in.putExtra(GlobalConstants.ADD_RACEDET_DISTANCE,milesText.getText().toString()+"");
		in.putExtra(GlobalConstants.ADD_RACEDET_DURATION,timerText.getText().toString()+"");
		in.putExtra(GlobalConstants.ADD_RACEDET_CALORIES,caloriesText.getText().toString()+"");
		in.putExtra(GlobalConstants.ADD_RACEDET_CURRENT_PACE,paceText.getText().toString()+"");
		
		ArrayList<String> llist = new ArrayList<String>();
		int upto = locCoodList.size();
		for(int i=0;i<upto;i++){
			String s = locCoodList.get(i).latitude+","+locCoodList.get(i).longitude;
			llist.add(s);
		}
		global.setRunningTrackedPath(llist);
	
		//take user to final screens
		stopMyTimer();
		
		//remove route and markers....
		if(startMarker != null){
			startMarker.remove();
		}
		for(int i=0;i<mapMarkers.size();i++){
			mapMarkers.get(i).remove();
		}
		
		if(trackPolyline != null){
			trackPolyline.remove();
		}
		
		locCoodList.clear();
		routeOptions = new PolylineOptions();
		trackPolyline = null;
		startMarker = null;
		markerPositions.clear();
		mapMarkers.clear();
		//make GoRun visible
		handleVisibilityOfButtons(0);
		
		//start activities with all data as extras
		startActivity(in);
	}
	
	private void playStartSound() {
		//start btn sound
		try {
			final MediaPlayer mp = new MediaPlayer();
	        if(mp.isPlaying())
	        {  
	            mp.stop();
	            mp.reset();
	        } 
            AssetFileDescriptor afd;
            afd = getAssets().openFd("start_workout.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleVisibilityOfButtons(int which){
		//which == 0 > GoRun visible all else gone  
		//which == 1 > start visible all else gone
		//which == 2 > slide visible all else gone		
		//which == 3 > resume stop visible all else gone
		
		//At first hide all and make only required one visible
		goRun.setVisibility(View.GONE);
		startBtn.setVisibility(View.GONE);
		slideBtn.setVisibility(View.GONE);
		resumeStopContainer.setVisibility(View.GONE);
		
		if(which == 0){
			goRun.setVisibility(View.VISIBLE);
		}else if(which == 1){
			startBtn.setVisibility(View.VISIBLE);
		}else if(which == 2){
			slideBtn.setVisibility(View.VISIBLE);
		}else if(which == 3){
			resumeStopContainer.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if(myTimer != null){
				myTimer.cancel();
				myTimer = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Data Structure for saving coordinates.
	class Coordinates {
		double lat=0,lng=0;
		public void setLat(double lt){
			lat = lt;
		}
		public void setLng(double ln){
			lng = ln;
		}
	
		public double getLat(){
			return lat;
		}
		public double getLng(){
			return lng;
		}
	}
	//=========================================
}