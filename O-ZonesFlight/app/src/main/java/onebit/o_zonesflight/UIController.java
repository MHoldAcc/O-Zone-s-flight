package onebit.o_zonesflight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

    private static final String TAG = "UIC";

    /** The reference to the Game. */
    private Game GameInstance;
    /** The state of the UI Controller */
    private UIControllerState State;


    // Timer
    /** The timer which runs the game loop */
    private Timer GameTimer;
    /** The task which is called by the timer to run the game loop */
    private TimerTask TickWrapper;


    //Sprites
    /** The player Sprite */
    private Bitmap bmp_oZone;
    /** The Meteor Sprite */
    private Bitmap bmp_meteor;
    /** The Background Sprite */
    private Bitmap bmp_space;
    /** The Coin (1) Sprite */
    private Bitmap bmp_coin1;

    //Resources
    /** The Music Player */
    private MediaPlayer player = null;
    /** The Display Bitmap the canvas draws on */
    private Bitmap display;
    /** The Canvas instance that draws the game on the UI */
    private Canvas canvas;
    /** ImageView which renders the game */
    private ImageView canvasContainer;
    /** Data required to read the orientation of the device */
    private float[] Gravity;
    /** Data required to read the orientation of the device */
    private float[] Magnetic_Field;
    /**
     * Called upon creation of the Activity. A.K.A. Application start/resume
     * The initialisation of the UI happens here
     * @param savedInstanceState May be used to load data from previous sessions
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //load unmanaged stuff
        super.onCreate(savedInstanceState);

        //Init sensor variables
        Gravity = new float[3];
        Magnetic_Field = new float[3];

        //Initialize sensor
        SensorManager sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        new OrientationSensorListener(this, sensor);


        //Initialize SaveFileManager
        ISaveFileManager saveFileManager = new LocalSaveFileManager(this);

        //Initialize Game & Load Data
        GameInstance = new Game(saveFileManager);

        //Initialize Sound
        InitializeMusic();

        //Initialize Sprites
        bmp_oZone = BitmapFactory.decodeResource(getResources(), R.mipmap.ozone_character);
        bmp_meteor = BitmapFactory.decodeResource(getResources(), R.mipmap.meteorite);
        bmp_space = BitmapFactory.decodeResource(getResources(),R.mipmap.space);
        bmp_coin1 = BitmapFactory.decodeResource(getResources(), R.mipmap.coin_1);

        // Switch to GameMenu State
        GameMenu();
    }

    /**
     * Redirects onclick events to their respective target methods.
     * This is an unplanned workaround as onclick target methods require a view parameter.
     * @param sender The button that was 'clicked'
     */
    public void OnClickHandler(View sender) {
        System.out.print("TEST");
        switch (sender.getId()){
            case R.id.btn_start:    GameStart();    break;
            case R.id.btn_credits:  GameCredits();  break;
            case R.id.btn_back:     GameMenu();     break;
            // Invalid IDs should throw an exception
            default:
                Exception ex = new Exception("The ID of the previously clicked button is unknown.");
                ex.printStackTrace();
                //TODO; maybe implement error handler or something
                break;
        }
    }
    /**
     * Applies a single Tick of a span defined in Settings.Gameplay_MillisecondsPerFrame.
     * Called by the GameTimer each tick.
     */
    void GameTick(){

        //Clear Screen
        canvas.drawBitmap(
                bmp_space,
                new Rect(
                        0,
                        0,
                        bmp_space.getWidth(),
                        bmp_space.getHeight()),
                new Rect(
                        0,
                        0,
                        display.getWidth(),
                        display.getHeight()),
                null);
        //Redraw

        //Player
        //Calculate position
        RectF pos = TranslateRenderablePos(GameInstance.GetPlayer(), display);
        //Draw Player
        canvas.drawBitmap(
                bmp_oZone,
                new Rect(
                        0,
                        0,
                        bmp_oZone.getWidth(),
                        bmp_oZone.getHeight()),
                pos,
                null);
        //Iterate meteorites
        for (Meteorite m: GameInstance.GetMeteorites()) {
            //Calculate
            pos = TranslateRenderablePos(m, display);
            canvas.drawBitmap(
                    bmp_meteor,
                    new Rect(
                            0,
                            0,
                            bmp_meteor.getWidth(),
                            bmp_meteor.getHeight()),
                    pos,
                    null);
        }
        for(Coin c: GameInstance.GetCoins()){
            pos = TranslateRenderablePos(c, display);
            canvas.drawBitmap(bmp_coin1,
                    new Rect(
                            0,
                            0,
                            bmp_coin1.getWidth(),
                            bmp_coin1.getHeight()),
                    pos,
                    null);
        }
        // MAke screen refresh
        canvasContainer.invalidate();

        // Read Sensor Data
        float[] Rota = new float[9];
        float[] I = new float[9];
        float[] values = new float[3];
        SensorManager.getRotationMatrix(Rota,I,Gravity, Magnetic_Field);
        SensorManager.getOrientation(Rota,values);

        // Format sensor data
        float roll = values[2] / (float)Math.PI;

        //Do Game Tick
        boolean result = GameInstance.DoFrame(roll);
        if (!result)
        {
            TickWrapper.cancel();
            GameTimer.cancel();
            GameOver();
        } else{
            // Update Score
            TextView scoreDisplay = (TextView)findViewById(R.id.tex_score);
            scoreDisplay.setText(String.valueOf(GameInstance.GetScore()));
        }
    }

    /**
     * Sets the UIController to the Running state and applies changes partially.
     * Requires some value obtained after the layout is done. because of that it is
     * continued in LayoutDoneCallback()
     */
    public void GameStart(){
        // Set layout and state
        setContentView(R.layout.lay_gamerunning);
        State = UIControllerState.Running;

        //Reset existing game status
        GameInstance.ResetGame();

        canvasContainer = (ImageView) findViewById(R.id.img_canvas);
        canvasContainer.getViewTreeObserver().addOnGlobalLayoutListener(new CanvasLayoutListener(this, canvasContainer));
        // Continue rest once the canvas is available on the LayoutDoneCallback
    }

    /**
     * Callback for the GameStart UI update. The Game-Loop needs the UI values before starting,
     * which can only be retrieved once the UI has been drawn.
     * Can be seen as direct continuation of GameStart()
     * @param display The bitmap representing the display
     */
    public void LayoutDoneCallback(Bitmap display){
        this.display = display;
        final Handler mainHandler = new Handler(getMainLooper());
        canvas = new Canvas(display);
        GameTimer = new Timer();
        TickWrapper = new TimerTask() {
            @Override
            public void run() {
                mainHandler.post(new GameTickRunnable(UIController.this));
            }
        };

        GameTimer.schedule(TickWrapper, 0, Settings.Gameplay_MillisecondsPerFrame);
    }

    /**
     * Sets the UIController to the GameOver state and applies chages accordingly
     */
    public void GameOver(){
        // Set layout and state

        setContentView(R.layout.lay_gameover);
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
        scoreText.setText(
                String.format(
                        Locale.getDefault(),
                        "%s: %d",
                        getResources().getText(R.string.txt_score),
                        GameInstance.GetScore()));
    }

    /**
     * Sets the UIController to the Credits state und applies changes accordingly
     */
    public void GameCredits(){
        setContentView(R.layout.lay_gamecredits);
        State = UIControllerState.Credits;
    }

    /**
     * Sets the UIController into the GameMenu state and applies changes accordingly
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

    /**
     * Callback for the Gravity sensor.
     * @param values The newly calculated gravity values
     */
    public void setGravity(float[] values){ Gravity = values; }

    /**
     * Callback for the Magnetic Field sensor
     * @param values new magnetic field values
     */
    public void setMagField(float[] values){ Magnetic_Field = values; }

    /**
     * Initializes music for the game
     */
    private void InitializeMusic(){
        player = MediaPlayer.create(this, R.raw.apocalypse);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();
    }

    /**
     * Pauses music from the mediaplayer.
     */
    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    /**
     * Resumes music from the mediaplayer.
     */
    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }

    private RectF TranslateRenderablePos(IRenderable renderable, Bitmap display){
        return new RectF(
                renderable.GetX() / Settings.Environment_Width * display.getWidth(),
                display.getHeight()-renderable.GetY() / Settings.Environment_Height * display.getHeight(),
                (renderable.GetX() + renderable.GetWidth()) / Settings.Environment_Width * display.getWidth(),
                display.getHeight()-(renderable.GetY() - renderable.GetHeight()) / Settings.Environment_Height * display.getHeight()
                );
    }

}
