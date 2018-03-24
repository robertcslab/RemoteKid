package com.example.parentapplication;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
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
import android.widget.TextView;

public class LogIn extends Activity {

	protected EditText usernameText;
	protected EditText passwordText;
	protected Button btnLogIn;
	protected TextView goToSigUp;
	private Button btnExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_log_in);
		
		//********************************************************
		//			 	Exit
		//********************************************************
		btnExit=(Button) findViewById(R.id.btnExite);
		btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		
		//********************************************************
		//				Sign Up 
		//********************************************************
		goToSigUp = (TextView) findViewById(R.id.signUpText);		
		goToSigUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent SigUpIntent=new Intent(LogIn.this, SignUp.class);
				startActivity(SigUpIntent);
			}
		});
		
		usernameText = (EditText) findViewById(R.id.userField);
		passwordText = (EditText) findViewById(R.id.passwordField);	
		
		//********************************************************
		//				Log In Button
		//********************************************************
		btnLogIn= (Button) findViewById(R.id.loginButton);		
		btnLogIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String username = usernameText.getText().toString();
				String password = passwordText.getText().toString();

				username = username.trim();
				password = password.trim();
				
				if (username.isEmpty() || password.isEmpty()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
					builder.setMessage(R.string.login_error_message)
						.setTitle(R.string.login_error_title)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else {
					// Login
					setProgressBarIndeterminateVisibility(true);
					
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							
							if (e == null) {
								// Success!
								ParentApp.updateParseInstallation(user);
								
								Intent intent = new Intent(LogIn.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else {
								AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
								builder.setMessage(e.getMessage())
									.setTitle(R.string.login_error_title)
									.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
			}
		});
	}
}
