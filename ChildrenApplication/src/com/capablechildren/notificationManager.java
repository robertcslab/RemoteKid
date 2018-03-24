package com.capablechildren;

import java.util.List;

import com.capablechildren.ColorFullSoundsGame.ColorFullSoundGame;
import com.example.parentapplication.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.R.integer;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

public class notificationManager extends ParsePushBroadcastReceiver {

	public static String indexOfNotify="0";
	
	public String text="";
	
	@Override
	  public void onPushReceive(Context context, Intent intent) {

		//super.onPushReceive(context, intent);
		
		JSONObject receivedata = null;
	      try {
	    	   receivedata = new JSONObject(intent.getExtras().getString("com.parse.Data"));
	    	  indexOfNotify=  receivedata.getString(KidAppConstants.KEY_INDEX_NUMBER);
	    	   text= receivedata.getString("title");
	      } catch (JSONException e) {
	         // Json was not readable...
	    	  indexOfNotify="-1";//means error
	    	  text="Oops";
	      }
	  
	      NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
	     
	      builder.setSmallIcon(R.drawable.images);
	     
	      builder.setContentTitle(indexOfNotify);
	     
	      builder.setContentText(text);
	      
	     // builder.setContentIntent(myI);
	    
	      builder.setSound(Uri.parse("android.resource://com.example.parentapplication/raw/horse"));
	      
	      
	      NotificationManager notificationManager =
	      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	      
	      notificationManager.notify(1,builder.build());
	      Intent myIntent= new Intent(context, ColorFullSoundGame.class) ;
			
	      myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	      myIntent.setAction("com.parse.push.intent.OPEN");
	      context.startActivity(myIntent);
	     
	}
	

	 @Override
	  public void onPushOpen(Context context, Intent intent) {

		
		 
		 Toast toast = Toast.makeText(context,"zgjhgdjgfjs",Toast.LENGTH_LONG);
		 toast.show();


		 switch(Integer.parseInt(indexOfNotify))
		 {
		 //		Happiness
		 case 1:
			 Log.i("push Opennnnnnnnnnnnnnnnnn","case 1");
			
			 Intent hp=new Intent(context, ColorFullSoundGame.class);			 
			 hp.putExtras(intent.getExtras());
			 hp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 
			 context.startActivity(hp);
			
		      /*
			 AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("fuckckckc")
					.setTitle(R.string.error_title)
					.setPositiveButton(android.R.string.ok, null);
				AlertDialog dialog = builder.create();
				dialog.show();
			*/
			 
			 break;
		 }
		 
	  }
	 
	
}
