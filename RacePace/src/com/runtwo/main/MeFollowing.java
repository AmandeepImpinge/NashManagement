package com.runtwo.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.main.MyFollowers.FollowerFollowingAdapter;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeFollowing extends Fragment implements OnKeyListener{

	ListView followerList;
	EditText searchbox;
	LayoutInflater inflater;
	TextView title;
	ActionBarRun ab;
	TextView search_btn;
	ProgressDialog mProgressDialog;
	Globals global;
	ArrayList<HashMap<String,String>> ifollowList;
	ArrayList<HashMap<String,String>> searchedList = new ArrayList<HashMap<String,String>>();
	boolean searched = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		container = (LinearLayout)inflater.inflate(R.layout.my_followers,null);
		
		this.inflater = inflater; 
		
		ab = (ActionBarRun)container.findViewById(R.id.action_bar);
		
		search_btn = (TextView)ab.findViewById(R.id.run_txt);
		search_btn.setText("Search");
		search_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment f = new SearchFollowersScreen();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("followers");
				ft.commit();
			}
		});
		ab.findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
		
		global = (Globals) getActivity().getApplicationContext();
		
		title = (TextView)container.findViewById(R.id.title);
		searchbox = (EditText)container.findViewById(R.id.search_edit);
		searchbox.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					String txt = searchbox.getText().toString().trim();
					if(txt.length() > 0){
						searchIt();
					}
				}
				return false;
			}
		});
		
		followerList = (ListView)container.findViewById(R.id.follower_following_list);
		
		/*title.setText("I FOLLOWING");
		
		followerList.setAdapter(new FollowerFollowingAdapter());*/
		
		searchbox.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(searchbox.getText().toString().trim().length() == 0){
					followerList.setAdapter(new FollowerFollowingAdapter(ifollowList));
					searched = false;
				}
			}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void afterTextChanged(Editable s) {}
		});
		
		container.setOnKeyListener(this);
		
		return container;
	}
	
	
	
	private void searchIt(){
		if(ifollowList.size() > 0){
			String txt = searchbox.getText().toString().trim();
			int sz = ifollowList.size();
			searchedList = new ArrayList<HashMap<String,String>>();
			for(int i=0;i<sz;i++){
				if((ifollowList.get(i).get(GlobalConstants.FOLLOWERS_USERNAME)).contains(txt)){
					searchedList.add(ifollowList.get(i));
				}
			}
			
			if(searchedList.size() > 0){
				followerList.setAdapter(new FollowerFollowingAdapter(searchedList));
				searched = true;
			}else{
				Toast.makeText(getActivity(),"No Result found...",Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(getActivity(),"No Result found...",Toast.LENGTH_SHORT).show();
		}
	}
	
	class FollowerFollowingAdapter extends BaseAdapter{
		
		ArrayList<HashMap<String,String>> list;
		
		public FollowerFollowingAdapter(ArrayList<HashMap<String,String>> ll){			
			list = ll;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView  == null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.follower_list_item,null);
				
				holder.username = (TextView)convertView.findViewById(R.id.user_name);
				holder.img = (ImageView)convertView.findViewById(R.id.user_img);
						
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			HashMap<String,String> map = list.get(position);
			
			holder.username.setText(""+map.get(GlobalConstants.FOLLOWERS_USERNAME));
			
			String imgUrl = ""+map.get(GlobalConstants.FOLLOWERS_PROFILE_IMG);
			
			if(imgUrl.length() > 0){
				//Load Image Here
			}
			
			if(position == 0){
				convertView.setBackgroundColor(getResources().getColor(R.color.search_edit_back));
			}else if(position%2 == 0){
				convertView.setBackgroundColor(getResources().getColor(R.color.search_edit_back));
			}else{
				convertView.setBackgroundColor(Color.WHITE);
			}
			
			return convertView;
		}
	}
	
	class ViewHolder{
		TextView username;
		ImageView img;
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(MainTabActivity.menu.isMenuShowing()){
				MainTabActivity.menu.showContent();
			}else{
				getFragmentManager().popBackStack();
			}
			return true;
		}else{
			return false;	
		}
	}
	
	
	class CallIFollowingService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Loading...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getFollowersService(getActivity(),GlobalConstants.FOLLOWERS_MY_FOLLOWER_KEY);
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
				//Toast.makeText(getActivity(),"Service Successfull.",Toast.LENGTH_SHORT).show();
				ifollowList = global.getIFollowingList();
				followerList.setAdapter(new FollowerFollowingAdapter(ifollowList));
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
}