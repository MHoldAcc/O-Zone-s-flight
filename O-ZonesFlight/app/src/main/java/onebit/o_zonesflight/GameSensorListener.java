package onebit.o_zonesflight;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by admin on 25.09.2017.
 */
public class GameSensorListener implements SensorEventListener{
    UIController owner;

    public GameSensorListener(UIController owner){ this.owner = owner; }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        Log.d("SENSOR", "Sew Values: ["+x+"]["+y+"]["+z+"]");
        owner.SensorCallback(z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignore
    }
}
