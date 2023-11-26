package com.example.projetopim.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.projetopim.Entity.Client;
import com.example.projetopim.Model.ClientDAO;
import com.example.projetopim.R;

import java.util.ArrayList;
import java.util.List;

public class Consultation extends AppCompatActivity {

    private ListView listView;
    private ClientDAO clientDAO;
    private List<Client> clients;
    private List<Client> clientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        listView = findViewById(R.id.List_client);
        clientDAO = new ClientDAO(this);
        clients = clientDAO.AllClients();
        clientList.addAll(clients);

        clientAdapter adapter = new clientAdapter(this, clientList);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_primary, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchClient(s);
                return false;
            }
        });
        return true;
    }

    public void SearchClient(String name){
        clientList.clear();
        for (Client c : clients){
            if (c.getName().toUpperCase().contains(name.toUpperCase())) {
                clientList.add(c);
            }
        }
        listView.invalidateViews();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_context,menu);
    }

    public void deleteClient(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Client clientDelete = clientList.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Deseja excluir esse cliente ?")
                .setNegativeButton("não", null)
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clientList.remove(clientDelete);
                        clients.remove(clientDelete);
                        clientDAO.delete(clientDelete);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public void updateClient(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Client clientUpdate = clientList.get(menuInfo.position);

        Intent it = new Intent(this, Cadastre.class);
        it.putExtra("client",clientUpdate);
        startActivity(it);
    }

    public void Cadastre(MenuItem item){
        Intent it = new Intent(this, Cadastre.class);
        startActivity(it);
    }

    @Override
    public void onResume(){
        super.onResume();
        clients = clientDAO.AllClients();
        clientList.clear();
        clientList.addAll(clients);
        listView.invalidateViews();
    }
}