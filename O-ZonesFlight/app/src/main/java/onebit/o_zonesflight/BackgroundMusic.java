package onebit.o_zonesflight;

import android.media.MediaPlayer;
import android.os.AsyncTask;

/**
 * Created by admin on 25.09.2017.
 */
public class BackgroundMusic extends AsyncTask<Void, Void, Void> {

    public BackgroundMusic(UIController uiController){
        controller = uiController;
    }

    private static UIController controller;

    @Override
    protected Void doInBackground(Void... params) {
        MediaPlayer player = MediaPlayer.create(controller, R.raw.apocalypse);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();

        return null;
    }
}
