package com.runtwo.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewHelrWOne extends TextView{

	public TextViewHelrWOne(Context context){
		super(context);
		init();
	}
	
	public TextViewHelrWOne(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewHelrWOne(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public void init() {
	    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "helr_w_one.ttf");
	    setTypeface(tf);
	}
	
}