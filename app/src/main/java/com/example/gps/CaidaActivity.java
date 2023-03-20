package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextClock;
import android.widget.TextView;

public class CaidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caida);

        getSupportActionBar().hide();

        TextView countdown = findViewById(R.id.countdownView);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdown.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdown.setText("¡Enviando SMS!");
            }
        }.start();

    }

    public void iAmOkButton(View view) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}