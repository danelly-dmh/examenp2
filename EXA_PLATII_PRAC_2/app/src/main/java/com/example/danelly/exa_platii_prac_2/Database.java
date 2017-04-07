package com.example.danelly.exa_platii_prac_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class Database extends SQLiteOpenHelper {

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(" +
                    "id integer primary key AUTOINCREMENT, " +
                    "name text," +
                    "last_name text, " +
                    "username text, " +
                    "password text)");

        db.execSQL("create table files(" +
                   "id integer primary key AUTOINCREMENT, " +
                   "path," +
                   "user_id integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists files");

        db.execSQL("create table users(" +
                "id integer primary key autoincrement, " +
                "name text," +
                "last_name text, " +
                "username text, " +
                "password text)");

        db.execSQL("create table files(" +
                "id integer primary key autoincrement, " +
                "path," +
                "user_id integer)");
    }

    public void insertUser(SQLiteDatabase db, Usuario u){
        String[] arguments = {u.getNombre(), u.getApellido(), u.getUsuario(), u.getPassword() };
        db.execSQL("insert or replace into users(name, last_name, username, password) values (?,?,?,?)", arguments);
    }

    public void updateUsuario(SQLiteDatabase db, Usuario u){
        String[] arguments = {u.getNombre(), u.getApellido(), u.getUsuario(), u.getPassword() };
        db.execSQL("update users set name = ?, last_name = ?, username = ?, password = ?", arguments);
    }

    public void insertFile(SQLiteDatabase db, int userId, String path){
        String[] arguments = {path, ""+userId };
        db.execSQL("insert or replace into files(path, user_id) values (?,?)", arguments);
    }

    public void updateFile(SQLiteDatabase db, int userId, String path){
        String[] arguments = {""+userId, path};
        db.execSQL("update files set path = ?, user_id = ?", arguments);
    }
}
