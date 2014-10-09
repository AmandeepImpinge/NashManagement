package com.runtwo.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment implements OnKeyListener{

	LinearLayout moreNewsBtn;
	ListView mNewsList;
	
	LayoutInflater inflater;
	LinearLayout follower_btn,following_btn,groups_btn;
	FragmentTransaction ft;
	ActionBarRun ab;
	
	TextView username,followers,following,group,totalMiles;
	ImageView searchIcon,postIcon,runIcon;
	Globals global;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String,String>> feedList = new ArrayList<HashMap<String,String>>();
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
 		container = (LinearLayout)inflater.inflate(R.layout.news_feed_frag_lay,null);
		
 		container.findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
 		
 		
 		global = (Globals)getActivity().getApplicationContext();
 		this.inflater = inflater;
 		
 		mNewsList = (ListView)container.findViewById(R.id.newsfeed_list);
		
 		initViews(container);
		
 		handleClicks();
 		
		ft = getFragmentManager().beginTransaction();				
		
		mNewsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Fragment f = new NewsDetailsFragment();
				Bundle bd = new Bundle();
				bd.putInt("pos",position);
				f.setArguments(bd);
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("news");
				ft.commit();
			}
		});
		
		new CallFeedService().execute("");
		 
		container.setOnKeyListener(this);
		return container; 
	}
	

	  private void initViews(ViewGroup container){
		
		searchIcon = (ImageView)container.findViewById(R.id.search_icon);
		postIcon = (ImageView)container.findViewById(R.id.post_icon);
		runIcon = (ImageView)container.findViewById(R.id.run_icon);
		
		//moreNewsBtn = (LinearLayout)container.findViewById(R.id.clickfornews);
	}
	
	private void handleClicks(){
		searchIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
		
		postIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Fragment f = new PostFragment();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("post");
				ft.commit();
			}
		});
		
		runIcon.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
	}
	
	class NewsListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return feedList.size();
		}

		@Override
		public Object getItem(int position) {
			return feedList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder;
			
			if(convertView == null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.news_feed_list_item,null);
			
				holder.newsfeedtext = (TextView)convertView.findViewById(R.id.news_text);
				holder.no_likes = (TextView)convertView.findViewById(R.id.no_likes);
				holder.no_comments = (TextView)convertView.findViewById(R.id.no_comments);
				holder.date = (TextView)convertView.findViewById(R.id.news_date);
				holder.username = (TextView)convertView.findViewById(R.id.news_user_name);
				
				holder.image = (ImageView)convertView.findViewById(R.id.news_user_img);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			HashMap<String,String> map = feedList.get(position);
			try {
				holder.username.setText(""+map.get(GlobalConstants.FEED_USERNAME));
				holder.newsfeedtext.setText(""+map.get(GlobalConstants.FEED_DESC));
				holder.date.setText(""+map.get(GlobalConstants.FEED_DATE));
				holder.no_likes.setText(""+map.get(GlobalConstants.FEED_LIKE_COUNT));
				holder.no_comments.setText(""+map.get(GlobalConstants.FEED_COMMENTS_COUNT));
				String profile_pic_url = ""+map.get(GlobalConstants.FEED_POST_PIC);
				if(profile_pic_url.length() > 0){
					//load pic
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return convertView;
		}
	}
	
	class ViewHolder{
		TextView newsfeedtext,no_likes,no_comments,date,username;
		ImageView image;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.e("key","pressed");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.e("back","pressed");
			if(MainTabActivity.menu.isMenuShowing()){
				Log.e("menu","was showing");
				MainTabActivity.menu.showContent();
			}
			return true;
		}else{
			return false;
		}
	}
		
	class CallFeedService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Loading...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getFollowerFeedService(getActivity());
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
				feedList = global.getFollowerFeedList();
				mNewsList.setAdapter(new NewsListAdapter()); 
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	 
}
