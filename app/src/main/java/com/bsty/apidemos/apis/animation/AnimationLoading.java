package com.bsty.apidemos.apis.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
 * Created by bsty on 1/8/16.
 */
public class AnimationLoading extends Activity {
    private static final int DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_loading);
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

    public class MyAnimationView extends View implements ValueAnimator.AnimatorUpdateListener {
        private static final float BALL_SIZE = 100f;

        public final ArrayList<ShapeHolder> balls = new ArrayList<>();
        Animator animation = null;

        public MyAnimationView(Context context) {
            super(context);
            addBall(50, 50);
            addBall(200, 50);
            addBall(350, 50);
            addBall(500, 50, Color.GREEN);
            addBall(650, 50);
            addBall(800, 50);
            addBall(950, 50);
            addBall(800, 50, Color.YELLOW);
        }

        private void createAnimation() {
            Context appContext = AnimationLoading.this;

            if (animation == null) {
                final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.object_animator);
                anim.addUpdateListener(this);
                anim.setTarget(balls.get(0));

                ValueAnimator fader = (ValueAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.animator);

                fader.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        balls.get(1).setAlpha((Float) animation.getAnimatedValue());
                    }
                });

                AnimatorSet seq =
                        (AnimatorSet) AnimatorInflater.loadAnimator(appContext, R.anim.animator_set);
                seq.setTarget(balls.get(2));

                ObjectAnimator colorizer = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.color_animator);
                colorizer.setTarget(balls.get(3));

                ObjectAnimator animPvh = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.object_animator_pvh);
                animPvh.setTarget(balls.get(4));

                ObjectAnimator animPvhKf = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.object_animator_pvh_kf);
                animPvhKf.setTarget(balls.get(5));

                ValueAnimator faderKf = (ValueAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.object_animator_pvh_kf);
                faderKf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        balls.get(6).setAlpha((Float) animation.getAnimatedValue());
                    }
                });

                ObjectAnimator animPvhKfInterpolated = (ObjectAnimator) AnimatorInflater.loadAnimator(appContext, R.anim.object_animator_pvh_kf_interpolated);
                animPvhKfInterpolated.setTarget(balls.get(7));

                animation = new AnimatorSet();
                ((AnimatorSet) animation).playTogether(anim, fader, seq, colorizer, animPvh, animPvhKf, faderKf, animPvhKfInterpolated);
            }
        }

        public void startAnimation() {
            createAnimation();
            animation.start();
        }

        private ShapeHolder createBall(float x, float y) {
            OvalShape circle = new OvalShape();
            circle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            ShapeHolder shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            return shapeHolder;
        }

        private void addBall(float x, float y, int color) {
            ShapeHolder shapeHolder = createBall(x, y);
            shapeHolder.setColor(color);
            balls.add(shapeHolder);
        }

        private void addBall(float x, float y) {
            ShapeHolder shapeHolder = createBall(x, y);
            int red = (int) (Math.random() * 155 + 100);
            int green = (int) (Math.random() * 155 + 100);
            int blue = (int) (Math.random() * 155 + 100);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            Paint paint = shapeHolder.getShape().getPaint();
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(
                    37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP
            );
            paint.setShader(gradient);
            balls.add(shapeHolder);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball : balls) {
                canvas.translate(ball.getX(), ball.getY());
                ball.getShape().draw(canvas);
                canvas.translate(-ball.getX(), -ball.getY());
            }
        }


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            invalidate();
            ShapeHolder ball = balls.get(0);
            ball.setY((Float) animation.getAnimatedValue());
        }
    }
}
