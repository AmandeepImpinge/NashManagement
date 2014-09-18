package com.runtwo.main;


import java.util.HashMap;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.main.HomeFragment.NewsListAdapter;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailsFragment extends Fragment implements OnClickListener,OnKeyListener{

	TextView mNewsTxt,mSubmitBtn,mCancelBtn,like_txt,comment_txt,date_txt;
	EditText commentBox;
	LinearLayout likeBtn;
	ActionBarRun ab;
	HashMap<String,String> map;
	Globals global;
	ProgressDialog mProgressDialog;
	String comment = "";
	String feedId = "";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		container = (LinearLayout)inflater.inflate(R.layout.news_details,null);
		initViews(container);
	
		ab = (ActionBarRun)container.findViewById(R.id.action_bar);
		ab.findViewById(R.id.run_txt).setVisibility(View.INVISIBLE);
		ab.findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
		
		global = (Globals) getActivity().getApplicationContext();
		int which = getArguments().getInt("pos");
		map = global.getFollowerFeedList().get(which);
		setValues();
		
		mSubmitBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		likeBtn.setOnClickListener(this);
		comment_txt.setOnClickListener(this);
		container.setOnKeyListener(this);
		
		return container;
	}
	
	private void initViews(ViewGroup container){
		mNewsTxt = (TextView)container.findViewById(R.id.news_text);
		mSubmitBtn = (TextView)container.findViewById(R.id.submit_btn);
		mCancelBtn = (TextView)container.findViewById(R.id.cancel_btn);
		like_txt = (TextView)container.findViewById(R.id.news_likes);
		comment_txt = (TextView)container.findViewById(R.id.news_comments);
		date_txt = (TextView)container.findViewById(R.id.news_date);
		
		commentBox = (EditText)container.findViewById(R.id.comments_box);
		likeBtn = (LinearLayout)container.findViewById(R.id.like_btn);
	}

	
	private void setValues(){
		mNewsTxt.setText(""+map.get(GlobalConstants.FEED_DESC));
		like_txt.setText(""+map.get(GlobalConstants.FEED_LIKE_COUNT)+" Likes");
		comment_txt.setText(""+map.get(GlobalConstants.FEED_COMMENTS_COUNT)+" Comments");
		date_txt.setText(""+map.get(GlobalConstants.FEED_DATE));
		feedId = ""+map.get(GlobalConstants.FEED_ID);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_btn:
			commentBox.setError(null);
			comment = ""+commentBox.getText().toString();
			if(comment.length() > 0){
				new CommentService().execute("");
			}else{
				commentBox.setError("Enter some value");
			}
			break;
		case R.id.cancel_btn:
			try {
				getFragmentManager().popBackStack();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.like_btn:
			new LikeService().execute("");
			break;
			
		case R.id.news_comments:
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			Fragment f = new CommentsFragment();
			Bundle bd = new Bundle();
			bd.putString("id",""+feedId);
			f.setArguments(bd);
			ft.replace(R.id.contentintab,f);
			ft.addToBackStack("news");
			ft.commit();
			break;
		}
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
	
	
	class LikeService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Liking...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.likeCommentService(getActivity(),map.get(GlobalConstants.FEED_ID),GlobalConstants.LIKE_KEY,"");
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
				Toast.makeText(getActivity(),"Done.",Toast.LENGTH_SHORT).show();
				int lc = Integer.parseInt(""+map.get(GlobalConstants.FEED_LIKE_COUNT));
				lc += 1;
				like_txt.setText(lc+" Likes");
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	class CommentService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Commenting...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.likeCommentService(getActivity(),map.get(GlobalConstants.FEED_ID),GlobalConstants.COMMENT_KEY,""+comment);
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
				Toast.makeText(getActivity(),"Done.",Toast.LENGTH_SHORT).show();
				int lc = Integer.parseInt(""+map.get(GlobalConstants.FEED_COMMENTS_COUNT));
				lc += 1;
				comment_txt.setText(lc+" Comments");
				commentBox.setText("");
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}