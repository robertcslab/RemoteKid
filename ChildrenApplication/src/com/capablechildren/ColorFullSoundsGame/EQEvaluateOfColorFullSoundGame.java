package com.capablechildren.ColorFullSoundsGame;
import EQEvaluatorInterface.EQEvaluator.EQParamsEvaluator;
import android.util.Log;

import com.capablechildren.ColorFullSoundsGame.ColorFullSoundGame;

public  class EQEvaluateOfColorFullSoundGame implements EQParamsEvaluator{


	public ColorFullSoundGame game=new ColorFullSoundGame();
	
	boolean checkOptimism=true;
	
	// The Game Thresholds
	
	//1minutes (30second for discovering the game + 8*4~30 test sounds of colors)
	private long minTimeThresholdForplaying=60;//60
	
	//3 minutes 
	private long avgTimeThresholdForplaying=180;//180
	
	//6 minutes
	private long goodTimeThresholdForplaying=240;//240
	
	//2 minutes (1minutes for discover game and 1 minute for trying to create first sound) 
	private long minTimeToChangeTheSound=120;
	
	
	private long avgTimeToHitOnOk;
	
	//at least listen each sound once
	private int minNumberOfClickPerColors=8;
	
	//avgeTimeForPlaying/lenghtOdSounds=120/4(sec)=30
	private int avgNumberOfClickPerColors=30;
	
	//at least check the sound for once
	private int minNumberOfClickonPlay=1;
			
	//both problem solving and happiness
	//problem solving : min=1 max=6(each sound contain 3part listened twice) avg=3
	//happy: 
	private int avgNumberOfClickonPlay=3;
	
	//min=0 , max=3(existence combined sound ;)) avg=1
	private int avgNumberOfClickOnChangeSound=1;
	
	//at least check answer for one time either the answer is correct or not!
	private int minClickCountOnOk=1;
	
	//to test possible answers she/he think
	//min=1 max=? avg=3(sounds have three part)
	private int avgClickCountOnOk=4;//3
	
	//equal to avg of total click items
	//30(on colors)+ 3(on play) +1 (on change sound)
	private int avgTotClick;//OnLayout
	
	//avg 6?
	private int avgNumberOfFailer=6;
	
	//min =0 , max=3; avg=1
	private int avgNumberOfCorrect=1;
	
	@Override
	public int HappinessEvaluate() {

		int happiness=0;
		int result=0;
		/*
		Log.i("staticcccccccc",String.valueOf( ColorFullSoundGame.time_to_left_game));
		Log.i("not staticcccc",String.valueOf( game.time_to_left_game));
		*/
		if (ColorFullSoundGame.time_to_left_game < minTimeThresholdForplaying)
			happiness--;
		
		if (game.time_to_left_game >= minTimeThresholdForplaying && game.time_to_left_game < avgTimeThresholdForplaying)
			happiness++;
			
		if (game.time_to_left_game >= avgTimeThresholdForplaying)
			happiness++;
		
		if (game.time_to_left_game >= goodTimeThresholdForplaying)
			happiness++;
		
		int number_of_click_on_color= game.on_blue_click
									 +game.on_gray_click
									 +game.on_green_click
									 +game.on_orange_click
									 +game.on_pink_click
									 +game.on_purple_click
									 +game.on_red_click
									 +game.on_yello_click;
		/*
		Log.i("click on blue",String.valueOf(game.on_blue_click));
		Log.i("click on gray",String.valueOf(game.on_gray_click));
		Log.i("click on green",String.valueOf(game.on_green_click));
		Log.i("click on orange",String.valueOf(game.on_orange_click));
		Log.i("click on pink",String.valueOf(game.on_pink_click));
		Log.i("click on purple",String.valueOf(game.on_purple_click));
		Log.i("click on red",String.valueOf(game.on_red_click));
		Log.i("click on yello",String.valueOf(game.on_yello_click));
		Log.i("click on colorrrrrr",String.valueOf(number_of_click_on_color));
		*/
		if (number_of_click_on_color < minNumberOfClickPerColors)
			happiness--;
		
		if (number_of_click_on_color >= minNumberOfClickPerColors && number_of_click_on_color <= avgNumberOfClickPerColors)
			happiness++;
		
		if (number_of_click_on_color >= avgNumberOfClickPerColors)
			happiness+=2;
		
		if (game.on_playbtn_clicked < minNumberOfClickonPlay)
			happiness--;
		
		if (game.on_playbtn_clicked >= avgNumberOfClickonPlay)
			happiness++;
		
		if (game.on_change_music_click >= avgNumberOfClickOnChangeSound)
			happiness++;
		
		//also for self steem!
		if(game.number_of_failer>= avgNumberOfFailer && game.time_to_left_game >= avgTimeThresholdForplaying)
			happiness++;
		
		// max score for happiness is added by all of statements: 9
		//min score for happiness is not only Decrease in statments but also not added in any of them:-3
		//we have 4 seprate part so : 10-(-3)/4=12/4=3
		//weak : -3 
		//weak_not bad :-3 - 0
		//not bad-avg  : 0 - 3
		//avg-good     : 3 - 6
	//good-very good   : 6 - 9
		
		if (happiness<=-3)
			result=1;
		else if (happiness >-3 && happiness <= 0)
			result=2;
		else if (happiness > 0 && happiness<=3)
			result=3;
		else if (happiness > 3 && happiness <= 6)
			result =4;
		else if (happiness > 6)
			result=5;
		
		return result;
	}

