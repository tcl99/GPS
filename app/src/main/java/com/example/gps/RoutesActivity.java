package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RoutesActivity extends AppCompatActivity {

    private ListView routesView;
    private ArrayList<String> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

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
    }
}