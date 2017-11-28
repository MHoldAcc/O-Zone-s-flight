package onebit.o_zonesflight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Size;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Class is a Runnable that simply calls the Gametick on the uicontroller.
 * Created by silvan pfister on 20.09.2017.
 */
class GameLoop implements Runnable {

    private final UIController owner;
    private final SavedState   state;
    private final Size         screenSize;
    private final Game gameInstance;
    private final Canvas canvas;
    private final ImageView display;
    private final Bitmap backBuffer;
    private final Paint textPaint;

	private final Bitmap bmp_BG;


	// Timer
	/** The timer which runs the game loop */
	private Timer gameTimer;


	/** The task which is called by the timer to run the game loop */
	private TimerTask tickWrapper;

	/** Data required to read the orientation of the device */
	private float[] gravity;
	/** Data required to read the orientation of the device */
	private float[] magneticField;

    /**
     * Creates a GameLoop instance.
     * @param controller The UIController which GameTick method should be ran
     */
    GameLoop(UIController controller) {

		gravity = new float[3];
		magneticField = new float[3];
		textPaint = new Paint();
		textPaint.setARGB(255,255,255,255);
		textPaint.setTextSize(50);

		this.owner = controller;

		//Initialize sensor
		new OrientationSensorListener(this, (SensorManager) controller.getSystemService(Context.SENSOR_SERVICE));

		canvas = controller.canvas;
		display = controller.canvasContainer;
		backBuffer = controller.display;
		gameInstance = controller.GameInstance;

		state = gameInstance.GetState();

		screenSize = new Size(controller.display.getWidth(), controller.display.getHeight());

		bmp_BG = new Texture( controller.getResources(), state.GetBackgroundID()).GetBitmap(screenSize);
		Player.setTexture(
				new Texture( controller.getResources(), state.GetCharacterID()),
				new Size( Settings.Player_Width * screenSize.getWidth() / Settings.Environment_Width,
						Settings.Player_Height * screenSize.getHeight()/ Settings.Environment_Height));

		Meteorite.setTexture(
				new Texture( controller.getResources(), state.GetMeteorID()),
				new Size(Settings.Meteorites_Size * screenSize.getWidth() / Settings.Environment_Width,
						 Settings.Meteorites_Size * screenSize.getHeight() / Settings.Environment_Height));

		Coin.setTexture(
				new Texture( controller.getResources(), state.GetCoinID()),
				new Size(Settings.Coin_Size * screenSize.getWidth() / Settings.Environment_Width,
						 Settings.Coin_Size * screenSize.getHeight() / Settings.Environment_Height));

		gameTimer = new Timer();
		final Handler mainHandler = new Handler(controller.getMainLooper());
		tickWrapper = new TimerTask() {
			@Override
			public void run( ) {
				mainHandler.post(GameLoop.this);
			}
		};

		gameTimer.schedule(tickWrapper, 0, Settings.Gameplay_MillisecondsPerFrame);
	}

    /**
     * Simply runs the GameTick method of the UIController associated with this instance
     */
    @Override
    public void run() {

		//Clear Screen
		canvas.drawBitmap(
				bmp_BG,
				new Rect(
						0,
						0,
						bmp_BG.getWidth(),
						bmp_BG.getHeight()),
				new Rect(
						0,
						0,
						screenSize.getWidth(),
						screenSize.getHeight()),
				null);
		//Redraw

		//Draw all Renderables

		for ( IRenderable r : gameInstance.getRenderables() ) {
			canvas.drawBitmap(
					r.GetImage(),
					new Rect(0, 0, r.GetImage().getWidth(), r.GetImage().getHeight()),
					TranslateRenderablePos(r, backBuffer),
					null);

		}
		// MAke screen refresh
		display.invalidate();

		// Read Sensor Data
		float[] Rota   = new float[9];
		float[] I      = new float[9];
		float[] values = new float[3];
		SensorManager.getRotationMatrix(Rota, I, gravity, magneticField);
		SensorManager.getOrientation(Rota, values);

		// Format sensor data
		float roll = values[2] / (float) Math.PI;

		//Do Game Tick
		if ( !gameInstance.DoFrame(roll) ) {
			tickWrapper.cancel();
			gameTimer.cancel();
			bmp_BG.recycle();
			Player.DisposeBitmap();
			Meteorite.DisposeBitmap();
			Coin.DisposeBitmap();
			UIControllerState.GameOver.Activate(owner);
		} else {
			String text, valueText;
			float valueWidth, textMargin, position;

			position = textMargin = 10;


			text = owner.getResources().getText(R.string.txt_scoreupcase).toString();
			valueText = String.valueOf(gameInstance.GetScore());
			valueWidth = textPaint.measureText(valueText);

			canvas.drawText(text, textMargin,position + textPaint.getTextSize(),textPaint);
			canvas.drawText(valueText, screenSize.getWidth() - valueWidth - textMargin, position + textPaint.getTextSize(), textPaint);

			position += textPaint.getTextSize() + textMargin;

			text = owner.getResources().getText(R.string.txt_coin).toString();
			valueText = String.valueOf(state.GetCoins());
			valueWidth = textPaint.measureText(valueText);

			canvas.drawText(text, textMargin, position + textPaint.getTextSize(),textPaint);
			canvas.drawText(valueText, screenSize.getWidth() - valueWidth - textMargin, position + textPaint.getTextSize(), textPaint);

		}
    }


	private RectF TranslateRenderablePos(IRenderable renderable, Bitmap display) {
		RectF rect  = renderable.GetRect();
		float multX = display.getWidth() / Settings.Environment_Width;
		float multY = display.getHeight() / Settings.Environment_Height;
		rect.left *= multX;
		rect.right *= multX;
		rect.top *= multY;
		rect.bottom *= multY;
		return rect;
	}

	/**
	 * Callback for the gravity sensor.
	 *
	 * @param values
	 * 		The newly calculated gravity values
	 */
	void setGravity(float[] values) { gravity = values; }

	/**
	 * Callback for the Magnetic Field sensor
	 *
	 * @param values
	 * 		new magnetic field values
	 */
	void setMagField(float[] values) { magneticField = values; }
}
