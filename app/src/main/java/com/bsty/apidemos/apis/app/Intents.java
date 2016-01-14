package com.bsty.apidemos.apis.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/12/16.
 */
public class Intents extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intents);
    }

    public void onGetMusic(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivity(Intent.createChooser(intent, "Select music"));
    }

    public void onGetImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Select Image"));
    }

    public void onGetStream(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivity(Intent.createChooser(intent, "Select Stream"));
    }
}
