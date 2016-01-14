package com.bsty.apidemos.apis.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bsty.apidemos.R;
import com.bsty.apidemos.apis.ApiDemos;

/**
 * Created by bsty on 1/12/16.
 */
public class IntentActivityFlags extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_activity_flags);
        Button button = (Button) findViewById(R.id.flag_activity_clear_task);
        button.setOnClickListener(mFlagActivityClearTaskListener);
        button = (Button) findViewById(R.id.flag_activity_clear_task_pi);
        button.setOnClickListener(mFlagActivityClearTaskPIListener);
    }

    private Intent[] buildIntentsToViewsLists() {
        Intent[] intents = new Intent[3];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(this, com.bsty.apidemos.apis.ApiDemos.class));
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(IntentActivityFlags.this, ApiDemos.class);
        intent.putExtra("com.bsty.apidemos.apis.Path", "Views");
        intents[1] = intent;
        intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(IntentActivityFlags.this, ApiDemos.class);
        intent.putExtra("com.bsty.apidemos.apis.Path", "Views/Lists");

        intents[2] = intent;
        return intents;
    }

    private View.OnClickListener mFlagActivityClearTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivities(buildIntentsToViewsLists());
        }
    };

    private View.OnClickListener mFlagActivityClearTaskPIListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context= IntentActivityFlags.this;
            PendingIntent pi = PendingIntent.getActivities(context,0,buildIntentsToViewsLists(),PendingIntent.FLAG_UPDATE_CURRENT);

            try {
                pi.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    };
}
