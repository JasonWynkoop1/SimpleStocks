package com.cs360.simplestocks.activities;

import android.annotation.SuppressLint;
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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        TextView textView = findViewById(R.id.splash_screen_title);
        textView.setText("Simple Stocks");

        final long period = 20;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (i < 100) {
                    runOnUiThread(() -> {
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


