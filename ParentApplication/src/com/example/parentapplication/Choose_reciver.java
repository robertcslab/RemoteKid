package com.example.parentapplication;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.parentapplication.SignUp;
public class Choose_reciver extends ListActivity{
		
	
	protected List<ParseUser> listOfParentChild;	
	protected ParseRelation<ParseUser> ParentsChild;
	protected ParseUser mCurrentUser;	
	
	protected ListView childListView;
	private Button sendBtn;
	private Button cancelBtn;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.choose_reciver);
	
		//***********************************************************
		//					Child GridView selected
		//***********************************************************

			getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
							
				
		//***********************************************************
		//					Send Button Clicked!
		//***********************************************************
		sendBtn=(Button) findViewById(R.id.btnSending);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ParseObject message=new ParseObject(ParentAppConstants.CLASS_MESSAGES);				
				message=createMessage(SendingOptions.getIndexOfNotification());						
				send(message);
				showSendingToast();
			}
			
		});
		//***********************************************************
		//					Cancel Button Clicked!
		//***********************************************************
				cancelBtn=(Button) findViewById(R.id.btnCanceling);
				cancelBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
	}
	
	//end on create
	
	//
	//************************ On Resume
	//
	
	@Override
	public void onResume() {
		super.onResume();
		
	setProgressBarIndeterminateVisibility(true);
	
		mCurrentUser = ParseUser.getCurrentUser();			
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(ParentAppConstants.KEY_CHILDRENS_RELATION, mCurrentUser.getObjectId());
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> child, ParseException e) {
				setProgressBarIndeterminateVisibility(false);
				
				if (e == null) {
					// Success
					listOfParentChild = child;
					String[] usernames = new String[listOfParentChild.size()];
					int i = 0;
					for(ParseUser user : listOfParentChild) {
						usernames[i] = user.getUsername();
						i++;
					}
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getListView().getContext(),
							android.R.layout.simple_list_item_checked,
							usernames);
					
					setListAdapter(adapter);
					
				}
				else {
					Log.e("Show Recivers", e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(Choose_reciver.this);
					builder.setMessage(e.getMessage())
						.setTitle(R.string.error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// TODO Auto-generated method stub
			sendBtn.setVisibility(1);
	
		}
	
	protected ArrayList<String> getRecipientIds() {
		ArrayList<String> recipientIds = new ArrayList<String>();
		for (int i = 0; i < getListView().getCount(); i++) {
			if (getListView().isItemChecked(i)) {
				recipientIds.add(listOfParentChild.get(i).getObjectId());
			}
		}
		return recipientIds;
	}
	
	protected ParseObject createMessage(int index) {
		
		ParseObject message = new ParseObject(ParentAppConstants.CLASS_MESSAGES);
		message.put(ParentAppConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
		message.put(ParentAppConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
		message.put(ParentAppConstants.KEY_RECIPIENT_IDS, getRecipientIds());
		message.put(ParentAppConstants.KEY_INDEX_NUMBER,index);
		
		return message;
		
	}
	
	protected void send(ParseObject message) {
		message.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// success!
			//Toast.makeText(RecipientsActivity.this, R.string.success_message, Toast.LENGTH_LONG).show();
					sendPushNotifications();
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(Choose_reciver.this);
					builder.setMessage(R.string.error_sending_message)
						.setTitle(R.string.error_selecting_file_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}
	
	
	protected void sendPushNotifications()
	{
		ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
		query.whereContainedIn(ParentAppConstants.KEY_USER_ID, getRecipientIds());
		
		// send push notification
		ParsePush push = new ParsePush();
		push.setQuery(query);
		
		JSONObject data = new JSONObject();
		try {
			data.put(ParentAppConstants.KEY_INDEX_NUMBER, SendingOptions.getIndexOfNotification());
			data.put("title", SendingOptions.getparentMessages());
			
		} catch (JSONException e) {
			Log.d("Unable to create JSon", "JSONException in setMessage: " + e.getMessage());
		}
	
		
		push.setData(data);
		
		push.sendInBackground();
	}
	
	private void showSendingToast()
	{
		Toast.makeText(this, "ارسال شد", Toast.LENGTH_LONG).show();	
	}
}
