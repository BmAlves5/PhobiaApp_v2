package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private Button statsButton;
    private Button feedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        statsButton = findViewById(R.id.statisticsButton);
        feedbackButton = findViewById(R.id.patientfeedbackButton);

        // Configurar o clique do botão de Estatísticas
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adicione a lógica desejada para exibir estatísticas
                startActivity(new Intent(ResultActivity.this, Statistics.class));
            }
        });

        // Configurar o clique do botão de Feedback do Paciente
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adicione a lógica desejada para exibir feedback do paciente
                startActivity(new Intent(ResultActivity.this, PatientFeedbackActivity.class));
            }
        });

        // Botão para ir para a página inicial
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Home" é clicado
                startActivity(new Intent(ResultActivity.this, InitialPageActivity.class));
            }
        });

        // Botão para acessar a conta do usuário
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Account" é clicado
                startActivity(new Intent(ResultActivity.this, AccountActivity.class));
            }
        });

        // Botão para sair do aplicativo
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(ResultActivity.this, LoginActivity.class);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
