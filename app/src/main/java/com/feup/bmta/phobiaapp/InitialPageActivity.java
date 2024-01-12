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
                // Obter os valores do ECG e SDNN
             //   ArrayList<Integer> ecgDataValues = getEcgDataValues();
             //   ArrayList<Double> sdnnValues = getSDNNValues(ecgDataValues);

                // Iniciar a HistoryActivity e passar os dados do ECG e SDNN via Intent
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

        // Botão para aceder a conta do utilizador
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long userId = 1;

                DBHelper dbHelper = new DBHelper(InitialPageActivity.this);
                User userAccount = dbHelper.getUserById(userId);

                // Inicia a AccountActivity e passa o ID do utilizador como extra
                Intent intent = new Intent(InitialPageActivity.this, AccountActivity.class);
                intent.putExtra("USER_ID", userId);

                // Se o utilizador existir, inicia a AccountActivity, caso contrário, mostra um Toast
                if (userAccount != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(InitialPageActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botão para sair da app
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(InitialPageActivity.this, LoginActivity.class);
            }
        });
    }
}
