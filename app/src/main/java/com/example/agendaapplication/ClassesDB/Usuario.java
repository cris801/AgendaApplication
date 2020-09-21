package com.example.agendaapplication.ClassesDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class Usuario {
    SQLiteDatabase db = null;
    Context context1 = null;
    String tableName = "usuarios";

    public Usuario(Context context1, SQLiteDatabase db) {
        this.db = db;
        this.context1 =context1;
    }

    public void Nuevo (String nombre, String direccion, int telefono){
        ContentValues valores = new ContentValues();
        valores.put("nombre",nombre);
        valores.put("direccion",direccion);
        valores.put("telefono",telefono);
        db.insert(tableName,null,valores);
        Toast.makeText(context1, "USUARIO REGISTRADO", Toast.LENGTH_SHORT).show();
    }

    public Cursor Consulta(){
        Cursor cursor1 = db.query(tableName,null,null,null,
                null,null,null);
        return cursor1;
    }
}
