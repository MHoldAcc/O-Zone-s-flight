package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by Michael on 20.09.2017.
 */
public class Player implements IRenderable, ITickable {

    private static Bitmap bmp;
    public static void setBitmap(Bitmap image){ bmp = image; }
    /**
     * Current position longitude of player
     */
    private float Position;

    /**
     * Initializes players
     */
    public Player(){
        Position = Settings.Environment_Width / 2 - Settings.Player_Width / 2;
    }

    /**
     * Returns position of player
     * @return current position of player
     */
    public float GetPosition(){
        return Position;
    }

    /**
     * Uses bearing to set player position
     * @param bearing how much the phone is tilted
     */
    public void ApplyBearing(float bearing){
        //Ignore values below the DeathZone
        if( Math.abs(bearing) > Settings.Inputs_DeathZone ) {
            //Applies Input limitation
            bearing = Math.min(Math.max(bearing, -Settings.Inputs_MaxInput), Settings.Inputs_MaxInput);
            //Sets position
            Position += Settings.Player_BaseSpeed * bearing;
            //Screen Bounds
            Position = Math.min(Math.max(Position, 0), Settings.Environment_Width - GetWidth());
        }
    }

    @Override
    public float GetTop() { return GetBottom() - GetHeight(); }

    @Override
    public float GetBottom() { return Settings.Environment_Height; }

    @Override
    public float GetLeft() { return GetPosition(); }

    @Override
    public float GetRight() { return GetLeft() + GetWidth(); }

    @Override
    public float GetWidth() { return Settings.Player_Width; }

    @Override
    public float GetHeight() { return Settings.Player_Height; }

    @Override
    public RectF GetRect() { return new RectF(GetLeft(), GetTop(), GetRight(), GetBottom()); }

    @Override
    public Bitmap GetImage() { return bmp; }

    @Override
    public void Tick(Game game, Object... data) {
        // data[0] is the bearing
        ApplyBearing((Float)data[0]);
    }

    @Override
    public boolean CanBeRemoved() {
        return false;
    }
}
