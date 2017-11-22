package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Environment;

/**
 * Created by silva on 26.10.2017.
 */

public class Coin implements IRenderable, ITickable{

    private static Bitmap bmp;
    public static void setBitmap(Bitmap image){ bmp = image; }

    /**
     * The position of the left side of the coin
     */
    private float left;
    /**
     * The position of the top side of the coin
     */
    private float top;

    private boolean canBeRemoved;

    public Coin(float left){ this.left = left; top = -Settings.Coin_Size; }

    /**
     * Sets the position of the top side to the new value
     * @param newTop The new value for the position of the top side
     */
    public void SetTop(float newTop) { top = newTop; }

    @Override
    public float GetTop() { return top; }

    @Override
    public float GetBottom() { return GetTop() + GetHeight(); }

    @Override
    public float GetLeft() { return left; }

    @Override
    public float GetRight() { return GetLeft() + GetWidth(); }

    @Override
    public float GetWidth() { return Settings.Coin_Size; }

    @Override
    public float GetHeight() { return Settings.Coin_Size; }

    @Override
    public RectF GetRect() { return new RectF(GetLeft(), GetTop(), GetRight(), GetBottom()); }

    @Override
    public Bitmap GetImage() { return bmp; }

    @Override
    public void Tick(Game game, Object... data) {
        // data[1] is the velocity
        SetTop(GetLeft() - Settings.Coin_Movement * (Float)data[1]);
        if (GetTop() > Settings.Environment_Height) canBeRemoved = true;
        else if(RectF.intersects(game.GetPlayer().GetRect(), GetRect())) {
            game.Collided(this);
            canBeRemoved = true;
        }
    }

    @Override
    public boolean CanBeRemoved() { return canBeRemoved; }
}
