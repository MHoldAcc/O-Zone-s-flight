package onebit.o_zonesflight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Main Activity.
 * This Activity provides the user with an interface.
 * Depending on the state the UI will change.
 */
public class UIController extends Activity {
    private SensorManager Sensor;

    /** The reference to the Game. */
    private Game GameInstance;
    /** The state of the UI Controller */
    private UIControllerState State;
    /** The Game Timer to make the game act on itself */
    private Timer GameTimer;

    /**
     * Called upon creation of the Activity. A.K.A. Application start/resume
     * The initialisation of the UI happens here
     * @param savedInstanceState May be used to load data from previous sessions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //load unmanaged stuff
        super.onCreate(savedInstanceState);

        //Initialize sensor
        Sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);



        //Initialize SaveFileManager
        String saveFile = getResources().getString(R.string.txt_defaultsavefile);
        ISaveFileManager saveFileManager = new LocalSaveFileManager(saveFile);

        //Initialize Game & Load Data
        GameInstance = new Game(saveFileManager);

        InitializeMusic();

        // Switch to GameMenu State
        GameMenu();
    }

    /**
     * Redirects onclick events to their respective target methods.
     * This is an unplanned workaround as onclick target methods require a view parameter.
     * @param sender The button that was 'clicked'
     */
    public void OnClickHandler(View sender) {
        switch (sender.getId()){
            case R.id.btn_start:    GameStart();    break;
            case R.id.btn_credits:  GameCredits();  break;
            // Invalid IDs should throw an exception
            default:
                Exception ex = new Exception("The ID of the previously clicked button is unknown.");
                ex.printStackTrace();
                //TODO; maybe implement error handler or something
                break;
        }
    }
    /**
     * Makes the game progress while it's running.
     * Called by the GameTimer each tick.
     */
    private void GameTick(){
        float bearing = 0;



        //TODO read Bearing;

        bearing = Math.min(-1, Math.max(1, bearing));
        if (GameInstance.DoFrame(bearing))
        {
            GameTimer.cancel();
            GameTimer.purge();
            GameOver();
        }
    }

    public void GameStart(){
        // Set layout and state
        setContentView(R.layout.lay_gamerunning);
        State = UIControllerState.Running;
        GameInstance.ResetGame();
        ImageView Canvas = (ImageView) findViewById(R.id.img_canvas);

        //Canvas.setImageBitmap();

        GameTimer = new Timer();
        GameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameTick();
            }
        }, 0, 33);
    }

    public void GameOver(){
        // Set layout and state
        setContentView(R.layout.lay_gamestart);
        State = UIControllerState.GameOver;

        //load highscore
        TextView highScoreText = (TextView) findViewById(R.id.tex_highscore);
        highScoreText.setText(
                String.format(
                        Locale.getDefault(),
                        "%s: %d",
                        getResources().getText(R.string.txt_highscore),
                        GameInstance.GetHighScore()));

        //load score

        TextView scoreText = (TextView) findViewById(R.id.tex_score);
        highScoreText.setText(
                String.format(
                        Locale.getDefault(),
                        "%s: %d",
                        getResources().getText(R.string.txt_score),
                        GameInstance.GetScore()));
    }

    public void GameCredits(){

    }

    /**
     * Sets the UIManage into the GameMenu state and applies changes accordingly
     */
    public void GameMenu(){
        // Set layout and state
        setContentView(R.layout.lay_gamestart);
        State = UIControllerState.MainMenu;

        //load highscore
        TextView highScoreText = (TextView) findViewById(R.id.tex_highscore);
        highScoreText.setText(
                String.format(
                        Locale.getDefault(),
                        "%s: %d",
                        getResources().getText(R.string.txt_highscore),
                        GameInstance.GetHighScore()));
    }



    public void InitializeMusic(){
        //TODO make music run or something liek taht.
    }
}
