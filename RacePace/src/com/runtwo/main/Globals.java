package com.runtwo.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

public class Globals extends Application{

	//Device Properties======================
	int deviceWidth = 480;
	public int getDeviceWidth() {
		return deviceWidth;
	}
	public void setDeviceWidth(int deviceWidth) {
		this.deviceWidth = deviceWidth;
	}
	//Dev ht
	int deviceHeight = 800;
	public int getDeviceHeight() {
		return deviceHeight;
	}
	public void setDeviceHeight(int deviceHeight) {
		this.deviceHeight = deviceHeight;
	}
	//Dev id(IMEI)
	String deviceId = "";
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceid){
		this.deviceId = deviceid;
	}
	//========================================
	
	//PolyOptions for the Run Usa map in case for reuse when app is not killed===============
	PolylineOptions usaRoutePolyOption = new PolylineOptions(); 
	public PolylineOptions getRunUsaRoutePolyOptions() {
		return usaRoutePolyOption;
	}
	public void setRunUsaRoutePolyOptions(PolylineOptions  options) {
		usaRoutePolyOption = options;
	}

	LatLngBounds.Builder boundbuilder;
	public LatLngBounds.Builder getMapRouteBounds() {
		return boundbuilder;
	}
	public void setMapRouteBounds(LatLngBounds.Builder builder){
		boundbuilder = builder;
	}
	//==================================
	
	//Response Message after calling service
	String responseErrorMessage = "";
	public void setMessageOfResponse(String string) {
		responseErrorMessage = string;
	}
	public String getMessageOfResponse(){
		return responseErrorMessage;
	}
	//=================================
	
	//USER PROPERTIES================

	//ACCESS TOKEN
	String accessToken = "";
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	//USER ID
	String userId = "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//USER NAME
	String userName = "";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//OTHER DATA HASH MAP
	HashMap<String,String> userData = new HashMap<String, String>();
	public HashMap<String, String> getUserData() {
		return userData;
	}
	public void setUserData(HashMap<String, String> userData) {
		this.userData = userData;
	}
	//=================================

	//Get Community List===============
	public ArrayList<HashMap<String,String>> communityList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getCommunityList() {
		return communityList;
	}
	public void setCommunityList(ArrayList<HashMap<String, String>> communityList) {
		this.communityList = communityList;
	}
	//==================================
	
	
	//FOLLOWERS FEED LIST===============
	public ArrayList<HashMap<String,String>> followerFeedList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getFollowerFeedList() {
		return followerFeedList;
	}
	public void setFollowerFeedList(ArrayList<HashMap<String, String>> followerFeedList) {
		this.followerFeedList = followerFeedList;
	}
	//==================================
	
	//Followers List====================
	ArrayList<HashMap<String, String>> followersList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getFollowersList() {
		return followersList;
	}
	public void setFollowersList(ArrayList<HashMap<String, String>> followersList) {
		this.followersList = followersList;
	}
	//===================================
	
	//I Following List===================
	ArrayList<HashMap<String, String>> iFollowingList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getIFollowingList() {
		return iFollowingList;
	}
	public void setIFollowingList(ArrayList<HashMap<String, String>> iFollowingList) {
		this.iFollowingList = iFollowingList;
	}
	//====================================
	
	//Get Comments List===================
	ArrayList<HashMap<String, String>> commentsList = new ArrayList<HashMap<String,String>>();  
	public void setCommentsList(ArrayList<HashMap<String, String>> list) {
		commentsList = list;
	}
	public ArrayList<HashMap<String, String>> getCommentsList(){
		return commentsList;
	}
	//=====================================
	
	//Followers Search List================
	ArrayList<HashMap<String, String>> followersSearchList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getFollowersSearchList() {
		return followersSearchList;
	}
	public void setFollowersSearchList(
			ArrayList<HashMap<String, String>> followersSearchList) {
		this.followersSearchList = followersSearchList;
	}
	//=====================================
	
	//Friends Search List==================
	ArrayList<HashMap<String,String>> friendsSearchList = new ArrayList<HashMap<String,String>>();
	public ArrayList<HashMap<String, String>> getFriendsSearchList() {
		return friendsSearchList;
	}
	public void setFriendsSearchList(
			ArrayList<HashMap<String, String>> friendsSearchList) {
		this.friendsSearchList = friendsSearchList;
	}
	//======================================
	
	//Dashboard Content=====================
	HashMap<String,String> dashboardContent = new HashMap<String, String>();
	public HashMap<String, String> getDashboardContent() {
		return dashboardContent;
	}
	public void setDashboardContent(HashMap<String, String> dashboardContent) {
		this.dashboardContent = dashboardContent;
	}
	//======================================

	//for distance in running mode
	Float distanceInMeters = 0.0f;
	public Float getDistanceInMeters() {
		return distanceInMeters;
	}
	public void setDistanceInMeters(Float distanceInMeters) {
		this.distanceInMeters = distanceInMeters;
	}
	
	
	//HashMap for storing the data of the ACHIEVEMENTs==
	HashMap<String,ArrayList<HashMap<String,String>>> achievementData = new HashMap<String, ArrayList<HashMap<String,String>>>();
	public HashMap<String, ArrayList<HashMap<String, String>>> getAchievementData() {
		return achievementData;
	}
	public void setAchievementData(
			HashMap<String, ArrayList<HashMap<String, String>>> achievementData) {
		this.achievementData = achievementData;
	}
	//===================================================
	
	//ArrayList for saving the coordinates recorded while running==
	ArrayList<String> runningTrackedPath = new ArrayList<String>();
	public void setRunningTrackedPath(ArrayList<String> llist) {
		runningTrackedPath = llist;
	}
	public ArrayList<String> getRunningTrackedPath(){
		return runningTrackedPath;
	}
	//==============================================================
}	