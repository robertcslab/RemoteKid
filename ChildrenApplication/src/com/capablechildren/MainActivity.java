package com.capablechildren;

import com.capablechildren.ColorFullSoundsGame.ColorFullSoundGame;
import com.example.parentapplication.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

	private Button btnExit;
	private Button btnLogout;
	public static final String TAG = MainActivity.class.getSimpleName();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			navigateToLogin();
		}
		else {
			Log.i(TAG, currentUser.getUsername());
		}
		
		
		
		//*******************************************************
		//					Sending Options
		//*******************************************************
		 btnExit = (Button) findViewById(R.id.btn_exitapp);		
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				finish();
			}
				
			}
		);
		
		btnLogout=(Button) findViewById(R.id.btnLogOut);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (ParseUser.getCurrentUser()!=null)
				{
					ParseUser.getCurrentUser();
					ParseUser.logOut();
				navigateToLogin();
				}
			}
		});
		
		
		
	}

	private void navigateToLogin() {
		Intent intent = new Intent(this, LogIn.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
	
}
