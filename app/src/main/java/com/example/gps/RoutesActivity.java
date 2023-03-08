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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RoutesActivity extends AppCompatActivity implements LocationListener {

    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";
    private ListView routesView;
    private ArrayList<String> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(MainActivity.EXTRA_MSG);

        TextView textView = findViewById(R.id.welcomeMsg);
        ImageView imageView = findViewById(R.id.imgPerfil);

        if(msg.isEmpty()) {
            textView.setText("BUENAS, PERSONA MISTERIOSA ");
            imageView.setImageDrawable(getResources().getDrawable(R.drawable._55_5553299_incognito_logo_hd_png_download, null));
        }
        else {
            textView.setText("BUENAS, " + msg);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.prismo, null));
        }

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
                String msg = routes.get(i).toString();
                intent.putExtra(EXTRA_MSG, msg);
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