package com.example.gps;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

public abstract interface SensorEventListener {

    public void onAccuracyChanged(Sensor sensor, int precision);
    public void onSensorChanged(SensorEvent evento);
}
