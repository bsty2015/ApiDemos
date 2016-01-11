package com.bsty.apidemos.apis.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/11/16.
 */
public class PathAnimations extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnLayoutChangeListener {
    private CanvasView mCanvasView;
    private static float TRAVERSE_PATH_SIZE = 7.0f;
    private static Path sTraversalPath = new Path();
    private ObjectAnimator mAnimator;

    final static Property<PathAnimations, Point> POINT_PROPERTY
            = new Property<PathAnimations, Point>(Point.class, "point") {
        @Override
        public Point get(PathAnimations object) {
            View v = object.findViewById(R.id.moved_item);
            return new Point(Math.round(v.getX()), Math.round(v.getY()));
        }

        @Override
        public void set(PathAnimations object, Point value) {
            object.setCoordinates(value.x, value.y);
        }
    };

    static {
        float inverse_sqrt8 = (float) Math.sqrt(0.125);
        RectF bounds = new RectF(1, 1, 3, 3);
        sTraversalPath.addArc(bounds, 45, 180);

        bounds.set(1.5f + inverse_sqrt8, 1.5f + inverse_sqrt8, 2.5f + inverse_sqrt8, 2.5f + inverse_sqrt8);
        sTraversalPath.addArc(bounds, 45, 180);
        sTraversalPath.addArc(bounds, 225, 180);

        bounds.set(4, 1, 6, 3);
        sTraversalPath.addArc(bounds, 135, -180);
        sTraversalPath.addArc(bounds, -45, -180);

        bounds.set(4.5f - inverse_sqrt8, 1.5f + inverse_sqrt8, 5.5f - inverse_sqrt8, 2.5f + inverse_sqrt8);
        sTraversalPath.addArc(bounds, 135, -180);
        sTraversalPath.addArc(bounds, -45, -180);

        sTraversalPath.addCircle(3.5f, 3.5f, 0.5f, Path.Direction.CCW);
        sTraversalPath.addArc(new RectF(1, 2, 6, 6), 0, 180);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        startAnimator(checkedId);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int checkedId = ((RadioGroup) findViewById(R.id.path_animation_type)).getCheckedRadioButtonId();
        if (checkedId != RadioGroup.NO_ID) {
            startAnimator(checkedId);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimator(int checkedId) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        View view = findViewById(R.id.moved_item);
        Path path = mCanvasView.getPath();
        if (path.isEmpty()) {
            return;
        }

        switch (checkedId) {
            case R.id.named_components:
                mAnimator = ObjectAnimator.ofFloat(view, "x", "y", path);
                break;
            case R.id.property_components:
                mAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
                break;
            case R.id.multi_int:
                mAnimator = ObjectAnimator.ofMultiFloat(this, "changeCoordinates", path);
                break;
            case R.id.named_setter:
                mAnimator = ObjectAnimator.ofObject(this, "point", null, path);
                break;
            case R.id.property_setter:
                mAnimator = ObjectAnimator.ofObject(this, POINT_PROPERTY, new PointFToPointConverter(), path);
                break;
        }

        mAnimator.setDuration(10000);
        mAnimator.setRepeatMode(Animation.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_animations);
        mCanvasView = (CanvasView) findViewById(R.id.canvas);
        mCanvasView.addOnLayoutChangeListener(this);
        ((RadioGroup) findViewById(R.id.path_animation_type)).setOnCheckedChangeListener(this);

    }

    public void setCoordinates(int x, int y) {
        changeCoordinates(x, y);
    }

    public void changeCoordinates(float x, float y) {
        View v = findViewById(R.id.moved_item);
        v.setX(x);
        v.setY(y);
    }

    public void setPoint(PointF point) {
        changeCoordinates(point.x, point.y);
    }


    public static class CanvasView extends FrameLayout {
        Path mPath = new Path();
        Paint mPathPaint = new Paint();

        public CanvasView(Context context) {
            super(context);
            init();
        }

        public CanvasView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            setWillNotDraw(false);
            mPathPaint.setColor(0xFFFF0000);
            mPathPaint.setStrokeWidth(2.0f);
            mPathPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if (changed) {
                Matrix scale = new Matrix();
                float scaleWidth = (right - left) / TRAVERSE_PATH_SIZE;
                float scaleHeight = (bottom - top) / TRAVERSE_PATH_SIZE;
                scale.setScale(scaleWidth, scaleHeight);
                sTraversalPath.transform(scale, mPath);
            }
        }

        public Path getPath() {
            return mPath;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawPath(mPath, mPathPaint);
            super.draw(canvas);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class PointFToPointConverter extends TypeConverter<PointF, Point> {
        Point mPoint = new Point();

        public PointFToPointConverter() {
            super(PointF.class, Point.class);
        }

        @Override
        public Point convert(PointF value) {
            mPoint.set(Math.round(value.x), Math.round(value.y));
            return mPoint;
        }
    }
}
