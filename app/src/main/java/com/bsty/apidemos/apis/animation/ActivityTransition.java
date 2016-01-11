package com.bsty.apidemos.apis.animation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bsty.apidemos.R;

import java.util.List;
import java.util.Map;

/**
 * Created by bsty on 12/31/15.
 */
public class ActivityTransition extends Activity {
    public static final String TAG = "ActivityTransition";

    public static final String KEY_ID = "ViewTransitionValues:id";

    private ImageView mHero;

    public static final int[] DRAWABLES = {
            R.drawable.ball,
            R.drawable.block,
            R.drawable.ducky,
            R.drawable.jellies,
            R.drawable.mug,
            R.drawable.pencil,
            R.drawable.scissors,
            R.drawable.woot,
    };

    public static final int[] IDS = {
            R.id.ball,
            R.id.block,
            R.id.ducky,
            R.id.jellies,
            R.id.mug,
            R.id.pencil,
            R.id.scissors,
            R.id.woot,
    };

    public static final String[] NAMES = {
            "ball",
            "block",
            "ducky",
            "jellies",
            "mug",
            "pencil",
            "scissors",
            "woot",
    };

    public static int getIdForKey(String id) {
        return IDS[getIndexForkey(id)];
    }

    public static int getDrawableIdForKey(String id) {
        return DRAWABLES[getIndexForkey(id)];
    }

    public static int getIndexForkey(String id) {
        for (int i = 0; i < NAMES.length; i++) {
            String name = NAMES[i];
            if (name.equals(id)) {
                return i;
            }
        }
        return 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(randomColor()));
        setContentView(R.layout.image_block);
        setupHero();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupHero() {
        String name = getIntent().getStringExtra(KEY_ID);
        mHero = null;
        if (name != null) {
            mHero = (ImageView) findViewById(getIdForKey(name));
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.put("hero", mHero);
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void clicked(View v) {
        mHero = (ImageView) v;
        Intent intent = new Intent(this, ActivityTransitionDetails.class);
        intent.putExtra(KEY_ID, v.getTransitionName());
        ActivityOptions activityOptions
                = ActivityOptions.makeSceneTransitionAnimation(this, mHero, "hero");
        startActivity(intent, activityOptions.toBundle());
    }


    private static int randomColor() {
        int red = (int) (Math.random() * 128);
        int green = (int) (Math.random() * 128);
        int blue = (int) (Math.random() * 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
}
