package com.example.parentapplication;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddChildActivity extends Activity {


	private EditText usernameChild;
	private EditText passwordChild;

	private	Button signUpBtn;
	private Button cancelBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_child);
		
		usernameChild = (EditText) findViewById(R.id.userNameChild);
		passwordChild= (EditText) findViewById(R.id.passwordChild);
		
		signUpBtn=(Button) findViewById(R.id.btn_SignUp);
		signUpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String username = usernameChild.getText().toString();
				String password = passwordChild.getText().toString();
				
				username = username.trim();
				password = password.trim();
				
				final String parentId= ParseUser.getCurrentUser().getObjectId();
				if (username.isEmpty() || password.isEmpty() ) {
					AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);
					builder.setMessage(R.string.signup_error_message)
						.setTitle(R.string.signup_error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					// create the new user!
					setProgressBarIndeterminateVisibility(true);
					
					final ParseUser childUser = new ParseUser();
					childUser.setUsername(username);
					childUser.setPassword(password);
					childUser.put("ParentID",parentId);
					//****************** start child sign up
					
					childUser.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							
							if (e == null) {
								// Success!
								ParentApp.updateParseInstallation(
										ParseUser.getCurrentUser());
								//****************************************************
							
									
								AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);
								builder.setMessage("ثبت نام کودک شما با موفقیت ثبت گردید جهت استفاده از برنامه لطفاً مجدداً وارد شوید.")
									.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();

								childUser.logOut();
								
								Intent intent = new Intent(AddChildActivity.this, LogIn.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else {
								AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);
								builder.setMessage(e.getMessage())
									.setTitle(R.string.signup_error_title)
									.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
			}
			
		});//end setonclick listener
	}//end on create
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_child, menu);
		return true;
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
