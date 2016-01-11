package com.bsty.apidemos.apis.animation;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by bsty on 1/11/16.
 */
public class AnimationSeeking extends Activity {
    private static final int DURATION = 1500;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {
        public MyAnimationView(Context context) {
            super(context);
        }

        private void createAnimation() {

        }

        public void startAnimation() {

        }

        public void seek(long seekTime) {

        }

        private ShapeHolder addBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(50f, 50f);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            int red = (int) (Math.random() * 155 + 100);
            int green = (int) (Math.random() * 155 + 100);
            int blue = (int) (Math.random() * 155 + 100);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = drawable.getPaint();
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            shapeHolder.setPaint(paint);
            return shapeHolder;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
        }
    }
}
