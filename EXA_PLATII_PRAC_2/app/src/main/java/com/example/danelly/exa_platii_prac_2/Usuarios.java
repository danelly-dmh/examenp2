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

public class Usuarios extends AppCompatActivity {
    EditText txtName;
    EditText txtLastName;
    EditText txtUser;
    EditText txtPassword;

    Button btnNuevo;
    Button btnGuardar;
    Button btnAbrir;
    Button btnBorrar;

    Database helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        txtName = (EditText) findViewById(R.id.txtName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtUser = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPass);

        btnNuevo = (Button) findViewById(R.id.btnNuevo);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);

        helper = new Database(this, "db", null, 1);
        db = helper.getWritableDatabase();

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().hasExtra("usuario"))
                    getIntent().removeExtra("usuario");
                limpiar();
            }
        });
        usuario(getIntent());
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();

                usuario.setNombre(txtName.getText().toString());
                usuario.setApellido(txtLastName.getText().toString());
                usuario.setUsuario(txtUser.getText().toString());
                usuario.setPassword(txtPassword.getText().toString());
                if(getIntent().hasExtra("usuario")){
                    helper.updateUsuario(db, usuario);
                    getIntent().removeExtra("usuario");
                }else{
                    helper.insertUser(db, usuario);
                }
                limpiar();
                Toast.makeText(Usuarios.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
            }
        });

        btnAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Usuarios.this, ListaUsuarios.class);
                startActivity(intent);
            }
        });

    }
    private void limpiar(){
        txtName.setText("");
        txtLastName.setText("");
        txtUser.setText("");
        txtPassword.setText("");
    }

    private void usuario(Intent intent){
        if (intent.hasExtra("usuario")){
            String usuario = intent.getStringExtra("usuario");
            Cursor cursor = db.rawQuery("select * from users where username = ?", new String[]{usuario});

            if(cursor.moveToFirst()){
                do{
                    txtName.setText(cursor.getString(1));
                    txtLastName.setText(cursor.getString(2));
                    txtUser.setText(cursor.getString(3));
                    txtPassword.setText(cursor.getString(4));
                }while(cursor.moveToNext());
            }
        }
        Log.i("usuario", "no tiene usuario");
    }
}
