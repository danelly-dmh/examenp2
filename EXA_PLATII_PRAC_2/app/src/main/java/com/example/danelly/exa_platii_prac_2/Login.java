package com.example.danelly.exa_platii_prac_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText usuario;
    EditText password;
    Button acceder;
    Button regresar;
    SQLiteDatabase db;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Database helper = new Database(this, "db", null, 1);
        db = helper.getWritableDatabase();
        usuario = (EditText) findViewById(R.id.txtDialogoUsuario);
        password = (EditText) findViewById(R.id.txtDialogoPassword);
        acceder = (Button) findViewById(R.id.btnLogin);
        regresar = (Button) findViewById(R.id.btnRegresar);
        id = 0;

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eUsuario = usuario.getText().toString();
                String ePassword = password.getText().toString();
                if(login(eUsuario, ePassword))
                    if (id != 0) {
                        Intent i = new Intent(Login.this, Archivos.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    } else {
                        Log.i("id", ""+id);
                        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
                    }
                Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Principal.class);
                startActivity(intent);
            }
        });
    }

    public boolean login(String usuario, String password) {

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? and password = ?", new String[]{usuario, password});
        if(cursor.getCount() > 0){
                cursor.moveToFirst();
                id = cursor.getInt(0);
                Log.i("id", ""+id);
                Log.i("usuario", cursor.getString(3));
                Log.i("usuario", cursor.getString(4));
            return true;
        }
        return false;
    }
}
