package onebit.o_zonesflight;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by admin on 25.09.2017.
 */
public class OrientationSensorListener implements SensorEventListener {

    private final static String TAG = "SENSOR";

    UIController owner;

    public OrientationSensorListener(UIController owner, SensorManager sensor){
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensor.SENSOR_DELAY_GAME);
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensor.SENSOR_DELAY_GAME);
        this.owner = owner;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                owner.setGravity(event.values);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                owner.setMagField(event.values);
                break;
            default: Log.e(TAG, "Unknown Sensor Type: " + event.sensor.getType()); return;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore
}
