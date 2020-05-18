package com.example.repostator_2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ActualizaDatos extends AppCompatActivity {

    //declaramos la variable que guardará la posición de la fila que pulsó el usuario
    int posicion=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repostaje);
    }

    private void leeDatos(int indice){
        EditText editPrecio = (EditText)findViewById(R.id.precio);
        EditText editKilometos = (EditText) findViewById(R.id.kilometros);
        EditText editLitros = (EditText) findViewById(R.id.litros);
        DatePicker editFecha = (DatePicker) findViewById(R.id.fecha);
        SharedPreferences sharedPref = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String precio = sharedPref.getString("precio_" + indice, null);
        String kilometros = sharedPref.getString("kilometros_" + indice, null);
        String litros = sharedPref.getString("litros_" + indice, null);
        int dia =  sharedPref.getInt("dia_" + indice, 0) ;
        int mes =  sharedPref.getInt("mes_" + indice, 0) ;
        int year =  sharedPref.getInt("anyo_" + indice, 0);
        editPrecio.setText(precio);
        editKilometos.setText(kilometros);
        editLitros.setText(litros);
        editFecha.updateDate(year, mes,dia);
    }

    private void escribeDatos(int indice){
        EditText editPrecio = (EditText) findViewById(R.id.precio);
        EditText editKilometos = (EditText) findViewById(R.id.kilometros);
        EditText editLitros = (EditText) findViewById(R.id.litros);
        DatePicker editFecha = (DatePicker) findViewById(R.id.fecha);
        SharedPreferences sharedPref = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String precio = editPrecio.getText().toString();
        if (precio.equals("")){precio = "0";}

        String kilometros = editKilometos.getText().toString();
        if (kilometros.equals("")){kilometros = "0";}

        String litros = editLitros.getText().toString();
        if (litros.equals("")){litros = "0";}

        editor.putString("precio_" + indice, precio);
        editor.putString("kilometros_" + indice, kilometros);
        editor.putString("litros_" + indice, litros);

        editor.putInt("dia_" + indice, editFecha.getDayOfMonth());
        editor.putInt("mes_" + indice, editFecha.getMonth());
        editor.putInt("anyo_" + indice, editFecha.getYear());

        editor.commit();
    }

    @Override
    public void onStart(){
        super.onStart();
        Bundle parametros = getIntent().getExtras();
        if (parametros != null){
            posicion = parametros.getInt("posicion");
            posicion++;
            leeDatos(posicion);
        }
        final EditText editPrecio = (EditText) findViewById(R.id.precio);
        editPrecio.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    escribeDatos(posicion);
                    finish();
                }
                return false;
            }
        });
        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                escribeDatos(posicion);
                finish();
            }
        });
    }


}
