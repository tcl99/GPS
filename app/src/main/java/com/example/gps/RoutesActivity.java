package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("usuarios");
        ref.child(user.getUid()).child("guia");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean esGuia = snapshot.getValue(Boolean.class);
                if(esGuia) Toast.makeText(RoutesActivity.this, "SoY GUIA", Toast.LENGTH_SHORT).show();
                else Toast.makeText(RoutesActivity.this, "NOPE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RoutesActivity.this, "lol", Toast.LENGTH_SHORT).show();
            }
        });



        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        TextView textView = findViewById(R.id.welcomeMsg);
        ImageView imageView = findViewById(R.id.imgPerfil);

        String msg = intent.getStringExtra(LoginActivity.EXTRA_MSG);

        textView.setText("BUENAS, " + user.getEmail());
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.prismo, null));


        routesView = (ListView) findViewById(R.id.routes);
        routes = new ArrayList<String>();
        routes.add("Reinosa-Potes");
        routes.add("Reinosa-Vega de Pas");
        routes.add("Vuelta a Campoo de suso");
        routes.add("Vuelta a Campoo de yuso");
        routes.add("Vuelta a Campoo de enmedio");
        routes.add("Puerto del escudo");
        routes.add("Piedrasluengas");
        routes.add("Valderredible");
        routes.add("Soto-Cabezón de la sal");
        routes.add("Costa");
        routes.add("Picos de Europa");
        routes.add("Vuelta al pantano");
        routes.add("Reinosa-Orbaneja del castillo(Burgos)");
        routes.add("Fuente Dé-Riaño(León)");
        routes.add("Camino Lebaniego");
        routes.add("Ruta del asón");

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

}