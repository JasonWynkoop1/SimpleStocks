package com.cs360.simplestocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.simplestocks.loginregister.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Timer timer;
    private ProgressBar progressBar;
    private int i = 0;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        textView = findViewById(R.id.splash_screen_title);
        textView.setText("Simple Stocks");

        final long period = 20;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    progressBar.setProgress(i);
                    i++;
                } else {
                    //closing the timer
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // close this activity
                    finish();
                }
            }
        }, 0, period);
    }
}

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash);

        int SPLASH_SCREEN_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            Intent i=new Intent(SplashActivity.this,
                   LoginActivity.class);

            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }
}*/


