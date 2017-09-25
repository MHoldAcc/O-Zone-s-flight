package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class Settings {
    //***Gameplay***
    protected static int Gameplay_TimeTillNewMeteorite = 5000;
    protected static int Gameplay_TimeTillVelocityIncrease = 10000;
    protected static float Gameplay_VelocityIncrease = (float)1.1;
    protected static int Gameplay_MillisecondsPerFrame = 33;
    //***Player***
    protected static int Player_MaxMovement = 5;
    protected static int Player_Height = 10;
    protected static int Player_Width = 20;
    //***Meteorites***
    protected static int Meteorites_Movement = 1;
    //***Environment Sizes***
    protected static int Environment_Width = 100;
    protected static int Environment_Height = 100;
    protected static int Environment_LineCount = 3;
    protected static int Environment_LineWidth(){
        return (int)Environment_Width / 3;
    }
    //***Inputs***
    protected static float Inputs_DeathZone = (float)0.1;
}
