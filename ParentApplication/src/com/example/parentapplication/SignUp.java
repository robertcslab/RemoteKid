package com.example.parentapplication;

import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.example.parentapplication.ParentApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity {

	private EditText userName;
	private EditText passWord;

	private	Button signUpBtn;
	private Button cancelBtn;
		
//****************************
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_sign_up);
		
		userName=(EditText) findViewById(R.id.userField);
		passWord=(EditText) findViewById(R.id.passField);
		
	
		//*************************************************************
		//						 Cancel Click
		//*************************************************************
		cancelBtn=(Button) findViewById(R.id.btnCancel);
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		
		
		//*************************************************************
		//						 Sign Up Click
		//*************************************************************		
		signUpBtn=(Button) findViewById(R.id.btn_SignUp);
		signUpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				String username = userName.getText().toString();
				String password = passWord.getText().toString();
				
				username = username.trim();
				password = password.trim();
				
				if (username.isEmpty() || password.isEmpty() ) {
					AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
					builder.setMessage(R.string.signup_error_message)
						.setTitle(R.string.signup_error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					// create the new user!
					setProgressBarIndeterminateVisibility(true);
					
					final ParseUser ParentUser = new ParseUser();
					ParentUser.setUsername(username);
					ParentUser.setPassword(password);
				
					
					//****************** start parent sign up
					
					ParentUser.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							
							if (e == null) {
								// Success!
								ParentApp.updateParseInstallation(
										ParseUser.getCurrentUser());
								Intent intent = new Intent(SignUp.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else {
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
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
	
	}//end onCreate methode

	
	
}
