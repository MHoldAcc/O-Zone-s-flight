package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.widget.ImageView;

/**
 * Created by admin on 20.09.2017.
 */
public class GameTickRunnable implements Runnable {

    private final UIController owner;

    public GameTickRunnable(UIController owner){
        this.owner = owner;
    }

    @Override
    public void run() { owner.GameTick(); }

}
