package managers;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerManager implements SensorEventListener{
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	private float[] accelerometerValues = null;

	public AccelerometerManager(Activity oActivity) {

		mSensorManager = (SensorManager) oActivity.getSystemService(Context.SENSOR_SERVICE);

		if (mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
			mAccelerometer = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	public void unRegisterListener(){
		mSensorManager.unregisterListener(this);
	}

	public float getXAxis(){
		if (accelerometerValues != null)
			return this.accelerometerValues[0];
		else return 0;
	}

	public float getYAxis(){
		if (accelerometerValues != null)
			return this.accelerometerValues[1];
		else return 0;
	}

	public float getZAxis(){
		if (accelerometerValues != null)
			return this.accelerometerValues[2];
		else return 0;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		accelerometerValues = event.values;
	}
}
