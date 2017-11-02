package onebit.o_zonesflight;

import android.graphics.PointF;

/**
 * Created by silva on 26.10.2017.
 */

public class Coin implements IRenderable{
    /**
     * The horizontal position of the coin
     */
    private float x;
    /**
     * The vertical position of the coin
     */
    private float y;

    public Coin(float x){ this.x = x; y = Settings.Environment_Height + Settings.Coin_Size; }

    /**
     * Sets the Y position to the new value
     * @param newY The new value for the Y position
     */
    public void SetY(float newY) { y = newY; }

    @Override
    public PointF GetPos() { return new PointF(GetX(),GetY()); }

    @Override
    public float GetX() { return x; }

    @Override
    public float GetY() { return y; }

    @Override
    public float GetWidth() { return Settings.Coin_Size; }

    @Override
    public float GetHeight() { return Settings.Coin_Size; }
}
