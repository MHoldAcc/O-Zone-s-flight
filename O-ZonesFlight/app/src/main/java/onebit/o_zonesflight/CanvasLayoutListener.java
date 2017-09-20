package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by admin on 20.09.2017.
 */
public class CanvasLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private UIController owner;
    private ImageView canvasContainer;
    public CanvasLayoutListener(UIController owner, ImageView canvasContainer){ this.owner = owner; this.canvasContainer = canvasContainer; }
    @Override
    public void onGlobalLayout() {
        canvasContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Bitmap display = Bitmap.createBitmap(canvasContainer.getWidth(), canvasContainer.getHeight(), Bitmap.Config.ARGB_8888);
        canvasContainer.setImageBitmap(display);
        owner.LayoutDoneCallback(display);
    }
}
