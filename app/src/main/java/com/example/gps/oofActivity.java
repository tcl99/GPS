package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class oofActivity extends AppCompatActivity {

    private static int contador;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oof);
        TextView textView = findViewById(R.id.contador);
        textView.setText(""+(contador));
    }
    public void oof (View view) {
        mPlayer = MediaPlayer.create(this, R.raw.oofv3);
        mPlayer.start();
        TextView textView = findViewById(R.id.contador);
        textView.setText(""+(contador++));
    }
}