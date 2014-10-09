package com.runtwo.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.utils.ImageFileManipulation;
import com.runtwo.utils.Utils;

public class ShareRun extends Activity{

	TextView shareBtn,username,finishedMiles,duration,currpace,calories;
	CheckBox fbCheck,twitterCheck;
	String calo,dur,distance,pace;
	Globals global;
	AlertDialog dialog;
	String dialoglist[] = {"Camera","Gallery","Cancel"};
	ImageView postImg;
	String strBase64 = "";
	String imageName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_run);
		
		initViews();
		
		global = (Globals) getApplicationContext();
		
		Intent ii = getIntent();
		calo = ii.getStringExtra(GlobalConstants.ADD_RACEDET_CALORIES);
		distance = ii.getStringExtra(GlobalConstants.ADD_RACEDET_DISTANCE);
		dur = ii.getStringExtra(GlobalConstants.ADD_RACEDET_DURATION);
		pace = ii.getStringExtra(GlobalConstants.ADD_RACEDET_CURRENT_PACE);
		
		shareBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ShareRun.this.finish();
			}
		});
		
		postImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createPictureDialog();
			}
		});
		
		username.setText(global.getUserName());
		finishedMiles.setText("has finished his run for "+distance+" miles");
		duration.setText(dur);
		calories.setText(calo);
		currpace.setText(pace);
		
	}
	
	public void createPictureDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(ShareRun.this);

		builder.setTitle("Choose Action");
		builder.setItems(dialoglist,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0: 
					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(ShareRun.this.getPackageManager()) != null) {
						startActivityForResult(takePictureIntent, GlobalConstants.POST_CAMERA);					        
					}
					dialog.dismiss();
					break;
				case 1:
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i,GlobalConstants.POST_GALLERY);

					dialog.dismiss();
					break;
				case 2:
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

		if(requestCode == GlobalConstants.POST_GALLERY && resultCode == RESULT_OK){

			int drigree;
			Uri imageUri = data.getData();
			try {
				//drigree = PhotoUtil.getImageOrientation(imageUri);
				InputStream inputStream = getContentResolver().openInputStream(imageUri);

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inTempStorage = new byte[16 * 1024];
				options.inJustDecodeBounds = true;
				File f = new File(Utils.getRealPathFromURI(ShareRun.this,imageUri));
				if(f.exists()){
					imageName = ""+f.getName();
					BitmapFactory.decodeStream(new FileInputStream(/*imageUri.getPath()*/f), null,options);
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
						Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(new ImageFileManipulation(ShareRun.this).getThumbnail(imageUri, 50), 50, 50);
						postImg.setImageBitmap(bitmap2);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}else{
					Toast.makeText(ShareRun.this,"Error Getting Image, Try again.",Toast.LENGTH_SHORT).show();
				}
				//PhotoUtil.rotate(imageUri, drigree,getActivity());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if(requestCode == GlobalConstants.POST_CAMERA && resultCode == RESULT_OK){
			
		    Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        try {
	        	Bitmap scaledBitmap = Utils.scaleDown(imageBitmap,800,true);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				scaledBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
				byte[] byte_arr = stream.toByteArray();
				strBase64 = Base64.encodeToString(byte_arr,Base64.DEFAULT);
				
				imageName = ImageFileManipulation.getFileName()+"_img.png";
				
				postImg.setImageBitmap(scaledBitmap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initViews(){
		shareBtn = (TextView)findViewById(R.id.share_btn);
		username = (TextView)findViewById(R.id.user_name);
		finishedMiles = (TextView)findViewById(R.id.no_of_miles);
		duration = (TextView)findViewById(R.id.duration);
		currpace = (TextView)findViewById(R.id.curr_pace);
		calories = (TextView)findViewById(R.id.calories);
		
		fbCheck = (CheckBox)findViewById(R.id.fb_check);
		twitterCheck = (CheckBox)findViewById(R.id.twitter_check);
		postImg = (ImageView)findViewById(R.id.add_pic);
	}
	
}