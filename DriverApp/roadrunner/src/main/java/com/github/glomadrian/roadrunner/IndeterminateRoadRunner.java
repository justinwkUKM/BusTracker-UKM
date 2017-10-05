package com.github.glomadrian.roadrunner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import com.github.glomadrian.roadrunner.painter.configuration.PathPainterConfiguration;
import com.github.glomadrian.roadrunner.painter.configuration.factory.PathPainterConfigurationFactory;
import com.github.glomadrian.roadrunner.painter.indeterminate.IndeterminatePainter;
import com.github.glomadrian.roadrunner.painter.indeterminate.IndeterminatePathPainter;
import com.github.glomadrian.roadrunner.painter.indeterminate.factory.IndeterminatePainterFactory;
import com.github.glomadrian.roadrunner.path.PathContainer;
import com.github.glomadrian.roadrunner.utils.AssertUtils;
import java.text.ParseException;

/**
 * @author Adrián García Lomas
 */
public class IndeterminateRoadRunner extends RoadRunner {

  private static final String TAG = "IndeterminateLoading";
  private int originalWidth;
  private int originalHeight;
  private String pathData;
  private PathContainer pathContainer;
  private IndeterminatePathPainter pathPainter;
  private IndeterminatePainter pathIndeterminatePainterSelected = IndeterminatePainter.MATERIAL;
  private PathPainterConfiguration pathPainterConfiguration;
  private boolean animateOnStart = true;

  public IndeterminateRoadRunner(Context context) {
    super(context);
    throw new UnsupportedOperationException("The view can not be created programmatically yet");
  }

  public IndeterminateRoadRunner(Context context, AttributeSet attrs) {
    super(context, attrs);
    initPath(attrs);
    initConfiguration(attrs);
  }

  public IndeterminateRoadRunner(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPath(attrs);
    initConfiguration(attrs);
  }

  @Override
  public void setColor(int color) {
    pathPainter.setColor(color);
  }

  private void initPath(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.RoadRunner);
    int animationValue = attributes.getInt(R.styleable.RoadRunner_path_animation_type, 0);
    pathIndeterminatePainterSelected = IndeterminatePainter.fromId(animationValue);
    pathData = attributes.getString(R.styleable.RoadRunner_path_data);
    originalWidth = attributes.getInteger(R.styleable.RoadRunner_path_original_width, 0);
    originalHeight = attributes.getInteger(R.styleable.RoadRunner_path_original_height, 0);
    animateOnStart = attributes.getBoolean(R.styleable.RoadRunner_animate_on_start, true);

    AssertUtils.assertThis(pathData != null, "Path data must be defined", this.getClass());
    AssertUtils.assertThis(!pathData.isEmpty(), "Path data must be defined", this.getClass());
    AssertUtils.assertThis(!pathData.equals(""), "Path data must be defined", this.getClass());
    AssertUtils.assertThis(originalWidth > 0, "Original with of the path must be defined",
        this.getClass());
    AssertUtils.assertThis(originalHeight > 0, "Original height of the path must be defined",
        this.getClass());
  }

  private void initConfiguration(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.RoadRunner);
    pathPainterConfiguration =
        PathPainterConfigurationFactory.makeConfiguration(attributes,
            pathIndeterminatePainterSelected);
    attributes.recycle();
  }

  private void initPathPainter() {
    pathPainter =
        IndeterminatePainterFactory.makeIndeterminatePathPainter(
            pathIndeterminatePainterSelected, pathContainer,
            this, pathPainterConfiguration);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    try {
      pathContainer = buildPathData(w, h, pathData, originalWidth, originalHeight);
      initPathPainter();
    } catch (ParseException e) {
      Log.e(TAG, "Path parse exception:", e);
    }
    if (animateOnStart) {
      pathPainter.start();
    }
  }

  /**
   * Tell the pathPainter to draw the path in the onDraw
   */
  @Override
  protected void onDraw(Canvas canvas) {
    pathPainter.paintPath(canvas);
  }

  public void start() {
    pathPainter.start();
  }

  public void stop() {
    pathPainter.stop();
  }

  public void restart() {
    pathPainter.restart();
  }
}
