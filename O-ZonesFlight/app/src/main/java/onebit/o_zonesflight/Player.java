package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class Player {
    //Settings for development
    protected float setting_deathZone = (float)0.1;
    protected float setting_maxPosition = 100;
    protected float setting_maxMovement = 5;

    //0-100
    private float Position;

    public Player(){
        int position = (int) (setting_maxPosition / 2);
        Position = (long)position;
    }

    public float GetPosition(){
        return Position;
    }

    //bearing = -1 - 1
    public void ApplyBearing(float bearing){
        //Applies Deathzone 0.1
        if(bearing >= -setting_deathZone && bearing <= setting_deathZone)
            bearing = 0;
        //Rounds if to big number
        if(bearing > 1)
            bearing = 1;
        else if(bearing < -1)
            bearing = -1;
        //Sets position
        float movement = setting_maxMovement * bearing;
        Position = Position + movement;
        if(Position > setting_maxPosition)
            Position = setting_maxPosition;
        else if (Position < 0)
            Position = 0;
    }
}
