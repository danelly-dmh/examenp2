package com.example.danelly.exa_platii_prac_2;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UsuarioAdapter  extends ArrayAdapter<Usuario>{
    LayoutInflater inflater;
    public UsuarioAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.list_usuarios,null);
        }

        Usuario usuario = getItem(position);

        if(usuario != null){
            TextView tvUsuario = (TextView) view.findViewById(R.id.tvUsuario);
            TextView tvnombre = (TextView) view.findViewById(R.id.tvNombre);

            if(tvUsuario != null) tvUsuario.setText(usuario.getUsuario());
            if(tvnombre != null) tvnombre.setText(usuario.getNombre() + " " + usuario.getApellido());
        }

        return view;
    }
}
