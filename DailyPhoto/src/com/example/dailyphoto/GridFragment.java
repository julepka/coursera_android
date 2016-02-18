package com.example.dailyphoto;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class GridFragment extends Fragment {
	
	static final String path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
			"MyCameraApp").getAbsolutePath();
	
	private GridView gridView;
	private GridImageAdapter gridImageAdapter;
	private Button captureButton;
	private Button filterButton;
	
	MainActivity mainActivity;
	
	private Photo photoToUpload;
	private ArrayList<Long> selectList;
	
	private enum Mode { GENERAL, FILTER };
	Mode currentMode = Mode.GENERAL;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_grid, container, false);
		mainActivity = (MainActivity) getActivity();
		mainActivity.photoService.downloadMissingFiles();
		
		gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Photo photo = (Photo) (mainActivity.photoService.photoList.toArray())[position];
				if(currentMode == Mode.GENERAL) {
					mainActivity.openSinglePhotoFragment(photo);
				} else if(currentMode == Mode.FILTER) {
					if(selectList == null) {
						selectList = new ArrayList<Long>();
					}
					if(selectList.contains(photo.getId())) {
						selectList.remove(photo.getId());
						view.setAlpha((float) 1);
					} else {
						selectList.add(photo.getId());
						view.setAlpha((float) 0.5);
					}
					
				}
			}
		});
		
		captureButton = (Button) view.findViewById(R.id.captureButton);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View v) {
            	if(currentMode == Mode.GENERAL) {
	            	Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	        		try {
	        			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(createImageFile()));
	        		} catch (Exception e) {
	        			Log.i("exception", "failed to creat intent", e);
	        			return;
	        		}
	        		startActivityForResult(intent, 1);
            	} else if(currentMode == Mode.FILTER) {
					currentMode = Mode.GENERAL;
					captureButton.setText("Take Photo");
					filterButton.setText("Filter Photos");
					for(int i=0; i<gridView.getChildCount(); i++) {
					    ImageView child = (ImageView)gridView.getChildAt(i);
					    child.setAlpha((float) 1);
					}
					selectList = null;
            	}
            }
        });
		
		filterButton = (Button) view.findViewById(R.id.filterButton);
		filterButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(currentMode == Mode.GENERAL) {
					currentMode = Mode.FILTER;
					mainActivity.showToast("Select photos to filter");
					captureButton.setText("Cancel");
					filterButton.setText("Done");
				} else if(currentMode == Mode.FILTER) {
					currentMode = Mode.GENERAL;
					captureButton.setText("Take Photo");
					filterButton.setText("Filter Photos");
					for(int i=0; i<gridView.getChildCount(); i++) {
					    ImageView child = (ImageView)gridView.getChildAt(i);
					    child.setAlpha((float) 1);
					}
					filter(selectList);
					selectList = null;
				}
			}
		});
		
	    return view;
	}
	
	public void onResume() {
		super.onResume();
		if(mainActivity.photoService.photoList != null) {
			gridImageAdapter = new GridImageAdapter(mainActivity, (ArrayList<Photo>) mainActivity.photoService.photoList);
			gridView.setAdapter(gridImageAdapter);
		}
	}
	
	private File createImageFile() throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String imageFileName = dateFormat.format(Calendar.getInstance().getTime());
		new File(path).mkdirs();
		File image = new File(path, imageFileName);
		image.createNewFile();
		image.setReadable(true);
		image.setWritable(true);
		photoToUpload = new Photo(imageFileName, image.getAbsolutePath());
		return image;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		Log.i("test", "got activity result, code=" + requestCode + " result=" + resultCode);
		if (requestCode == 1) {
			if (resultCode == mainActivity.RESULT_OK) {
				Log.i("test", "camera result");
				mainActivity.photoService.uploadPhoto(photoToUpload);
			}
		}
	}
	
	public void updateGrid() {
		Log.d("test", "update");
		//gridImageAdapter.notifyDataSetChanged();
		gridImageAdapter = new GridImageAdapter(mainActivity, (ArrayList<Photo>) mainActivity.photoService.photoList);
		gridView.setAdapter(gridImageAdapter);
	}
	
	public void filter(final ArrayList<Long> idList) {
		final CharSequence[] items = {" Grayscale "," Sepia "," Invert Colors ",
									  " Brighten ", " Darken ", " Blur ", " Sharpen "};
        final ArrayList<Integer> selectedItems=new ArrayList<Integer>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Filters");
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
				if (isChecked) {
	                 selectedItems.add(indexSelected);
	             } else if (selectedItems.contains(indexSelected)) {
	                 selectedItems.remove(Integer.valueOf(indexSelected));
	             }
			}
     })
     .setPositiveButton("OK", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
            mainActivity.photoService.filterPhotos(idList, selectedItems);
         }
     })
     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
        	 
         }
     });
        AlertDialog dialog = builder.create();
        dialog.show();
	}
	
}
