package com.example.projetopim.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetopim.Controller.SaveClient;
import com.example.projetopim.Entity.Client;
import com.example.projetopim.Model.ClientDAO;
import com.example.projetopim.R;

public class Cadastre extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText telephone;
    private ClientDAO clientDAO;
    private Client client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastre);

        //getSupportActionBar().hide();

        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        telephone = findViewById(R.id.edit_telephone);

        clientDAO = new ClientDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("client")){
            client = (Client) it.getSerializableExtra("client");
            name.setText(client.getName());
            email.setText(client.getEmail());
            telephone.setText(client.getTelephone());
        }
    }

    public void Save(View view){
        if (client == null){
            Client client = new Client(name.getText().toString(),
                    email.getText().toString(),
                    telephone.getText().toString());
            long id = clientDAO.insert(client);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Informação")
                    .setMessage("Cliente cadastrado com sucesso id: "+ id)
                    .setPositiveButton("ok", null).create();
            dialog.show();
            //Toast.makeText(this, "Cliente cadastrado com sucesso id: "+ id, Toast.LENGTH_SHORT).show();
        }else {
            client.setName(name.getText().toString());
            client.setEmail(email.getText().toString());
            client.setTelephone(telephone.getText().toString());
            clientDAO.Update(client);
            Toast.makeText(this, "Cliente atualizado com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}