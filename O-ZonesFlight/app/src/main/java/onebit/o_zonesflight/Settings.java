package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class Settings {
    //***Gameplay***
    protected final static int Gameplay_TimeTillNewMeteorite = 1000;
    protected final static int Gameplay_TimeTillVelocityIncrease = 5000;
    protected final static float Gameplay_VelocityIncrease = 1.5f;
    protected final static int Gameplay_MillisecondsPerFrame = 33;
    //***Player***
    protected final static int Player_MaxMovement = 40;
    protected final static int Player_Height = 10;
    protected final static int Player_Width = 20;
    //***Environment Sizes***
    protected final static int Environment_Width = 100;
    protected final static int Environment_Height = 100;
    protected final static int Environment_LineCount = 3;
    protected final static int Environment_LineWidth = Environment_Width / Environment_LineCount;
    //***Meteorites***
    protected final static int Meteorites_Movement = 3;
    protected final static int Meteorites_Height = Environment_LineWidth;
    //***Inputs***
    protected final static float Inputs_DeathZone = 0.02f;
    protected final static float Inputs_MaxInput = 0.3f;
}
