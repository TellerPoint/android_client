package com.tellerpoint.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tellerpoint.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by eit on 3/18/16.
 */
public class PaymentSuccessfulActivity extends Activity {
    @InjectView(R.id.btn_start)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_successful);

        ButterKnife.inject(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
            }

        });


    }

    public void start(){
        Intent i = new Intent(PaymentSuccessfulActivity.this, MainActivity.class);
        startActivity(i);
    }
}
