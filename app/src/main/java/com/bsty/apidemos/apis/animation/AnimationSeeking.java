package com.bsty.apidemos.apis.animation;

import android.animation.ObjectAnimator;
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
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/11/16.
 */
public class AnimationSeeking extends Activity {
    private static final int DURATION = 1500;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_seeking);
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

        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setMax(DURATION);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (animView.getHeight() != 0) {
                    animView.seek(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {
        private static final int RED = 0xffFF8080;
        private static final int BLUE = 0xff8080FF;
        private static final int CYAN = 0xff80ffff;
        private static final int GREEN = 0xff80ff80;
        private static final float BALL_SIZE = 100f;
        AnimationSet animation = null;
        ValueAnimator bounceAnim = null;
        ShapeHolder ball = null;

        public MyAnimationView(Context context) {
            super(context);
            ball = addBall(200, 0);
        }

        private void createAnimation() {
            if (bounceAnim == null) {
                bounceAnim = ObjectAnimator.ofFloat(ball, "y", ball.getY(), getHeight() - BALL_SIZE).setDuration(1500);
                bounceAnim.setInterpolator(new BounceInterpolator());
                bounceAnim.addUpdateListener(this);
            }

        }

        public void startAnimation() {
            createAnimation();
            bounceAnim.start();
        }

        public void seek(long seekTime) {
            createAnimation();
            bounceAnim.setCurrentPlayTime(seekTime);
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
        protected void onDraw(Canvas canvas) {
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
            long playtime = bounceAnim.getCurrentPlayTime();
        }
    }
}
