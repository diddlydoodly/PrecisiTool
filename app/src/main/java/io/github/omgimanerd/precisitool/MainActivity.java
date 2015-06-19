package io.github.omgimanerd.precisitool;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity implements SensorEventListener {

  private PrecisiToolView precisiToolView_;
  private SensorManager sManager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_layout);

    precisiToolView_ = (PrecisiToolView) findViewById(R.id.precisitoolView);
    sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
  }

  @Override
  protected void onResume() {
    sManager.registerListener(
        this,
        sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_FASTEST);
    super.onResume();
  }

  @Override
  protected void onPause() {
    sManager.unregisterListener(this);
    super.onPause();
  }

  @Override
  public void onAccuracyChanged(Sensor arg0, int arg1) {}

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
      precisiToolView_.updateRawOrientationValues(event.values);
    }
  }
}
