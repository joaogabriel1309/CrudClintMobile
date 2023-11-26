package com.example.projetopim.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetopim.Entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private Connection connection;
    private SQLiteDatabase BD;

    public ClientDAO(Context context){
        connection = new Connection(context);
        BD = connection.getWritableDatabase();
    }

    public long insert (Client client){
        ContentValues values = new ContentValues();
        values.put("name", client.getName());
        values.put("email", client.getEmail());
        values.put("telephone", client.getTelephone());
        return BD.insert("client", null, values);
    }

    public List<Client> AllClients(){
        List<Client> listClients = new ArrayList<>();
        Cursor cursor = BD.query("client",  new String[]{"id","name", "email", "telephone"},null,null,null,null,null);
        while (cursor.moveToNext()){
            Client client = new Client(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            client.setId(cursor.getInt(0));
            listClients.add(client);
        }
        return listClients;
    }

    public void delete(Client client){
        BD.delete("client", "id = ?", new String[]{client.getId().toString()});
    }

    public void Update (Client client){
        ContentValues values = new ContentValues();
        values.put("name", client.getName());
        values.put("email", client.getEmail());
        values.put("telephone", client.getTelephone());
        BD.update("client", values, "id = ?", new String[] {client.getId().toString()});
    }
}
