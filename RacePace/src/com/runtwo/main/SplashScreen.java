package com.runtwo.main;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.main.LoginActivity.CallLoginService;
import com.runtwo.webservice.WebServiceHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class SplashScreen extends Activity{

	private final int DELAY_MILLISEC = 2000;
	SharedPreferences prefs;
	String username = "";
	String password = "";
	String devid = "";
	ProgressDialog mProgressDialog;
	Globals global;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	
		prefs = getSharedPreferences(GlobalConstants.PREF_NAME,MODE_PRIVATE);
		
		Display display = getWindowManager().getDefaultDisplay();
		int width = 480;
		int height = 800;
		System.out.println("Manikkkkk");
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
		}else{
			height = display.getHeight();
			width = display.getWidth();
		}
		
		global = (Globals)getApplicationContext();
		global.setDeviceWidth(width);
		global.setDeviceHeight(height);
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		devid = telephonyManager.getDeviceId();
		Log.e("dev id",""+devid);
		global.setDeviceId(devid);
		
		handleNext.postDelayed(null,DELAY_MILLISEC);
	}
	
	private Handler handleNext = new Handler(){
		public void handleMessage(Message msg) {
			username = prefs.getString(GlobalConstants.USERNAME_SAVE,"");
			password = prefs.getString(GlobalConstants.PASSWORD_SAVE,"");
			if(username.length() > 0){
				new CallLoginService().execute("");
			}else{
				startActivity(new Intent(SplashScreen.this,LoginActivity.class));
				SplashScreen.this.finish();
			}
		}
	};
	
	
	class CallLoginService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(SplashScreen.this).show(SplashScreen.this,"","Logging in...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getLoggedIn(SplashScreen.this,username,password,devid);
				//username,password;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			
			if(result.equalsIgnoreCase("true")){
				Toast.makeText(SplashScreen.this,"Login Successfull.",Toast.LENGTH_SHORT).show();
				startActivity(new Intent(SplashScreen.this,MainTabActivity/*TabHostActivity*/.class));
				SplashScreen.this.finish();
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(SplashScreen.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(SplashScreen.this,"Error in Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}