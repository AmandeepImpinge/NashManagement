package com.runtwo.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import com.runtwo.main.R;

public class ShareRun extends Activity{

	TextView shareBtn;
	CheckBox fbCheck,twitterCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_run);
		
		shareBtn = (TextView)findViewById(R.id.share_btn);
		fbCheck = (CheckBox)findViewById(R.id.fb_check);
		twitterCheck = (CheckBox)findViewById(R.id.twitter_check);
		
		shareBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShareRun.this.finish();
			}
		});
		
	}
	
}
