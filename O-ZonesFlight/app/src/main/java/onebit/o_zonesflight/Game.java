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

    }

    protected boolean DoFrame(float bearing){
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

    }
}
