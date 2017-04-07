package com.example.danelly.exa_platii_prac_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaArchivos extends AppCompatActivity {
    ListView lista;
    SQLiteDatabase db;
    Database helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_archivos);
        lista = (ListView) findViewById(R.id.listaArchivos);
        helper = new Database(this, "db", null, 1);
        db = helper.getWritableDatabase();
        ArrayAdapter adapter = new ArrayAdapter(ListaArchivos.this, android.R.layout.simple_list_item_1, cargarArchivos());
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Archivo a = (Archivo) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ListaArchivos.this, Archivos.class);
                Log.i("nombre", a.getPath());
                intent.putExtra("nombre", a.getPath());
                startActivity(intent);
            }
        });

    }

    private ArrayList<Archivo> cargarArchivos(){
        ArrayList<Archivo> archivos = new ArrayList<>();
        if(getIntent().hasExtra("id")){
            int id = getIntent().getIntExtra("id", -1);
            if(id != -1){
                String[] args = {""+id};
                Cursor cursor = db.rawQuery("select * from files where user_id = ?",args);
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    archivos.add(new Archivo(id, cursor.getString(1)));
                    cursor.moveToNext();
                }
            }
        }
        return archivos;
    }
}
