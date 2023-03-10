package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class GoingRoute extends AppCompatActivity implements SensorEventListener, LocationListener {

    private long start_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_route);

        start_t = System.nanoTime();

        TextView tiempo = findViewById(R.id.tiempoTranscurrido);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : listaSensores) {
            System.out.println(sensor.getName());
        }

        Sensor acelerometter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometter!=null) {
            sensorManager.registerListener((SensorEventListener) this, acelerometter, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyro!=null) {
            sensorManager.registerListener((SensorEventListener) this, gyro, SensorManager.SENSOR_DELAY_UI);
        }

        //locatiion talll

        //Periodico actualiza tiempo


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Toast.makeText(this, "Heyhey", Toast.LENGTH_SHORT).show();
    }
    public void onLocationChanged(Location location) {
        if (location != null) {
            //tvLongitud.setText(location.getLongitude() + "");
            //vLatitud.setText(location.getLatitude() + "");
        }
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }

    @Override
    public void onBackPressed() {


        Toast.makeText(this, "Tiempo Ruta: "+
                TimeUnit.SECONDS.convert(System.nanoTime()-start_t,TimeUnit.NANOSECONDS),
                Toast.LENGTH_SHORT).show();
        this.finish();
    }
}