package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.lights.Light;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.EXTRA_MSG);

        TextView textView = findViewById(R.id.welcome);
        ImageView imageView = findViewById(R.id.imagePerfil);

        if(msg.isEmpty()) {
            textView.setText("Buenas, persona misteriosa ");
            imageView.setImageDrawable(getResources().getDrawable(R.drawable._55_5553299_incognito_logo_hd_png_download, null));
        }
        else {
            textView.setText("Buenas, " + msg);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.prismo, null));
        }
    }

    public void misRutasButton (View view) {
        Intent intent = new Intent(this, RoutesActivity.class);
        startActivity(intent);
    }
    public void oofButton (View view) {
        Intent intent = new Intent(this, oofActivity.class);
        startActivity(intent);
    }

    public void sensoresButton (View view) {
        Intent intent = new Intent(this, sensoresActivity.class);
        startActivity(intent);
    }
}