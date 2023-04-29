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
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MSG = "com.example.gps.MESSAGE";

    private FirebaseAuth aut;
    private TextView user;
    private TextView pass;
    private boolean hasUserChanged;
    private boolean hasPassChanged;
    private Chip guia;
    private Chip senderista;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Autenticación
        aut = FirebaseAuth.getInstance();

        //Carga de las vistas
        guia = findViewById(R.id.guia);
        senderista = findViewById(R.id.senderista);
        user = findViewById(R.id.editUsername);
        pass = findViewById(R.id.editPassword);

        //Inicialización de los valores para los listeners
        hasUserChanged = false;
        hasPassChanged = false;
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

        //LISTENERS PARA GESTIONAR USUARIO Y CONTRASEÑA
        user.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hasUserChanged = true;
                user.setText("");
                user.setTextColor(Color.parseColor("#FF000000"));
                user.setOnTouchListener(null);
                return false;
            }
        });
        pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hasPassChanged = true;
                pass.setText("");
                pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                pass.setTextColor(Color.parseColor("#FF000000"));
                pass.setOnTouchListener(null);
                return false;
            }
        });



    }

    public void iniciarSesionButton (View view) {
        //LOGEARSE CON EMAIL Y CONTRASEÑA
        //Primero se compueba que los campos se rellenen
        if(user.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || !hasUserChanged || !hasPassChanged) {
            Toast.makeText(this, "No puedes dejar campos vacios", Toast.LENGTH_SHORT).show();
        }
        else if ( !senderista.isChecked() && !guia.isChecked() ) {
            Toast.makeText(this, "Escoge un rol", Toast.LENGTH_SHORT).show();
        }
        else {
            aut.signInWithEmailAndPassword(user.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //SE CONSIGUE AUTENTICAR
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = aut.getCurrentUser();

                        Intent intent = new Intent(LoginActivity.this, RoutesActivity.class);
                        EditText editText = (EditText) findViewById(R.id.editUsername);
                        String msg = editText.getText().toString();
                        intent.putExtra(EXTRA_MSG, msg);
                        startActivity(intent);

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
}