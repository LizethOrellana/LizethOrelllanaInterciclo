package com.example.orellanalizeth_interciclo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.regex.Pattern;

public class Usuario implements Parcelable {
    private String usuario;
    private String contraseña;
    private String email;

    public Usuario(String usuario, String contraseña, String email) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.email = email;
    }

    protected Usuario(Parcel in) {
        usuario = in.readString();
        contraseña = in.readString();
        email = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usuario);
        dest.writeString(contraseña);
        dest.writeString(email);
    }

    public boolean validarClave(String clave, String repclave) {
            if (clave.equals(repclave) ) {
                return true;
            } else {
                return false;
            }
    }
    public int NivelSeguridad( String contra){
        int contador = 0;
        char arreglo[] = contra.toCharArray();
        for (int i = 0; i < contra.length(); i++) {
            if (arreglo[i]=='?' || arreglo[i]=='*' || arreglo[i]=='?' || arreglo[i]=='¡' || arreglo[i]=='!' || arreglo[i]=='#' || arreglo[i]=='$' || arreglo[i]=='%' || arreglo[i]=='&') {
                contador= contador+1;
            }
        }
        if(contra.length()>11 && contador>3 ){
            return 5;
        }else if (contra.length()==10 || (contra.length()==11 && contador==2 ||  contador==3) ){
            return 4;
        }else if(contra.length()>7 && contra.length()<10  && contador==1 ){
            return 3;

        }else if(contra.length()>7 && contra.length()<10 && contador==0){
            return 2;
        }
        else {
            return 1;
        }

    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public  boolean RegistrarUsuario(String txtUsuario,String txtEmail, String txtContraseA){
        if (txtUsuario.equals("") || txtEmail.equals("") || txtContraseA.equals("")) {
            return false;
        }else if(!validarEmail(txtEmail)){
            return false;
        }else{
            return true;
        }
    }
}
