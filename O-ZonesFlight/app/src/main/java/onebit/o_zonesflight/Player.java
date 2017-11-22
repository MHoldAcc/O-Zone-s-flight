package onebit.o_zonesflight;

import android.graphics.PointF;
import android.widget.TextView;

/**
 * Created by Michael on 20.09.2017.
 */
public class Player implements IRenderable {
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
        //Applies Deathzone 0.1
        if(bearing >= -Settings.Inputs_DeathZone && bearing <= Settings.Inputs_DeathZone)
            bearing = 0;
        //Rounds if to big number
        if(bearing > Settings.Inputs_MaxInput)
            bearing = Settings.Inputs_MaxInput;
        else if(bearing < -Settings.Inputs_MaxInput)
            bearing = -Settings.Inputs_MaxInput;
        //Sets position
        float movement = Settings.Player_MaxMovement * bearing;
        Position = Position + movement;
        if(Position > Settings.Environment_Width - Settings.Player_Width)
            Position = Settings.Environment_Width - Settings.Player_Width;
        else if (Position < 0)
            Position = 0;

        TextView tv = new TextView(null);

        tv.setOnTouchListener(null);
    }

    @Override
    public PointF GetPos() { return new PointF(GetX(),GetY()); }

    @Override
    public float GetX() { return GetPosition(); }

    @Override
    public float GetY() { return Settings.Player_Height; }

    @Override
    public float GetWidth() { return Settings.Player_Width; }

    @Override
    public float GetHeight() { return Settings.Player_Height; }
}
