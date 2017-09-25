package onebit.o_zonesflight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.hardware.SensorManager;
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
    private SensorManager Sensor;

    /** The reference to the Game. */
    private Game GameInstance;
    /** The state of the UI Controller */
    private UIControllerState State;


    // Timer
    private Timer GameTimer;
    private TimerTask TickWrapper;


    //Sprites
    private Bitmap oZone;
    private Bitmap meteor;

    //Resources
    private Bitmap display;
    private Canvas canvas;
    private ImageView canvasContainer;
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

        // TODO: finish it

        //Initialize SaveFileManager
        String saveFile = getResources().getString(R.string.txt_defaultsavefile);
        ISaveFileManager saveFileManager = new LocalSaveFileManager(this);

        //Initialize Game & Load Data
        GameInstance = new Game(saveFileManager);

        //Initialize Sound
        InitializeMusic();

        //Initialize Sprites
        oZone = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        meteor = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

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
    protected void GameTick(){

        //Clear Screen
        canvas.drawColor(Color.BLACK);
        //Redraw

        //Player
        //Calculate position
        PointF pos = TranslatePlayerPos(GameInstance.GetPlayer(), display);
        /*
        PointF pos = TranslatePlayerPos(
                new PointF(GameInstance.GetPlayer().GetPosition(),100),
                oZone, display);
                */
        //Draw Player
        canvas.drawBitmap(
                oZone,
                pos.x,
                pos.y,
                null);

        for (Meteorite m: GameInstance.GetMeteorites()) {
            pos = TranslateMeteorPos(m, display);
            canvas.drawBitmap(meteor,
                    pos.x,
                    pos.y,
                    null);
        }

        canvasContainer.invalidate();

        float bearing = 0;



        //TODO read Bearing;

        bearing = Math.min(-1, Math.max(1, bearing));
        boolean result = GameInstance.DoFrame(bearing);
        if (!result)
        {
            TickWrapper.cancel();
            GameTimer.cancel();
            GameOver();
        }
    }

    /**
     * Initiates the Game
     */
    public void GameStart(){
        // Set layout and state
        setContentView(R.layout.lay_gamerunning);
        State = UIControllerState.Running;

        //Reset existing game status
        GameInstance.ResetGame();

        //
        canvasContainer = (ImageView) findViewById(R.id.img_canvas);
        canvasContainer.getViewTreeObserver().addOnGlobalLayoutListener(new CanvasLayoutListener(this, canvasContainer));
        // Continue rest once the canvas is available on the LayoutDoneCallback
    }

    /**
     * Layout has been finished, the game screen size calculated. Game Loop can now be started
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
        setContentView(R.layout.lay_gamestartforcerename);
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
    private PointF TranslatePlayerPos(Player player, Bitmap display){
        return new PointF(
                player.GetPosition() / Settings.Environment_Width * (display.getWidth() - oZone.getWidth()),
                display.getHeight() - oZone.getHeight());
    }
    private PointF TranslateMeteorPos(Meteorite m, Bitmap display){
        return new PointF(
                (m.GetCourse() * Settings.Environment_LineWidth())  / Settings.Environment_Width * (display.getWidth() - meteor.getWidth()),
                (Settings.Environment_Height-m.GetLatitude())       / Settings.Environment_Height * (display.getHeight() - meteor.getHeight())
        );
    }
}
