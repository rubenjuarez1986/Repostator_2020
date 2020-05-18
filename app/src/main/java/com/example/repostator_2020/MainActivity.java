package com.example.repostator_2020;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent ventanaCaptura = new Intent(this, Repostaje.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ventanaCaptura);
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        ArrayList<String> listadoRepostajes = new ArrayList<String>();
        ListView listaVista;
        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
//cargo la lista de repostajes de sharedPreferences al arraylist
        int size = sp.getInt("listado_size", 0); //el tamaño de la lista de repostajes que hemos hecho
        String precio = "";
        String kilometros = "";
        String litros = "";
        String dia = "";
        String mes = "";
        String year = "";
        double costeTotal = 0;
        for(int i = 1 ; i <= size; i++){
            precio = sp.getString("precio_" + i, "0");
            kilometros = sp.getString("kilometros_" + i, "0");
            litros = sp.getString("litros_" + i, "0");
            costeTotal = (Double.valueOf(precio) * Double.valueOf(litros));
            dia = String.valueOf( sp.getInt("dia_" + i, 0) );
            mes = String.valueOf( sp.getInt("mes_" + i, 0) );
            year = String.valueOf( sp.getInt("anyo_" + i, 0) );
            listadoRepostajes.add(kilometros + " km " + litros + " L " + precio + " €/L coste: " + String.format("%.2f",
                    costeTotal) + " fecha: " + dia + "-" + mes + "-" + year);
        }
//poblamos el listview con el arrayList
        listaVista = (ListView) findViewById(R.id.marcoLista);
//este es el array adapter, que conecta el arrayList con el activity
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listadoRepostajes
        );
        listaVista.setAdapter(arrayAdapter);

        listaVista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final Intent ventana = new Intent(MainActivity.this, ActualizaDatos.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ventana.putExtra("posicion",position);
                startActivity(ventana);
            }
        });
    }
}