package com.runtwo.main;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.utils.Utils;
import com.runtwo.webservice.WebServiceHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	
	TextView mForgotPassText,mNewUserText;
	EditText mUserNameEdit,mPasswordEdit;
	RelativeLayout loginBtn,fbBtn,twitter_btn;
	String username,password;
	ProgressDialog mProgressDialog;
	CheckBox remember_check;
	TextView remember_txt;
	Globals global;
	String deviceid = "";
	SharedPreferences prefs;
	AlertDialog ad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		global = (Globals)getApplicationContext();
		prefs = getSharedPreferences(GlobalConstants.PREF_NAME,MODE_PRIVATE);
		
		initViews();
		applyClickListenersToViews();
		
		/*mUserNameEdit.setText("as@gmail.com");
		mPasswordEdit.setText("qwerty");*/
		
		deviceid = global.getDeviceId();
		
	}
	
	/*
	 * To intialize the views to be used in UI
	 */
	private void initViews(){
		mUserNameEdit = (EditText)findViewById(R.id.username_field);
		mPasswordEdit = (EditText)findViewById(R.id.password_field);
		loginBtn = (RelativeLayout)findViewById(R.id.login_btn);
		fbBtn = (RelativeLayout)findViewById(R.id.fb_btn);
		twitter_btn = (RelativeLayout)findViewById(R.id.twitter_btn);
		mForgotPassText = (TextView)findViewById(R.id.forgot_text);
		mNewUserText = (TextView)findViewById(R.id.new_user_text);
		remember_check = (CheckBox)findViewById(R.id.remember_me_check);
		remember_txt = (TextView)findViewById(R.id.remember_text);
	}
	
	private void applyClickListenersToViews(){
		loginBtn.setOnClickListener(this);
		fbBtn.setOnClickListener(this);
		twitter_btn.setOnClickListener(this);
		mForgotPassText.setOnClickListener(this);
		mNewUserText.setOnClickListener(this);
		remember_txt.setOnClickListener(this);
	}
	
	/*
	 * Validate the fields of username,password and 
	 * call the web service for login 
	 */
	private void validateAndProceed(){
		
		mUserNameEdit.setError(null);
		mPasswordEdit.setError(null);
		
		boolean ok = true;
		username = mUserNameEdit.getText().toString().trim();
		password = mPasswordEdit.getText().toString().trim();
		
		if(username.length() == 0){
			ok = false;
			mUserNameEdit.setError("Email required.");
		}else if(!Utils.isEmailValid(username)){
			ok = false;
			mUserNameEdit.setError("Enter valid email.");
		}
		
		if(password.length() == 0){
			ok = false;
			mPasswordEdit.setError("Password is required.");
		}
		
		if(ok){
			//call intent to next screen
			new CallLoginService().execute("");
		}
	}

	class CallLoginService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(LoginActivity.this).show(LoginActivity.this,"","Logging in...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getLoggedIn(LoginActivity.this,username,password,deviceid);
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
				Toast.makeText(LoginActivity.this,"Login Successfull.",Toast.LENGTH_SHORT).show();
				
				if(remember_check.isChecked()){
					Editor edit = prefs.edit();
					edit.putString(GlobalConstants.USERNAME_SAVE,""+username);
					edit.putString(GlobalConstants.PASSWORD_SAVE,""+password);
					edit.commit();
				}
				
				startActivity(new Intent(LoginActivity.this,MainTabActivity/*TabHostActivity*/.class));
				LoginActivity.this.finish();
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(LoginActivity.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(LoginActivity.this,"Error Connecting to server, Please try again.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_btn:
				validateAndProceed();
			break;
		case R.id.fb_btn: 	
			
			break;
		case R.id.twitter_btn:
			
			break;
		case R.id.forgot_text: 	
			 openForgotDialog();
			break;
		case R.id.remember_text:
			if(remember_check.isChecked()){
				remember_check.setChecked(false);
			}else{
				remember_check.setChecked(true);
			}
			break;
		case R.id.new_user_text: 
			LoginActivity.this.finish();
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			break;
		}
	}
	
	
	public void openForgotDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		builder.setTitle("Forgot Password");
		builder.setMessage("Please enter the email used for Registration.");
		
		final EditText ed = new EditText(this);
		ed.setTextColor(Color.BLACK);
		ed.setTextSize(12);
		ed.setSingleLine();
		ed.setGravity(Gravity.CENTER_VERTICAL);
		ed.setPadding(5,5,5,5);
		
		builder.setView(ed);
		
		builder.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ad.dismiss();
			}
		});
		
		builder.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ed.setError(null);
				String email = ed.getText().toString().trim();
				if(email.length() > 0){
					if(Utils.isEmailValid(email)){
						ad.dismiss();
						new ForgotPasswordService(email).execute("");
					}else{
						ed.setError("Email invalid.");
					}
				}else{
					ed.setError("Email invalid.");
				}
			}
		});
		
		ad = builder.create();
		ad.show();
	}
	
	class ForgotPasswordService extends AsyncTask<String,Integer,String>{
		String email = "";
		public ForgotPasswordService(String ema){
			email = ema;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(LoginActivity.this).show(LoginActivity.this,"","Sending Password...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.forgotPassworService(LoginActivity.this,email);
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
				Toast.makeText(LoginActivity.this,"Email sent Successfully.",Toast.LENGTH_SHORT).show();
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(LoginActivity.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(LoginActivity.this,"Error Connecting to server, Please try again.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mUserNameEdit.getWindowToken(), 0);
		return super.dispatchTouchEvent(ev);
	}
}