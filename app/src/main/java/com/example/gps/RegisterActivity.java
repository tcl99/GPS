package com.example.gps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText correo;
    private EditText password;
    private EditText rep_password;
    private Chip guia;
    private Chip senderista;
    private FirebaseAuth aut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //Para crear el usuario en firebase
        aut = FirebaseAuth.getInstance();

        //Carga de las vistas
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
        rep_password = findViewById(R.id.rep_password);
        guia = findViewById(R.id.guia);
        senderista = findViewById(R.id.senderista);

        //Inicializar valores
        senderista.setChecked(false);
        guia.setChecked(false);

        //LISTENERS PARA MARCAR SOLO UNA OPCIÓN A LA VEZ
        senderista.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(senderista.isChecked()) {
                    if (guia.isChecked()) {
                        guia.setChecked(false);
                    }
                }
            }
        });

        guia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(guia.isChecked()) {
                    if (senderista.isChecked()) {
                        senderista.setChecked(false);
                    }
                }
            }
        });


    }

    public void registrarseButton (View  view) {
        //Si el formato de mail no es valido
        if(!isValidEmail(correo.getText().toString())) {
            Toast.makeText(this, "Correo no válido", Toast.LENGTH_SHORT).show();
        }
        //Si las contraseñas introducidas no son iguales
        else if (password.getText().toString().compareTo( rep_password.getText().toString() ) != 0) {
            Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
        }
        else if ( !senderista.isChecked() && !guia.isChecked() ) {
            Toast.makeText(this, "Escoge un rol", Toast.LENGTH_SHORT).show();
        }
        else {
            aut.createUserWithEmailAndPassword(correo.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Se añade el rol a la base de datos
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref = db.getReference("usuarios");

                        String UID = task.getResult().getUser().getUid();
                        Map<String, Object> datosUsuario = new HashMap<>();
                        datosUsuario.put("guia", guia.isChecked());

                        ref.child(UID).setValue(datosUsuario);

                        Intent intent = new Intent(RegisterActivity.this, RoutesActivity.class);
                        startActivity(intent);
                    } else {
                        try {

                        } catch(Exception e) {

                        }
                        Toast.makeText(RegisterActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                }});
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}