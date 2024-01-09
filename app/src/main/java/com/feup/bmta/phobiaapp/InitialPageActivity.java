package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InitialPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);

        // Botão para executar o teste
        Button performTestButton = findViewById(R.id.performTestButton);
        performTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Perform Test" é clicado
                startActivity(new Intent(InitialPageActivity.this, BluetoothService.class));
            }
        });

        // Botão para exibir o histórico
        Button historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtendo os valores do ECG e SDNN
             //   ArrayList<Integer> ecgDataValues = getEcgDataValues();
             //   ArrayList<Double> sdnnValues = getSDNNValues(ecgDataValues);

                // Iniciando a HistoryActivity e passando os dados do ECG e SDNN via Intent
                Intent intent = new Intent(InitialPageActivity.this, HistoryActivity.class);
            //    intent.putIntegerArrayListExtra("ECG_DATA_VALUES", ecgDataValues);
             //   intent.putDoubleArrayListExtra("SDNN_VALUES", sdnnValues);
                startActivity(intent);
            }
        });

        // Botão para ir para a página inicial
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Home" é clicado
                startActivity(new Intent(InitialPageActivity.this, InitialPageActivity.class));
            }
        });

        // Botão para acessar a conta do usuário
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtém o ID do usuário de onde você o tem
                long userId = 1; // Substitua isso pela maneira como você obtém o ID do usuário

                // Use o DBHelper para obter os detalhes do usuário
                DBHelper dbHelper = new DBHelper(InitialPageActivity.this);
                User userAccount = dbHelper.getUserById(userId);

                // Inicia a AccountActivity e passa o ID do usuário como extra
                Intent intent = new Intent(InitialPageActivity.this, AccountActivity.class);
                intent.putExtra("USER_ID", userId);

                // Se o usuário existir, inicia a AccountActivity, caso contrário, mostra um Toast
                if (userAccount != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(InitialPageActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botão para sair do aplicativo
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(InitialPageActivity.this, LoginActivity.class);
            }
        });
    }
}
