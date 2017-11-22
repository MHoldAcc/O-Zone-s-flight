package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides the available States for the UI Controller
 * Created by Silvan Pfister on 20.09.2017.
 */
public enum UIControllerState {
    /** The UI is on the Main Menu */
    MainMenu,
    /** The UI is running the Game */
    Running,
    /** The UI is displaying the GameOver screen */
    GameOver,
    /** The UI is displaying the Credits screen */
    Credits;
    public class InvalidStateException extends Exception{
        InvalidStateException(UIControllerState state, String message) {
            super("Invalid State on '" + state.toString() + "' with message '" + message +"'");
        }
    }

    /**
     * Callback for the GameStart UI update. The Game-Loop needs the UI values before starting,
     * which can only be retrieved once the UI has been drawn.
     * Can be seen as direct continuation of GameStart()
     * @param data index 0 is the controller, the rest are dependant on the situation
     */
    public void Continue(Object... data) throws InvalidStateException {
        switch (this){
            case Running:
                final UIController controller = (UIController)data[0];
                Bitmap display = (Bitmap)data[1];
                controller.display = display;
                final Handler mainHandler = new Handler(controller.getMainLooper());
                controller.canvas = new Canvas(display);
                controller.GameTimer = new Timer();
                controller.TickWrapper = new TimerTask() {
                    @Override
                    public void run() {
                        mainHandler.post(new GameTickRunnable(controller));
                    }
                };

                controller.GameTimer.schedule(controller.TickWrapper, 0, Settings.Gameplay_MillisecondsPerFrame);
                break;
            default: throw new InvalidStateException(this, "There is no case where the '" + this.toString() + "' state would continue something.");
        }
    }
    public void Activate(UIController controller){
        TextView highScoreText, scoreText;
        controller.State = this;
        switch (this){
            case Credits:
                controller.setContentView(R.layout.lay_gamecredits);
                break;
            case Running:
                controller.setContentView(R.layout.lay_gamerunning);

                //Reset existing game status
                controller.GameInstance.ResetGame();

                controller.canvasContainer = (ImageView) controller.findViewById(R.id.img_canvas);
                controller.canvasContainer
                        .getViewTreeObserver()
                        .addOnGlobalLayoutListener(new CanvasLayoutListener(controller, controller.canvasContainer));
                // Continues rest once the canvas is available on the LayoutDoneCallback
                break;
            case GameOver:

                controller.setContentView(R.layout.lay_gameover);

                //load highscore
                highScoreText = (TextView) controller.findViewById(R.id.tex_highscore);
                highScoreText.setText(
                        String.format(
                                Locale.getDefault(),
                                "%s: %d",
                                controller.getResources().getText(R.string.txt_highscore),
                                controller.GameInstance.GetHighScore()));

                //load score

                scoreText = (TextView) controller.findViewById(R.id.tex_score);
                scoreText.setText(
                        String.format(
                                Locale.getDefault(),
                                "%s: %d",
                                controller.getResources().getText(R.string.txt_score),
                                controller.GameInstance.GetScore()));
                break;
            default:
            case MainMenu:
                controller.setContentView(R.layout.lay_gamestart);

                //load highscore
                highScoreText = (TextView) controller.findViewById(R.id.tex_highscore);
                highScoreText.setText(
                        String.format(
                                Locale.getDefault(),
                                "%s: %d",
                                controller.getResources().getText(R.string.txt_highscore),
                                controller.GameInstance.GetHighScore()));
                break;
        }
    }
}
