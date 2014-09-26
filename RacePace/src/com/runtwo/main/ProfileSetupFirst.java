package com.runtwo.main;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.lf;
import com.runtwo.constants.GlobalConstants;
import com.runtwo.webservice.WebServiceHandler;

public class ProfileSetupFirst extends Activity implements OnClickListener /*Fragment*/{	

	EditText eFname, eLname, eEmail, ePass, eCountry, eCity, eAddress, eZip,
	eRunningTeam, eFavQuote, eDOB, eGender, eWeight, eHeight, eTsize, eShoeSize;

	TextView tSave, tCancel;
	ProgressDialog mProgressDialog;

	Globals global;

	//View rootView;

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//container = (LinearLayout)inflater.inflate(R.layout.profile_setup_first,null);
		rootView = inflater.inflate(R.layout.profile_setup_first, container,false);

		getProfileDetails();
		editProfile();

		return rootView;
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_setup_first);

		global = (Globals)this.getApplicationContext();

		initializeUI();
		getProfileDetails();
	}	

	private void getProfileDetails() {
		String fName = "";
		String lName = "";

		Globals global =  (Globals)this.getApplicationContext();	

		HashMap<String, String> userDetails = global.getUserData();
		if(userDetails.size()!=0) {

			String userName = userDetails.get(GlobalConstants.USERNAME);
			Log.v("userName", userName);
			
			//String password = userDetails.get(GlobalConstants.PASSWORD);
			//Log.v("password", password);

			String[] parts = userName.split(" ");
			fName = parts[0]; 
			lName = parts[1]; 

			eFname.setText(fName);
			eLname.setText(lName);
			eEmail.setText(userDetails.get(GlobalConstants.EMAIL));			
			//ePass.setText(password);

			String country = userDetails.get(GlobalConstants.STATE);			
			if(country != null) {
				Log.v("country", country);
				eCountry.setText(country);
			}

			String city = userDetails.get(GlobalConstants.CITY);			
			if(city != null) {
				Log.v("city", city);
				eCity.setText(city);
			}

			String address = userDetails.get(GlobalConstants.ADDRESS);			
			if(address != null) {
				Log.v("address", address);
				eAddress.setText(address);				
			}

			String zipCode = userDetails.get(GlobalConstants.ZIP_CODE);		
			if(zipCode != null) {
				Log.v("zipCode", zipCode);
				eZip.setText(zipCode);
			}

			String runningTeam = userDetails.get(GlobalConstants.RUNNING_TEAM);			
			if(runningTeam != null) {
				Log.v("runningTeam", runningTeam);
				eRunningTeam.setText(runningTeam);
			}

			String favQ = userDetails.get(GlobalConstants.FAV_DIST);
			if(favQ != null) {
				Log.v("favQ", favQ);
				eFavQuote.setText(favQ);
			}

			String dob = userDetails.get(GlobalConstants.DOB);			
			if(dob != null) {
				Log.v("dob", dob);
				eDOB.setText(dob);
			}

			String gender = userDetails.get(GlobalConstants.GENDER);			
			if(gender != null) {
				Log.v("gender", gender);
				eGender.setText(gender);
			}

			String weight = userDetails.get(GlobalConstants.WEIGHT);			
			if(weight != null) {
				Log.v("weight", weight);
				eWeight.setText(weight);
			}

			String height = userDetails.get(GlobalConstants.HEIGHT);			
			if(height != null) {
				Log.v("height", height);
				eHeight.setText(height);
			}

			String tSize = userDetails.get(GlobalConstants.TSHIRT_SIZE);			
			if(tSize != null) {
				Log.v("tSize", tSize);
				eTsize.setText(tSize);
			}

			String shoeSize = userDetails.get(GlobalConstants.SHOE_SIZE);			
			if(shoeSize != null) {
				Log.v("shoeSize", shoeSize);
				eShoeSize.setText(shoeSize);
			}		

			/*String password = userDetails.get(GlobalConstants.PASSWORD_SAVE);
			Log.v("password", password);
			if(password != null) {
				((EditText)rootView.findViewById(R.id.pass)).setText(password);
			}*/		
		}		
	}	

	public JSONObject saveProfile() {

		String username = (eFname.getText().toString()) +" "+ (eLname.getText().toString());
		JSONObject obj = new JSONObject();

		try{			
			obj.accumulate(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());	
			obj.accumulate(GlobalConstants.USERNAME,""+username);			
			obj.accumulate(GlobalConstants.PASSWORD,""+(ePass.getText().toString()));		
			//obj.accumulate(GlobalConstants.EMAIL,""+(eEmail.getText().toString()));		

			obj.accumulate("dob",(eDOB.getText().toString()));
			obj.accumulate("gender",(eGender.getText().toString()));
			obj.accumulate("weight",(eWeight.getText().toString()));
			obj.accumulate("height",(eHeight.getText().toString()));
			obj.accumulate("tshirt_size",(eTsize.getText().toString()));
			obj.accumulate("shoes_size",(eShoeSize.getText().toString()));			
			obj.accumulate("goals","");
			obj.accumulate("motto","");
			obj.accumulate("img_name","");
			obj.accumulate("profile_image","");		
			obj.accumulate("running_team",(eRunningTeam.getText().toString()));
			obj.accumulate("fav_shoe",(eFavQuote.getText().toString()));
			obj.accumulate("fav_distance","");
			obj.accumulate("city",(eCity.getText().toString()));
			obj.accumulate("state",(eCountry.getText().toString()));
			obj.accumulate("zip_code",(eZip.getText().toString()));
			obj.accumulate("phone_num","");
			obj.accumulate("address",(eAddress.getText().toString()));
			obj.accumulate("timeline_image","");
			obj.accumulate("timeline_name","");

		}
		catch(JSONException j) {
			j.printStackTrace();
		}
		return obj;
	}	

	private void initializeUI(){	

		eFname = (EditText)findViewById(R.id.first_name);
		eLname = (EditText)findViewById(R.id.last_name);
		eEmail = (EditText)findViewById(R.id.email_address);
		ePass = (EditText)findViewById(R.id.pass);
		eCountry = (EditText)findViewById(R.id.country_name);
		eCity = (EditText)findViewById(R.id.city_name);
		eAddress = (EditText)findViewById(R.id.address);
		eZip = (EditText)findViewById(R.id.zip_code);
		eRunningTeam = (EditText)findViewById(R.id.running_team);
		eFavQuote = (EditText)findViewById(R.id.fav_quote);
		eDOB = (EditText)findViewById(R.id.dob);
		eGender = (EditText)findViewById(R.id.sex);
		eWeight = (EditText)findViewById(R.id.weight);
		eHeight = (EditText)findViewById(R.id.height);
		eTsize = (EditText)findViewById(R.id.tShirt_size);
		eShoeSize = (EditText)findViewById(R.id.shoe_size);		 

		tSave = (TextView)findViewById(R.id.save);
		tCancel = (TextView)findViewById(R.id.cancel);

		tCancel.setOnClickListener(this);
		tSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.cancel:
			finish();
			break;    

		case R.id.save:
			//saveProfile();
			new CallService().execute();
			break;
		}		
	}

	private class CallService extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			new ProgressDialog(ProfileSetupFirst.this);
			mProgressDialog = ProgressDialog.show(ProfileSetupFirst.this,"","Loading...");
		}

		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				JSONObject jObj = saveProfile();
				result = WebServiceHandler.updateUserDetails(ProfileSetupFirst.this, jObj);
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
				Toast.makeText(ProfileSetupFirst.this,"Profile Updated Succesfully",Toast.LENGTH_SHORT).show();
				finish();
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(ProfileSetupFirst.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(ProfileSetupFirst.this,"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}

	}
}
