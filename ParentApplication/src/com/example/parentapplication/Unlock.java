package com.example.parentapplication;

import java.util.List;

import com.example.parentapplication.R;
import com.example.parentapplication.R.id;
import com.example.parentapplication.R.layout;
import com.example.parentapplication.R.menu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Unlock extends Activity {

	private Button btn_enter;
	private EditText pass_field;

	protected List<ParseUser> listOfParentChild;	
	protected ParseRelation<ParseUser> ParentsChild;
	protected ParseUser mCurrentUser;		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		
	pass_field=(EditText) findViewById(R.id.edt_passcode);
	
	btn_enter=(Button) findViewById(R.id.btn_enter);
	btn_enter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String pass=pass_field.getText().toString().trim();
				if (pass==ParseUser.getCurrentUser().getUsername())
				{
					Intent main=new Intent(Unlock.this,MainActivity.class);
					startActivity(main);
				}
				else
				{
					navigateToLogin();
				}
				
			}//end onclick
		});//end onclick listener
	
}		//end on create
	
	private void navigateToLogin() {

		//Toast.makeText(this, ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, LogIn.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
