package io.github.omgimanerd.precisitool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PrecisiToolView extends View {

  private static final int BALL_COLOR = Color.RED;
  private static final int BALL_RADIUS = 40;
  private static final int CENTER_INDICATOR_COLOR = Color.YELLOW;
  private static final int CENTER_INDICATOR_STROKE = 5;
  private static final int CENTER_INDICATOR_RADIUS = 50;
  
  private static final float SENSITIVITY = 25;

  private float[] rawOrientationValues_;
  private float[] calibratedOrientationFlatValues_;
  private float screenWidth_;
  private float screenHeight_;

  private Paint precisiToolIndicatorPaint_;
  private Paint precisiToolBallPaint_;

  public PrecisiToolView (Context context, AttributeSet attrs) {
    super(context, attrs);

    rawOrientationValues_ = new float[3];
    calibratedOrientationFlatValues_ = new float[3];
    decalibrateOrientationValues();
    screenWidth_ = getResources().getDisplayMetrics().widthPixels;
    screenHeight_ = getResources().getDisplayMetrics().heightPixels;

    precisiToolBallPaint_ = new Paint();
    precisiToolBallPaint_.setColor(BALL_COLOR);

    precisiToolIndicatorPaint_ = new Paint();
    precisiToolIndicatorPaint_.setStyle(Paint.Style.STROKE);
    precisiToolIndicatorPaint_.setStrokeWidth
        (CENTER_INDICATOR_STROKE);
    precisiToolIndicatorPaint_.setColor(CENTER_INDICATOR_COLOR);
  }

  public void updateRawOrientationValues(float[] rawOrientationValues) {
    rawOrientationValues_ = rawOrientationValues;
  }

  public void decalibrateOrientationValues() {
    for (int i = 0; i < calibratedOrientationFlatValues_.length; ++i) {
      calibratedOrientationFlatValues_[i] = 0;
    }
  }

  public void onDraw(Canvas canvas) {
    float[] deviations = new float[3];
    for (int i = 0; i < deviations.length; ++i) {
      deviations[i] = calibratedOrientationFlatValues_[i] -
          rawOrientationValues_[i];
    }

    float centerX = screenWidth_ / 2;
    float centerY = screenHeight_ / 2;

    canvas.drawCircle(centerX, centerY, CENTER_INDICATOR_RADIUS,
                      precisiToolIndicatorPaint_);
    canvas.drawCircle(centerX + deviations[0] * SENSITIVITY,
                      centerY + -deviations[1] * SENSITIVITY,
                      BALL_RADIUS, precisiToolBallPaint_);

    invalidate();
  }
}
