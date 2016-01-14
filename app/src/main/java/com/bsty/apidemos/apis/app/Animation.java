package com.bsty.apidemos.apis.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/12/16.
 */
public class Animation extends Activity {
    private static final String TAG = "Animation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onEnterAnimationComplete() {
        Log.i(TAG, "onEnterAnimationComplete");
    }

    private View.OnClickListener mFadeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "Starting fade-in animation...");

            startActivity(new Intent(Animation.this, AlertDialogSamples.class));
            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        }
    };

    private View.OnClickListener mZoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener mModernFadeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "Starting modern-zoom-in animation ...");
//            ActivityOptions opts = ActivityOptions.makeBasic()
        }
    };

    private View.OnClickListener mModernZoomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mScaleUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mZoomThumbnailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mNoAnimationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
