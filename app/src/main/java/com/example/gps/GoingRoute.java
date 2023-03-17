package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Observable;
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
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GoingRoute  extends AppCompatActivity implements SensorEventListener, LocationListener, OnMapReadyCallback {

    private final float THRESHOLD = 4f; // Umbral de detección de caídas
    private boolean fallen = false; // Indica si se ha detectado una caída previamente

    private Chronometer simpleChronometer;
    private LocationManager locationManager;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Runtime runtime = Runtime.getRuntime();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_route);

        //LOCALIZACION
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.goingMap);
        mapFragment.getMapAsync(this);

        //SENSORES
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : listaSensores) {
            System.out.println(sensor.getName());
        }

        Sensor acelerometter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometter != null) {
            sensorManager.registerListener((SensorEventListener) this, acelerometter, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            sensorManager.registerListener((SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        }

        //TIEMPO DE LA RUTA CON CRONOMETRO
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.start();
        simpleChronometer.setFormat("Tiempo de ruta\n%s");

        TextView mem = findViewById(R.id.memory);

        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                mem.setText(""+runtime.totalMemory()+" "+runtime.freeMemory());
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el listener de sensores
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Detener la lectura de sensores
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la aceleración total
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            if (acceleration < THRESHOLD && !fallen) {
                // Si se ha superado el umbral y no se ha detectado una caída previamente
                fallen = true;
                Toast.makeText(this, "Se ha detectado una posible caída", Toast.LENGTH_SHORT).show();
            }
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la velocidad angular total
            float angularSpeed = (float) Math.sqrt(x * x + y * y + z * z);

            if (angularSpeed > THRESHOLD && fallen) {
                // Si se ha superado el umbral y se había detectado una caída previamente
                fallen = false;
                Toast.makeText(this, "La caída ha sido detenida", Toast.LENGTH_SHORT).show();
            }
        }    }

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
        long s, m, h;
        simpleChronometer.stop();
        long tiempo = SystemClock.elapsedRealtime() - simpleChronometer.getBase();
        s = tiempo / 1000;
        //Formateo a H M S
        m = s / 60;
        s = s % 60;
        h = m / 60;
        m = m % 60;
        Toast.makeText(this, "Tiempo de Ruta: " + h + " horas, " + m + " minutos y " + s + " segundos", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void pararRutaButton(View view) {
        this.onBackPressed();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng ini = new LatLng(location.getLatitude(),  location.getLongitude());

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ini));

        googleMap.setMyLocationEnabled(true);
    }
}