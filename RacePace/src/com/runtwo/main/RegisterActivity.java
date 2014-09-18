package com.runtwo.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.runtwo.main.R;
import com.runtwo.utils.Utils;
import com.runtwo.webservice.WebServiceHandler;

public class RegisterActivity extends Activity{
	
	
	ImageView mUserimg;
	EditText mFirstNameEdit,mLastNameEdit,mEmailEdit,mPasswordEdit,mConfirmPasswordEdit;
	RelativeLayout signup_btn,cancel_btn;
	ProgressDialog mProgressDialog;
	String deviceId = "";
	Globals global;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	
		initViews();
		
		global = (Globals)getApplicationContext();
		
		signup_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				validateAndProceed();
			}
		});
		
		cancel_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RegisterActivity.this.finish();
				startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
			}
		});
		
		deviceId = global.getDeviceId();
		//Log.e("dev id oncreate"," : "+deviceId);
	}	 

	/*Initializing the views*/
	private void initViews(){
		mFirstNameEdit = (EditText)findViewById(R.id.first_name);
		mLastNameEdit = (EditText)findViewById(R.id.last_name);
		mEmailEdit = (EditText)findViewById(R.id.email_address);
		mPasswordEdit = (EditText)findViewById(R.id.password);
		mConfirmPasswordEdit = (EditText)findViewById(R.id.confirm_pass);
		
		/*mFirstNameEdit.setText("Amandeep");
		mLastNameEdit.setText("Singh");
		mEmailEdit.setText("aman@gmail.com");
		mPasswordEdit.setText("qwerty");
		mConfirmPasswordEdit.setText("qwerty");*/
		
		signup_btn = (RelativeLayout)findViewById(R.id.register_btn);
		cancel_btn = (RelativeLayout)findViewById(R.id.cancel_btn);
	}
	
	
	public void validateAndProceed(){
		boolean ok = true;
		
		mFirstNameEdit.setError(null);
		mLastNameEdit.setError(null);
		mEmailEdit.setError(null);
		mPasswordEdit.setError(null);
		mConfirmPasswordEdit.setError(null);
		
		if(mFirstNameEdit.getText().toString().isEmpty()){
			ok = false;
			mFirstNameEdit.setError("First name is required.");
		}else if(mLastNameEdit.getText().toString().isEmpty()){
			ok = false;
			mLastNameEdit.setError("Last name is required.");
		}else if(mEmailEdit.getText().toString().isEmpty()){
			ok = false;
			mEmailEdit.setError("Email is required.");
		}else if(!Utils.isEmailValid(mEmailEdit.getText().toString())){
			ok = false;
			mEmailEdit.setError("Email is not valid.");
		}else if(mPasswordEdit.getText().toString().isEmpty()){
			ok = false;
			mPasswordEdit.setError("Password is required.");
		}else if(mPasswordEdit.getText().toString().length() < 5){
			ok = false;
			mPasswordEdit.setError("Min 5 characters required.");
		}else if(mConfirmPasswordEdit.toString().isEmpty()){
			ok = false;
			mConfirmPasswordEdit.setError("Password is required.");
		}else {
			String pass = mPasswordEdit.getText().toString();
			String confirmpass = mConfirmPasswordEdit.getText().toString();
			if(!pass.equals(confirmpass)){
				ok = false;
				mPasswordEdit.setError("Password did not match.");
			}
		}
		
		if(ok){
			new CallRegisterService().execute("");
		}
	}
	
	class CallRegisterService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(RegisterActivity.this).show(RegisterActivity.this,"","Registering...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				String name = mFirstNameEdit.getText().toString().trim()+" "+mLastNameEdit.getText().toString().trim();
				String email = mEmailEdit.getText().toString().trim();
				String password = mPasswordEdit.getText().toString().trim();
				result = WebServiceHandler.getRegisteredOnServer(RegisterActivity.this,name,email,password,global.getDeviceId());
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
				Toast.makeText(RegisterActivity.this,"Successfully Registered...",Toast.LENGTH_SHORT).show();
				startActivity(new Intent(RegisterActivity.this,MainTabActivity.class));
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(RegisterActivity.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(RegisterActivity.this,"Error Connecting to server, Please try again.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mFirstNameEdit.getWindowToken(), 0);
		
		return super.dispatchTouchEvent(ev);
	}
	
}