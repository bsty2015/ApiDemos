package com.bsty.apidemos.apis.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.bsty.apidemos.R;

/**
 * Created by bsty on 1/7/16.
 */
public class LayoutAnimationByDefault extends Activity {
    private int numButtons = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animations_by_default);
        final GridLayout gridContainer = (GridLayout) findViewById(R.id.gridContainer);
        Button addButton = (Button) findViewById(R.id.addNewButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button newButton = new Button(LayoutAnimationByDefault.this);
                newButton.setText(String.valueOf(numButtons++));
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridContainer.removeView(v);
                    }
                });
                gridContainer.addView(newButton, Math.min(1, gridContainer.getChildCount()));
            }
        });
    }
}
