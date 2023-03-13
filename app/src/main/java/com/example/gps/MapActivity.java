package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView3);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.EXTRA_MSG);

        TextView nombreRuta = findViewById(R.id.nombreRuta);
        nombreRuta.setText(msg);

        mapView = findViewById(R.id.mapView3);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        }

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //AIzaSyDIbjDBQaq3TlkiszrQ6sbSibz7MUy815I
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(43.127456,-4.795184)).title("Éxito"));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void atrasButton(View view) {
        this.finish();
    }


    public void empezarRutaButton(View view) {
        Intent intent = new Intent(this, GoingRoute.class);
        startActivity(intent);
    }
}