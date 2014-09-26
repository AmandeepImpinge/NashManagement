package com.runtwo.main;

import java.util.HashMap;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.HomeFragment.NewsListAdapter;
import com.runtwo.webservice.WebServiceHandler;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment implements OnKeyListener{

	LayoutInflater inflater;
	LinearLayout moreNewsBtn;
	ListView mNewsList;
	LinearLayout follower_btn,following_btn,groups_btn;
	FragmentTransaction ft;
	ActionBarRun ab;
	Resources res;
	TextView username,followers,following,group,totalMiles;
	Globals global;
	ProgressDialog mProgressDialog;
	
	RelativeLayout imageContainer;
	
	//Custom Tabs and Contents
	LinearLayout wallBtn,photosBtn,meBtn,togetherBtn,workoutBtn;
	TextView wallText,photosText,meText,togetherText,workoutText;
	ImageView wallImg,photosImg,meImg,togetherImg,workoutImg;
	LinearLayout wallLay,photosLay,meLay,togetherLay,workoutLay;
	//===
	
	//List and Grid Btns
	ImageView listBtnWall,gridBtnWall,listBtnPhotos,gridBtnPhotos,listBtnMe,gridBtnMe,
			  listBtnTogether,gridBtnTogether,listBtnWorkout,gridBtnWorkout;
	//===
	
	int CurrentCustomTab = 1;
	LinearLayout achievementContainer;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		container = (LinearLayout)inflater.inflate(R.layout.profile_layout,null);
		
		ab = (ActionBarRun) container.findViewById(R.id.action_bar);
 		ab.findViewById(R.id.menu_icon).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
 		
 		ab.findViewById(R.id.run_txt).setVisibility(View.INVISIBLE);
 		
 		global = (Globals)getActivity().getApplicationContext();
 		this.inflater = inflater;
 		res = getResources();
		
 		initViews(container);
		handleClicks();
 		
		HashMap<String,String> userMap = global.getUserData(); 
		
		username.setText(global.getUserName());
		followers.setText(userMap.get(GlobalConstants.MY_FOLLOWERS));
		following.setText(userMap.get(GlobalConstants.I_FOLLOWING));
		group.setText(userMap.get(GlobalConstants.GP_COUNT));
		totalMiles.setText(userMap.get(GlobalConstants.TOTAL_DISTANCE));
		
		ft = getFragmentManager().beginTransaction();
		
		addAchievements();
		
		new CallService().execute("");
		
		container.setOnKeyListener(this);
		return container; 
	}

	
	private void initViews(ViewGroup container){
		follower_btn = (LinearLayout)container.findViewById(R.id.followers_btn);
		following_btn = (LinearLayout)container.findViewById(R.id.following_btn);
		groups_btn = (LinearLayout)container.findViewById(R.id.groups_btn);
		
		username = (TextView)container.findViewById(R.id.username_new);
		followers = (TextView)container.findViewById(R.id.no_followers);
		following = (TextView)container.findViewById(R.id.no_followings);
		group = (TextView)container.findViewById(R.id.no_groups);
		totalMiles = (TextView)container.findViewById(R.id.no_miles_new);
 		
		imageContainer = (RelativeLayout)container.findViewById(R.id.img_container);
		
		//custom tabs===
		wallBtn = (LinearLayout)container.findViewById(R.id.wall_btn);
		photosBtn = (LinearLayout)container.findViewById(R.id.photos_btn);
		meBtn = (LinearLayout)container.findViewById(R.id.me_btn);
		togetherBtn = (LinearLayout)container.findViewById(R.id.together_btn);
		workoutBtn = (LinearLayout)container.findViewById(R.id.workout_btn);
		
		wallText = (TextView)container.findViewById(R.id.cus_tab_txt1);
		photosText = (TextView)container.findViewById(R.id.cus_tab_txt2);
		meText = (TextView)container.findViewById(R.id.cus_tab_txt3);
		togetherText = (TextView)container.findViewById(R.id.cus_tab_txt4);
		workoutText = (TextView)container.findViewById(R.id.cus_tab_txt5);
		
		wallImg = (ImageView)container.findViewById(R.id.cus_tab_img1);
		photosImg = (ImageView)container.findViewById(R.id.cus_tab_img2);
		meImg = (ImageView)container.findViewById(R.id.cus_tab_img3);
		togetherImg = (ImageView)container.findViewById(R.id.cus_tab_img4);
		workoutImg = (ImageView)container.findViewById(R.id.cus_tab_img5);
		//===============
		
		//containers=====
		wallLay = (LinearLayout)container.findViewById(R.id.wall_lay);
		photosLay = (LinearLayout)container.findViewById(R.id.photos_lay);
		meLay = (LinearLayout)container.findViewById(R.id.me_lay);
		togetherLay = (LinearLayout)container.findViewById(R.id.together_lay);
		workoutLay = (LinearLayout)container.findViewById(R.id.workout_lay);
		//===============
		
		//List grid Btns
		listBtnWall = (ImageView)container.findViewById(R.id.list_btn_wall);
		gridBtnWall = (ImageView)container.findViewById(R.id.grid_btn_wall);
		listBtnPhotos = (ImageView)container.findViewById(R.id.list_btn_photos);
		gridBtnPhotos = (ImageView)container.findViewById(R.id.grid_btn_photos);
		listBtnMe = (ImageView)container.findViewById(R.id.list_btn_me);
		gridBtnMe = (ImageView)container.findViewById(R.id.grid_btn_me);
		listBtnTogether = (ImageView)container.findViewById(R.id.list_btn_together);
		gridBtnTogether = (ImageView)container.findViewById(R.id.grid_btn_together);
		listBtnWorkout = (ImageView)container.findViewById(R.id.list_btn_workout);
		gridBtnWorkout = (ImageView)container.findViewById(R.id.grid_btn_workout);
		//==============
		
		achievementContainer = (LinearLayout)container.findViewById(R.id.achievement_container);
	}

	private void handleClicks(){
		follower_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Fragment f = new MyFollowers();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("followers");
				ft.commit();
			}
		});
		following_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Fragment f = new MeFollowing();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("following");
				ft.commit();
			}
		});
		groups_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Fragment f = new Groups();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("groups");
				ft.commit();
			}
		});
		
		imageContainer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Fragment f = new ProfileSetupFirst();
				ft.replace(R.id.contentintab,f);
				ft.addToBackStack("home");
				ft.commit();
			}
		});
		
		wallBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CurrentCustomTab != 1){
					handleCustomTabsClicked(1);
					CurrentCustomTab = 1;
				}
			}
		});
		
		photosBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CurrentCustomTab != 2){
					handleCustomTabsClicked(2);
					CurrentCustomTab = 2;
				}
			}
		});
		
		meBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CurrentCustomTab != 3){
					handleCustomTabsClicked(3);
					CurrentCustomTab = 3;
				}
			}
		});
		
		togetherBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CurrentCustomTab != 4){
					handleCustomTabsClicked(4);
					CurrentCustomTab = 4;
				}
			}
		});
		
		workoutBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CurrentCustomTab != 5){
					handleCustomTabsClicked(5);
					CurrentCustomTab = 5;
				}
			}
		});
		
		listBtnWall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnWall.setImageResource(R.drawable.list_btn_selected);
				gridBtnWall.setImageResource(R.drawable.grid_btn_unselected);
			}
		});
		
		gridBtnWall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnWall.setImageResource(R.drawable.list_btn_unselected);
				gridBtnWall.setImageResource(R.drawable.grid_btn_selected);
			}
		});
		listBtnPhotos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnPhotos.setImageResource(R.drawable.list_btn_selected);
				gridBtnPhotos.setImageResource(R.drawable.grid_btn_unselected);
			}
		});
		gridBtnPhotos.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnPhotos.setImageResource(R.drawable.list_btn_unselected);
				gridBtnPhotos.setImageResource(R.drawable.grid_btn_selected);
			}
		});
		listBtnMe.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnMe.setImageResource(R.drawable.list_btn_selected);
				gridBtnMe.setImageResource(R.drawable.grid_btn_unselected);
			}
		});
		gridBtnMe.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnMe.setImageResource(R.drawable.list_btn_unselected);
				gridBtnMe.setImageResource(R.drawable.grid_btn_selected);
			}
		});
		listBtnTogether.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnTogether.setImageResource(R.drawable.list_btn_selected);
				gridBtnTogether.setImageResource(R.drawable.grid_btn_unselected);
			}
		});
		gridBtnTogether.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnTogether.setImageResource(R.drawable.list_btn_unselected);
				gridBtnTogether.setImageResource(R.drawable.grid_btn_selected);
			}
		});
		listBtnWorkout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnWorkout.setImageResource(R.drawable.list_btn_selected);
				gridBtnWorkout.setImageResource(R.drawable.grid_btn_unselected);
			}
		});
		gridBtnWorkout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listBtnWorkout.setImageResource(R.drawable.list_btn_unselected);
				gridBtnWorkout.setImageResource(R.drawable.grid_btn_selected);
			}
		});
	}
	
	public void handleCustomTabsClicked(int which){
		
		wallLay.setVisibility(View.GONE);
		photosLay.setVisibility(View.GONE);
		meLay.setVisibility(View.GONE);
		togetherLay.setVisibility(View.GONE);
		workoutLay.setVisibility(View.GONE);
		
		wallText.setTextColor(Color.BLACK);
		photosText.setTextColor(Color.BLACK);
		meText.setTextColor(Color.BLACK);
		togetherText.setTextColor(Color.BLACK);
		workoutText.setTextColor(Color.BLACK);
		
		wallImg.setImageResource(R.drawable.wall_icon_unselected);
		photosImg.setImageResource(R.drawable.photo_icon_unselected);
		meImg.setImageResource(R.drawable.me_icon_unselected);
		togetherImg.setImageResource(R.drawable.together_unselected);
		workoutImg.setImageResource(R.drawable.workout_unselected);
		
		switch (which) {
		case 1:
			wallText.setTextColor(res.getColor(R.color.topbar_blue_back_color));
			wallImg.setImageResource(R.drawable.wall_icon_selected);
			wallLay.setVisibility(View.VISIBLE);
			break;
		case 2:
			photosText.setTextColor(res.getColor(R.color.topbar_blue_back_color));
			photosImg.setImageResource(R.drawable.photo_icon_selected);
			photosLay.setVisibility(View.VISIBLE);
			break;
		case 3:
			meText.setTextColor(res.getColor(R.color.topbar_blue_back_color));
			meImg.setImageResource(R.drawable.me_icon_selected);
			meLay.setVisibility(View.VISIBLE);
			break;
		case 4:
			togetherText.setTextColor(res.getColor(R.color.topbar_blue_back_color));
			togetherImg.setImageResource(R.drawable.together_selected);
			togetherLay.setVisibility(View.VISIBLE);
			break;
		case 5:
			workoutText.setTextColor(res.getColor(R.color.topbar_blue_back_color));
			workoutImg.setImageResource(R.drawable.workout_selected);
			workoutLay.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	public void addAchievements(){
		int upto = 5;
		for(int i=0;i<upto;i++){
			try {
				RelativeLayout lays = (RelativeLayout) inflater.inflate(R.layout.achievement_list_item,null);
				achievementContainer.addView(lays);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.e("key","pressed");
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.e("back","pressed");
			if(MainTabActivity.menu.isMenuShowing()){
				Log.e("menu","showing");
				MainTabActivity.menu.showContent();
			}
			return true;
		}else{
			return false;
		}   
	}

	//ASYNC TASK class to call any service needed in this screen.
	class CallService extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Loading...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.getAchievementService(getActivity(),"");
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
				
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}

	//ACHIEVEMENT CRITERIA IPHONE========
	

	//===========================================
}