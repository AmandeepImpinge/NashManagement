package com.runtwo.constants;

public class GlobalConstants {
											 
	public static final String COMMON_URL = "http://ganga.impingesolutions.com/projects/MobileApps/Patrick/source/apps/index.php/api/"; 
	
	//Shared Prefs========
	public static final String PREF_NAME = "racepace_prefs";
	public static final String USERNAME_SAVE = "username_sp";
	public static final String PASSWORD_SAVE = "password_sp";
	//=====================
	
	//=====================
	public static final int POST_GALLERY = 120;
	public static final int POST_CAMERA = 130;
	//=====================
	
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	public static final String USER_DETAILS = "userDetail";
	
	//REGISTER VALUES
	public static final String REGISTERATION_URL = COMMON_URL+"user/register/format/json";
	//===================
	
	//COMMON VALUES
	public static final String USERNAME = "username";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String ACCOUNT_TYPE = "account_type";
	public static final String DEVICE_TYPE = "device_type";
	public static final String DEVICE_TOKEN = "device_token";
	
	public static final String FBUSERID = "fbuser_id";
	public static final String USER_STATUS = "user_status";
	public static final String USER_ID = "user_id";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String PROFILE_IMG = "profile_img";
	public static final String DOB = "dob";
	public static final String GENDER = "gender";
	public static final String WEIGHT = "weight";
	public static final String HEIGHT = "height";
	public static final String TSHIRT_SIZE = "tshirt_size";
	public static final String SHOE_SIZE = "shoes_size";
	public static final String GOALS = "goals";
	public static final String MOTO = "motto";
	public static final String BADGE_COUNT = "badge_count";
	public static final String COINS = "coins";
	public static final String TOTAL_DISTANCE = "total_distance";
	public static final String GP_COUNT = "groupCnt";
	public static final String MY_FOLLOWERS = "myfollowers";
	public static final String I_FOLLOWING = "ifollowing";
	public static final String PROFILE_COMPLETION = "profileCompletion";
	public static final String TOTAL_PROFILE_COMPLETION = "TotalprofileCompletion";
		
	//forlogin data
	public static final String RUNNING_TEAM = "running_team";
	public static final String FAV_SHOE = "fav_shoe";
	public static final String FAV_DIST = "fav_distance";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String ZIP_CODE = "zip_code";
	public static final String PHONE_NUM = "phone_num";
	
	//LOGIN VALUES
	public static final String LOGIN_URL = COMMON_URL+"authentication/authenticateUser/format/json";
	
	//=======================
	
	//FORGOT PASSWORD
	public static final String FORGOT_PASSWORD_URL = COMMON_URL+"user/forgotPassword/format/json";
	//=======================
	
	//GET COMMUNITY==========
	public static final String GET_COMMUNITY_URL = COMMON_URL+"friend/getCommunity/format/json";

	public static final String CATEGORY_ID = "cat_id";
	public static final String CATEGORY_NAME = "cat_name";
	//=======================
	
	//ADD COMMUNITY==========
	public static final String ADD_COMMUNITY_URL = COMMON_URL+"friend/addCommunity/format/json";
	//=======================
	
	//GET LOGGED OUT=========
	public static final String LOG_OUT_URL = COMMON_URL+"authentication/updateLoggedInStaus/format/json";
	//=======================

	//GET DASHBOARD CONTENT======
	public static final String GET_DASHBOARD_CONTENT_URL = COMMON_URL+"user/getDashboardDetail/format/json";
	public static final String DASHBOARD_MYAGE = "myAge";
	public static final String DASHBOARD_FRIENDS_CT = "friendsCnt";
	public static final String DASHBOARD_WATCHLIST_CT = "watchlistCnt";
	public static final String DASHBOARD_DISTANCEBY_AGE = "distanceByAge";
	public static final String DASHBOARD_COMPARE_ALL = "compareAll";
	public static final String DASHBOARD_AVG_PACE = "AveragePace";
	public static final String DASHBOARD_ALL_RUN2 = "AllRun2";
	public static final String DASHBOARD_CALORIES = "calories";
	public static final String DASHBOARD_ADDRESS = "address";
	//===========================
	
	//GET FOLLOWERS FEED=========									
	public static final String GET_FOLLOWER_FEED_URL = COMMON_URL+"friend/getFollowersFeed/format/json";
	public static final String FEED_DATE = "feed_date";
	public static final String FEED_ID = "feed_id";
	public static final String FEED_DATE_TIME = "feed_datetime";
	public static final String FEED_LIKE_COUNT = "likes_count";
	public static final String FEED_COMMENTS_COUNT = "comments_count";
	public static final String FEED_DESC = "feed_desc";
	public static final String FEED_USERNAME = "username";
	public static final String FEED_PROFILE_IMG = "profile_img";
	public static final String FEED_POST_PIC = "post_pic";
	//=======================
	
