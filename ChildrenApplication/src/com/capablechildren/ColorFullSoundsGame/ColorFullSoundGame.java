package com.capablechildren.ColorFullSoundsGame;
import com.capablechildren.KidAppConstants;
import com.example.parentapplication.R;

import java.util.Random;

import com.parse.ParseObject;

import EQEvaluatorInterface.EQEvaluator.Kid;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaCryptoException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.test.suitebuilder.TestSuiteBuilder.FailedToCreateTests;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class ColorFullSoundGame extends Activity {
	
	// the code of colors
	//green 1
	//blue 2
	//red 3
	//orange 4
	//purple 5
	//black 6
	//gray 7
	//yello 8
	public Button btn_green;
	public Button btn_blue;
	public Button btn_red;
	public Button btn_orange;
	public Button btn_yello;
	public Button btn_pink;
	public Button btn_purple;
	public Button btn_gray;
	
	public Button btn_play;
	public Button btn_check_answer;
	public Button btn_change_music;
	public Button btn_play_kid;
	public Button btn_exit;
	
	private TextView firstPart;
	private TextView secondPart;
	private TextView thirdPart;
	
	private Boolean part_one_selected=false;
	private Boolean part_two_selected=false;
	private Boolean part_three_selected=false;
	
	private int part_one_color;
	private int part_two_color;
	private int part_three_color;
	
	//*************************************
	//			Control Variables
	//*************************************
	private static int current_sound_code;
	private static int current_sound_number;
	private static int part_selected=0;//for answering
	
	public static int total_layout_clicked=0;
	public static int on_playbtn_clicked=0;
	public static int on_change_music_click=0;
	public static int on_ok_clicked=0;
	public static int number_of_failer=0;//نچ اشتباس
	public static int number_of_correctness=0;
	
	public static int on_green_click=0;
	public static int on_blue_click=0;
	public static int on_orange_click=0;
	public static int on_yello_click=0;
	public static int on_red_click=0;
	public static int on_purple_click=0;
	public static int on_pink_click=0;
	public static int on_gray_click=0;
	
	public static long time_left_to_change_music=0;//first hit
	public static long time_to_hit_ok=0;//first hit
	public static long time_to_left_game=0;// by press Back button	or exite application
	//public long playing_lenght=0;//by press exite button


	public static int number_of_click_on_muteColor=0;
	public static int indexOfMuteVoice=0;
	public static int optimismDegree=0;
	
	//!!!!!!!!!1
	private Chronometer ch;
	Random rand=new Random();
	
	private MediaPlayer[] media_player=new MediaPlayer[12];
	private  Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_the_voice);
		
		// chosse on voice to be mute 
		ChooseRandomMuteVoice();
		
		ch=(Chronometer) findViewById(R.id.chronometer1);
		ch.start();
		//((Chronometer) findViewById(R.id.chronometer1)).stop();
		// set colour buttons
		btn_green=(Button) findViewById(R.id.btn_green);
		btn_blue=(Button) findViewById(R.id.btn1_blue);
		btn_red=(Button) findViewById(R.id.btn_red);
		btn_orange=(Button) findViewById(R.id.btn_orange);
		btn_yello=(Button) findViewById(R.id.btn_yello);
		btn_purple=(Button) findViewById(R.id.btn_purple);
		btn_pink=(Button) findViewById(R.id.btn_pink);
		btn_gray=(Button) findViewById(R.id.btn_gray);
		
		
		btn_play=(Button) findViewById(R.id.btn_playsound);
		btn_check_answer=(Button) findViewById(R.id.btn_check_answer);
		btn_change_music=(Button) findViewById(R.id.btn_change_sound);
		btn_exit=(Button) findViewById(R.id.btn_exitapp);
		btn_play_kid=(Button) findViewById(R.id.btn_play_kid_sounds);
		
		firstPart=(TextView) findViewById(R.id.txtView_first_part);
		secondPart=(TextView) findViewById(R.id.txtView_second_part);
		thirdPart=(TextView) findViewById(R.id.txtView_third_part);
		
		//
		handler= new Handler();
		for(int i =1;i<=12;i++)
		{
			switch(i)
			{
			case 1:
				media_player[i]=MediaPlayer.create(this, R.raw.green);
				break;
			case 2:
				media_player[i]=MediaPlayer.create(this, R.raw.blue);
				break;
			case 3:
				media_player[i]=MediaPlayer.create(this, R.raw.red_note_g);
				break;
			case 4:
				media_player[i]=MediaPlayer.create(this, R.raw.orange);
				break;
			case 5:
				media_player[i]=MediaPlayer.create(this, R.raw.purple);
				break;
			case 6:
				media_player[i]=MediaPlayer.create(this, R.raw.black);
				break;
			case 7:
				media_player[i]=MediaPlayer.create(this, R.raw.gray);
				break;
			case 8:
				media_player[i]=MediaPlayer.create(this, R.raw.yello);
				break;
			case 9:
				media_player[i]=MediaPlayer.create(this, R.raw.blkblupur);
				break;
			case 10:
				media_player[i]=MediaPlayer.create(this, R.raw.greenblueblk);
				break;
			}
		}
		
		//************************************************
		//			On Play Click
		//************************************************
		btn_green.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				on_green_click++;
				
				if (indexOfMuteVoice == 1)
				{
					number_of_click_on_muteColor++;
					optimismDegree=1;
				}
				
				if(number_of_click_on_muteColor > 2)
				{
					indexOfMuteVoice=0;
					optimismDegree=2;
				}
				
			 if (indexOfMuteVoice != 1)
				{
					player(media_player[1]);
					switch(part_selected)
					{
					case 0:
						AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
						builder.setMessage(R.string.choose_field_for_color)
							.setPositiveButton(android.R.string.ok, null);
						AlertDialog dialog = builder.create();
						dialog.show();
						break;
					case 1:
						firstPart.setBackgroundColor(Color.GREEN);
						part_one_color=1;
						break;
					case 2:
						secondPart.setBackgroundColor(Color.GREEN);
						part_two_color=1;
						break;
					case 3:
						thirdPart.setBackgroundColor(Color.GREEN);
						part_three_color=1;
						break;
					
					}
				}
			}
		});
		
		btn_blue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			

				on_blue_click++;
				if (indexOfMuteVoice == 2)
				{
				number_of_click_on_muteColor++;
				optimismDegree=1;
				}
			
			if(number_of_click_on_muteColor > 2)
			{
				indexOfMuteVoice=0;
				optimismDegree=2;
			}
				
			 if (indexOfMuteVoice != 2)
				{
					player(media_player[2]);
					switch(part_selected)
					{
					case 0:
						AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
						builder.setMessage(R.string.choose_field_for_color)
							.setPositiveButton(android.R.string.ok, null);
						AlertDialog dialog = builder.create();
						dialog.show();
						break;
					case 1:
						firstPart.setBackgroundColor(Color.BLUE);
						part_one_color=2;
						break;
					case 2:
						secondPart.setBackgroundColor(Color.BLUE);
						part_two_color=2;
						break;
					case 3:
						thirdPart.setBackgroundColor(Color.BLUE);
						part_three_color=2;
						break;
					
					}
				}
			}
	});
		
		btn_red.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				on_red_click++;
				if (indexOfMuteVoice == 3)
				{
					number_of_click_on_muteColor++;
					optimismDegree=1;
					}
				
				if(number_of_click_on_muteColor > 2)
				{
					indexOfMuteVoice=0;
					optimismDegree=2;
				}
				
			 if (indexOfMuteVoice != 3)
				{
				
				player(media_player[3]);
				
				switch(part_selected)
				{
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage(R.string.choose_field_for_color)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					break;
				case 1:
					firstPart.setBackgroundColor(Color.RED);
					part_one_color=3;
					break;
				case 2:
					secondPart.setBackgroundColor(Color.RED);
					part_two_color=3;
					break;
				case 3:
					thirdPart.setBackgroundColor(Color.RED);
					part_three_color=3;
					break;
				
				}
			}
			}
		});

		btn_orange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				on_orange_click++;
				
				if (indexOfMuteVoice == 4)
					{
					number_of_click_on_muteColor++;
					optimismDegree=1;
					}
				
				if(number_of_click_on_muteColor > 2)
				{
					indexOfMuteVoice=0;
					optimismDegree=2;
				}
					
				
			 if (indexOfMuteVoice != 4)
				{
				player(media_player[4]);
				switch(part_selected)
				{
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage(R.string.choose_field_for_color)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					break;
				case 1:
					firstPart.setBackgroundColor(Color.parseColor("#FF3300"));
					part_one_color=4;
					break;
				case 2:
					secondPart.setBackgroundColor(Color.parseColor("#FF3300"));
					part_two_color=4;
					break;
				case 3:
					thirdPart.setBackgroundColor(Color.parseColor("#FF3300"));
					part_three_color=4;
					break;
				
				}
			}
			}
		});
		
		btn_purple.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				on_purple_click++;
				
				if (indexOfMuteVoice == 5){
					number_of_click_on_muteColor++;
					optimismDegree=1;
					}
				
				if(number_of_click_on_muteColor > 2)
				{
					indexOfMuteVoice=0;
					optimismDegree=2;
				}
					
				
			 if (indexOfMuteVoice != 5)
				{
				player(media_player[5]);

				switch(part_selected)
				{
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage(R.string.choose_field_for_color)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					break;
				case 1:
					firstPart.setBackgroundColor(Color.parseColor("#FF33CC"));
					part_one_color=5;
					break;
				case 2:
					secondPart.setBackgroundColor(Color.parseColor("#FF33CC"));
					part_two_color=5;
					break;
				case 3:
					thirdPart.setBackgroundColor(Color.parseColor("#FF33CC"));
					part_three_color=5;
					break;
				
				}
			}
			}
		});

	btn_pink.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		on_pink_click++;
		
		if (indexOfMuteVoice == 6)
		{
			number_of_click_on_muteColor++;
			optimismDegree=1;
			}
		
		if(number_of_click_on_muteColor > 2)
		{
			indexOfMuteVoice=0;
			optimismDegree=2;
		}
		
	 if (indexOfMuteVoice != 6)
		{
		player(media_player[6]);
		on_pink_click++;
		switch(part_selected)
		{
		case 0:
			AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
			builder.setMessage(R.string.choose_field_for_color)
				.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		case 1:
			firstPart.setBackgroundColor(Color.parseColor("#FF0066"));
			part_one_color=6;
			break;
		case 2:
			secondPart.setBackgroundColor(Color.parseColor("#FF0066"));
			part_two_color=6;
			break;
		case 3:
			thirdPart.setBackgroundColor(Color.parseColor("#FF0066"));
			part_three_color=6;
			break;
		
		}
	}
	}
	});
	
	btn_gray.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			on_gray_click++;
			
			if (indexOfMuteVoice == 7){
				number_of_click_on_muteColor++;
				optimismDegree=1;
				}
			
			if(number_of_click_on_muteColor > 2)
			{
				indexOfMuteVoice=0;
				optimismDegree=2;
			}
				
			
		 if (indexOfMuteVoice != 7)
			{
			player(media_player[7]);
			switch(part_selected)
			{
			case 0:
				AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
				builder.setMessage(R.string.choose_field_for_color)
					.setPositiveButton(android.R.string.ok, null);
				AlertDialog dialog = builder.create();
				dialog.show();
				break;
			case 1:
				firstPart.setBackgroundColor(Color.parseColor("#9900FF"));
				part_one_color=7;
				break;
			case 2:
				secondPart.setBackgroundColor(Color.parseColor("#9900FF"));
				part_three_color=7;
				break;
			case 3:
				thirdPart.setBackgroundColor(Color.parseColor("#9900FF"));
				part_three_color=7;
				break;
			
			}
		}
		}
	});
	
	btn_yello.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			on_yello_click++;
			
			if (indexOfMuteVoice == 8){
				number_of_click_on_muteColor++;
				optimismDegree=1;
				}
			
			if(number_of_click_on_muteColor > 2)
			{
				indexOfMuteVoice=0;
				optimismDegree=2;
			}
			
		 if (indexOfMuteVoice != 8)
			{
			//if (part_selected !=0)
			player(media_player[8]);
				switch(part_selected)
				{
				case 0:
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage(R.string.choose_field_for_color)
						.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
					break;
				case 1:
					firstPart.setBackgroundColor(Color.YELLOW);
					part_one_color=8;
					break;
				case 2:
					secondPart.setBackgroundColor(Color.YELLOW);
					part_two_color=8;
					break;
				case 3:
					thirdPart.setBackgroundColor(Color.YELLOW);
					part_three_color=8;
					break;
				
				}
			}
		}
	});
	
	//***************************************************************
	//						Play Songs
	//***************************************************************
	btn_play.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			on_playbtn_clicked++;
			if(on_playbtn_clicked == 1)
			{
				btn_play.setBackgroundResource(R.drawable.replay);
				int random=rand.nextInt(2);// 0,1
				
				switch(random)
				{
				case 0:
					current_sound_code=126;//green,blue,black
					current_sound_number=10;
					player(media_player[10]);
				break;
				
				case 1:
					current_sound_code=625;//black, blue, purple
					current_sound_number=9;
					player(media_player[9]);		
					break;
					
				
				}
			}
			else if(on_playbtn_clicked > 1)
				player(media_player[current_sound_number]);
		}
	});
	//*******************************************************
	//						Change Music
	//*******************************************************
	
	btn_change_music.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		//ch.sete
			long elapsedMillis = SystemClock.elapsedRealtime() - ch.getBase();
			long time_seconds=elapsedMillis/1000l;
			Log.i("Time passessssssed",String.valueOf(time_seconds));
	    	time_left_to_change_music=time_seconds;
			on_change_music_click++;
			//on_playbtn_clicked=0;
			/*
			on_green_click=0;
			on_blue_click=0;
			on_orange_click=0;
			on_yello_click=0;
			on_red_click=0;
			on_pink_click=0;
			on_purple_click=0;
			on_green_click=0;
			 */
			//number_of_failer=0;//نچ اشتباس
			
			btn_play.setBackgroundResource(R.drawable.play);
			//number_of_correctness=0; Dont Set To Zero
			
		}
	});
	//************************************************
	//			Play sounds created by kid
	//************************************************
	btn_play_kid.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			if (part_one_color != 0)
			{
			player(media_player[part_one_color]);
			
			/*	
			 OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}
			}*/
				while (media_player[part_one_color].isPlaying())
				{
					//do nothing
				}
			}
			if (part_two_color != 0 )
			{
				player(media_player[part_two_color]);
				
				while (media_player[part_two_color].isPlaying())
				{
					//do nothing
				}
			}
			if (part_three_color != 0 )
				player(media_player[part_three_color]);
			
		}
	});
	
	//*************************************************
	// Check answring options
	//****************************************************
	
	firstPart.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			part_one_selected=true;
			part_selected=1;
			
		}
	});	
	
	secondPart.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			part_two_selected=true;
			part_selected=2;
			
		}
	});	
	
	thirdPart.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			part_three_selected=true;
			part_selected=3;
			
		}
	});	
	
	//**********************************************************
	//				Check answer
	//*********************************************************
	btn_check_answer.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			String children_code;
			

			 MediaPlayer cheer= MediaPlayer.create(getApplicationContext(),R.raw.mestrey);
			 MediaPlayer wrong= MediaPlayer.create(getApplicationContext(),R.raw.beeb);
			 
			long elapsedMillis = SystemClock.elapsedRealtime() - ch.getBase();
			long time_seconds=elapsedMillis/1000l;
			time_to_hit_ok=time_seconds;
			
			on_ok_clicked++;
			if(part_one_selected && part_two_selected && part_three_selected)
			{
				//go to check answer
				//first create the answer code
				children_code=String.valueOf(part_one_color)+String.valueOf(part_two_color)+String.valueOf(part_three_color);
				Log.i("children_code",children_code);
				Log.i("Answer",String.valueOf(current_sound_code));
				//compare with correct code
				if( children_code.equals(String.valueOf(current_sound_code)))
				{
					//you win
					number_of_correctness++;
					cheer.start();
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage("باریکلاااا")
						.setPositiveButton(android.R.string.ok, null);
					//.set sound
					AlertDialog dialog = builder.create();
					dialog.show();
					//check scores
				}
				else
				{
					Log.i("children_code",children_code);
					//wrong answer
					number_of_failer++;
					wrong.start();
					AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
					builder.setMessage("نچ اشتباس!")
						.setPositiveButton(android.R.string.ok, null);
					//final View view = factory.inflate(R.layout.view_wrong_answer, null);
					builder.setIcon(R.drawable.spongebob);
					//.set sound
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
			else
			{
				//first fill all parts
				AlertDialog.Builder builder = new AlertDialog.Builder(ColorFullSoundGame.this);
				builder.setMessage("جاهای خالی را پر کنید!")
					.setPositiveButton(android.R.string.ok, null);
				//final View view = factory.inflate(R.layout.view_wrong_answer, null);
				//.set sound
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}
	});
	
	//***********************
	ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_draw_the_voice, null);
	layout.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			total_layout_clicked++;
			return false;
		}
	});
	//****************************************************
	//				On Exit Button(send scores to parent)
	//****************************************************
	btn_exit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
		ExiteTheActivity();
			
	}
	});
	
	
	}//end on create
	
	@Override
    public void onBackPressed() {
        super.onBackPressed();   
       ExiteTheActivity();

    }
	/*
	@Override
	protected void onResume()
	{
		EQEvaluateOfColorFullSoundGame eqFeedBack=new EQEvaluateOfColorFullSoundGame();
		
		eqFeedBack.ActivateChangingGameStatus();
	}
*/
	/*
	@Override
	protected void onPause()
	{
		ExiteTheActivity();
	}
*/
	public int ChooseRandomMuteVoice()
	{
		indexOfMuteVoice=rand.nextInt(8);
		
		return indexOfMuteVoice;
	}
	//*******************************************************
	//
	//*******************************************************
	public void player(MediaPlayer mediaplayer)
	{
	
		for(int i=1;i<9;i++)
			if (media_player[i].isPlaying())
			{
				try{
					media_player[i].stop();
					media_player[i].prepare();
				}catch(Exception e)
				{
					Log.i("Catch exception in stop player", e.getMessage());
				}
		    	
			}
		/*
		mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				public void onPrepared(MediaPlayer player) {
					player.start();
				}
			});
		*/
		try{

			mediaplayer.start();
		}catch(Exception e)
		{
			Log.i("Catch exception in start player", e.getMessage());
		}
		//handler.postDelayed(r, 1000);
	}
	//**********************************************************
	
	
	public void ExiteTheActivity()
	{
		long elapsedMillis = SystemClock.elapsedRealtime() - ch.getBase();
		long time_seconds=elapsedMillis/1000l;
		time_to_left_game=time_seconds;
		
		ParseObject message=new ParseObject(KidAppConstants.CLASS_EQPARAMETERS);		
		
		KidImplementForColorFullSounds children= new KidImplementForColorFullSounds();
		
		boolean[] indexOfEQParameter=new boolean[15];
		indexOfEQParameter[1]=true;//happiness
		indexOfEQParameter[2]=true;//optimism
		indexOfEQParameter[3]=true;//problem solving
		indexOfEQParameter[4]=true;//selfsteem
		
		//indexOfEQParameter[3]=true;//problem solving
		
		children.SetParent();
		/* Works correct!
		AlertDialog.Builder builder = new AlertDialog.Builder(DrawTheVoiceActivity.this);
		builder.setMessage("Parent Id : "+children.parent_id)
			.setPositiveButton(android.R.string.ok, null);
		//.set sound
		AlertDialog dialog = builder.create();
		dialog.show();
		*/
		
		if (children.parent_id != "not found" && children.parent_id!=null)
		{
			message=children.createMessage(indexOfEQParameter);
			children.SendParameterToParent(message);
		}
		else
		{
			Toast tt= new Toast(ColorFullSoundGame.this);
			tt.makeText(ColorFullSoundGame.this, "Parent Not Found", Toast.LENGTH_SHORT);
		}
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw_the_voice, menu);
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
