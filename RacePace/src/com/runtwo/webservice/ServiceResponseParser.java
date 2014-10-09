package com.runtwo.webservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.Settings.Global;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.Globals;

public class ServiceResponseParser {

	// Register Response Parsing
	public static String registerResponseParser(Globals global, JSONArray data) {
		String result = "true";
		try {
			JSONObject job = data.getJSONObject(0);

			String fbuser_id = job.getString(GlobalConstants.FBUSERID);
			String userstatus = job.getString(GlobalConstants.USER_STATUS);
			String user_id = job.getString(GlobalConstants.USER_ID);
			String access_token = job.getString(GlobalConstants.ACCESS_TOKEN);
			String acc_type = job.getString(GlobalConstants.ACCOUNT_TYPE);
			String email = job.getString(GlobalConstants.EMAIL);
			String username = job.getString(GlobalConstants.USERNAME);
			String profile_img = job.getString(GlobalConstants.PROFILE_IMG);			
			String dob = job.getString(GlobalConstants.DOB);
			String gender = job.getString(GlobalConstants.GENDER);
			String weight = job.getString(GlobalConstants.WEIGHT);
			String height = job.getString(GlobalConstants.HEIGHT);
			String tshirt_size = job.getString(GlobalConstants.TSHIRT_SIZE);
			String shoe_size = job.getString(GlobalConstants.SHOE_SIZE);
			String goals = job.getString(GlobalConstants.GOALS);
			String moto = job.getString(GlobalConstants.MOTO);
			String badge_ct = job.getString(GlobalConstants.BADGE_COUNT);
			String coins = job.getString(GlobalConstants.COINS);
			String total_dis = job.getString(GlobalConstants.TOTAL_DISTANCE);
			String group_count = job.getString(GlobalConstants.GP_COUNT);
			String my_follower = job.getString(GlobalConstants.MY_FOLLOWERS);
			String i_following = job.getString(GlobalConstants.I_FOLLOWING);
			String profile_completion = job
					.getString(GlobalConstants.PROFILE_COMPLETION);
			String total_completion = job
					.getString(GlobalConstants.TOTAL_PROFILE_COMPLETION);
			// adding to hashmap
			HashMap<String, String> maps = new HashMap<String, String>();
			maps.put(GlobalConstants.FBUSERID, fbuser_id);
			maps.put(GlobalConstants.USER_STATUS, userstatus);
			maps.put(GlobalConstants.USER_ID, user_id);
			maps.put(GlobalConstants.ACCESS_TOKEN, access_token);
			maps.put(GlobalConstants.ACCOUNT_TYPE, acc_type);
			maps.put(GlobalConstants.EMAIL, email);
			maps.put(GlobalConstants.USERNAME, username);
			maps.put(GlobalConstants.PROFILE_IMG, profile_img);
			maps.put(GlobalConstants.DOB, dob);
			maps.put(GlobalConstants.GENDER, gender);
			maps.put(GlobalConstants.WEIGHT, weight);
			maps.put(GlobalConstants.HEIGHT, height);
			maps.put(GlobalConstants.TSHIRT_SIZE, tshirt_size);
			maps.put(GlobalConstants.SHOE_SIZE, shoe_size);
			maps.put(GlobalConstants.GOALS, goals);
			maps.put(GlobalConstants.MOTO, moto);
			maps.put(GlobalConstants.BADGE_COUNT, badge_ct);
			maps.put(GlobalConstants.COINS, coins);
			maps.put(GlobalConstants.TOTAL_DISTANCE, total_dis);
			maps.put(GlobalConstants.GP_COUNT, group_count);
			maps.put(GlobalConstants.MY_FOLLOWERS, my_follower);
			maps.put(GlobalConstants.I_FOLLOWING, i_following);
			maps.put(GlobalConstants.PROFILE_COMPLETION, profile_completion);
			maps.put(GlobalConstants.TOTAL_PROFILE_COMPLETION, total_completion);

			global.setAccessToken(access_token);
			global.setUserId(user_id);
			global.setUserName(username);
			global.setUserData(maps);

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	// ===================================================

	// Login Response Parsing
	public static String loginResponseParser(Globals global, JSONArray data) {
		String result = "true";
		try {
			JSONObject job = data.getJSONObject(0);

			String userstatus = job.getString(GlobalConstants.USER_STATUS);
			String user_id = job.getString(GlobalConstants.USER_ID);
			String access_token = job.getString(GlobalConstants.ACCESS_TOKEN);
			String acc_type = job.getString(GlobalConstants.ACCOUNT_TYPE);
			String email = job.getString(GlobalConstants.EMAIL);
			String username = job.getString(GlobalConstants.USERNAME);
			
			String profile_img = job.getString(GlobalConstants.PROFILE_IMG);
			String header_img = job.getString(GlobalConstants.TIMELINE_PIC);
			
			String dob = job.getString(GlobalConstants.DOB);
			String gender = job.getString(GlobalConstants.GENDER);
			String weight = job.getString(GlobalConstants.WEIGHT);
			String height = job.getString(GlobalConstants.HEIGHT);
			String tshirt_size = job.getString(GlobalConstants.TSHIRT_SIZE);
			String shoe_size = job.getString(GlobalConstants.SHOE_SIZE);
			String goals = job.getString(GlobalConstants.GOALS);
			String moto = job.getString(GlobalConstants.MOTO);
			String badge_ct = job.getString(GlobalConstants.BADGE_COUNT);
			String coins = job.getString(GlobalConstants.COINS);
			String total_dis = job.getString(GlobalConstants.TOTAL_DISTANCE);
			String group_count = job.getString(GlobalConstants.GP_COUNT);
			String my_follower = job.getString(GlobalConstants.MY_FOLLOWERS);
			String i_following = job.getString(GlobalConstants.I_FOLLOWING);
			String profile_completion = job
					.getString(GlobalConstants.PROFILE_COMPLETION);
			String total_completion = job
					.getString(GlobalConstants.TOTAL_PROFILE_COMPLETION);

			String running_team = job.getString(GlobalConstants.RUNNING_TEAM);
			String fav_shoe = job.getString(GlobalConstants.FAV_SHOE);
			String fav_dist = job.getString(GlobalConstants.FAV_DIST);
			String address = job.getString(GlobalConstants.ADDRESS);
			String city = job.getString(GlobalConstants.CITY);
			String state = job.getString(GlobalConstants.STATE);
			String zipcode = job.getString(GlobalConstants.ZIP_CODE);
			String phone_num = job.getString(GlobalConstants.PHONE_NUM);

			// adding to hashmap
			HashMap<String, String> maps = new HashMap<String, String>();
			maps.put(GlobalConstants.USER_STATUS, userstatus);
			maps.put(GlobalConstants.USER_ID, user_id);
			maps.put(GlobalConstants.ACCESS_TOKEN, access_token);
			maps.put(GlobalConstants.ACCOUNT_TYPE, acc_type);
			maps.put(GlobalConstants.EMAIL, email);
			maps.put(GlobalConstants.USERNAME, username);
			
			maps.put(GlobalConstants.PROFILE_IMG, profile_img);
			maps.put(GlobalConstants.TIMELINE_PIC, header_img);
			
			maps.put(GlobalConstants.DOB, dob);
			maps.put(GlobalConstants.GENDER, gender);
			maps.put(GlobalConstants.WEIGHT, weight);
			maps.put(GlobalConstants.HEIGHT, height);
			maps.put(GlobalConstants.TSHIRT_SIZE, tshirt_size);
			maps.put(GlobalConstants.SHOE_SIZE, shoe_size);
			maps.put(GlobalConstants.GOALS, goals);
			maps.put(GlobalConstants.MOTO, moto);
			maps.put(GlobalConstants.BADGE_COUNT, badge_ct);
			maps.put(GlobalConstants.COINS, coins);
			maps.put(GlobalConstants.TOTAL_DISTANCE, total_dis);
			maps.put(GlobalConstants.GP_COUNT, group_count);
			maps.put(GlobalConstants.MY_FOLLOWERS, my_follower);
			maps.put(GlobalConstants.I_FOLLOWING, i_following);
			maps.put(GlobalConstants.PROFILE_COMPLETION, profile_completion);
			maps.put(GlobalConstants.TOTAL_PROFILE_COMPLETION, total_completion);

			maps.put(GlobalConstants.RUNNING_TEAM, running_team);
			maps.put(GlobalConstants.FAV_SHOE, fav_shoe);
			maps.put(GlobalConstants.FAV_DIST, fav_dist);
			maps.put(GlobalConstants.ADDRESS, address);
			maps.put(GlobalConstants.CITY, city);
			maps.put(GlobalConstants.STATE, state);
			maps.put(GlobalConstants.ZIP_CODE, zipcode);
			maps.put(GlobalConstants.PHONE_NUM, phone_num);

			global.setAccessToken(access_token);
			global.setUserId(user_id);
			global.setUserName(username);
			global.setUserData(maps);

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// COMMUNITY Response Parsing
	public static String communityParser(Globals global, JSONArray data) {
		String result = "true";
		try {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			int size = data.length();

			for (int i = 0; i < size; i++) {
				JSONObject job = data.getJSONObject(i);

				String catid = job.getString(GlobalConstants.CATEGORY_ID);
				String catname = job.getString(GlobalConstants.CATEGORY_NAME);
				String userid = job.getString(GlobalConstants.USER_ID);

				// adding to hashmap
				HashMap<String, String> maps = new HashMap<String, String>();
				maps.put(GlobalConstants.CATEGORY_ID, catid);
				maps.put(GlobalConstants.CATEGORY_NAME, catname);
				maps.put(GlobalConstants.USER_ID, userid);

				list.add(maps);
			}
			global.setCommunityList(list);
		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// Follower feed Parser
	public static String followerFeedParser(Globals global, JSONArray data) {
		String result = "true";
		try {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			int size = data.length();

			for (int i = 0; i < size; i++) {
				JSONObject job = data.getJSONObject(i);

				String feed_date = job.getString(GlobalConstants.FEED_DATE);
				String feed_id = job.getString(GlobalConstants.FEED_ID);
				String datetime = job.getString(GlobalConstants.FEED_DATE_TIME);
				String like_ct = job.getString(GlobalConstants.FEED_LIKE_COUNT);
				String comments_ct = job
						.getString(GlobalConstants.FEED_COMMENTS_COUNT);
				String feed_desc = job.getString(GlobalConstants.FEED_DESC);
				String username = job.getString(GlobalConstants.FEED_USERNAME);
				String profile_pic = job
						.getString(GlobalConstants.FEED_PROFILE_IMG);
				String post_pic = job.getString(GlobalConstants.FEED_POST_PIC);

				// adding to hashmap
				HashMap<String, String> maps = new HashMap<String, String>();
				maps.put(GlobalConstants.FEED_DATE, feed_date);
				maps.put(GlobalConstants.FEED_ID, feed_id);
				maps.put(GlobalConstants.FEED_DATE_TIME, datetime);
				maps.put(GlobalConstants.FEED_LIKE_COUNT, like_ct);
				maps.put(GlobalConstants.FEED_COMMENTS_COUNT, comments_ct);
				maps.put(GlobalConstants.FEED_DESC, feed_desc);
				maps.put(GlobalConstants.FEED_USERNAME, username);
				maps.put(GlobalConstants.FEED_PROFILE_IMG, profile_pic);
				maps.put(GlobalConstants.FEED_POST_PIC, post_pic);

				list.add(maps);
			}
			global.setFollowerFeedList(list);
		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// Follower feed Parser
	public static String followersParser(Globals global, JSONArray data,
			String type) {
		String result = "true";
		try {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			int size = data.length();

			for (int i = 0; i < size; i++) {
				JSONObject job = data.getJSONObject(i);

				String rp_frnd_id = job
						.getString(GlobalConstants.FOLLOWERS_RP_FRIEND_ID);
				String friend_id = job
						.getString(GlobalConstants.FOLLOWERS_FRIEND_ID);
				String catid = job.getString(GlobalConstants.FOLLOWERS_CAT_ID);
				String user_id = job
						.getString(GlobalConstants.FOLLOWERS_USER_ID);
				String username = job
						.getString(GlobalConstants.FOLLOWERS_USERNAME);
				String profile_img = job
						.getString(GlobalConstants.FOLLOWERS_PROFILE_IMG);
				String total_dist = job
						.getString(GlobalConstants.FOLLOWERS_TOTAL_DIST);

				// adding to hashmap
				HashMap<String, String> maps = new HashMap<String, String>();
				maps.put(GlobalConstants.FOLLOWERS_RP_FRIEND_ID, rp_frnd_id);
				maps.put(GlobalConstants.FOLLOWERS_FRIEND_ID, friend_id);
				maps.put(GlobalConstants.FOLLOWERS_CAT_ID, catid);
				maps.put(GlobalConstants.FOLLOWERS_USER_ID, user_id);
				maps.put(GlobalConstants.FOLLOWERS_USERNAME, username);
				maps.put(GlobalConstants.FOLLOWERS_PROFILE_IMG, profile_img);
				maps.put(GlobalConstants.FOLLOWERS_TOTAL_DIST, total_dist);

				list.add(maps);
			}

			if (type.equals(GlobalConstants.FOLLOWERS_MY_FOLLOWER_KEY)) {
				global.setFollowersList(list);
			} else {
				global.setIFollowingList(list);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// Get list of likes and comments==============
	public static String likeCommentParser(Globals global, JSONArray data,
			String type) {
		String result = "true";
		try {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			int size = data.length();

			for (int i = 0; i < size; i++) {
				JSONObject job = data.getJSONObject(i);

				String userid = job.getString(GlobalConstants.USER_ID);
				String feedid = job.getString(GlobalConstants.FEED_ID);
				String commentdesc = job
						.getString(GlobalConstants.LIKE_COMMENT_DESC);
				String username = job.getString(GlobalConstants.USERNAME);
				String profileimg = job.getString(GlobalConstants.PROFILE_IMG);
				String rpcommentid = job
						.getString(GlobalConstants.RP_COMMENT_ID);

				// adding to hashmap
				HashMap<String, String> maps = new HashMap<String, String>();
				maps.put(GlobalConstants.USER_ID, userid);
				maps.put(GlobalConstants.FEED_ID, feedid);
				maps.put(GlobalConstants.LIKE_COMMENT_DESC, commentdesc);
				maps.put(GlobalConstants.USERNAME, username);
				maps.put(GlobalConstants.PROFILE_IMG, profileimg);
				maps.put(GlobalConstants.RP_COMMENT_ID, rpcommentid);

				list.add(maps);
			}

			if (type.equals(GlobalConstants.COMMENT_KEY)) {
				global.setCommentsList(list);
			} else {
				global.setIFollowingList(list);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// =========================================

	// Search Friends List Parser================
	public static String searchFriendsParser(Globals global, JSONArray data,
			String type) {
		String result = "true";
		try {
			ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
			int size = data.length();

			for (int i = 0; i < size; i++) {
				JSONObject job = data.getJSONObject(i);

				String userid = job.getString(GlobalConstants.USER_ID);
				String email = job.getString(GlobalConstants.EMAIL);
				String username = job.getString(GlobalConstants.USERNAME);
				String profile_img = job.getString(GlobalConstants.PROFILE_IMG);
				// String dob = job.getString(GlobalConstants.DOB);
				// String gender = job.getString(GlobalConstants.GENDER);
				String weight = job.getString(GlobalConstants.WEIGHT);
				String height = job.getString(GlobalConstants.HEIGHT);
				String tshirt_size = job.getString(GlobalConstants.TSHIRT_SIZE);
				String shoesize = job.getString(GlobalConstants.SHOE_SIZE);
				String goals = job.getString(GlobalConstants.GOALS);
				String motto = job.getString(GlobalConstants.MOTO);
				String coins = job.getString(GlobalConstants.COINS);
				String total_distance = job
						.getString(GlobalConstants.TOTAL_DISTANCE);
				String group_count = job.getString(GlobalConstants.GP_COUNT);
				String myfollower = job.getString(GlobalConstants.MY_FOLLOWERS);
				String ifollowing = job.getString(GlobalConstants.I_FOLLOWING);

				HashMap<String, String> maps = new HashMap<String, String>();
				maps.put(GlobalConstants.USER_ID, userid);
				maps.put(GlobalConstants.EMAIL, email);
				maps.put(GlobalConstants.USERNAME, username);
				maps.put(GlobalConstants.PROFILE_IMG, profile_img);
				// maps.put(GlobalConstants.DOB,dob);
				// maps.put(GlobalConstants.GENDER,gender);
				maps.put(GlobalConstants.WEIGHT, weight);
				maps.put(GlobalConstants.HEIGHT, height);
				maps.put(GlobalConstants.TSHIRT_SIZE, tshirt_size);
				maps.put(GlobalConstants.SHOE_SIZE, shoesize);
				maps.put(GlobalConstants.GOALS, goals);
				maps.put(GlobalConstants.MOTO, motto);
				maps.put(GlobalConstants.COINS, coins);
				maps.put(GlobalConstants.TOTAL_DISTANCE, total_distance);
				maps.put(GlobalConstants.GP_COUNT, group_count);
				maps.put(GlobalConstants.MY_FOLLOWERS, myfollower);
				maps.put(GlobalConstants.I_FOLLOWING, ifollowing);

				list.add(maps);
			}

			if (type.equals(GlobalConstants.FOLLOWER_KEY)) {
				global.setFollowersSearchList(list);
			} else {
				global.setFriendsSearchList(list);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// =============================

	// Dashboard Content Parser================
	public static String dashboardContentParser(Globals global, JSONArray data) {
		String result = "true";
		try {
			JSONObject job = data.getJSONObject(0);

			String myage = job.getString(GlobalConstants.DASHBOARD_MYAGE);
			String totalDis = job.getString(GlobalConstants.TOTAL_DISTANCE);
			String coins = job.getString(GlobalConstants.COINS);
			String profile_img = job.getString(GlobalConstants.PROFILE_IMG);
			String username = job.getString(GlobalConstants.USERNAME);
			String gender = job.getString(GlobalConstants.GENDER);
			String friends_ct = job
					.getString(GlobalConstants.DASHBOARD_FRIENDS_CT);
			String watchct = job
					.getString(GlobalConstants.DASHBOARD_WATCHLIST_CT);
			String dist_by_age = job
					.getString(GlobalConstants.DASHBOARD_DISTANCEBY_AGE);
			String compare_all = job
					.getString(GlobalConstants.DASHBOARD_COMPARE_ALL);
			String avg_pace = job.getString(GlobalConstants.DASHBOARD_AVG_PACE);
			String allrun = job.getString(GlobalConstants.DASHBOARD_ALL_RUN2);
			String calories = job.getString(GlobalConstants.DASHBOARD_CALORIES);
			String address = job.getString(GlobalConstants.DASHBOARD_ADDRESS);
			String moto = job.getString(GlobalConstants.MOTO);

			HashMap<String, String> maps = new HashMap<String, String>();
			maps.put(GlobalConstants.DASHBOARD_MYAGE, myage);
			maps.put(GlobalConstants.TOTAL_DISTANCE, totalDis);
			maps.put(GlobalConstants.COINS, coins);
			maps.put(GlobalConstants.PROFILE_IMG, profile_img);
			maps.put(GlobalConstants.USERNAME, username);
			maps.put(GlobalConstants.GENDER, gender);
			maps.put(GlobalConstants.DASHBOARD_FRIENDS_CT, friends_ct);
			maps.put(GlobalConstants.DASHBOARD_WATCHLIST_CT, watchct);
			maps.put(GlobalConstants.DASHBOARD_DISTANCEBY_AGE, dist_by_age);
			maps.put(GlobalConstants.DASHBOARD_COMPARE_ALL, compare_all);
			maps.put(GlobalConstants.DASHBOARD_AVG_PACE, avg_pace);
			maps.put(GlobalConstants.DASHBOARD_ALL_RUN2, allrun);
			maps.put(GlobalConstants.DASHBOARD_CALORIES, calories);
			maps.put(GlobalConstants.DASHBOARD_ADDRESS, address);
			maps.put(GlobalConstants.MOTO, moto);

			global.setDashboardContent(maps);

		} catch (JSONException e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

	// =============================
	// User Details Parser

	public static String userDetailsParser(Globals global, JSONArray data) {
		String result = "true";

		try {
			JSONObject job = data.getJSONObject(0);
			
			String userName = job.getString(GlobalConstants.USERNAME);
			String state = job.getString(GlobalConstants.STATE);
			String city = job.getString(GlobalConstants.CITY);
			String address = job.getString(GlobalConstants.ADDRESS);
			String email = job.getString(GlobalConstants.EMAIL);
			String zipCode = job.getString(GlobalConstants.ZIP_CODE);
			String runningTeam = job.getString(GlobalConstants.RUNNING_TEAM);
			String favShoes = job.getString(GlobalConstants.FAV_SHOE);
			String dob = job.getString(GlobalConstants.DOB);
			String gender = job.getString(GlobalConstants.GENDER);
			String weight = job.getString(GlobalConstants.WEIGHT);
			String height = job.getString(GlobalConstants.HEIGHT);
			String tShirtSize = job.getString(GlobalConstants.TSHIRT_SIZE);
			String shoeSize = job.getString(GlobalConstants.SHOE_SIZE);
			
			String profileImage = job.getString(GlobalConstants.PROFILE_IMG);
			String timeLineImage = job.getString(GlobalConstants.TIMELINE_PIC);
			
			String profileCompletion = job.getString(GlobalConstants.TOTAL_PROFILE_COMPLETION);

			HashMap<String, String> maps = global.getUserData();
			
			maps.put(GlobalConstants.USERNAME, userName);			
			maps.put(GlobalConstants.STATE, state);
			maps.put(GlobalConstants.CITY, city);
			maps.put(GlobalConstants.ADDRESS, address);
			maps.put(GlobalConstants.EMAIL, email);
			maps.put(GlobalConstants.ZIP_CODE, zipCode);
			maps.put(GlobalConstants.RUNNING_TEAM, runningTeam);			
			maps.put(GlobalConstants.FAV_SHOE, favShoes);
			maps.put(GlobalConstants.DOB, dob);
			maps.put(GlobalConstants.GENDER, gender);
			maps.put(GlobalConstants.WEIGHT, weight);
			maps.put(GlobalConstants.HEIGHT, height);
			maps.put(GlobalConstants.TSHIRT_SIZE, tShirtSize);
			maps.put(GlobalConstants.SHOE_SIZE, shoeSize);	
			maps.put(GlobalConstants.PROFILE_COMPLETION, profileCompletion);	
			maps.put(GlobalConstants.PROFILE_IMG, profileImage);	
			maps.put(GlobalConstants.TIMELINE_PIC, timeLineImage);	

			global.setUserData(maps);

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

}