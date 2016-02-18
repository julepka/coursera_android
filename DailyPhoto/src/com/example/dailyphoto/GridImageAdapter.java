package com.example.dailyphoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.media.ExifInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridImageAdapter extends BaseAdapter {

	private Context mContext;
	ArrayList<Photo> images;

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return images.get(position);
	}

	public long getItemId(int position) {
		return images.get(position).hashCode();
	}

	public GridImageAdapter(Context c, ArrayList<Photo> imageList) {
		mContext = c;
		images = imageList;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		//set params to imageview: size of the cell and crop style
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(250, 250)); 
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		} else {
			imageView = (ImageView) convertView;
		}

		String name = images.get(position).getTitle();
		
		if (name == null || name.isEmpty()) {
			return imageView;
		}

		//create or set existing preview image
		File scaled = new File(GridFragment.path, name + "_scaled");
		Log.d("test", "Name: " + name);
		Bitmap rotatedBitmap = loadScaledImage(GridFragment.path, name, 300, 300);

		if (rotatedBitmap == null) {
			return imageView;
		}
		try {
			rotatedBitmap.compress(CompressFormat.PNG, 75, new FileOutputStream(scaled));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		imageView.setImageBitmap(rotatedBitmap);
		return imageView;
	}

//calculates how to resize the image
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
	
		if (height > reqHeight || width > reqWidth) {
	
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
			
			while ((halfHeight / inSampleSize) < reqHeight && (halfWidth / inSampleSize) < reqWidth) {
				inSampleSize /= 2;
			}
		}
	
		return inSampleSize;
	}
	
	//scale image to load and fit to the imageview avoiding complex image resizing and lags
		public static Bitmap loadScaledImage(String path, String name, int width, int height) {
			
			File image = new File(path, name);
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(image.getAbsolutePath(), options);
			options.inSampleSize = calculateInSampleSize(options, width, height);
			options.inJustDecodeBounds = false;

			ExifInterface exif;
			try {
				exif = new ExifInterface(image.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

			Bitmap scaledBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
			if (scaledBitmap == null) {
				return null;
			}

			Bitmap rotatedBitmap = null;

			//change photo orientation if needed
			Matrix matrix = new Matrix();

			if (orientation == 6) {
				matrix.postRotate(90);
			} else if (orientation == 3) {
				matrix.postRotate(180);
			} else if (orientation == 8) {
				matrix.postRotate(270);
			}

			rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(),
					matrix, true);

			return rotatedBitmap;
		}
}
