package com.bsty.apidemos.apis.animation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/12/16.
 */
public class Transitions extends Activity {
    Scene mScene1, mScene2, mScene3;
    ViewGroup mSceneRoot;
    TransitionManager mTransitionManager;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition);

        mSceneRoot = (ViewGroup) findViewById(R.id.sceneRoot);

        TransitionInflater inflater = TransitionInflater.from(this);

        mScene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_scene1, this);
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_scene2, this);
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_scene3, this);
        mTransitionManager = inflater.inflateTransitionManager(R.transition.transition_mgr, mSceneRoot);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void selectScene(View view) {
        switch (view.getId()) {
            case R.id.scene1:
                mTransitionManager.transitionTo(mScene1);
                break;
            case R.id.scene2:
                mTransitionManager.transitionTo(mScene2);
                break;
            case R.id.scene3:
                mTransitionManager.transitionTo(mScene3);
                break;
            case R.id.scene4:
                TransitionManager.beginDelayedTransition(mSceneRoot);
                setNewSize(R.id.view1, 150, 25);
                setNewSize(R.id.view2, 150, 25);
                setNewSize(R.id.view3, 150, 25);
                setNewSize(R.id.view4, 150, 25);
                break;
        }
    }

    private void setNewSize(int id, int width, int height) {
        View view = findViewById(id);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
