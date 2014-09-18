package com.runtwo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.runtwo.main.R;

public class AddPictureActivity extends Activity{

	ImageView smily_one,smily_two,smily_three,smily_four,smily_fifth,smily_six;
	TextView doneBtn;
	int currentlySelectedSmily = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_picture);
		
		initViews();
		addClicks();
		
	}
	
	private void initViews(){
		smily_one = (ImageView)findViewById(R.id.first_smily);
		smily_two = (ImageView)findViewById(R.id.sec_smily);
		smily_three = (ImageView)findViewById(R.id.third_smily);
		smily_four = (ImageView)findViewById(R.id.fourth_smily);
		smily_fifth = (ImageView)findViewById(R.id.fifth_smily);
		smily_six = (ImageView)findViewById(R.id.sixth_smily);
		
		doneBtn = (TextView)findViewById(R.id.done_btn);
	}
	
	private void addClicks(){
		smily_one.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 1){
					changeSmilySelection(1);
				}
			}
		});
		smily_two.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 2){
					changeSmilySelection(2);
				}
			}
		});
		smily_three.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 3){
					changeSmilySelection(3);
				}
			}
		});
		smily_four.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 4){
					changeSmilySelection(4);
				}
			}
		});
		smily_fifth.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 5){
					changeSmilySelection(5);
				}
			}
		});
		smily_six.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(currentlySelectedSmily != 6){
					changeSmilySelection(6);
				}
			}
		});
		
		doneBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AddPictureActivity.this.finish();
				startActivity(new Intent(AddPictureActivity.this,ShareRun.class));
			}
		});
	}

	private void changeSmilySelection(int which){
		smily_one.setImageResource(R.drawable.smiley_one_unselected);
		smily_two.setImageResource(R.drawable.smiley_two_unselected);
		smily_three.setImageResource(R.drawable.smiley_three_unselected);
		smily_four.setImageResource(R.drawable.smiley_four_unselected);
		smily_fifth.setImageResource(R.drawable.smiley_five_unselected);
		smily_six.setImageResource(R.drawable.smiley_six_unselected);
		
		switch (which) {
		case 1:
			currentlySelectedSmily = 1;
			smily_one.setImageResource(R.drawable.smiley_one_selected);
			break;
		case 2:
			currentlySelectedSmily = 2;
			smily_two.setImageResource(R.drawable.smiley_two_selected);
			break;
		case 3:
			currentlySelectedSmily = 3;
			smily_three.setImageResource(R.drawable.smiley_three_selected);
			break;
		case 4:
			currentlySelectedSmily = 4;
			smily_four.setImageResource(R.drawable.smiley_four_selected);
			break;
		case 5:
			currentlySelectedSmily = 5;
			smily_fifth.setImageResource(R.drawable.smiley_five_selected);
			break;
		case 6:
			currentlySelectedSmily = 6;
			smily_six.setImageResource(R.drawable.smiley_six_selected);
			break;
		}
	}
}