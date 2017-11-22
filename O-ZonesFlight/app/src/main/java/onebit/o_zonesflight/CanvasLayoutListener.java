package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * A Listener which waits for the imageview used for the canvas to finish it's layout.
 * Upon finishing a Callback is invoked on the UIController
 * Created by Silvan Pfister on 20.09.2017.
 */
class CanvasLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    /** The UIController on which the callback is invoked */
    private final UIController owner;
    /** The ImageView which serves as a canvas */
    private final ImageView canvasContainer;

    /**
     * Creates an instance of the CanvasLayoutListener.
     * @param owner UIController on which the callback is invoked
     * @param canvasContainer The ImageView which serves as a canvas
     */
    public CanvasLayoutListener(UIController owner, ImageView canvasContainer){ this.owner = owner; this.canvasContainer = canvasContainer; }

    /**
     * This method is called once the Layout has been set up.
     * It will invoke a callback on the UIController and send the Bitmap with is used as display from that point on
     */
    @Override
    public void onGlobalLayout() {
        canvasContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Bitmap display = Bitmap.createBitmap(canvasContainer.getWidth(), canvasContainer.getHeight(), Bitmap.Config.ARGB_8888);
        canvasContainer.setImageBitmap(display);

        try {
            UIControllerState.Running.Continue(owner, display);
        } catch (UIControllerState.InvalidStateException e) {
            e.printStackTrace();
        }
    }
}
