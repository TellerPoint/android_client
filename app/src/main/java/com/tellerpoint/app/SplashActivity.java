package com.tellerpoint.app;

/**
 * Created by eit on 3/17/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 10000;

    private static final String TAG = "SplashActivity";

    @InjectView(R.id.btn_start) Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
            }

        });
//        new Handler().postDelayed(new Runnable() {
//
//            /*
//             * Showing splash screen with a timer. This will be useful when you
//             * want to show case your app logo / company
//             */
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                start();
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);


    }

    public void start(){
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
    }
}