package com.example.dailyphoto;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public PhotoServiceProvider photoService;
	public GridFragment gridFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
		}
		photoService = new PhotoServiceProvider(this);
		
		// set up notification service reminder
		Intent notificationIntent = new Intent(getBaseContext(), NotificationService.class);
		PendingIntent contentIntent = PendingIntent.getService(getBaseContext(), 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		am.cancel(contentIntent);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES
				/ 30, AlarmManager.INTERVAL_FIFTEEN_MINUTES / 150,
		// System.currentTimeMillis() + AlarmManager.INTERVAL_DAY,
		// AlarmManager.INTERVAL_DAY,
				contentIntent);
	}
	
	public void openPhotoListFragment() {
		if (gridFragment == null) {
			Log.d("test", "creating new grid");
			showToast("Welcome");
			gridFragment = new GridFragment();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container, gridFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.addToBackStack(null);
			ft.commit(); 
		} else {
			Log.d("test", "updating new grid");
			updateGrid();
		}
	}
	
	public void openSinglePhotoFragment(Photo photo) {
		PhotoFragment photoFragment = new PhotoFragment(photo);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.container, photoFragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	public void updateGrid() {
		gridFragment.updateGrid();
	}
	
	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
