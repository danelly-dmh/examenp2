package com.example.danelly.exa_platii_prac_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    Button btnArchivos;
    Button btnUsuarios;
    Button btnSalir;

    SQLiteDatabase db;
    Database helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnArchivos =(Button) findViewById(R.id.btnArchivos);
        btnUsuarios =(Button) findViewById(R.id.btnUsuarios);
        btnSalir =(Button) findViewById(R.id.btnSalir);

        helper = new Database(this, "db", null, 1);

        btnArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivityForResult(intent,0);
            }
        });

        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Usuarios.class);
                startActivityForResult(intent,0);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
