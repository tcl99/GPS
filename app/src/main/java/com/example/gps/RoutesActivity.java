package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoutesActivity extends AppCompatActivity {

    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";
    private ListView routesView;
    private ArrayList<String> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        getSupportActionBar().hide();

        Intent intent = getIntent();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        TextView textView = findViewById(R.id.welcomeMsg);
        ImageView imageView = findViewById(R.id.imgPerfil);

        String msg = intent.getStringExtra(LoginActivity.EXTRA_MSG);

        textView.setText("BUENAS, " + user.getEmail().substring(0, FirebaseAuth.getInstance().getCurrentUser().getEmail().indexOf("@")));

        routesView = (ListView) findViewById(R.id.routes);
        routes = new ArrayList<String>();
        routes.add("Camino Lebaniego");

        routes.sort(String::compareToIgnoreCase);

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

    @Override
    public void onBackPressed() {
        FirebaseAuth.getInstance().signOut();
        super.onBackPressed();
    }
}