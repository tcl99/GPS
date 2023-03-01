package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.List;

public class sensoresActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores_acitivity);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : listaSensores) {
            System.out.println(sensor.getName());
        }

        Sensor sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor!=null) {
            sensorManager.registerListener((SensorEventListener) this, sensor, SensorManager.SENSOR_DELAY_UI);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.println("hola");
       // System.out.println(event.values[SensorManager.DATA_Y]);
        //System.out.println(event.values[SensorManager.DATA_Z]);
    }

}