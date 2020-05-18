package com.example.repostator_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class Repostaje extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repostaje);
    }

    @Override
    public void onStart(){
        super.onStart();
        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                escribeDatos();
                finish();
            }
        });

        final EditText editPrecio = (EditText) findViewById(R.id.precio);
        editPrecio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    escribeDatos();
                    finish();
                }
                return false;
            }
        });
    }

    private void escribeDatos(){
        EditText editPrecio = (EditText) findViewById(R.id.precio);
        EditText editKilometros = (EditText) findViewById(R.id.kilometros);
        EditText editLitos = (EditText) findViewById(R.id.litros);
        DatePicker editFecha = (DatePicker) findViewById(R.id.fecha);
        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int posicion = sp.getInt("listado_size", 0);
        posicion++;
        editor.putString("precio_" + posicion, chequeaCadena(editPrecio.getText().toString()));
        editor.putString("kilometros_" + posicion, chequeaCadena(editKilometros.getText().toString()));
        editor.putString("litros_" + posicion, chequeaCadena(editLitos.getText().toString()));
        editor.putInt("dia_" + posicion, editFecha.getDayOfMonth());
        editor.putInt("mes_" + posicion, editFecha.getMonth());
        editor.putInt("anyo_" + posicion, editFecha.getYear());
        editor.putInt("listado_size", posicion);
        //Log.e("TAMANO", ""+posicion);
        editor.commit();
    }

    //el siguiente método sirve para que si al hacer un repostaje dejamos un campo sin rellenar no dé error
    //, le asigna 0 como valor por defecto
    private String chequeaCadena(String cadena){
        if (cadena.equals("")){return "0";}
        else {return cadena;}
    }

}