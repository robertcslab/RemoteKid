package com.capablechildren;
import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.capablechildren.KidAppConstants;
import com.example.parentapplication.R;

public class KidApp extends Application{ 
	
	@Override
	public void onCreate() {
		super.onCreate();
        Parse.initialize(this, "1tsz0JuAqq8ieym6AGdpKMP2tL6BXjuKuJ40mAzC", "e6oXzRmMWouBo5f7HVobuSFPNcF5OnznuH4gRH1a");  
        ParseUser.enableRevocableSessionInBackground();
		PushService.setDefaultPushCallback(this, MainActivity.class,R.drawable.images);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(KidAppConstants.KEY_USER_ID, user.getObjectId());
		installation.saveInBackground();
	}
}
