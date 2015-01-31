package io.github.omgimanerd.precisitool;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;


public class MainActivity extends Activity implements SensorEventListener {

  private SensorManager sensorManager_;
  private Sensor sensor_;

  MainView mainView_;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    sensorManager_ = (SensorManager) getSystemService(
        Context.SENSOR_SERVICE);
    sensor_ = sensorManager_.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    mainView_ = new MainView(this);
    setContentView(mainView_);
  }


  protected void onResume() {
    super.onResume();
    sensorManager_.registerListener(this, sensor_,
                        SensorManager.SENSOR_DELAY_NORMAL);
  }

  protected void onPause() {
    super.onPause();
    sensorManager_.unregisterListener(this);
  }

  public void onAccuracyChanged(Sensor sensor, int accuracy) {}

  public void onSensorChanged(SensorEvent event) {
    mainView_.update(event);
  }
}
