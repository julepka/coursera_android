package com.example.dailyphoto;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {
	
	private EditText serverEditText;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private MainActivity mainActivity;
	private Button loginButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		mainActivity = (MainActivity) getActivity();
		
		serverEditText = (EditText) view.findViewById(R.id.server);
		usernameEditText = (EditText) view.findViewById(R.id.username);
		passwordEditText = (EditText) view.findViewById(R.id.password);
		
		loginButton = (Button) view.findViewById(R.id.login);
		loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
		
	    return view;
	}
	
	private void login() {
		String username = usernameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		String server = serverEditText.getText().toString();
		
		mainActivity.gridFragment = null;
		mainActivity.photoService.login(server, username, password);
		try {
			mainActivity.photoService.getPhotoList();
		} catch (Exception e) {
			Log.e("GetPhotoListTask", e.toString());
			Toast.makeText(getActivity(), "Incorrect username or password", Toast.LENGTH_LONG).show();
		}
	}

}
