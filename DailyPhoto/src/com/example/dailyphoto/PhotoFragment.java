package com.example.dailyphoto;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFragment extends Fragment {
	
	Photo photo;
	
	public PhotoFragment(Photo photo) {
		this.photo = photo;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_photo, container, false);
		
		ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
		imageView.setImageURI(Uri.parse(photo.getUrl()));
		
		TextView textView = (TextView)view.findViewById(R.id.textView);
		textView.setText(photo.getTitle());
		
		return view;
	}

}
