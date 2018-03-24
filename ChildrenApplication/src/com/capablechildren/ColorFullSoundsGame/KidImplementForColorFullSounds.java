package com.capablechildren.ColorFullSoundsGame;
import java.util.List;

import EQEvaluatorInterface.EQEvaluator.Kid;
import android.app.AlertDialog;
import android.util.Log;

import com.capablechildren.KidAppConstants;
import com.capablechildren.notificationManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class KidImplementForColorFullSounds implements Kid{

	String parent_id;
	boolean sendParamsToParentStatus=false;
	EQEvaluateOfColorFullSoundGame eqParams= new EQEvaluateOfColorFullSoundGame();
	
	
	@Override
	public String SetParent() {

		ParseUser mCurrentUser = ParseUser.getCurrentUser();		
		parent_id=(String) mCurrentUser.get(KidAppConstants.KEY_CHILDRENS_RELATION);
		/*
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(ParseConstants.KEY_OBJECT_ID, mCurrentUser.get(ParseConstants.KEY_CHILDRENS_RELATION));
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> parent, ParseException e) {
				List<ParseUser> parentList;
				if (e == null) {
					// Success
					parentList = parent;
					//there is just one parent
					for(ParseUser user : parentList) {
						parent_id = user.getObjectId();
					}
					
				}
				else {
					parent_id="not found";
					Log.e("Show Recivers", e.getMessage());
					/*
					AlertDialog.Builder builder = new AlertDialog.Builder(EQEvaluateHappy.this);
					builder.setMessage(e.getMessage())
						.setTitle(R.string.error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}
			}
		});
		*/
		return parent_id;
	}
	
	@Override
	public boolean SendParameterToParent(ParseObject message) {
		
		message.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// success!
			//Toast.makeText(RecipientsActivity.this, R.string.success_message, Toast.LENGTH_LONG).show();
					sendParamsToParentStatus=true;
					sendPushNotification();
				}
				else {
					Log.i("can not send message", "chi begam");
					sendParamsToParentStatus=false;
					/*
					AlertDialog.Builder builder = new AlertDialog.Builder(DrawTheVoiceActivity.class);
					builder.setMessage(R.string.error_sending_message)
						.setTitle(R.string.error_selecting_file_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					*/
				}
			}
		});
		
		return sendParamsToParentStatus;
	}
	
	@Override
	public void PlayGame(int levelNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ParseObject createMessage(boolean[] involvedEQParameters) {

		ParseObject kidEQPoints = new ParseObject(KidAppConstants.CLASS_EQPARAMETERS);
		notificationManager notifManger=new notificationManager();
		
		kidEQPoints.put(KidAppConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
		kidEQPoints.put(KidAppConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
		kidEQPoints.put(KidAppConstants.KEY_INDEX_NUMBER,notifManger.indexOfNotify);
		kidEQPoints.put(KidAppConstants.KEY_RECEIVER_ID, parent_id);
		for (int i=0;i<15;i++)
		{
			if(involvedEQParameters[i])
			switch(i)
			{
			case 1:
					kidEQPoints.put(KidAppConstants.KEY_HAPPINESS,eqParams.HappinessEvaluate());
				break;
				
			case 2:
					kidEQPoints.put(KidAppConstants.KEY_OPTIMISM,eqParams.OptimismEvaluate());
				break;
			
			case 3:
					kidEQPoints.put(KidAppConstants.KEY_PROBLEM_SOLVING,eqParams.ProblemSolvingEvaluate());
			break;
			
			case 4:
					kidEQPoints.put(KidAppConstants.KEY_SELF_REGARD,eqParams.SelfEsteemEvaluate());
			break;
			
			}
		}

		return kidEQPoints;
		
	}

	@Override
	public void sendPushNotification() 
	{
		ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
		query.whereContains(KidAppConstants.KEY_USER_ID, parent_id);
		//query.whereEqualTo("objectId", parent_id);
		
		// send push notification
		ParsePush push = new ParsePush();
		push.setQuery(query);
		/*
		JSONObject data = new JSONObject();
		try {
			data.put(ParseConstants.KEY_INDEX_NUMBER, SendingOptions.getIndexOfNotification());
			data.put("title", SendingOptions.getparentMessages());
			
		} catch (JSONException e) {
			Log.d("Unable to create JSon", "JSONException in setMessage: " + e.getMessage());
		}
	
		
		push.setData(data);
		*/
		push.setMessage("this notification send from : "+ParseUser.getCurrentUser().getUsername()+"\n contains score!");
		push.sendInBackground();
	
		
	}
	

}
