package com.bsty.apidemos.apis.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/12/16.
 */
public class Transition3d extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;

    private static final String[] PHOTOS_NAMES = new String[]{
            "Lyon",
            "Livermore",
            "Tahoe Pier",
            "Lake Tahoe",
            "Grand Canyon",
            "Bodie"
    };

    private static final int[] PHOTOS_RESOURCES = new int[]{
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
            R.drawable.photo6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animations_main_screen);
        mPhotosList = (ListView) findViewById(android.R.id.list);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PHOTOS_NAMES);
        mPhotosList.setAdapter(adapter);
        mPhotosList.setOnItemClickListener(this);

        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setOnClickListener(this);

        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    private void applyRotation(int position, float start, float end) {
        final float centerX = mContainer.getWidth() / 2.0f;
        final float centerY = mContainer.getHeight() / 2.0f;

        final Rotate3dAnimation rotation =
                new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);

        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(position));

        mContainer.startAnimation(rotation);
    }

    @Override
    public void onClick(View v) {
        applyRotation(-1, 180, 90);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mImageView.setImageResource(PHOTOS_RESOURCES[position]);
        applyRotation(position, 0, 90);
    }

    private final class DisplayNextView implements Animation.AnimationListener {
        private int mPosition;

        private DisplayNextView(int position) {
            mPosition = position;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private final class SwapViews implements Runnable {
        private final int mPosition;

        public SwapViews(int position) {
            mPosition = position;
        }

        @Override
        public void run() {
            final float centerX = mContainer.getWidth() / 2.0f;
            final float centerY = mContainer.getHeight() / 2.0f;
            Rotate3dAnimation rotation;

            if (mPosition > -1) {
                mPhotosList.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();

                rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f, false);
            } else {
                mImageView.setVisibility(View.GONE);
                mPhotosList.setVisibility(View.VISIBLE);
                mPhotosList.requestFocus();

                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            mContainer.startAnimation(rotation);
        }
    }
}
