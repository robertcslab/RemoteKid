package EQEvaluatorInterface;

import java.sql.Time;

import com.parse.ParseObject;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;

public class EQEvaluator extends Activity {

	    interface Player 
	    {
	     /*
	      *    static String PlayerID ;
	       // public static int playerLevelNumber;
	        public static int playerCurrentScores;//used to show player_diffrent with EQPoints
	        public static Time playersTime;//total playing time(its updatable the last value is equal to total playing time)
	        public float[] differenceTimeBetweenPlayersFails;
	        public static int playerFailesNumber;//for each wrong objects which player cathe ,this variable wil be added

	        */
	    	//برای بازی هایی که امتیاز ذخیره می شود.
	        void ResumeGame(/*GameObject GB*/);

	        /// <summary>
	        /// Update players score in her/his record to show next time kid wants to play
	        /// </summary>
	       
	        void UpdatePlayersMaxScores();
	         //update 3 last points of gamer
	            //compare currentscore with kid.maxscore saved before and update in in gamer table
	            //should compare with 3 score recorded before sepratedly


	        /// <summary>
	        /// Evalute EQ parameters from players score and record in kid class and gamer table
	        /// </summary>
	        /// <param name="playerID"></param>
	        void EQEvaluate(int playerID);
	        
	        /// <summary>
	        /// 
	        /// </summary>
	        /// <param name="systemTime">the time of calling this function after happening an event</param>
	        /// <param name="playingTime">the time of last event ex:start palying</param>
	        /// <returns>the float shows difference between two event happenings time</returns>
	      float GetTimePassed(Time systemTime,Time playingTime);
	       // {
	         //   float TimePassed=0;
	            
	         //   TimePassed = Convert.ToDouble( systemTime - playingTime);
	      //      return TimePassed;
	       // }

	        /// <summary>
	        /// Compute the total players score by his/her currentPoint and timePlayed(and misspoint(fail...))
	        /// </summary>
	        /// <returns>int total score</returns>
	      int ComputeTotalScore();
	      /*  {
	            int totScores = 0;

	            return totScores;
	        }
	       */

	    }

	    
	    public interface Kid
	    {
	    	/*
	        public Kid()
	        {
	            EQParams = new int[15] { -900, -900, -900, -900, -900, -900, -900, -900, -900, -900, -900, 900, -900, -900, -900 };
	            //declare kids max score to 3 or 5 or 10
	        }
	        
	        public static int kidID=0;
	        public static int kidsParentID = 0;//0 refers to : "parent not set yet!"
	        public static int [] EQParams;
	        public static int[] kidsMaxScores;
	        public static int kidsLastLevelNumber;//to resume game more affetienly
	    	 */
	        /// <summary>
	        /// run after kids prees Play (resume game after last level or any level kids want)
	        /// </summary>
	        void PlayGame(int levelNumber);

	        boolean SendParameterToParent(ParseObject message);
	       /* {
	            bool sendStatus = false;
	            //if p_id==0 set parent
	            //else connect
	            
	            
	            return sendStatus;
	        }
	        */
	        public void sendPushNotification();
	        public ParseObject createMessage(boolean[] involvedEQParameters);
	        
	        public String SetParent();
	        /*
	        {
	            int parentID=0;

	            return parentID;
	        }*/
	    }
/*
	    public interface GameStatus 
	    {
	    game_id
	        public  static final int timeThresholdForFirstFail;//compare with starting time till first fail happened
	        public  static int maxTimeThreshold;
	        public  static int levelNumber;//isn uniqu (ex: level 1 : type easy or type hard)
	        public  static int hardness;//1:easy 2:hard 3:very hard
	        public  static int maxScoreForCurrentLevel;
	        public  static int numberOfAnnoyingObject;
	        public  static int speedRatioOfAnnoyingObject;
	        public  static int numberOfNeutralObject;
	        public  static int speedRatioOfNeutralObject;
	        public  static boolean NeedToUpdateGame = false;
	        public  static boolean[] IndexOfEQParamsConsideredInThisGame;//declare to 15 set by 0 in constructor
	    }
*/
	    public interface Game 
	    {
	       // public static int gameID;
	    	
	        /// <summary>
	        /// called when games parameter like speed of objects,... must change
	        /// </summary>
	        /// <param name="gameID">for game recognition </param>
	        public void UpdateGamesParameters(int gameID);//void or gamestatus for calling in sensor?
	        

	      //  public GameStatus Sensor();
	        /*{
	            GameStatus gs=new GameStatus();

	            return gs;
	        }*/

	    }

	    public interface EQParamsEvaluator 
	    {
	       // public int[] EQParameters;//constructor 15

	        public int DetectionStatus(/*GameStatus gs*/);
/*	        {
	            int result=0;

	            if (gs.NeedToUpdateGame)
	            {
	               // if()
	            }
	            else
	                result = -1;//shows error
	            return result;
	        }
*/
	        public void ActivateChangingGameStatus();
	       
	        public int HappinessEvaluate();
	        
	        public int OptimismEvaluate();
	        
	        public int ProblemSolvingEvaluate();
	        
	        public int SelfEsteemEvaluate();
	        
	    }

	
}
