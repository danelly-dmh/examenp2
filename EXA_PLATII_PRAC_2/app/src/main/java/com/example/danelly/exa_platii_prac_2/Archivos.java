package com.example.danelly.exa_platii_prac_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Archivos extends AppCompatActivity {
    EditText texto;
    Button nuevo;
    Button guardar;
    Button abrir;
    SQLiteDatabase db;
    String pathSDCARD;
    boolean permiso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);
        texto = (EditText) findViewById(R.id.txtTexto);
        nuevo = (Button) findViewById(R.id.btnArchivosNuevo);
        guardar = (Button) findViewById(R.id.btnArchivosGuardar);
        abrir = (Button) findViewById(R.id.btnArchivosAbrir);
        pathSDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
        final Database helper = new Database(this, "db", null, 1);
        db = helper.getWritableDatabase();

        if(getIntent().hasExtra("nombre")){
            Log.i("nombre", getIntent().getStringExtra("nombre"));
            readFile(getIntent().getStringExtra("nombre"));
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    4000);
        }

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().hasExtra("usuario"))
                    getIntent().removeExtra("usuario");
                limpiar();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("id",-1);

                if(getIntent().hasExtra("id")){
                    if(id != -1){
                        String nombre = "data" + new Date().getTime()+".txt";
                        Log.i("nombre", nombre);
                        helper.insertFile(db, id, nombre);
                        Toast.makeText(Archivos.this, "Archivo creado", Toast.LENGTH_SHORT).show();
                        writeFile(nombre);
                    }
                }else{
                    if(getIntent().hasExtra("nombre")){
                        helper.updateFile(db, id, getIntent().getStringExtra("nombre"));
                        writeFile(getIntent().getStringExtra("nombre"));
                        Toast.makeText(Archivos.this, "Archivo editado", Toast.LENGTH_SHORT).show();
                    }
                }
                limpiar();
            }
        });

        abrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Archivos.this, ListaArchivos.class);
                intent.putExtra("id", getIntent().getIntExtra("id", -1));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 4000){
            permiso = true;
        }
    }

    private void limpiar(){
        if(getIntent().hasExtra("nombre"))
            getIntent().removeExtra("nombre");
        texto.setText("");
    }

    private void readFile(String name){
        if(permiso){
            try{
                FileInputStream fis = new FileInputStream(pathSDCARD +  "/".concat(name));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader reader = new BufferedReader(isr);
                String text;
                while((text = reader.readLine()) != null){
                    texto.append(text + "\n");
                }
                reader.close();
                fis.close();
            }catch(FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    private void writeFile(String name){
        if(permiso){
            try {
                FileOutputStream fos = new FileOutputStream(pathSDCARD + "/".concat(name));
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter writer = new BufferedWriter(osw);
                String text = texto.getText().toString();
                writer.write(text + "\n");
                fos.close();
                writer.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
