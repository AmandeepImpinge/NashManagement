package com.runtwo.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.util.Log;

public class Utility {

	private static String imageName = null;
	private static String currentFileName = null;

	public static boolean isEmailValid(String email) {
		boolean isValid;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		} else {

			Log.i(".....Email", "Not valid");

			isValid = false;

		}
		return isValid;
	}

	public static Uri getImageUri() {
		File file = null;
		if (imageName != null) {
			file = new File(imageName);
			if (file.exists()) {
				file.delete();
				imageName = "";
			}

		}
		currentFileName = ImageFileManipulation.getFileName() + ".jpeg";
		imageName = ImageFileManipulation.EXTERNAL_MEMORY + currentFileName;
		file = new File(imageName);
		Uri imgUri = Uri.fromFile(file);
		System.out.println("Image uri" + imgUri);
		return imgUri;
	}

	public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,
			int borderWidth) {
		if (bitmap == null || bitmap.isRecycled()) {
			return null;
		}

		final int width = bitmap.getWidth() + borderWidth;
		final int height = bitmap.getHeight() + borderWidth;

		Bitmap canvasBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		Canvas canvas = new Canvas(canvasBitmap);
		float radius = width > height ? ((float) height) / 2f
				: ((float) width) / 2f;
		canvas.drawCircle(width / 2, height / 2, radius, paint);
		paint.setShader(null);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(borderWidth);
		canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2,
				paint);
		return canvasBitmap;
	}

	
	
	public static Bitmap createRoundImage(Bitmap loadedImage) {
		System.out.println("loadbitmap" + loadedImage);
		final int width = loadedImage.getWidth();
		final int height = loadedImage.getHeight();
		
		loadedImage = Bitmap.createScaledBitmap(loadedImage, width, height, true);
		Bitmap circleBitmap = Bitmap.createBitmap(loadedImage.getWidth(),
				loadedImage.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader(loadedImage,
				Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setShader(shader);

		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(loadedImage.getWidth() / 2, loadedImage.getHeight() / 2,
				loadedImage.getWidth() / 2, paint);
		return circleBitmap;
	}
}
