package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.location.LocationListenerCompat;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RoutesActivity extends AppCompatActivity implements LocationListener {

    private ListView routesView;
    private ArrayList<String> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        routesView = (ListView) findViewById(R.id.routes);
        routes = new ArrayList<String>();
        routes.add("Reinosa-Potes");
        routes.add("Reinosa-Vega de Pas");
        routes.add("Soto-Cabezón de la sal");
        routes.add("Costa");
        routes.add("Picos de Europa");
        routes.add("Vuelta al pantano");
        routes.add("Reinosa-Orbaneja del castillo(Burgos)");
        routes.add("Fuente Dé-Riaño(León)");
        routes.add("Camino Lebaniego");
        routes.add("oof1");
        routes.add("oof2");
        routes.add("oof3");
        routes.add("oof4");
        routes.add("oof5");
        routes.add("oof6");
        routes.add("oof7");
        routes.add("oof8");
        routes.add("oof9");
        routes.add("oof");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);
        routesView.setAdapter(adapter);

        routesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                String opcion = routes.get(i);

                intent = new Intent(RoutesActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
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
}