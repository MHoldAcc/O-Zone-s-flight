package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by silva on 26.10.2017.
 */
public interface IRenderable {
    float GetTop();
    float GetBottom();
    float GetLeft();
    float GetRight();
    float GetWidth();
    float GetHeight();
    RectF GetRect();
    Bitmap GetImage();
}
