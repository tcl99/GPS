package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);
        getSupportActionBar().hide();


    }

    public void anadirRutaActivityButton (View view) {
        Intent intent = new Intent(GuiaActivity.this, AnadirRutaActivity.class);
        startActivity(intent);
    }
}