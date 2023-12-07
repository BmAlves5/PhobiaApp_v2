package com.feup.bmta.phobiaapp;

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

        statsButton = findViewById(R.id.statsButton);
        feedbackButton = findViewById(R.id.feedbackButton);

        // Configurar o clique do botão de Estatísticas
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adicione a lógica desejada para exibir estatísticas
                showToast("Estatísticas Button Clicked");
            }
        });

        // Configurar o clique do botão de Feedback do Paciente
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adicione a lógica desejada para exibir feedback do paciente
                showToast("Feedback do Paciente Button Clicked");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
