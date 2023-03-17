package com.example.gps;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";
    private TextView user;
    private TextView pass;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = findViewById(R.id.editUsername);
        pass = findViewById(R.id.editPassword);

        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                user.setText("");
                user.setTextColor(Color.parseColor("#FF000000"));
                user.setOnTouchListener(null);
                return false;
            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pass.setText("");
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                pass.setTextColor(Color.parseColor("#FF000000"));
                pass.setOnTouchListener(null);
                return false;
            }
        });

    }

    public void sendMessage (View view) {
        if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, RoutesActivity.class);
            EditText editText = (EditText) findViewById(R.id.editUsername);
            String msg = editText.getText().toString();
            intent.putExtra(EXTRA_MSG, msg);
            startActivity(intent);
        }
    }
}