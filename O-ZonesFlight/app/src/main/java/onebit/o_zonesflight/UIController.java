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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The Main Activity.
 * This Activity provides the user with an interface.
 * Depending on the state the UI will change.
 */
public class UIController extends Activity {

    /** The reference to the Game. */
    Game GameInstance;
    /** The state of the UI Controller */
    UIControllerState State;


    // Timer
    /** The timer which runs the game loop */
    Timer GameTimer;
    /** The task which is called by the timer to run the game loop */
    TimerTask TickWrapper;


    //Sprites
    /** The Background Sprite */
    private Bitmap bmp_space;

    //Resources
    /** The Music Player */
    private MediaPlayer player = null;
    /** The Display Bitmap the canvas draws on */
    Bitmap display;
    /** The Canvas instance that draws the game on the UI */
    Canvas canvas;
    /** ImageView which renders the game */
    ImageView canvasContainer;
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
        Player.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ozone_character));
        Meteorite.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.meteorite));
        Coin.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.coin_1));
        bmp_space = BitmapFactory.decodeResource(getResources(),R.mipmap.space);



        // Switch to GameMenu State
        UIControllerState.MainMenu.Activate(this);
    }

    /**
     * Redirects onclick events to their respective target methods.
     * This is an unplanned workaround as onclick target methods require a view parameter.
     * @param sender The button that was 'clicked'
     */
    public void OnClickHandler(View sender) {
        System.out.print("TEST");
        switch (sender.getId()){
            case R.id.btn_start:    UIControllerState.Running.Activate(this);   break;
            case R.id.btn_credits:  UIControllerState.Credits.Activate(this);   break;
            case R.id.btn_back:     UIControllerState.MainMenu.Activate(this);  break;
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

        //Draw all Renderables

        for(IRenderable r : GameInstance.getRenderables()){
            canvas.drawBitmap(
                    r.GetImage(),
                    new Rect(0,0, r.GetImage().getWidth(), r.GetImage().getHeight()),
                    TranslateRenderablePos(r, display),
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
        if (!GameInstance.DoFrame(roll))
        {
            TickWrapper.cancel();
            GameTimer.cancel();
            UIControllerState.GameOver.Activate(this);
        } else{
            // Update Score
            TextView scoreDisplay = (TextView)findViewById(R.id.tex_score);
            scoreDisplay.setText(String.valueOf(GameInstance.GetScore()));
        }
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
        RectF rect = renderable.GetRect();
        float multX = display.getWidth() / Settings.Environment_Width;
        float multY = display.getHeight() / Settings.Environment_Height;
        rect.left *= multX;
        rect.right *= multX;
        rect.top *= multY;
        rect.bottom *= multY;
        return rect;
    }

}
