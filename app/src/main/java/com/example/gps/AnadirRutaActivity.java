package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnadirRutaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String origen, destino;
    private Marker markerInicio, markerFinal;
    private GoogleMap googleMap;
    private Polyline ruta;
    private RequestQueue colaPeticiones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ruta);
        getSupportActionBar().hide();

        colaPeticiones= Volley.newRequestQueue(this);

        //Autocompletar localizaciones
        AutocompleteSupportFragment autoOrigen = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoOrigen);
        AutocompleteSupportFragment autoDestino = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoDestino);

        autoOrigen.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autoDestino.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        //Cuando se selecciona un lugar, se añade un marcador, y cuando se tiene los 2, se dibuja la ruta entre ellos
        autoOrigen.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                origen = place.getName();

                if(markerInicio != null) markerInicio.remove();

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLatLng())
                        .title("Origen");
                markerInicio = googleMap.addMarker(markerOptions);
                //Dibuja la ruta
                if(markerFinal != null) {
                    if(ruta != null) ruta.remove();
                    PolylineOptions options = new PolylineOptions();
                    options.color(Color.GREEN);
                    options.width(5);
                    options.add(markerInicio.getPosition());
                    options.add(markerFinal.getPosition());
                    ruta = googleMap.addPolyline(options);
                }
            }
        });

        autoDestino.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {

            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                destino = place.getName();

                if(markerFinal != null) markerFinal.remove();

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(place.getLatLng())
                        .title("Destino");
                markerFinal = googleMap.addMarker(markerOptions);
                //Dibuja la ruta
                if(markerInicio != null) {
                    if(ruta != null) ruta.remove();
                    PolylineOptions options = new PolylineOptions();
                    options.color(Color.GREEN);
                    options.width(5);
                    options.add(markerInicio.getPosition());
                    options.add(markerFinal.getPosition());
                    ruta = googleMap.addPolyline(options);
                }
            }
        });

        //MAPA
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.crearRutaMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    public void anadirRutaButton(View view ) {
        if(markerInicio != null && markerFinal != null) {
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://electric-unity-359112-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference myRef = mDatabase.getReference("basegpst/rutas_propuestas");

            Map<String, Ruta> users = new HashMap<>();

            users.put(origen+"-"+destino, new Ruta(markerInicio.getPosition(), markerFinal.getPosition(), null));

            myRef.setValue(users);

        }
    }
}