package onebit.o_zonesflight;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Michael on 20.09.2017.
 */
public class Game {
    /**
     * The time till the next meteorite strikes
     */
    private int timeTillNextMeteorite;
    /**
     * The current Velocity of the meteorites
     */
    private float currentVelocity;
    /**
     * The time till the next increase of velocity
     */
    private int timeTillVelocityIncrease;
    /**
     * All current meteorites
     */
    private ArrayList<Meteorite> Meteorites;
    /**
     * The current player
     */
    private Player PlayerInstance;
    /**
     * The current score
     */
    private int Score;
    /**
     * The current highscore
     */
    private int HighScore;
    /**
     * Manages saving and loading
     */
    private ISaveFileManager SaveManager;

    /**
     * Initializes Game
     * @param saveManager How to load and save
     */
    public Game(ISaveFileManager saveManager){
        SaveManager = saveManager;
        ResetGame();
    }

    /**
     * Does one frame and calculates everything in it.
     * @param bearing how much the player moves to the left or right
     * @return false if game over
     */
    protected boolean DoFrame(float bearing){
        boolean collision = false;

        //Moves Player
        PlayerInstance.ApplyBearing(bearing);

        Meteorite meteoriteToRemove = null;

        //Moves existing Meteorites
        for (Meteorite meteor:Meteorites){
            meteor.SetLatitude(meteor.GetLatitude() - Settings.Meteorites_Movement * currentVelocity);

            //Removes meteorite if Latitude is under 0
            if(meteor.GetLatitude() <= 10) {
                meteoriteToRemove = meteor;
            }
            //Checks for collisions
            else if(meteor.GetLatitude() - Settings.Meteorites_Height <= Settings.Player_Height){
                int meteorPositionLeft = (meteor.GetCourse() - 1) * Settings.Environment_LineWidth;
                int meteorPositionRight = meteor.GetCourse() * Settings.Environment_LineWidth;
                int playerPositionLeft = (int)PlayerInstance.GetPosition() - Settings.Player_Width / 2;
                int playerPositionRight = (int)PlayerInstance.GetPosition() + Settings.Player_Width / 2;
                if(meteorPositionLeft <= playerPositionRight && playerPositionLeft <= meteorPositionRight)
                    collision = true;
            }
        }
        //Removes meteorite if needed
        if(meteoriteToRemove != null)
            Meteorites.remove(meteoriteToRemove);

        if(collision && Score > HighScore){
            SaveManager.SaveGame(new SavedState(Score));
            HighScore = SaveManager.LoadGame().GetHighscore();
        }
        else{
            //Adds Score
            Score = Score + Settings.Gameplay_MillisecondsPerFrame;
            //Sets time till
            timeTillNextMeteorite = timeTillNextMeteorite - Settings.Gameplay_MillisecondsPerFrame;
            timeTillVelocityIncrease = timeTillVelocityIncrease - Settings.Gameplay_MillisecondsPerFrame;

            //Add Meteorite if needed
            if(timeTillNextMeteorite <= 0) {
                Random rand = new Random();
                int randomNum = rand.nextInt((Settings.Environment_LineCount - 1) + 1) + 1;
                Meteorite meteor = new Meteorite(randomNum);
                meteor.SetLatitude(Settings.Environment_Height + Settings.Meteorites_Height);
                Meteorites.add(meteor);
                timeTillNextMeteorite = Settings.Gameplay_TimeTillNewMeteorite;
            }
            //Sets velocity
            if(timeTillVelocityIncrease <= 0){
                timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
                currentVelocity = currentVelocity * Settings.Gameplay_VelocityIncrease;
            }
        }

        return !collision;
    }

    /**
     * Returns all current meteorites
     */
    public ArrayList<Meteorite> GetMeteorites(){
        return Meteorites;
    }

    /**
     * Returns current Player
     */
    public Player GetPlayer(){
        return PlayerInstance;
    }

    /**
     * Returns current score
     */
    public int GetScore(){
        return Score;
    }

    /**
     * Returns current Highscore
     */
    public int GetHighScore(){
        return HighScore;
    }

    /**
     * Resets the current game
     */
    public void ResetGame(){
        Score = 0;
        HighScore = SaveManager.LoadGame().GetHighscore();
        PlayerInstance = new Player();
        Meteorites = new ArrayList<Meteorite>();
        timeTillNextMeteorite = 0;
        timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
        currentVelocity = 1;
    }
}
