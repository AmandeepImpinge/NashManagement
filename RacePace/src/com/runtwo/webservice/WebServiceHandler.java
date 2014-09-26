package com.runtwo.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import android.content.Context;
import android.util.Log;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.Globals;
import com.runtwo.utils.Utils;

public class WebServiceHandler {


	//REGISTER SERVICE ==================================================
	public static String getRegisteredOnServer(Context c,String name,String email,String pass,String deviceId){
		Globals global;
		String result ="error";
		global = (Globals)c.getApplicationContext();

		try {
			JSONObject obj = new JSONObject();
			obj.accumulate(GlobalConstants.USERNAME,""+name);
			obj.accumulate(GlobalConstants.EMAIL,""+email);
			obj.accumulate(GlobalConstants.PASSWORD,""+pass);
			obj.accumulate(GlobalConstants.DEVICE_TYPE,"2");
			obj.accumulate(GlobalConstants.ACCOUNT_TYPE,"1");
			obj.accumulate(GlobalConstants.DEVICE_TOKEN,""+deviceId);
			//Log.e("dev id service"," : "+deviceId);

			//Empty strings to be sent
			obj.accumulate(GlobalConstants.FBUSERID,"");
			obj.accumulate("dob","");
			obj.accumulate("gender","");
			obj.accumulate("weight","");
			obj.accumulate("height","");
			obj.accumulate("tshirt_size","");
			obj.accumulate("shoes_size","");
			obj.accumulate("goals","");
			obj.accumulate("motto","");
			obj.accumulate("img_name","");
			obj.accumulate("profile_image","");

			obj.accumulate("running_team","");
			obj.accumulate("fav_shoe","");
			obj.accumulate("fav_distance","");
			obj.accumulate("city","");
			obj.accumulate("state","");
			obj.accumulate("zip_code","");
			obj.accumulate("phone_num","");
			obj.accumulate("address","");
			obj.accumulate("twuser_id","");

			Log.e("json ","Values "+obj);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.REGISTERATION_URL);

				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

				response = client.execute(post);

				if(response!=null){
					result = Utils.convertToString(response.getEntity().getContent());
					Log.e("Result is new ",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result);

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.USER_DETAILS);
						result = ServiceResponseParser.registerResponseParser(global,dataar);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//====================================

	//Get Logged In service==========================================================
	public static String getLoggedIn(Context c,String email,String pass,String deviceId){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.EMAIL,""+email);
			obj.put(GlobalConstants.PASSWORD,""+pass);
			obj.put(GlobalConstants.DEVICE_TYPE,"2");
			obj.put(GlobalConstants.ACCOUNT_TYPE,"1");
			obj.put(GlobalConstants.DEVICE_TOKEN,""+deviceId);

			//Empty values to be sent
			obj.put(""+GlobalConstants.FBUSERID,"");

			Log.e("json ","Values "+obj);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.LOGIN_URL);

				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity()); //Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.loginResponseParser(global, dataar);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//======================================================================

	//forgot Password Service ==============================================
	public static String forgotPassworService(Context c,String email){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.EMAIL,""+email);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.FORGOT_PASSWORD_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity()); //Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						result = "true";
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//=========================================================================


	//Get Community Service ==============================================
	public static String getCommunityService(Context c){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.GET_COMMUNITY_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.communityParser(global, dataar);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//=========================================================================

	//Add Community Service ==============================================
	public static String addCommunityService(Context c,String communityName){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.CATEGORY_NAME,communityName);
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.ADD_COMMUNITY_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						JSONObject jj = dataar.getJSONObject(0);

						HashMap<String,String> map = new HashMap<String, String>();
						map.put(GlobalConstants.CATEGORY_ID,""+jj.getString(GlobalConstants.CATEGORY_ID));
						map.put(GlobalConstants.CATEGORY_NAME,""+jj.getString(GlobalConstants.CATEGORY_NAME));
						map.put(GlobalConstants.USER_ID,""+jj.getString(GlobalConstants.USER_ID));

						global.communityList.add(map);
						result = "true"; 
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//=========================================================================

	//Get Followers Feed====================================
	public static String getFollowerFeedService(Context c){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.GET_FOLLOWER_FEED_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.followerFeedParser(global, dataar);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//Get Followers====================================
	public static String getFollowersService(Context c,String type){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.FOLLOWERS_TYPE,""+type);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.GET_FOLLOWERS_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.followersParser(global, dataar,type);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//Like Comment on Follower Feed====================================
	public static String likeCommentService(Context c,String id,String type,String comment){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.FEED_ID,id);
			obj.put(GlobalConstants.LIKE_COMMENT_TYPE,""+type);
			obj.put(GlobalConstants.LIKE_COMMENT_DESC,comment);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.LIKE_COMMENT_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						result = "true";
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//get Like Comment on Follower Feed====================================
	public static String getLikeCommentService(Context c,String feedid,String type){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.FEED_ID,feedid);
			obj.put(GlobalConstants.LIKE_COMMENT_TYPE,""+type);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.GET_LIKE_COMMENT_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.likeCommentParser(global, dataar,type);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	//==============================


	//search Friends and Followers====================================
	public static String searchFriendsFollowersService(Context c,String catid,String type,String searchtext){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.CATEGORY_ID,catid);
			obj.put(GlobalConstants.LIKE_COMMENT_TYPE,""+type);
			obj.put(GlobalConstants.SEARCH_TEXT,""+searchtext);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.SEARCH_FRIENDS_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());//Get the data in the entity
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.searchFriendsParser(global, dataar,type);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//GET DASHBOARD CONTENTS====================================
	public static String getDashboardContentsService(Context c){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.GET_DASHBOARD_CONTENT_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
						result = ServiceResponseParser.dashboardContentParser(global,dataar);
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//POST FEED SERVICE====================================
	public static String postFeedService(Context c,String imgname,String img,String desc){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		//{"access_token":"LDMLL9MGCOGK0KAC0OSR","img_name":"postpic.png","profile_image":"postpic.png",
		//"post_desc":"Test desc","feed_type":"post"}

		try {
			JSONObject obj = new JSONObject();


			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.POST_IMAGE_NAME,imgname);
			obj.put(GlobalConstants.POST_PROFILE_IMAGE,""+img);
			obj.put(GlobalConstants.POST_DESC,""+desc);
			obj.put(GlobalConstants.POST_FEED_TYPE,GlobalConstants.POST_KEY);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.POST_USER_FEED_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						result = "true";
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	//ADD FRIENDS/FOLLOWER==========
	public static String addFollowersService(Context c,String friendId,String isFollower){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";

		
 		
		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.ADD_FOLLOWERS_FRIEND_ID,friendId);
			obj.put(GlobalConstants.ADD_FOLLOWERS_IS_FOLLOWER,isFollower);

			HttpClient client = new DefaultHttpClient();
			HttpResponse response;

			try {
				HttpPost post = new HttpPost(GlobalConstants.ADD_FOLLOWERS_URL);
				List<NameValuePair> list = new ArrayList<NameValuePair>(1);
				list.add(new BasicNameValuePair("JsonObject",obj.toString()));
				post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
				response = client.execute(post);

				if(response!=null){
					result = EntityUtils.toString(response.getEntity());
					Log.e("Result is",""+result);
				}

				if(result.length() > 0){
					JSONObject job = new JSONObject(result); 

					String code = ""+job.getString(GlobalConstants.CODE);
					if(code.equals("0")){
						result = "false";
						global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
					}else if(code.equals("1")){
						result = "true";
					}
				}else{
					result = "error";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================

	
	
	//GET ACHIEVEMENTS====================================
	public static String getAchievementService(Context c,String achid){
		Globals global = (Globals)c.getApplicationContext();
		String result ="error";
		
		try {
			JSONObject obj = new JSONObject();
			obj.put(GlobalConstants.ACCESS_TOKEN,""+global.getAccessToken());
			obj.put(GlobalConstants.GET_ACHIEVEMENT_ID,achid);
			
			HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            
            try {
                HttpPost post = new HttpPost(GlobalConstants.GET_ACHIEVEMENT_URL);
                List<NameValuePair> list = new ArrayList<NameValuePair>(1);
                list.add(new BasicNameValuePair("JsonObject",obj.toString()));
                post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
                response = client.execute(post);
                
                if(response!=null){
                	result = EntityUtils.toString(response.getEntity());//Get the data in the entity
                	Log.v("Result is",""+result);
                }
                
                if(result.length() > 0){
                	JSONObject job = new JSONObject(result); 
                	
                	String code = ""+job.getString(GlobalConstants.CODE);
                	if(code.equals("0")){
                		result = "false";
                		global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
                	}else if(code.equals("1")){
                		JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
                		result = ServiceResponseParser.achievementParser(global, dataar);
                	}
                }else{
                	result = "error";
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//==============================
	

	// Update User Details ====================

	public static String updateUserDetails(Context c, JSONObject jObj) {
		
		String result ="error";
		Globals global = (Globals)c.getApplicationContext();
		Log.e("json ","Values "+jObj);

		/*JSONObject obj = new JSONObject();
			obj.accumulate(GlobalConstants.ACCESS_TOKEN,""+accessToken);	
			obj.accumulate(GlobalConstants.USERNAME,""+userName);			
			obj.accumulate(GlobalConstants.PASSWORD,""+password);*/				

		//Empty strings to be sent

		/*obj.accumulate("dob","");
			obj.accumulate("gender","");
			obj.accumulate("weight","");
			obj.accumulate("height","");
			obj.accumulate("tshirt_size","");
			obj.accumulate("shoes_size","");
			obj.accumulate("goals","");
			obj.accumulate("motto","");
			obj.accumulate("img_name","");
			obj.accumulate("profile_image","");*/

		/*obj.accumulate("running_team","");
			obj.accumulate("fav_shoe","");
			obj.accumulate("fav_distance","");
			obj.accumulate("city","");
			obj.accumulate("state","");
			obj.accumulate("zip_code","");
			obj.accumulate("phone_num","");
			obj.accumulate("address","");
			obj.accumulate("twuser_id","");*/

		HttpClient client = new DefaultHttpClient();
		HttpResponse response;

		try {
			
			HttpPost post = new HttpPost(GlobalConstants.UPDATE_USER_DETAIL_URL);

			List<NameValuePair> list = new ArrayList<NameValuePair>(1);
			list.add(new BasicNameValuePair("JsonObject",jObj.toString()));
			post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));

			response = client.execute(post);

			if(response!=null){
				result = Utils.convertToString(response.getEntity().getContent());
				Log.e("Result is new ",""+result);
			}

			if(result.length() > 0){
				JSONObject job = new JSONObject(result);

				String code = ""+job.getString(GlobalConstants.CODE);
				if(code.equals("0")){
					result = "false";
					global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
				}else if(code.equals("1")){
					
					JSONArray dataar = job.getJSONArray(GlobalConstants.DATA);
					Log.v("dataar", ""+dataar);
					result = ServiceResponseParser.userDetailsParser(global, dataar);				
					
					//result = "true";
					//global.setMessageOfResponse(job.getString(GlobalConstants.MESSAGE));
				}
			}else{
				result = "error";
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

}