	@Override
	public int OptimismEvaluate() {

		int result=1;//not optimism (between Bad and weak )
		
		if (checkOptimism)
			result=2; //NOt Bad (between weak and medium)
		
		if (game.optimismDegree == 1)
			result=3;// Just Good (between medium and good)
		
		else if (game.optimismDegree == 2)
			result=4;// Goood (more than good)
		
		if(game.number_of_failer>= avgNumberOfFailer && game.time_to_left_game >= avgTimeThresholdForplaying)
			result++;
		return result;
	}
	
	@Override
    public int ProblemSolvingEvaluate()
    {
		int problemSolving=0;
		int result=0;
		
		if (game.time_to_left_game < minTimeThresholdForplaying)
			problemSolving--;
		
		else if (game.time_to_left_game >= minTimeThresholdForplaying && game.time_to_left_game < avgTimeThresholdForplaying)
			problemSolving++;
			
		else if (game.time_to_left_game >= avgTimeThresholdForplaying)
			problemSolving++;
		
		else if (game.time_to_left_game >= goodTimeThresholdForplaying)
			problemSolving++;
		
		if (game.on_ok_clicked < minClickCountOnOk)
			problemSolving--;
		
		else if (game.on_ok_clicked >= minClickCountOnOk && game.on_ok_clicked < avgClickCountOnOk)
			problemSolving++;
		
		else if (game.on_ok_clicked >= avgClickCountOnOk )
			problemSolving++;
		
		if (game.time_left_to_change_music < minTimeToChangeTheSound)
			problemSolving--;
		
		if (game.number_of_correctness >= avgNumberOfCorrect)
			problemSolving+=2;
		
		if (game.number_of_failer <= avgNumberOfFailer)
			problemSolving++;
	
		else if (game.number_of_failer >=avgNumberOfFailer && game.time_to_left_game >= avgTimeThresholdForplaying)
			problemSolving++;
		
		//he is failing but not try to listen again and again to music
		if (game.on_playbtn_clicked <= avgNumberOfClickonPlay && game.number_of_failer >= avgNumberOfFailer)
			problemSolving--;
		
		if (game.on_playbtn_clicked <= avgNumberOfClickonPlay && game.number_of_failer <= avgNumberOfFailer)
			problemSolving+=2;
		
		else if (game.on_playbtn_clicked <=avgNumberOfFailer && game.number_of_failer <= avgNumberOfFailer )
			problemSolving++;
		
		// max score for problem solving is added by all of statements: 12
		//min score for problem solving is not only Decrease in statements but also not added in any of them:-4
				//we have 4 separate part so : 12-(-4)/4=16/4=4
				//weak : -4 
				//weak_not bad :-4 - 0
				//not bad-avg  : 0 - 4
				//avg-good     : 4 - 8
			//good-very good   : 8 - 12
		
		if (problemSolving <= -4)
			result=1;
		else if (problemSolving >-4 && problemSolving <= 0)
			result=2;
		else if (problemSolving > 0 && problemSolving <= 4)
			result=3;
		else if (problemSolving >4 && problemSolving <= 8)
			result=4;
		else if (problemSolving >8)
			result=5;
		
    	return result;
    }
    
	@Override
    public int SelfEsteemEvaluate()
    {
		int selfSteem=0;
		int result=0;
		
		if(game.number_of_failer >= avgNumberOfFailer && game.time_to_left_game >= avgTimeThresholdForplaying)
			selfSteem++;
		
		if (game.number_of_failer <= avgNumberOfFailer && game.time_to_left_game < minTimeThresholdForplaying)
			selfSteem--;
		
		else if (game.number_of_failer <= avgNumberOfFailer && game.time_to_left_game <= avgTimeThresholdForplaying)
			selfSteem--;
		
		if (game.on_ok_clicked < minClickCountOnOk)
			selfSteem--;
		
		if (game.on_ok_clicked >= minClickCountOnOk)
			selfSteem++;
		
		if (game.on_ok_clicked >= avgClickCountOnOk)
			selfSteem++;
		
		if(game.time_to_left_game >= avgTimeThresholdForplaying)
			selfSteem++;
		
		if(game.number_of_correctness <= game.number_of_failer && game.time_to_left_game >= avgTimeThresholdForplaying)
			selfSteem+=2;
		
		else if(game.number_of_correctness <= game.number_of_failer && game.time_to_left_game >= minTimeThresholdForplaying)
			selfSteem--;
		
		if(game.number_of_failer <= avgNumberOfFailer && game.time_to_left_game <= minTimeToChangeTheSound)
			selfSteem--;
		


		// max score for selfSteem is added by all of statements: 6
				//min score for selfSteem is not only Decrease in statments but also not added in any of them:-5
				//we have 4 seprate part so : 7-(-5)/4=11/4~3
				//weak : -5 
				//weak_not bad :-5 - -2
				//not bad-avg  :-2 -  1
				//avg-good     : 1 -  4
			//good-very good   : 4 -  6
		if(selfSteem <= -5)
			result=1;
		else if (selfSteem > -5 && selfSteem <= -2)
			result=2;
		else if (selfSteem > -2 && selfSteem <= 1)
			result=3;
		else if (selfSteem > 1 && selfSteem <= 4)
			result=4;
		else if(selfSteem > 4)
			result=5;
		
		return result;
    }

	@Override
	public int DetectionStatus() {
		
		int result=0;
		
		if (game.optimismDegree > 1 && checkOptimism)
		{
			//optimism detected

			checkOptimism=false;//just test kids optimism twice! ;)
			game.ChooseRandomMuteVoice();
			result=1;
		}
		
		return result;
	}

	@Override
	public void ActivateChangingGameStatus() {
		
		if( DetectionStatus()==1 )
		{
			game.number_of_click_on_muteColor=0;
			game.optimismDegree=0;
		}
	}

}
