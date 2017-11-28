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
class OrientationSensorListener implements SensorEventListener {
    /**
     * The Tag for Debugging logs.
     */
    private final static String TAG = "SENSOR";

    /**
     * The UIController which requires the Sensor Data
     */
    private final GameLoop loop;

    /**
     * Creates an instance of the OrientationSensorListener
     * @param loop The UIController which requires the Sensor Data
     * @param sensor The Sensor that will use the sensor data to provide orientation data
     */
    OrientationSensorListener(GameLoop loop, SensorManager sensor){
		this.loop = loop;
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensor.registerListener(this, sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
    }

    /**
     * Called if any of the registered sensors detects a change
     * @param event The data the sensor delivers
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                loop.setGravity(event.values);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                loop.setMagField(event.values);
                break;
            default: Log.e(TAG, "Unknown Sensor Type: " + event.sensor.getType());
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
