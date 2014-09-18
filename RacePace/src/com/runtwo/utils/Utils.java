package com.runtwo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.runtwo.main.Globals;

import android.content.Context;
import android.location.Location;
import android.util.FloatMath;
import android.util.Log;

public class Utils {

	public static boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static String convertToString(InputStream content) throws Exception {

		InputStreamReader inputStreamReader = new InputStreamReader(content);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String s = "";
		StringBuffer buffer = new StringBuffer();
		while ((s = bufferedReader.readLine()) != null) {
			buffer.append(s);
		}

		return buffer.toString();
	}
	
	
	public static float getDistance(Globals global,double startlat,double startlng,double endlat,double endlng){
		float results[] = new float[10];
		Location.distanceBetween(startlat,startlng,endlat, endlng,results);
		float miles = 0;
		float dist = results[0];
		Log.e("original dist","meter : "+dist);
		
		//if the distance covered is at least 1 meter
		//then only calculate and plus with the original distance
		if(dist >= 1){
			/*
			 * Approximate difference found per meter in distance 
			 * calculated online and through this method is 0.04012 .
			 * so to overcome this subtract the distance with 
			 * number of meters to generate it with more accuracy
			 *  
			 * ** But only works for distances in small measures.....
			 *
			 * */
			
			float diff = 0.04012f;
			
			try {
				if(dist > diff){
					int no_of_meter = Integer.parseInt((""+dist).substring(0,(""+dist).indexOf(".")));
					dist = dist - (no_of_meter*diff); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Log.e("meter after","subtraction : "+dist);
			
			global.setDistanceInMeters(dist);
			
			//mi = m * 0.00062137
			miles = (float) (dist*0.00062137);
			Log.e("original","miles : "+miles);
			miles = precision(2,miles);
			
			String mm = ""+miles;
			if(mm.length() > 4){
				mm = mm.substring(0,4);
			}
			
			//
			try {
				miles = Float.parseFloat(mm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return miles;
	}

	public static Float precision(int decimalPlace, Float d) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
	
	public static String convertToMinutesPerMile(float meterpersec){
		String finalResult = "00:00";
		
		float meterPerMin = meterpersec*60;
		Log.e("meterper min",""+meterPerMin);
		double milesPerMin = (meterPerMin/1609.344);//1609.344 meters in one mile
		Log.e("miles per min",""+milesPerMin);
		double timeValue = 1/milesPerMin;
		Log.e("minutes per mile",""+timeValue);
		
		String totalTime = ""+timeValue;
		
		try {
			//Calculate minutes===
			
			String minutes = totalTime.substring(0,totalTime.indexOf("."));
			int min = Integer.parseInt(minutes);
			//========
	
			//Calculate seconds========
			String secVal = totalTime.substring(totalTime.indexOf("."),totalTime.length());
			secVal = "0."+secVal;
			
			float secflo = Float.parseFloat(secVal);
			secflo = secflo*60;//convert into seconds
			
			String secon = (""+secflo).substring(0,2);
			
			int seconds = Integer.parseInt(secon);
			if(seconds >= 60){
				seconds = 0;
				min += 1;
			}
			//==========================
			
			if(min < 10){
				finalResult = "0"+min;
			}else{
				finalResult = ""+min;
			}
			
			if(seconds < 10){
				finalResult += "0"+seconds;
			}else{
				finalResult += ""+seconds;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
	}
	
	public static String convertToKilometerPerHour(float meterpersec){
		String result = "";
		
		float meterPerHour = meterpersec*3600;
		Log.e("meterper hour",""+meterPerHour);
		float kilometerperhour = (meterPerHour/1000);//1000 meters in one km
		result = ""+precision(2,kilometerperhour);
		return result;
	}
	
	
	public static float getBurnedCaloriesCount(Context c,float currentpace,float weight,float distance){
		//timeInSec;
		//currentPace in meter/sec = distanceLongLat/timeInSec;
		//float  MET_value = 7.50;  // Pre define value for Running case
		//currentPace =  meter/min    
		//float Weight_MET = weight * MET_value;
		//float WeightResult = Weight_MET  /currentPace;
		//float CaloriesBurn = WeightResult*distance;
	
		
		//convert it into meter/min==========================
		currentpace = currentpace*60;
		weight = weight * 7.50f; 		//float  MET_value = 7.50f;  // Pre define value for Running case
		float weightResult = weight / currentpace;
		float caloriesBurned =  weightResult * distance;
		return caloriesBurned;
	}

	//Get current Speed on the basis of Distance over time
	public static float getSpeed(float distance, long diff) {
		float time = diff/1000; //conversion of millisec to sec
		
		float speed = distance/time; // distance in meters
		
		return speed;  // result is in the units of meter/sec
	}

}