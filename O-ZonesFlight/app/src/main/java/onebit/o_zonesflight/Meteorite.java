package onebit.o_zonesflight;

/**
 * Created by admin on 20.09.2017.
 */
public class Meteorite {
    //1-3
    private int Course;

    //100-0
    private float Latitude;

    public Meteorite(int course){
        Course = course;
    }

    int GetCourse(){
        return Course;
    }

    float GetLatitude(){
        return Latitude;
    }

    void SetLatitude(float latitude){
        Latitude = latitude;
    }
}
