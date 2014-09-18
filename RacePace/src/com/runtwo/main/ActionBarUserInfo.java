package com.runtwo.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.runtwo.main.R;

public class ActionBarUserInfo extends RelativeLayout{

	public ActionBarUserInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		RelativeLayout lay = (RelativeLayout)inflater.inflate(R.layout.action_bar_user_info,null);
		
		addView(lay);
		
	}

}
