package onebit.o_zonesflight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Michael on 20.09.2017.
 */
public class Game {
    /** The time till the next meteorite strikes */
    private int timeTillNextMeteorite;
    /** The current Velocity of the meteorites */
    private float currentVelocity;
    /** The time till the next increase of velocity */
    private int timeTillVelocityIncrease;
    /** All current meteorites */
    private ArrayList<Meteorite> Meteorites;
    /** All current coins */
    private ArrayList<Coin> Coins;
    /** The current player */
    private Player PlayerInstance;
    /** The current money */
    private int Money;
    /** The current score */
    private int Score;
    /** The current highscore */
    private int HighScore;
    /** Manages saving and loading */
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

        ArrayList<Meteorite> meteoriteToRemove = new ArrayList<>();
        ArrayList<Coin> coinsToRemove = new ArrayList<>();

        for (Coin coin : Coins){
            coin.SetY(coin.GetY() - Settings.Coin_Movement * currentVelocity);
            if (coin.GetY() + coin.GetHeight() < Settings.Environment_Height) {
                float distX, distY;
                distX = PlayerInstance.GetX() + PlayerInstance.GetWidth() / 2 - coin.GetX() - coin.GetWidth() / 2;
                distY = PlayerInstance.GetY() + PlayerInstance.GetHeight() / 2 - coin.GetY() - coin.GetHeight() / 2;
                float dist = (float) Math.sqrt(distX * distX + distY * distY) -
                        Math.max(coin.GetHeight(), coin.GetWidth()) / 2 -
                        Math.max(PlayerInstance.GetHeight(), PlayerInstance.GetWidth()) / 2;
                if (dist <= 0) {
                    coinsToRemove.add(coin);
                    Money++;
                }
            } else{
                coinsToRemove.add(coin);
            }
        }

        //Moves existing Meteorites
        for (Meteorite meteor:Meteorites){
            meteor.SetLatitude(meteor.GetLatitude() - Settings.Meteorites_Movement * currentVelocity);

            //Removes meteorite if Latitude is under 0
            if(meteor.GetLatitude() <= Settings.Meteorites_Height) {
                meteoriteToRemove.add(meteor);
            }
            //Checks for collisions
            else if(meteor.GetLatitude() - Settings.Meteorites_Height <= Settings.Player_Height){
                int meteorPositionLeft = meteor.GetCourse() * Settings.Environment_LineWidth;
                int meteorPositionRight = meteorPositionLeft + Settings.Environment_LineWidth;
                int playerPositionLeft = (int)PlayerInstance.GetPosition();
                int playerPositionRight = (int)PlayerInstance.GetPosition() + Settings.Player_Width;
                if(meteorPositionLeft <= playerPositionRight && playerPositionLeft <= meteorPositionRight)
                    collision = true;
            }
        }

        Coins.removeAll(coinsToRemove);
        Meteorites.removeAll(meteoriteToRemove);

        if(collision && Score > HighScore){
            SaveManager.SaveGame(new SavedState(Score));
            HighScore = SaveManager.LoadGame().GetHighscore();
        }
        else if (!collision){
            //Adds Score
            Score = Score + Settings.Gameplay_MillisecondsPerFrame;
            //Sets time till
            timeTillNextMeteorite -= Settings.Gameplay_MillisecondsPerFrame;
            timeTillVelocityIncrease -= Settings.Gameplay_MillisecondsPerFrame;

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
                currentVelocity *= Settings.Gameplay_VelocityIncrease;
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

    public ArrayList<Coin> GetCoins() { return Coins; }

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
        Meteorites = new ArrayList<>();
        timeTillNextMeteorite = 0;
        timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
        currentVelocity = 1;
    }
}
