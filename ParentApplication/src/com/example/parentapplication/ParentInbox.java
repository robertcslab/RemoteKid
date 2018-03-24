package com.example.parentapplication;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parentapplication.R.string;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParentInbox extends ListActivity {

	protected List<ParseObject> mMessages;
	protected SwipeRefreshLayout mSwipeRefreshLayout;
	
	public static String child_name;
	
	public static int Game_Id=0;
	
	public static int Happiness=0;
	public static int Optimism=0;
	public static int ProblemSolving=0;
	public static int SelfRegard=0;
	/*
	public static int EmotionalSelfAwareness=0;
	public static int Assertiveness=0;
	public static int SelfActualization=0;
	public static int Independency=0;
	public static int InterpersonalRelationship=0;
	public static int SocialResponsibility=0;
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_parent_inbox);
		
		mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
		
		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
		mSwipeRefreshLayout.setColorScheme(
				R.color.swipeRefresh1,
				R.color.swipeRefresh2,
				R.color.swipeRefresh3,
				R.color.swipeRefresh4);
				
	//	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		getListView().setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				ParseObject message = mMessages.get(getListView().getCheckedItemPosition());
				message.deleteInBackground();
				showDeleting();
				return false;
			}
	});
	}

	@Override
	public void onResume() {
		super.onResume();
		
		retrieveMessages();
	}

	private void retrieveMessages() {
	
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParentAppConstants.CLASS_EQPARAMETERS);
		query.whereEqualTo(ParentAppConstants.KEY_RECEIVER_ID, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParentAppConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				//getActivity().setProgressBarIndeterminateVisibility(false);
				
				if (mSwipeRefreshLayout.isRefreshing()) {
					mSwipeRefreshLayout.setRefreshing(false);
				}
				
				if (e == null) {
					// We found messages!
					mMessages = messages;

					String[] usernames = new String[mMessages.size()];
					int i = 0;
					for(ParseObject message : mMessages) {
						
						usernames[i] = setInboxInfo(message);
						i++;
					}
					if (getListView().getAdapter() == null) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getListView().getContext(),
								android.R.layout.simple_list_item_1,
								usernames);
						
						setListAdapter(adapter);
						
					}
					else {
						// refill the adapter!
						Log.e("Retrive Messages", "Inbox Refreshed");
						/*
					    AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage(e.getMessage())
							.setTitle(R.string.error_title)
							.setPositiveButton(android.R.string.ok, null);
						AlertDialog dialog = builder.create();
						dialog.show();
						*/
					}
				}
			}
		});
	}
	
	public String setInboxInfo(ParseObject message)
	{
		String messageText="";
		
		String gameName="";
		int gameId;
		try 
		{
			gameId=Integer.parseInt(message.get(ParentAppConstants.KEY_INDEX_NUMBER).toString());
			Log.i("game idddd",String.valueOf(gameId));
		}
		catch(NumberFormatException e)
		{
			gameId=-1;
		}
		switch(gameId)
		{
		case 1:
			gameName="صداهای رنگی رنگی";
			break;
		case -1:
			gameName="بازی ------------";
			break;
		}
		
		
		messageText="نتایج  "+
		message.getString(ParentAppConstants.KEY_SENDER_NAME)
		+"  : در بازی\n  "
		+gameName
		+"  \n"+message.getCreatedAt().toString();
		return messageText;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		ParseObject message = mMessages.get(position);
	
		String Game_Id= (String) message.get(ParentAppConstants.KEY_INDEX_NUMBER);
		child_name=message.getString(ParentAppConstants.KEY_SENDER_NAME);
		Log.i("fuckkc",Game_Id);
		//if (Game_Id == "0")
			//{
				Happiness=(Integer) message.get(ParentAppConstants.KEY_HAPPINESS);
				Optimism = (Integer) message.get(ParentAppConstants.KEY_OPTIMISM);
				ProblemSolving= (Integer) message.get(ParentAppConstants.KEY_PROBLEM_SOLVING);
				SelfRegard=(Integer) message.get(ParentAppConstants.KEY_SELF_REGARD);
				
				Intent intent = new Intent(this, ShowResult.class);
				startActivity(intent);
		//	}
		/*
		switch (Game_Id) {
		case 1:
			// view the ColorFul Sounds Game
			Game_Id=1;
			Happiness=(Integer) message.getNumber(ParentAppConstants.KEY_HAPPINESS);
			Optimism = (Integer) message.getNumber(ParentAppConstants.KEY_OPTIMISM);
			ProblemSolving= (Integer) message.getNumber(ParentAppConstants.KEY_PROBLEM_SOLVING);
			SelfRegard=(Integer) message.getNumber(ParentAppConstants.KEY_SELF_REGARD);
			
			Intent intent = new Intent(this, ShowResult.class);
			startActivity(intent);
			break;
		}
		/*
		// Delete it!
		List<String> ids = message.getList(ParseConstants.KEY_RECIPIENT_IDS);
		
		if (ids.size() == 1) {
			// last recipient - delete the whole thing!
			message.deleteInBackground();
		}
		else {
			// remove the recipient and save
			ids.remove(ParseUser.getCurrentUser().getObjectId());
			
			ArrayList<String> idsToRemove = new ArrayList<String>();
			idsToRemove.add(ParseUser.getCurrentUser().getObjectId());
			
			message.removeAll(ParseConstants.KEY_RECIPIENT_IDS, idsToRemove);
			message.saveInBackground();
		}
		*/
		
	}
	protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
		@Override
		public void onRefresh() {
			retrieveMessages();
		}
	};
	
	private void showDeleting()
	{
		Toast.makeText(this, "حذف شذ", Toast.LENGTH_LONG);
	}
}








