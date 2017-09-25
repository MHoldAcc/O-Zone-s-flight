package onebit.o_zonesflight;

/**
 * Created by Michael on 20.09.2017.
 */
public class Player {
    /**
     * Current position longitude of player
     */
    private float Position;

    /**
     * Initializes players
     */
    public Player(){
        int position = (int) (Settings.Environment_Width / 2);
        Position = (long)position;
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
        if(Position > Settings.Environment_Width)
            Position = Settings.Environment_Width;
        else if (Position < 0)
            Position = 0;
    }
}
