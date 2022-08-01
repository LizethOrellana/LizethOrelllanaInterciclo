package com.example.orellanalizeth_interciclo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.orellanalizeth_interciclo.databinding.ActivityBienvenidoBinding;

public class ActivityBienvenido extends AppCompatActivity {
    public static final String USUARIO2_KEY = "usuario";
    public static final String BITMAP_KEY="bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBienvenidoBinding binding = ActivityBienvenidoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extras = getIntent().getExtras();
        Usuario usuario = extras.getParcelable(USUARIO2_KEY);
        binding.setUsuario2(usuario);
        binding.txtNom.setText("Bienvenido "+usuario.getUsuario());
        int val=usuario.NivelSeguridad(usuario.getContraseña());
        Bitmap bitmap = extras.getParcelable(BITMAP_KEY);
        if(bitmap!=null){
            binding.imgUsuario.setImageBitmap(bitmap);
        }

        if(val==5 ){
            binding.valcontra.setRating(5);
            binding.txtvalcontra.setText("ALTA");
        }else if (val==4){
            binding.valcontra.setRating(4);
            binding.txtvalcontra.setText("MEDIA ALTA");
        }else if(val==3){
            binding.valcontra.setRating(3);
            binding.txtvalcontra.setText("MEDIA   ");

        }else if(val==2){
            binding.valcontra.setRating(2);
            binding.txtvalcontra.setText("BAJA");
        }
        else if(val==1){
            binding.valcontra.setRating(1);
            binding.txtvalcontra.setText("INSEGURA");
        }
    }
}