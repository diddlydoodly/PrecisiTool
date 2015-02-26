package io.github.omgimanerd.precisitool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by omgimanerd on 2/23/15.
 */
public class PrecisiToolView extends View {

  private static final int BALL_COLOR = Color.RED;
  private static final int BALL_RADIUS = 40;
  private static final int CENTER_INDICATOR_COLOR = Color.YELLOW;
  private static final int CENTER_INDICATOR_STROKE = 5;
  private static final int CENTER_INDICATOR_RADIUS = 50;
  
  private static final int ANGLE_INDICATOR_OFFSET = 20;
  
  private static final float SENSITIVITY = 8;

  private float[] rawOrientationValues_;
  private float[] calibratedOrientationFlatValues_;
  private float screenWidth_;
  private float screenHeight_;

  private Paint precisiToolIndicatorPaint_;
  private Paint precisiToolBallPaint_;
  private Paint textPaint_;

  public PrecisiToolView (Context context) {
    super(context);

    rawOrientationValues_ = new float[3];
    calibratedOrientationFlatValues_ = new float[3];
    screenWidth_ = getResources().getDisplayMetrics().widthPixels;
    screenHeight_ = getResources().getDisplayMetrics().heightPixels;

    precisiToolBallPaint_ = new Paint();
    precisiToolBallPaint_.setColor(BALL_COLOR);

    precisiToolIndicatorPaint_ = new Paint();
    precisiToolIndicatorPaint_.setStyle(Paint.Style.STROKE);
    precisiToolIndicatorPaint_.setStrokeWidth
        (CENTER_INDICATOR_STROKE);
    precisiToolIndicatorPaint_.setColor(CENTER_INDICATOR_COLOR);

    textPaint_ = new Paint();
    textPaint_.setColor(Color.BLACK);
    textPaint_.setTextSize(getResources().getDimensionPixelSize(
        R.dimen.text_size));
    textPaint_.setTextAlign(Paint.Align.CENTER);
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

    canvas.drawCircle(centerX, centerY, CENTER_INDICATOR_RADIUS,
                      precisiToolIndicatorPaint_);
    canvas.drawCircle(centerX + deviations[2] * SENSITIVITY,
                      centerY + deviations[1] * SENSITIVITY,
                      BALL_RADIUS, precisiToolBallPaint_);

    String calibrateString = getResources().getString(R.string.recalibrate);
    canvas.drawText(calibrateString, centerX, centerY, textPaint_);

    String xAngleText = getResources().getString(R.string.angular_difference)
        + " " + (int) deviations[2];
    String yAngleText = getResources().getString(R.string.angular_difference)
        + " " + (int) deviations[1];

    Path yAngleTextPath = new Path();
    yAngleTextPath.moveTo(centerX, centerY);
    yAngleTextPath.lineTo(centerX, 0);

    canvas.drawText(xAngleText, centerX, 3 * screenHeight_ / 4,
                    textPaint_);
    canvas.drawTextOnPath(yAngleText, yAngleTextPath, ANGLE_INDICATOR_OFFSET,
                          0, textPaint_);

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