	//GET FOLLOWERS == FOLLOWINGS =================
	public static final String GET_FOLLOWERS_URL = COMMON_URL+"friend/getFollowers/format/json";
	public static final String FOLLOWERS_MY_FOLLOWER_KEY = "myFollowers";
	public static final String FOLLOWERS_I_FOLLOWING_KEY = "ifollowing";
	public static final String FOLLOWERS_TYPE = "type";
	
	public static final String FOLLOWERS_RP_FRIEND_ID = "rp_friend_id";
	public static final String FOLLOWERS_FRIEND_ID = "friend_id";
	public static final String FOLLOWERS_CAT_ID = "cat_id";
	public static final String FOLLOWERS_USER_ID = "user_id";
	public static final String FOLLOWERS_USERNAME = "username";
	public static final String FOLLOWERS_PROFILE_IMG = "profile_img";
	public static final String FOLLOWERS_TOTAL_DIST = "total_distance";
	//==============================
	
	//ADD LIKE COMMENT==============
	public static final String LIKE_COMMENT_URL = COMMON_URL+"friend/addFeedLikeComment/format/json";
	public static final String LIKE_COMMENT_TYPE = "type";
	public static final String LIKE_KEY ="like";
	public static final String COMMENT_KEY = "comment";
	public static final String LIKE_COMMENT_DESC = "comment_desc";
	//==============================
	
	//GET LIKE COMMENTS=============
	public static final String GET_LIKE_COMMENT_URL = COMMON_URL+"friend/getFeedLikesComments/format/json";
	public static final String RP_COMMENT_ID = "rp_comments_id";
	//==============================
	
	//SEARCH FRIENDS AND FOLLOWERS==
	public static final String SEARCH_FRIENDS_URL = COMMON_URL+"friend/searchFollowersFriends/format/json";
	public static final String SEARCH_TEXT = "search_text";
	public static final String FRIEND_KEY = "friend";
	public static final String FOLLOWER_KEY = "follower";
	//==============================
	
	//POST USER FEED================
	public static final String POST_USER_FEED_URL = COMMON_URL+"friend/createUserFeed/format/json";
	public static final String POST_IMAGE_NAME = "img_name";
	public static final String POST_PROFILE_IMAGE = "profile_image";
	public static final String POST_DESC = "post_desc";
	public static final String POST_FEED_TYPE = "feed_type";
	public static final String POST_KEY = "post";
	//==============================
	
	//ADD FOLLOWERS=================
	public static final String ADD_FOLLOWERS_URL = COMMON_URL+"api/friend/addFollower/format/json";
	public static final String ADD_FOLLOWERS_FRIEND_ID = "friend_id";
	public static final String ADD_FOLLOWERS_IS_FOLLOWER = "is_follower";
	//==============================
	
	//GET ACHIEVEMENTS==============
	public static final String GET_ACHIEVEMENT_URL = COMMON_URL+"Challenge/getUsersAchievements/format/json";
	public static final String GET_ACHIEVEMENT_ID = "achievement_id";
	public static final String GET_ACHIEVEMENT_MILESTONE = "milestone";
	public static final String GET_ACHIEVEMENT_COINS = "coins";
	public static final String GET_ACHIEVEMENT_AWARD = "award";
	public static final String GET_ACHIEVEMENT_COLOR = "color";
	public static final String GET_ACHIEVEMENT_AWARD_TYPE = "award_type";
	public static final String GET_ACHIEVEMENT_ACHEIEVEMENT_DETAIL = "achievement_detail";
	public static final String GET_ACHIEVEMENT_AGE_DESC = "age_desc";
	public static final String GET_ACHIEVEMENT_TOTAL_COUNT = "totalCnt";
	public static final String GET_ACHIEVEMENT_GENDER = "gender";
	public static final String GET_ACHIEVEMENT_USER_ID = "user_id";
	public static final String GET_ACHIEVEMENT_USER_ACH_ID = "user_achievement_id";
	
	//Achievement types
	public static final String ACHIEVEMENT_PLAQUE = "Plaque";
	public static final String ACHIEVEMENT_LARGEGOLD_WIMB = "Large Gold Plate like Wimbledon";
	public static final String ACHIEVEMENT_MEDAL = "Medal";
	public static final String ACHIEVEMENT_RIBBON = "Ribbon";
	public static final String ACHIEVEMENT_TROPHY = "Trophy";
	public static final String ACHIEVEMENT_BELT_BUCKLE = "Belt Buckle";
	public static final String ACHIEVEMENT_PLATE = "Plate";
	public static final String ACHIEVEMENT_CUP = "Cup";
	//==============================
	
}