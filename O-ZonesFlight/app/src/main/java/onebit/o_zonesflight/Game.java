package onebit.o_zonesflight;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 20.09.2017.
 */
public class Game {
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
    }

    protected boolean DoFrame(float bearing){
        boolean collision = false;

        //Moves Player
        PlayerInstance.ApplyBearing(bearing);

        Meteorite meteoriteToRemove = null;

        //Moves existing Meteorites
        for (Meteorite meteor:Meteorites){
            meteor.SetLatitude(meteor.GetLatitude() - Settings.Meteorites_Movement * currentVelocity);

            //Removes meteorite if Latitude is under 0
            if(meteor.GetLatitude() <= 0) {
                meteoriteToRemove = meteor;
            }
            //Checks for collisions
            else if(meteor.GetLatitude() <= Settings.Player_Height){
                int meteorPositionLeft = (meteor.GetCourse() - 1) * Settings.Environment_LineWidth();
                int meteorPositionRight = meteor.GetCourse() * Settings.Environment_LineWidth();
                int playerPositionLeft = (int)PlayerInstance.GetPosition() - Settings.Player_Width / 2;
                int playerPositionRight = (int)PlayerInstance.GetPosition() + Settings.Player_Width / 2;
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
            Score = Score + Settings.Gameplay_MillisecondsPerFrame;

            //Add Meteorite if needed
            if(timeTillNextMeteorite >= 0) {
                Random rand = new Random();
                int randomNum = rand.nextInt((Settings.Environment_LineCount - 1) + 1) + 1;
                Meteorite meteor = new Meteorite(randomNum);
                meteor.SetLatitude(Settings.Player_Height);
                Meteorites.add(meteor);
                timeTillNextMeteorite = Settings.Gameplay_TimeTillNewMeteorite;
            }
            //Sets velocity
            if(timeTillVelocityIncrease <= 0){
                timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
                currentVelocity = (int)(currentVelocity * Settings.Gameplay_VelocityIncrease);
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
        timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
        currentVelocity = 1;
    }
}
