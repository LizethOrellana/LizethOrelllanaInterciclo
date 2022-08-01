package com.example.orellanalizeth_interciclo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orellanalizeth_interciclo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String USUARIO1_KEY = "usuario1";
    ActivityMainBinding binding;
    Bitmap bitmap;
    public static final String BITMAP2_KEY="bitmap";
    boolean k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        binding.txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivityRegistro("", "", "");

            }
        });
        binding.btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k = ActivityRegistro.p;
                if (k == true) {
                    if (binding.txtUsuario.getText().toString().equals("") || binding.txtContrseA.getText().toString().equals("")) {
                        Context context = MainActivity.this;
                        CharSequence text = "Llene todos los campos";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        Bundle extras = getIntent().getExtras();
                        Usuario usuario = extras.getParcelable(USUARIO1_KEY);
                        binding.setUsuario1(usuario);
                        if (binding.txtUsuario.getText().toString().equals(usuario.getUsuario()) && binding.txtContrseA.getText().toString().equals(usuario.getContraseña())) {
                            System.out.println(usuario.getUsuario() + " contra: " + usuario.getContraseña() + " email: " + usuario.getEmail());
                            bitmap = extras.getParcelable(BITMAP2_KEY);
                            abrirActivityBienvenido(usuario.getUsuario(), usuario.getContraseña(), usuario.getEmail());
                        } else {
                            Context context = MainActivity.this;
                            CharSequence text = "Usuario o Contraseña no coinciden";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                } else {
                    Context context = MainActivity.this;
                    CharSequence text = "Registrese Primero";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }


    private void abrirActivityRegistro(String nom, String contra, String email) {
        Intent intent = new Intent(this, ActivityRegistro.class);
        Usuario usuario = new Usuario(nom, contra, email);
        intent.putExtra(ActivityRegistro.USUARIO_KEY, usuario);
        startActivity(intent);
    }

    private void abrirActivityBienvenido(String nom, String contra, String email) {
        Intent intent = new Intent(this, ActivityBienvenido.class);
        Usuario usuario = new Usuario(nom, contra, email);
        intent.putExtra(ActivityBienvenido.USUARIO2_KEY, usuario);
        intent.putExtra(ActivityBienvenido.BITMAP_KEY,bitmap);
        startActivity(intent);
    }
}