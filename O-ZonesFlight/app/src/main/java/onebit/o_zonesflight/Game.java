package onebit.o_zonesflight;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 20.09.2017.
 */
public class Game {
    //Settings
    private int settings_millisecondsPerFrame = 33;
    private int settings_playerHeight = 10;
    private int settings_playerWidth = 20;
    private int settings_meteoriteMovement = 5;
    private int settings_height = 100;
    private int settings_lineWidth;
    private int settings_linesExisting = 3;
    private int settings_timeTillNewMeteorite = 5000;
    private int settings_timeTillVelocityIncrease = 10000;
    private float settings_velocityIncrease = (float)1.1;

    private int timeTillNextMeteorite;
    private int currentVelocity;
    private int timeTillVelocityIncrease;

    private ArrayList<Meteorite> Meteorites;

    private Player PlayerInstance;

    private int Score;

    private int HighScore;

    private ISaveFileManager SaveManager;

    public Game(ISaveFileManager saveManager){
        SaveManager = saveManager;
        ResetGame();
        settings_lineWidth = (int)PlayerInstance.setting_maxPosition / 3;
    }

    protected boolean DoFrame(float bearing){
        boolean collision = false;

        //Moves Player
        PlayerInstance.ApplyBearing(bearing);

        Meteorite meteoriteToRemove = null;

        //Moves existing Meteorites
        for (Meteorite meteor:Meteorites){
            meteor.SetLatitude(meteor.GetLatitude() - settings_meteoriteMovement * currentVelocity);

            //Removes meteorite if Latitude is under 0
            if(meteor.GetLatitude() <= 0) {
                meteoriteToRemove = meteor;
            }
            //Checks for collisions
            else if(meteor.GetLatitude() <= settings_playerHeight){
                int meteorPositionLeft = (meteor.GetCourse() - 1) * settings_lineWidth;
                int meteorPositionRight = meteor.GetCourse() * settings_lineWidth;
                int playerPositionLeft = (int)PlayerInstance.GetPosition() - settings_playerWidth / 2;
                int playerPositionRight = (int)PlayerInstance.GetPosition() + settings_playerWidth / 2;
                if(meteorPositionLeft <= playerPositionRight && playerPositionLeft <= meteorPositionRight)
                    collision = true;
            }
        }
        //Removes meteorite if needed
        if(meteoriteToRemove != null)
            Meteorites.remove(meteoriteToRemove);

        if(collision && Score > HighScore)
            SaveManager.SaveGame(new SavedState(Score));
        else{
            //Adds Score
            Score = Score + settings_millisecondsPerFrame;

            //Add Meteorite if needed
            if(timeTillNextMeteorite >= 0) {
                Random rand = new Random();
                int randomNum = rand.nextInt((settings_linesExisting - 1) + 1) + 1;
                Meteorite meteor = new Meteorite(randomNum);
                meteor.SetLatitude(settings_height);
                Meteorites.add(meteor);
                timeTillNextMeteorite = settings_timeTillNewMeteorite;
            }
            //Sets velocity
            if(timeTillVelocityIncrease <= 0){
                timeTillVelocityIncrease = settings_timeTillVelocityIncrease;
                currentVelocity = (int)(currentVelocity * settings_velocityIncrease);
            }
        }

        return !collision;
    }

    public ArrayList<Meteorite> GetMeteorites(){
        return Meteorites;
    }

    public Player GetPlayer(){
        return PlayerInstance;
    }

    public int GetScore(){
        return Score;
    }

    public int GetHighScore(){
        return HighScore;
    }

    public void ResetGame(){
        Score = 0;
        HighScore = SaveManager.LoadGame().GetHighscore();
        PlayerInstance = new Player();
        Meteorites = new ArrayList<Meteorite>();
        timeTillNextMeteorite = 0;
        timeTillVelocityIncrease = settings_timeTillVelocityIncrease;
        currentVelocity = 1;
    }
}
