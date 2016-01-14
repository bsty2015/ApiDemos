package com.bsty.apidemos.apis.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/12/16.
 */
public class ListFlipper extends Activity {
    private static final int DURATION = 1500;
    private SeekBar mSeekBar;
    private static final String[] LIST_STRING_EN = new String[]{
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six"
    };
    private static final String[] LIST_STRING_FR = new String[]{
            "Un",
            "Deux",
            "Trois",
            "Quatre",
            "Le Five",
            "Six"
    };

    ListView mEnglishList;
    ListView mFrenchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rotating_list);
        mEnglishList = (ListView) findViewById(R.id.list_en);
        mFrenchList = (ListView) findViewById(R.id.list_fr);

        final ArrayAdapter<String> adapterEn = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LIST_STRING_EN);
        final ArrayAdapter<String> adapterFr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LIST_STRING_FR);

        mEnglishList.setAdapter(adapterEn);
        mFrenchList.setAdapter(adapterFr);

        mFrenchList.setRotationY(-90f);
        Button starter = (Button) findViewById(R.id.button);
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipit();
            }
        });

    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    private void flipit() {
        final ListView visibleList;
        final ListView invisibleList;
        if (mEnglishList.getVisibility() == View.GONE) {
            visibleList = mFrenchList;
            invisibleList = mEnglishList;
        } else {
            invisibleList = mFrenchList;
            visibleList = mEnglishList;
        }

        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY", -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }

}
