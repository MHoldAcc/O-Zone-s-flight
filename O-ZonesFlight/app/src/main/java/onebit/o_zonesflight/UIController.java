package onebit.o_zonesflight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Main Activity.
 * This Activity provides the user with an interface.
 * Depending on the state the UI will change.
 */
public class UIController extends Activity {

	/** The reference to the Game. */
	Game              GameInstance;
	/** The state of the UI Controller */
	UIControllerState State;

	//Sprites
	private ArrayList<Texture> Sprites;
	/** The Background Sprite */
	private Bitmap             bmp_space;

	//Resources
	/** The Music Player */
	private MediaPlayer mediaPlayer = null;
	/** The Display Bitmap the canvas draws on */
	Bitmap    display;
	/** The Canvas instance that draws the game on the UI */
	Canvas    canvas;
	/** ImageView which renders the game */
	ImageView canvasContainer;

	/**
	 * Called upon creation of the Activity. A.K.A. Application start/resume
	 * The initialisation of the UI happens here
	 *
	 * @param savedInstanceState
	 * 		May be used to load data from previous sessions
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//load unmanaged stuff
		super.onCreate(savedInstanceState);



		//Initialize SaveFileManager
		ISaveFileManager saveFileManager = new LocalSaveFileManager(this);

		//Initialize Game & Load Data
		GameInstance = new Game(saveFileManager);
		SavedState GameState = GameInstance.GetState();

		//Initialize Sound
		InitializeMusic();


		// Switch to GameMenu State
		UIControllerState.MainMenu.Activate(this);
	}

	/**
	 * Redirects onclick events to their respective target methods.
	 * This is an unplanned workaround as onclick target methods require a view parameter.
	 *
	 * @param sender
	 * 		The button that was 'clicked'
	 */
	public void OnClickHandler(View sender) {
		System.out.print("TEST");
		switch(sender.getId()) {
			case R.id.btn_start:
				UIControllerState.Running.Activate(this);
				break;
			case R.id.btn_credits:
				UIControllerState.Credits.Activate(this);
				break;
			case R.id.btn_back:
				UIControllerState.MainMenu.Activate(this);
				break;
			// Invalid IDs should throw an exception
			case R.id.btn_shop:
				UIControllerState.Textures.Activate(this);
				break;
			default:
				Exception ex = new Exception("The ID of the previously clicked button is unknown.");
				ex.printStackTrace();
				//TODO: maybe implement error handler or something
				break;
		}
	}

	/**
	 * Initializes music for the game
	 */
	private void InitializeMusic( ) {
		mediaPlayer = MediaPlayer.create(this, R.raw.apocalypse);
		mediaPlayer.setLooping(true); // Set looping
		mediaPlayer.setVolume(1.0f, 1.0f);
		mediaPlayer.start();
	}

	/**
	 * Pauses music from the mediaPlayer.
	 */
	@Override
	protected void onPause( ) {
		super.onPause();
		mediaPlayer.pause();
	}

	/**
	 * Resumes music from the mediaPlayer.
	 */
	@Override
	protected void onResume( ) {
		super.onResume();
		mediaPlayer.start();
	}

}