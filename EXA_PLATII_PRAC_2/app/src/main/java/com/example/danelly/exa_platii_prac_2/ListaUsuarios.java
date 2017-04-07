package com.example.danelly.exa_platii_prac_2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios extends AppCompatActivity {
    ListView lvUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);
        lvUsuarios = (ListView) findViewById(R.id.lvLista);

        UsuarioAdapter adapter = new UsuarioAdapter(ListaUsuarios.this, R.layout.list_usuarios, leerUsuarios());
        lvUsuarios.setAdapter(adapter);

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario u = (Usuario) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ListaUsuarios.this, Usuarios.class);
                intent.putExtra("usuario", u.getUsuario());
                startActivity(intent);
            }
        });
    }

    private List<Usuario> leerUsuarios(){
        List<Usuario> usuarioList = new ArrayList<>();
        Database helper = new Database(getApplicationContext(), "db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from users", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            usuarioList.add(new Usuario(cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        return usuarioList;
    }
}
