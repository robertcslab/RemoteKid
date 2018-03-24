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
		
		
		return result;
	}

	@Override
	public int OptimismEvaluate() {

		int result=1;//not optimism (between Bad and weak )
		
		return result;
	}
	
	@Override
    public int ProblemSolvingEvaluate()
    {
		int problemSolving=0;
		int result=0;
		
		
    	return result;
    }
    
	@Override
    public int SelfEsteemEvaluate()
    {
		int selfSteem=0;
		int result=0;
		
		
		return result;
    }

	@Override
	public int DetectionStatus() {
		
		int result=0;
		
		
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
