package com.example.parentapplication;


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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btnSend;
	private Button btnLogout;
	private Button btnInbox;
	private Button btnAddChild;
	private Button btnHelp;
	private Button btnExite;
	
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
		//	navigateToEnterPass();
		}
		
		//*******************************************************
		//					Sending Options
		//*******************************************************
		btnSend = (Button) findViewById(R.id.btnSendRequest);		
		
		btnSend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {

				Intent intent = new Intent(MainActivity.this, SendingOptions.class);
				
				startActivity(intent);
			}
				
			}
		);
		
		btnLogout=(Button) findViewById(R.id.btnLogOut);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (ParseUser.getCurrentUser()!=null)
				{
					ParseUser.getCurrentUser().logOut();
				navigateToLogin();
				}
			}
		});
		
		btnInbox=(Button) findViewById(R.id.btnRecieveRequest);
		btnInbox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent inbox=new Intent(MainActivity.this,ParentInbox.class);
			//	inbox.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(inbox);
			}
		});
		
		btnAddChild= (Button) findViewById(R.id.btnAddChild);
		btnAddChild.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent addChild=new Intent(MainActivity.this,AddChildActivity.class);
				startActivity(addChild);
			}
		});
		
		btnHelp= (Button) findViewById(R.id.btnHelp);
		btnHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//Intent help=new Intent()
				setContentView(R.layout.help);
			}
		});
	}//end on craete

	private void navigateToLogin() {
		Intent intent = new Intent(this, LogIn.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
	
	private void navigateToEnterPass()
	{
		Intent intent = new Intent(this, Unlock.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
}
