package com.example.gps;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage (View view) {
        Intent intent = new Intent(this, RoutesActivity.class);
        EditText editText = (EditText) findViewById(R.id.editUsername);
        String msg = editText.getText().toString();
        intent.putExtra(EXTRA_MSG, msg);
        startActivity(intent);
    }

    protected void onClickUsuario(View view) {
        //Para que aparezca Usuario y Contraseña pero al clicar se borre
        TextView textView = findViewById(R.id.editUsername);
        textView.setText("");
        textView.setTextColor(Color.parseColor("#FF000000"));

        textView = findViewById(R.id.editPassword);
        textView.setText("");
        textView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        textView.setTextColor(Color.parseColor("#FF000000"));
    }
}