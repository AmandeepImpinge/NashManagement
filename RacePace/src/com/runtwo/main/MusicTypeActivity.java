package com.runtwo.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.runtwo.main.R;

public class MusicTypeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_type_list);
		
		findViewById(R.id.back_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleBack();
			}
		});
		
	}
	
	private void handleBack(){
		MusicTypeActivity.this.finish();
	}
		
}