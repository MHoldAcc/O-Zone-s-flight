package onebit.o_zonesflight;

/**
 * Created by Michael on 20.09.2017.
 */
public class Meteorite {

    /**
     * Current course of the meteorite
     */
    private int Course;

    /**
     * Current latitude of the meteorite (where he is in the height)
     */
    private float Latitude;

    /**
     * Initializes Meteorite with course number
     * @param course the number of the course
     */
    public Meteorite(int course){
        Course = course;
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
}
