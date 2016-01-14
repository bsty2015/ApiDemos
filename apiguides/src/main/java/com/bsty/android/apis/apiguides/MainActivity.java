package com.bsty.android.apis.apiguides;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity {
    private float mDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDensity = getResources().getDisplayMetrics().density;
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
        BstyAnimation viewAnim = new BstyAnimation(this);
        container.addView(viewAnim);
    }

    public class BstyTypeEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            return null;
        }
    }

    public class BstyAnimation extends View {

        ShapeDrawable ball = null;

        public BstyAnimation(Context context) {
            super(context);
            ball = addBall();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.save();
            canvas.translate(25f, 25f);
            ball.draw(canvas);
            canvas.restore();
        }

        private ShapeDrawable addBall() {
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            int color = 0xff000000 | red << 16 | green << 8 | blue;
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;

            OvalShape circle = new OvalShape();
            circle.resize(50f * mDensity, 50f * mDensity);
            ShapeDrawable drawable = new ShapeDrawable(circle);
            Paint paint = drawable.getPaint();
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP);
            paint.setShader(gradient);
            return drawable;
        }
    }

}
