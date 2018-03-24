package com.example.parentapplication;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SendingOptions extends Activity {

	
	private Button btn_Happy;
	private static int indexOfNotification=8;
	private static String parentMessages;
	private EditText parentMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choices);
		
		btn_Happy=(Button) findViewById(R.id.btn_Happiness);		
		parentMessage= (EditText) findViewById(R.id.edtParentMessage);
		
		
        btn_Happy.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				
				indexOfNotification=1;
				if (parentMessage != null)
					parentMessages=parentMessage.getText().toString();
				
				Intent ChooseReciver=new Intent(SendingOptions.this,Choose_reciver.class);
				startActivity(ChooseReciver);
				
				
			}
				
			}
		);
	}
	
	
	public static int getIndexOfNotification()
	{
			return indexOfNotification;
	}
	
	public static String getparentMessages()
	{
		return parentMessages;
	}
}
