package com.runtwo.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.HomeFragment.ViewHolder;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsFragment extends Fragment{

	LayoutInflater inflater;
	ListView commentsList;
	String feedId = "";
	ArrayList<HashMap<String,String>> comments;
	Globals global;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater; 
			
		container = (LinearLayout)inflater.inflate(R.layout.commentsfrag_lay,null); 
		
		global = (Globals) getActivity().getApplicationContext();
		
		container.findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
		
		
		feedId = getArguments().getString("id","");
		
		commentsList = (ListView)container.findViewById(R.id.comments_list);
		
		
		return container;
	}

	class LikeService extends AsyncTask<String,Integer,String>{
		ProgressDialog mProgressDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Loading...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getLikeCommentService(getActivity(),feedId,GlobalConstants.COMMENT_KEY);
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
				comments = global.getCommentsList();
				//set Adapter Here...
				commentsList.setAdapter(new CommentsListAdapter());
			}
		}
	}
	
	class CommentsListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return comments.size();
		}

		@Override
		public Object getItem(int position) {
			return comments.get(position);
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
				convertView = inflater.inflate(R.layout.comments_list_item,null);
			
				holder.username = (TextView)convertView.findViewById(R.id.news_user_name);
				holder.image = (ImageView)convertView.findViewById(R.id.news_user_img);
				holder.comments = (TextView)convertView.findViewById(R.id.comments_text);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			HashMap<String,String> map = comments.get(position);
			try {
				holder.username.setText(""+map.get(GlobalConstants.USERNAME));
				holder.comments.setText(""+map.get(GlobalConstants.LIKE_COMMENT_DESC));
				
				String profile_pic_url = ""+map.get(GlobalConstants.PROFILE_IMG);
				
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
		TextView comments,username;
		ImageView image;
	}

}