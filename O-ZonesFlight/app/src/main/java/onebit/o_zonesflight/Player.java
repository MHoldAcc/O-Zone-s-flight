package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class Player {
    //0-100
    private float Position;

    public Player(){
        int position = (int) (Settings.Environment_Width / 2);
        Position = (long)position;
    }

    public float GetPosition(){
        return Position;
    }

    //bearing = -1 - 1
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
        if(Position > Settings.Environment_Width)
            Position = Settings.Environment_Width;
        else if (Position < 0)
            Position = 0;
    }
}
