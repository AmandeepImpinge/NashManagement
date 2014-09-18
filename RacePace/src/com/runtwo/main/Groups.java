package com.runtwo.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.runtwo.main.R;

public class Groups extends Fragment implements OnKeyListener{

	
	ListView followerList;
	EditText searchbox;
	LayoutInflater inflater;
	TextView title;
	ActionBarRun ab;
	TextView search_btn;
	
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
		
		title = (TextView)container.findViewById(R.id.title);
		searchbox = (EditText)container.findViewById(R.id.search_edit);
		followerList = (ListView)container.findViewById(R.id.follower_following_list);
		
		title.setText("GROUPS");
		
		followerList.setAdapter(new FollowerFollowingAdapter());
		
		container.setOnKeyListener(this);
		
		return container;
	}
	
	class FollowerFollowingAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Object getItem(int position) {
			return null;
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
			
			if(position == 0){
				holder.username.setText("10x10");
			}else{
				holder.username.setText("Run 2");
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
}