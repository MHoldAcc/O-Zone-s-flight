package onebit.o_zonesflight;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * A SensorEventListener which is used to provide the UIController with the required data to get the device orientation.
 * Created by Silvan Pfister on 25.09.2017.
 */
public class OrientationSensorListener implements SensorEventListener {
    /**
     * The Tag for Debugging logs.
     */
    private final static String TAG = "SENSOR";

    /**
     * The UIController which requires the Sensor Data
     */
    private UIController owner;

    /**
     * Creates an instance of the OrientationSensorListener
     * @param owner The UIController which requires the Sensor Data
     * @param sensor The Sensor that will use the sensor data to provide orientation data
     */
    public OrientationSensorListener(UIController owner, SensorManager sensor){
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensor.SENSOR_DELAY_GAME);
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), sensor.SENSOR_DELAY_GAME);
        this.owner = owner;
    }

    /**
     * Called if any of the registered sensors detects a change
     * @param event The data the sensor delivers
     */
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

    /**
     * Not used in any way.
     * @param sensor Unused
     * @param accuracy Unused
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore
}
