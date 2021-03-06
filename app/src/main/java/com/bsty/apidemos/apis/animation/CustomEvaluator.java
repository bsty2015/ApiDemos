package com.bsty.apidemos.apis.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bsty.apidemos.R;

import java.util.ArrayList;

/**
 * Created by bsty on 1/6/16.
 */
public class CustomEvaluator extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_custom_evaluator);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        final MyAnimationView animView = new MyAnimationView(this);
        container.addView(animView);

        Button starter = (Button) findViewById(R.id.startButton);
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animView.startAnimation();
            }
        });
    }


    public class XYHolder {
        private float mX;
        private float mY;

        public XYHolder(float mX, float mY) {
            this.mX = mX;
            this.mY = mY;
        }

        public float getX() {
            return mX;
        }

        public void setX(float mX) {
            this.mX = mX;
        }

        public float getY() {
            return mY;
        }

        public void setY(float mY) {
            this.mY = mY;
        }
    }

    public class XYEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            XYHolder startXY = (XYHolder) startValue;
            XYHolder endXY = (XYHolder) endValue;
            return new XYHolder(startXY.getX() + fraction * (endXY.getX() - startXY.getX())
                    , startXY.getY() + fraction * (endXY.getY() - startXY.getY()));
        }
    }

    public class BallXYHolder {
        private ShapeHolder mBall;

        public BallXYHolder(ShapeHolder mBall) {
            this.mBall = mBall;
        }

        public void setXY(XYHolder xyHolder) {
            mBall.setX(xyHolder.getX());
            mBall.setY(xyHolder.getY());
        }

        private XYHolder getXY() {
            return new XYHolder(mBall.getX(), mBall.getY());
        }
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {

        public final ArrayList<ShapeHolder> balls = new ArrayList<>();
        ValueAnimator bounceAnim = null;
        ShapeHolder ball = null;
        BallXYHolder ballHolder = null;

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
            canvas.restore();
        }

        public void startAnimation() {
            creatAnimation();
            bounceAnim.start();
        }

        private void creatAnimation() {
            if (bounceAnim == null) {
                XYHolder startXY = new XYHolder(0f, 0f);
                XYHolder endXY = new XYHolder(300f, 500f);
                bounceAnim = ObjectAnimator.ofObject(ballHolder, "xy",
                        new XYEvaluator(), endXY
                );
                bounceAnim.setDuration(1500);
                bounceAnim.addUpdateListener(this);
            }

        }

        private ShapeHolder createBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(50f, 50f);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x - 25f);
            shapeHolder.setY(y - 25f);
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint();
            paint.setAntiAlias(true);
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                    50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            return shapeHolder;
        }

        public MyAnimationView(Context context) {
            super(context);
            ball = createBall(25, 25);
            ballHolder = new BallXYHolder(ball);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }
}
