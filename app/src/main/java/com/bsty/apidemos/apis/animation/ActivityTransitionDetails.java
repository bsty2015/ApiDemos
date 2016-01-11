package com.bsty.apidemos.apis.animation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 12/31/15.
 */
public class ActivityTransitionDetails extends Activity {
    private static final String KEY_ID = "ViewTransitionValues:id";
    private int mImageResourceId = R.drawable.ducky;
    String mName = "ducky";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(randomColor()));
        setContentView(R.layout.image_details);
        ImageView titleImage = (ImageView) findViewById(R.id.titleImage);
        titleImage.setImageDrawable(getHeroDrawable());
    }

    private Drawable getHeroDrawable() {
        String name = getIntent().getStringExtra(KEY_ID);
        if (name != null) {
            mName = name;
            mImageResourceId = ActivityTransition.getDrawableIdForKey(name);
        }
        return getResources().getDrawable(mImageResourceId);
    }

    private static int randomColor() {
        int red = (int) (Math.random() * 128);
        int green = (int) (Math.random() * 128);
        int blue = (int) (Math.random() * 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void clicked(View v) {
        Intent intent = new Intent(this, ActivityTransition.class);
        intent.putExtra(KEY_ID, mName);
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, v, "hero");
        startActivity(intent, activityOptions.toBundle());
    }
}
