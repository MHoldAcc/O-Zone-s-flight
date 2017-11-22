package onebit.o_zonesflight;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by Michael on 20.09.2017.
 */
public class Meteorite implements IRenderable, ITickable {

    private static Bitmap bmp;
    public static void setBitmap(Bitmap image){ bmp = image; }

    /** Current course of the meteorite */
    private int Course;

    /** Current latitude of the meteorite (where he is in the height) */
    private float Latitude;

    /** Tells if the Meteorite can be removed */
    private  boolean canBeRemoved;

    /**
     * Initializes Meteorite with course number
     * @param course the number of the course
     */
    public Meteorite(int course){
        Course = course;
        Latitude = -Settings.Meteorites_Height;
    }

    /**
     * Gets course number
     * @return course number (1-3)
     */
    int GetCourse(){
        return Course;
    }

    /**
     * Returns latitude
     * @return latitude of meteorite
     */
    float GetLatitude(){
        return Latitude;
    }

    /**
     * Sets latitude
     * @param latitude latitude to set
     */
    void SetLatitude(float latitude){
        Latitude = latitude;
    }

    @Override
    public float GetLeft() { return GetCourse() * Settings.Environment_LineWidth; }

    @Override
    public float GetBottom() { return GetTop() + GetHeight(); }

    @Override
    public float GetTop() { return GetLatitude(); }

    @Override
    public float GetRight() { return GetLeft() + GetWidth(); }

    @Override
    public float GetWidth() { return Settings.Environment_LineWidth; }

    @Override
    public float GetHeight() { return Settings.Meteorites_Height; }

    @Override
    public RectF GetRect() { return new RectF(GetLeft(), GetTop(), GetRight(), GetBottom()); }

    @Override
    public Bitmap GetImage() { return bmp; }

    @Override
    public void Tick(Game game, Object... data) {
        //data[1] is the velocity
        SetLatitude(GetLatitude() + Settings.Meteorites_Movement * (Float)data[1]);
        //Removes meteorite as soon as it leaves the screen below
        if(GetTop() >= Settings.Environment_Height) canBeRemoved = true;
        // Tells the game that the meteor collided with the player
        else if (RectF.intersects(game.GetPlayer().GetRect(),GetRect())) game.Collided(this);
    }

    @Override
    public boolean CanBeRemoved() { return canBeRemoved; }
}
