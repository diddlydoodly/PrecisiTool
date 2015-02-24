package io.github.omgimanerd.precisitool;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener {

  private PrecisiToolView precisiToolView_;
  private SensorManager sManager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    precisiToolView_ = new PrecisiToolView(this);
    setContentView(precisiToolView_);
  }

  @Override
  protected void onResume() {
    super.onResume();
    sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                              SensorManager.SENSOR_DELAY_FASTEST);
  }

  @Override
  protected void onStop() {
    sManager.unregisterListener(this);
    super.onStop();
  }

  @Override
  public void onAccuracyChanged(Sensor arg0, int arg1) {}

  @Override
  public void onSensorChanged(SensorEvent event) {
    precisiToolView_.updateRawOrientationValues(event.values);
  }
}
