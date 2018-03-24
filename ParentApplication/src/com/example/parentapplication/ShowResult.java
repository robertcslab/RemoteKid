package com.example.parentapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowResult extends Activity {

	
	TextView kidHappinessResult;
	TextView kidProblemSolvingResult;
	TextView kidOptimisResult;
	TextView kidSelfRegardResult;
	
	ParentInbox getMessageInfo=new ParentInbox();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_result);
		
		String kid_name="عملکرد  "+getMessageInfo.child_name+" : ";
		
		
		String kid_Hp_point=kid_name;
		kid_Hp_point+=getStatus(getMessageInfo.Happiness);
		kidHappinessResult=(TextView) findViewById(R.id.txt_kid_score_status);
		kidHappinessResult.setText(kid_Hp_point);
		
		String kid_PS_point=kid_name;
		kid_PS_point+=getStatus(getMessageInfo.ProblemSolving);
		kidProblemSolvingResult=(TextView) findViewById(R.id.txt_kid_PSscore_status);
		kidProblemSolvingResult.setText(kid_PS_point);
		
		String kid_Op_point=kid_name;
		kid_Op_point+= getStatus(getMessageInfo.Optimism);
		kidOptimisResult=(TextView) findViewById(R.id.txt_kid_OP_score_status);
		kidOptimisResult.setText(kid_Op_point);
		
		String kid_SR_point=kid_name;
		kid_SR_point+=getStatus(getMessageInfo.SelfRegard);
		kidSelfRegardResult=(TextView) findViewById(R.id.txt_kid_SR_score_status);
		kidSelfRegardResult.setText(kid_SR_point);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_result, menu);
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
	public String getStatus(int score)
	{
		String result="";
		switch (score)
		{
		case 1:
			result="نیاز به تلاش بیشتر";
			break;
		case 2:
			result="پائین تر از حد متوسط";
			break;
		case 3:
			result="بالاتر از حد متوسط";
			break;
		case 4:
			result="مطلوب";
			break;
		case 5:
			result="خیلی خوب";
			break;
		}
		return result;
	}
}
