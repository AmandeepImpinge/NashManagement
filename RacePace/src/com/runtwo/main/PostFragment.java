package com.runtwo.main;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.main.MyFollowers.FollowerFollowingAdapter;
import com.runtwo.utils.ImageFileManipulation;
import com.runtwo.utils.PhotoUtil;
import com.runtwo.utils.Utility;
import com.runtwo.webservice.WebServiceHandler;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostFragment extends Fragment implements OnClickListener, OnKeyListener{

	TextView submit_btn,cancel_btn,postinruntext,postfbtext,posttwittertext;
	EditText ed;
	CheckBox postinruncheck,postfbcheck,posttwittercheck; 
	ActionBarRun ab;
	ProgressDialog mProgressDialog;
	Globals global;
	AlertDialog dialog;
	String dialoglist[] = {"Camera","Gallery","Cancel"};
	ImageView postImg;
	String strBase64 = "";
	Uri cameraImageUri;
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		container = (LinearLayout)inflater.inflate(R.layout.post_frag_lay,null);
		
		ab = (ActionBarRun) container.findViewById(R.id.action_bar);
		ab.menuBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MainTabActivity.menu.showMenu();
			}
		});
		ab.runBtn.setVisibility(View.INVISIBLE);
		
		global = (Globals)getActivity().getApplicationContext();
		
		initViews(container);
		setClickListeners();
		
		container.findViewById(R.id.attch_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createPictureDialog();
			}
		});
		
		container.setOnKeyListener(this);
		return container; 
	}
	
	public void initViews(ViewGroup container){
		submit_btn = (TextView)container.findViewById(R.id.submit_txt);
		cancel_btn = (TextView)container.findViewById(R.id.cancel_txt);
		ed = (EditText)container.findViewById(R.id.text_box);
		
		postinruntext = (TextView)container.findViewById(R.id.postrun_txt);
		postfbtext = (TextView)container.findViewById(R.id.postfb_txt);
		posttwittertext = (TextView)container.findViewById(R.id.posttwitter_txt);
		
		postinruncheck = (CheckBox)container.findViewById(R.id.post_run_check);
		postfbcheck = (CheckBox)container.findViewById(R.id.post_fb_check);
		posttwittercheck = (CheckBox)container.findViewById(R.id.post_twitter_check);
		
		postImg = (ImageView)container.findViewById(R.id.post_img);
	}
	
	public void setClickListeners(){
		submit_btn.setOnClickListener(this);
		cancel_btn.setOnClickListener(this);
		postinruntext.setOnClickListener(this);
		postfbtext.setOnClickListener(this);
		posttwittertext.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.postrun_txt:
			setCheckedTheBox(postinruncheck);
			break;
		case R.id.postfb_txt:
			setCheckedTheBox(postfbcheck);
			break;
		case R.id.posttwitter_txt:
			setCheckedTheBox(posttwittercheck);
			break;
		case R.id.submit_txt:
			
			break;
		case R.id.cancel_txt:
			
			break;
		}
	}
	
	public void setCheckedTheBox(CheckBox box){
		if(box.isChecked()){
			box.setChecked(false);
		}else{
			box.setChecked(true);
		}
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
	
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(MainTabActivity.menu.isMenuShowing()){
				MainTabActivity.menu.showContent();
			}
			return true;
		}else{
			return false;	
		}
	}
	
	class PostOnRun extends AsyncTask<String,Integer,String>{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity()).show(getActivity(),"","Posting...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "true";
			try {
				result = WebServiceHandler.postFeedService(getActivity(),"","","");
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
				Toast.makeText(getActivity(),"No result found...",Toast.LENGTH_SHORT).show();
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(getActivity(),""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(),"Error Connecting to server.",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	
	public void createPictureDialog(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle("Choose Action");
		builder.setItems(dialoglist,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					cameraImageUri = Utility.getImageUri();
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, cameraImageUri);
					startActivityForResult(intent, GlobalConstants.POST_CAMERA);
					
					dialog.dismiss();
					break;
				case 1:
					Intent intent1 = new Intent();
					intent1.setType("image/*");
					intent1.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent1, GlobalConstants.POST_GALLERY);

					dialog.dismiss();
					break;
				case 2:
					//Toast.makeText(getActivity(),"Cancel",Toast.LENGTH_SHORT).show();
					dialog.dismiss();
					break;
				}
			}
		});
		
		dialog = builder.create();
		dialog.show();
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** Aakansha, Please check the issue in this case **/
		if(requestCode == GlobalConstants.POST_GALLERY && resultCode == getActivity().RESULT_OK){
			
			int drigree;
			Uri imageUri = data.getData();
			try {
				//drigree = PhotoUtil.getImageOrientation(imageUri);
				InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inTempStorage = new byte[16 * 1024];
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(new FileInputStream(imageUri.getPath()), null,options);
				int IMAGE_MAX_SIZE = 640;
				int scale = 1;
				if (options.outHeight > IMAGE_MAX_SIZE
						|| options.outWidth > IMAGE_MAX_SIZE) {
					scale = (int) Math.pow(
							2,
							(int) Math.round(Math.log(IMAGE_MAX_SIZE
									/ (double) Math.max(options.outHeight,
											options.outWidth))
									/ Math.log(0.5)));
				}
				// Decode with inSampleSize
				BitmapFactory.Options mOption = new BitmapFactory.Options();
				mOption.inSampleSize = scale;
				mOption.inPurgeable = true;
				mOption.outHeight = 480;
				mOption.outWidth = 640;
				Bitmap b = BitmapFactory.decodeStream(inputStream, null,mOption);
				

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				b.compress(Bitmap.CompressFormat.PNG, 90, stream);
				byte[] byte_arr = stream.toByteArray();
				strBase64 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
				Log.e("image length",""+strBase64.length());
				
				try {
					Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(new ImageFileManipulation(getActivity()).getThumbnail(imageUri, 50), 50, 50);
					postImg.setImageBitmap(bitmap2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//PhotoUtil.rotate(imageUri, drigree,getActivity());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(requestCode == GlobalConstants.POST_CAMERA && resultCode == getActivity().RESULT_OK){
			int drigree;

			try {
				//drigree = PhotoUtil.getImageOrientation(cameraImageUri);
				java.io.InputStream inputStream = getActivity().getContentResolver().openInputStream(cameraImageUri);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inTempStorage = new byte[16 * 1024];
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(new FileInputStream(cameraImageUri.getPath()), null,options);
				int IMAGE_MAX_SIZE = 640;
				int scale = 1;
				if (options.outHeight > IMAGE_MAX_SIZE || options.outWidth > IMAGE_MAX_SIZE) {
					scale = (int) Math.pow(
							2,
							(int) Math.round(Math.log(IMAGE_MAX_SIZE
									/ (double) Math.max(options.outHeight,
											options.outWidth))
									/ Math.log(0.5)));
				}
				// Decode with inSampleSize
				BitmapFactory.Options mOption = new BitmapFactory.Options();
				mOption.inSampleSize = scale;
				mOption.inPurgeable = true;
				mOption.outHeight = 480;
				mOption.outWidth = 640;
				Bitmap b = BitmapFactory.decodeStream(inputStream, null,
						mOption);
				//PhotoUtil.rotate(cameraImageUri, drigree,getActivity());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(new ImageFileManipulation(getActivity()).getThumbnail(cameraImageUri, 50), 50, 50);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap2.compress(Bitmap.CompressFormat.PNG, 90, stream);
				byte[] byte_arr = stream.toByteArray();
				strBase64 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
				
				postImg.setImageBitmap(bitmap2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}