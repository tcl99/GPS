package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.Properties;

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
                //leer numero de telefono declarado en local.properties como TLF
                try {
                    // Cargar el archivo local.properties
                    Properties properties = new Properties();
                    FileInputStream inputStream = new FileInputStream("local.properties");
                    properties.load(inputStream);

                    // Leer el valor de la variable
                    String telefono = properties.getProperty("TLF");
                    inputStream.close();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telefono, null, "El remitente de este teléfono se ha caido y no se puede levantar" , null, null);
                    Toast.makeText(CaidaActivity.this, "¡SMS Enviado!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CaidaActivity.this, "Si no se encuentra número de teléfono, se enviaría un SMS al 112", Toast.LENGTH_SHORT).show();
                }
                countdown.setTextSize(48);
                countdown.setText("¡Ayuda en camino!");
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