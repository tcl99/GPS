package com.example.gps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.CountDownLatch;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";
    private TextView user;
    private TextView pass;

    private Usuario u;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private AuthResult resultadoLogin;
    private FirebaseAuth aut;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Por si hubiera problemas
        FirebaseAuth.getInstance().signOut();

        //Para iniciar sesion
        aut = FirebaseAuth.getInstance();

        //Carga de las vistas
        user = findViewById(R.id.editUsername);
        pass = findViewById(R.id.editPassword);

        //Inicia la bbdd
        mDatabase = FirebaseDatabase.getInstance("https://electric-unity-359112-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = mDatabase.getReference("basegpst/usuarios");


    }

    public void iniciarSesionButton(View view) {
        //LOGEARSE CON EMAIL Y CONTRASEÑA
        //Primero se compueba que los campos se rellenen
        if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
        } else {
            aut.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //SE CONSIGUE AUTENTICAR
                    if (task.isSuccessful()) {
                        resultadoLogin = task.getResult();
                        getUsuarioFromFirebase(resultadoLogin);

                        //FALLA LA AUTENTICACIÓN
                    } else {
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión: No existe el usuario", Toast.LENGTH_SHORT).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión: Comprueba la contraseña", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, "Error al iniciar sesión: Desconocido", Toast.LENGTH_SHORT).show();
                        }
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                    }
                }
            });
        }
    }

    public void registroButton(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void getUsuarioFromFirebase(AuthResult authResult) {
        myRef.child(authResult.getUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot snapshot = task.getResult();
                Usuario u = snapshot.getValue(Usuario.class);
                if (task.isSuccessful()) {
                    Intent intent;
                    if(u.isGuia()) {
                        intent = new Intent(LoginActivity.this, GuiaActivity.class);
                    }
                    else {
                        intent = new Intent(LoginActivity.this, RoutesActivity.class);
                    }
                    EditText editText = (EditText) findViewById(R.id.editUsername);
                    startActivity(intent);

                } else {
                    Log.d("Firebase", "error");
                }
            }
        });
    }
}

