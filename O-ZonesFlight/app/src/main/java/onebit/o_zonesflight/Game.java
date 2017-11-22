package onebit.o_zonesflight;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class represents the game itself
 * Created by Michael on 20.09.2017.
 */
class Game {
    /** The time till the next meteorite strikes */
    private int timeTillNextMeteorite;
    /** The current Velocity of the meteorites */
    private float currentVelocity;
    /** The time till the next increase of velocity */
    private int timeTillVelocityIncrease;
    /** All Tickable Objects like Players, Meteorites and Coins */
    private ArrayList<ITickable> tickables;
    /** Tells if the game is over */
    private boolean gameOver;
    /** The current money */
    private int money;
    /** The current score */
    private int score;
    /** The current highscore */
    private int highScore;
    /** Manages saving and loading */
    private ISaveFileManager saveManager;
    /** The List of unlocked items for the shop */
    private ArrayList<Integer> unlockables;
    /** The Random Number Generator */
    private Random RNG;

    /**
     * Initializes Game
     * @param saveManager How to load and save
     */
    Game(ISaveFileManager saveManager){
        this.saveManager = saveManager;
        RNG = new Random();
        ResetGame();
    }

    /**
     * Resets the current game
     */
    void ResetGame(){
        gameOver = false;
        score = 0;
        SavedState saveData = saveManager.LoadGame();
        highScore = saveData.GetHighscore();
        money = saveData.GetCoins();
        unlockables = saveData.GetUnlockedItems();
        tickables = new ArrayList<>();
        tickables.add(new Player());
        timeTillNextMeteorite = 0;
        timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
        currentVelocity = 1;
    }

    void Collided(ITickable tickable){
        if (tickable instanceof Coin){
            money++;
        } else if (tickable instanceof Meteorite){
            gameOver = true;
        }
    }

    /**
     * Does one frame and calculates everything in it.
     * @param bearing how much the player moves to the left or right
     * @return false if game over
     */
    boolean DoFrame(float bearing){

        ArrayList<ITickable> removable = new ArrayList<>();

        for (ITickable tickable : tickables) {
            tickable.Tick(this, bearing, currentVelocity);
            if (tickable.CanBeRemoved()) removable.add(tickable);
        }
        tickables.removeAll(removable);

        if (!gameOver){
            //Adds score
            score += Settings.Gameplay_MillisecondsPerFrame;
            //Sets time till
            timeTillNextMeteorite -= Settings.Gameplay_MillisecondsPerFrame;
            timeTillVelocityIncrease -= Settings.Gameplay_MillisecondsPerFrame;

            //Add Meteorite if needed
            if(timeTillNextMeteorite <= 0) {
                tickables.add(new Meteorite(RNG.nextInt(Settings.Environment_LineCount)));
                timeTillNextMeteorite = Settings.Gameplay_TimeTillNewMeteorite;
            }
            //Sets velocity
            if(timeTillVelocityIncrease <= 0){
                timeTillVelocityIncrease = Settings.Gameplay_TimeTillVelocityIncrease;
                currentVelocity *= Settings.Gameplay_VelocityIncrease;
            }
        } else {
            highScore = Math.max(score, highScore);
            saveManager.SaveGame(new SavedState(highScore, money, unlockables));
        }

        return !gameOver;
    }

    ArrayList<IRenderable> getRenderables(){
        ArrayList<IRenderable> renderables = new ArrayList<>();
        for (ITickable tickable : tickables) { if (tickable instanceof IRenderable) renderables.add((IRenderable) tickable); }
        return renderables;
    }

    /**
     * Returns current Player
     */
    Player GetPlayer(){
        for (ITickable tickable : tickables) if (tickable instanceof Player) return (Player)tickable;
        return null;
    }

    /**
     * Returns current score
     */
    int GetScore(){
        return score;
    }

    /**
     * Returns current Highscore
     */
    int GetHighScore(){
        return highScore;
    }
}
