package onebit.o_zonesflight;

/**
 * Created by Michael on 20.09.2017.
 * Tis class delivers the constant settings of the application
 * Modify if you feel adventurous ;)
 */
public class Settings {
    //***Gameplay***
    /** The amount of milliseconds until a new meteorite is spawned */
    protected final static int Gameplay_TimeTillNewMeteorite = 1000;
    /** The amount of milliseconds until a velocity increase takes place */
    protected final static int Gameplay_TimeTillVelocityIncrease = 5000;
    /** The velocity multiplier for a velocity increase */
    protected final static float Gameplay_VelocityIncrease = 1.1f;
    /** The amount of milliseconds each frame should have */
    protected final static int Gameplay_MillisecondsPerFrame = 33;
    //***Player***
    /** The maximum amount of units the player may move in a single tick */
    protected final static int Player_MaxMovement = 40;
    /** The height of the player in units */
    protected final static int Player_Height = 10;
    /** The width of the player in units */
    protected final static int Player_Width = 20;
    //***Environment Sizes***
    /** The width of the game world in units */
    protected final static int Environment_Width = 100;
    /** The height of the game world in units */
    protected final static int Environment_Height = 100;
    /** The amount of paths a meteorite may fall on */
    protected final static int Environment_LineCount = 3;
    /** The width of a path a meteorite takes, calculated from the Environment_Width and the Environment_LineCount */
    protected final static int Environment_LineWidth = Environment_Width / Environment_LineCount;
    //***Meteorites***
    /** The base speed of the meteorites in Unites per Tick*/
    protected final static int Meteorites_Movement = 3;
    /** The height of a meteorite, matching the Environment_LineWidth */
    protected final static int Meteorites_Height = Environment_LineWidth;
    //***Inputs***
    /** The DeathZone, the threshold of input that is ignored from the hardware sensor */
    protected final static float Inputs_DeathZone = 0.02f;
    /** The upper limit of the input that is accepted */
    protected final static float Inputs_MaxInput = 0.3f;
}
