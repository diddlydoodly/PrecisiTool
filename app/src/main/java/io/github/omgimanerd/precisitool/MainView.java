package io.github.omgimanerd.precisitool;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by omgimanerd on 1/27/15.
 */
public class MainView extends View {

  private static final int DIMENS = 3;

  private TextView[] textViews_;

  private double[] raw_acceleration_;
  private double[] acceleration_;

  public MainView(Context context) {
    super(context);
    final int[] TEXTVIEW_IDS = new int[] {
        R.id.x, R.id.y, R.id.z
    };
    textViews_ = new TextView[DIMENS];
    for (int i = 0; i < DIMENS; ++i) {
      textViews_[i] = (TextView) findViewById(TEXTVIEW_IDS[i]);
    }
    raw_acceleration_ = new double[] {
        0.0, 0.0, 0.0
    };
    acceleration_ = new double[DIMENS];
  }

  public void update(SensorEvent event) {
    for (int i = 0; i < DIMENS; ++i) {
      raw_acceleration_[i] = event.values[i];
    }
  }

  public void redraw() {
    for (int i = 0; i < DIMENS; ++i) {
      textViews_[i].setText("" + raw_acceleration_[i]);
    }
  }

  public void onDraw(Canvas canvas) {
    redraw();
  }
}
