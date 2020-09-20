package com.example.agendaapplication.ClassesDB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(@Nullable Context context, @Nullable String nameDB, @Nullable SQLiteDatabase.CursorFactory factory1, int version) {
        super(context, nameDB, factory1, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE usuarios (idU INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT, direccion TEXT, telefono INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS usuarios;");
        onCreate(db);
    }
}
