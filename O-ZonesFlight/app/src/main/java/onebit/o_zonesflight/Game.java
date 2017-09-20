package onebit.o_zonesflight;

import java.util.ArrayList;

/**
 * Created by admin on 20.09.2017.
 */
public class Game {
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
        //Moves Player
        PlayerInstance.ApplyBearing(bearing);

        //Move existing Meteorites and get collision

        //Add Meteorite if needed

        return false;
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
    }
}
