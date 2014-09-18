package com.runtwo.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextHelveticaLtZero extends EditText{

	public EditTextHelveticaLtZero(Context context) {
		super(context);
		init();
	}
	
	public EditTextHelveticaLtZero(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public EditTextHelveticaLtZero(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init(){
		
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"helvetica_neue_ltstd_lt_0.otf");
		setTypeface(tf);
	}
	
}