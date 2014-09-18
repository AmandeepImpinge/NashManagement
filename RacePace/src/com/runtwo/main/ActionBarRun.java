package com.runtwo.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.runtwo.main.R;

public class ActionBarRun extends RelativeLayout{

	TextView runBtn;
	ImageView menuBtn;
	
	public ActionBarRun(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		RelativeLayout lays = (RelativeLayout)inflater.inflate(R.layout.action_bar_run,null);
		
		runBtn = (TextView)lays.findViewById(R.id.run_txt);
		menuBtn = (ImageView)lays.findViewById(R.id.menu_icon);
		
		addView(lays);
		
	}

}
