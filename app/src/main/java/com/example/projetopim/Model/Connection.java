package com.example.projetopim.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Connection extends SQLiteOpenHelper {
    private static final String name = "banco.bd";
    private static final int version = 1;
    public Connection (Context context){
        super(context, name, null, version);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table client (id integer primary key autoincrement," +
                               "name varchar(30)," +
                               "email varchar(30)," +
                               "telephone char(11))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
