package com.runtwo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class NotificationFragment extends Fragment implements OnKeyListener{

	LayoutInflater inflater;
	ActionBarRun ab;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.inflater = inflater;
		
		container = (LinearLayout)inflater.inflate(R.layout.notifications_fragment,null);
		
		ab = (ActionBarRun)container.findViewById(R.id.action_bar);
		ab.menuBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
		
		ab.findViewById(R.id.run_txt).setVisibility(View.INVISIBLE);
		
		container.setOnKeyListener(this);
		return container;
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
	
}
