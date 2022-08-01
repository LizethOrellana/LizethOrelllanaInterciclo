package com.example.orellanalizeth_interciclo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orellanalizeth_interciclo.databinding.ActivityRegistroBinding;

import java.util.regex.Pattern;

public class ActivityRegistro extends AppCompatActivity {
    public static final String USUARIO_KEY = "usuario";
    ActivityRegistroBinding binding;
    Bitmap bitmap;
    boolean k = false;
    public static boolean p = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        Usuario usuario = extras.getParcelable(USUARIO_KEY);
        binding.setUsuario(usuario);
        binding.sololetras.setVisibility(View.GONE);
        binding.max.setVisibility(View.GONE);
        binding.errorcorreo.setVisibility(View.GONE);
        binding.btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbrirCamara();
                k = true;
            }
        });
        binding.txtUsuario.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String usuario = binding.txtUsuario.getText().toString();
                if (!valid(usuario.trim())) {
                    binding.sololetras.setVisibility(View.VISIBLE);
                    binding.txtUsuario.setText("");
                    return false;
                } else {
                    binding.sololetras.setVisibility(View.GONE);
                    return true;
                }
            }
        });
        binding.txtContraseA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String clave = binding.txtContraseA.getText().toString();
                if (clave.length() < 5) {
                    binding.max.setVisibility(View.VISIBLE);
                } else {
                    binding.max.setVisibility(View.GONE);
                    binding.ratingBar.setRating(usuario.NivelSeguridad(clave));
                }
                return false;
            }
        });

        binding.txtEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String correo = binding.txtEmail.getText().toString();
                if (!validarEmail(correo)) {
                    binding.errorcorreo.setVisibility(View.VISIBLE);
                    return false;
                } else {
                    binding.errorcorreo.setVisibility(View.GONE);
                    return true;
                }
            }
        });
        binding.btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clave = binding.txtContraseA.getText().toString();
                boolean x = usuario.validarClave(binding.txtContraseA.getText().toString(), binding.txtVerContraseA.getText().toString());
                boolean j = usuario.RegistrarUsuario(binding.txtUsuario.getText().toString(), binding.txtEmail.getText().toString(), binding.txtContraseA.getText().toString());
                if (j == false) {
                    Context context = ActivityRegistro.this;
                    CharSequence text = "Llene todos los campos o escriba bien su correo";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    if (x == true) {
                        if (k == true) {
                            if (clave.length() < 5) {
                                Context context = ActivityRegistro.this;
                                CharSequence text = "Contraseñas menor a 5 caracteres";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            } else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityRegistro.this);
                                alert.setMessage("Registrado Correctamente");
                                alert.setTitle("Registro");
                                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText((ActivityRegistro.this), "Bienvenido", Toast.LENGTH_LONG).show();
                                        abrirActivityMain(binding.txtUsuario.getText().toString(), binding.txtContraseA.getText().toString(), binding.txtEmail.getText().toString());
                                        p = true;
                                    }
                                });
                                alert.show();
                            }
                        } else {
                            Context context = ActivityRegistro.this;
                            CharSequence text = "Porfavor ingrese una fotografia";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                    } else {
                        Context context = ActivityRegistro.this;
                        CharSequence text = "Contraseñas no coinciden ";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                }

            }
        });

    }

    private void AbrirCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivity(camaraIntent);
        startActivityForResult(camaraIntent, 1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            if (data != null) {
                bitmap = data.getExtras().getParcelable("data");
                binding.imgUsuario.setImageBitmap(bitmap);
            }

        }
    }

    private void abrirActivityMain(String nom, String contra, String email) {
        Intent intent = new Intent(this, MainActivity.class);
        Usuario usuario = new Usuario(nom, contra, email);
        intent.putExtra(MainActivity.USUARIO1_KEY, usuario);
        intent.putExtra(MainActivity.BITMAP2_KEY,bitmap);
        startActivity(intent);
    }

    public static boolean valid(String v) {
        return v.matches("[a-z-A-Z- ]*");
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}