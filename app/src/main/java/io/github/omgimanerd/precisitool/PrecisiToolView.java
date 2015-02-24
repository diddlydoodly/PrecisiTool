package io.github.omgimanerd.precisitool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by omgimanerd on 2/23/15.
 */
public class PrecisiToolView extends View {

  private static final int PRECISITOOL_BALL_COLOR = Color.RED;
  private static final int PRECISITOOL_BALL_SIZE = 40;
  private static final float SENSITIVITY = 8;

  private float[] rawOrientationValues_;
  private float[] calibratedOrientationFlatValues_;
  private float screenWidth_;
  private float screenHeight_;

  Paint precisiToolBallPaint_;

  public PrecisiToolView (Context context) {
    super(context);

    rawOrientationValues_ = new float[3];
    calibratedOrientationFlatValues_ = new float[3];
    screenWidth_ = getResources().getDisplayMetrics().widthPixels;
    screenHeight_ = getResources().getDisplayMetrics().heightPixels;

    precisiToolBallPaint_ = new Paint();
    precisiToolBallPaint_.setColor(PRECISITOOL_BALL_COLOR);
  }

  public void updateRawOrientationValues(float[] rawOrientationValues) {
    rawOrientationValues_ = rawOrientationValues;
  }

  public void calibrateOrientationValues() {
    calibratedOrientationFlatValues_ = rawOrientationValues_.clone();
  }

  public void onDraw(Canvas canvas) {
    float[] deviations = new float[3];
    for (int i = 0; i < deviations.length; ++i) {
      deviations[i] = calibratedOrientationFlatValues_[i] -
          rawOrientationValues_[i];
    }

    float centerX = screenWidth_ / 2;
    float centerY = screenHeight_ / 2;

    canvas.drawCircle(centerX + deviations[2] * SENSITIVITY,
                      centerY + deviations[1] * SENSITIVITY,
                      PRECISITOOL_BALL_SIZE,
                      precisiToolBallPaint_);

    invalidate();
  }

  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();

    if (action == MotionEvent.ACTION_DOWN) {
      calibrateOrientationValues();
    }
    return true;
  }
}
