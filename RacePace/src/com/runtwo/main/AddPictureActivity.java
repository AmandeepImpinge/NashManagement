package com.runtwo.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.runtwo.constants.GlobalConstants;
import com.runtwo.main.R;
import com.runtwo.utils.ImageFileManipulation;
import com.runtwo.utils.Utils;
import com.runtwo.webservice.WebServiceHandler;

public class AddPictureActivity extends Activity{

	ImageView smily_one,smily_two,smily_three,smily_four,smily_fifth,smily_six;
	TextView doneBtn;
	EditText messageBox;
	int currentlySelectedSmily = 1;
	Globals global;
	ArrayList<String> trackedPath = new ArrayList<String>();
	String calories,distance,duration,currentpace;AlertDialog dialog;
	String dialoglist[] = {"Camera","Gallery","Cancel"};
	ImageView postImg;
	String strBase64 = "";
	String imageName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_picture);
	
		global = (Globals)getApplicationContext();
		trackedPath = global.getRunningTrackedPath();
		
		Intent ii = getIntent();
		calories = ii.getStringExtra(GlobalConstants.ADD_RACEDET_CALORIES);
		distance = ii.getStringExtra(GlobalConstants.ADD_RACEDET_DISTANCE);
		duration = ii.getStringExtra(GlobalConstants.ADD_RACEDET_DURATION);
		currentpace = ii.getStringExtra(GlobalConstants.ADD_RACEDET_CURRENT_PACE);
		
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
		messageBox = (EditText)findViewById(R.id.add_note_ed);
		
		postImg = (ImageView)findViewById(R.id.add_img);
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
				new AddRaceDetails().execute("");
			}
		});
		
		postImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createPictureDialog();
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
	
	public void createPictureDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(AddPictureActivity.this);

		builder.setTitle("Choose Action");
		builder.setItems(dialoglist,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0: 
					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(AddPictureActivity.this.getPackageManager()) != null) {
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
				File f = new File(Utils.getRealPathFromURI(AddPictureActivity.this,imageUri));
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
						Bitmap bitmap2 = ThumbnailUtils.extractThumbnail(new ImageFileManipulation(AddPictureActivity.this).getThumbnail(imageUri, 50), 50, 50);
						postImg.setImageBitmap(bitmap2);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}else{
					Toast.makeText(AddPictureActivity.this,"Error Getting Image, Try again.",Toast.LENGTH_SHORT).show();
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
	
	class AddRaceDetails extends AsyncTask<String, Integer, String>{
		ProgressDialog pd;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(AddPictureActivity.this).show(AddPictureActivity.this,"","Submiting...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			String result = "false";
			try {
				String path = "";
				int upto = trackedPath.size();
				for(int i=0;i<upto;i++){
					if(i == 0){
						path = trackedPath.get(i);
					}else{
						path = path+":"+trackedPath.get(i);
					}
				}
				result = WebServiceHandler.addRaceDetailsService(AddPictureActivity.this,"",path,distance,"","",
							distance,calories,duration,messageBox.getText().toString()+"", 
							strBase64,imageName,currentpace);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result.equalsIgnoreCase("true")){
				AddPictureActivity.this.finish();
				Intent in = new Intent(AddPictureActivity.this,ShareRun.class);
				in.putExtra(GlobalConstants.ADD_RACEDET_DISTANCE,distance);
				in.putExtra(GlobalConstants.ADD_RACEDET_DURATION,duration);
				in.putExtra(GlobalConstants.ADD_RACEDET_CALORIES,calories);
				in.putExtra(GlobalConstants.ADD_RACEDET_CURRENT_PACE,currentpace);
				startActivity(in);
			}else if(result.equalsIgnoreCase("false")){
				Toast.makeText(AddPictureActivity.this,""+global.getMessageOfResponse(),Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(AddPictureActivity.this,"Error connecting to server, try again...",Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}