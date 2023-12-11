package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private long userId;  // Variável para armazenar o ID do usuário

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        dbHelper = new DBHelper(this);

        // Obtenha o ID do usuário da Intent
        Intent intent = getIntent();
        userId = intent.getLongExtra("USER_ID", -1);

        // Verifique se o ID do usuário é válido
        if (userId != -1) {
            // Obtém os detalhes do usuário do banco de dados usando o ID do usuário
            User user = dbHelper.getUserById(userId);

            // Exibe os dados na nova atividade
            if (user != null) {
                TextView fullNameTextView = findViewById(R.id.fullNameEditText);
                TextView dateOfBirthTextView = findViewById(R.id.dateOfBirthEditText);
                TextView genderTextView = findViewById(R.id.genderEditText);
                TextView idCardNumberTextView = findViewById(R.id.idCardEditText);
                TextView usernameTextView = findViewById(R.id.usernameEditText);

                fullNameTextView.setText("Full Name: " + user.getFullName());
                dateOfBirthTextView.setText("Date of Birth: " + user.getDateOfBirth());
                genderTextView.setText("Gender: " + user.getGender());
                idCardNumberTextView.setText("ID Card Number: " + user.getIdCardNumber());
                usernameTextView.setText("Username: " + user.getUsername());
            }
        }

        // Botão para ir para a página inicial
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Home" é clicado
                startActivity(new Intent(AccountActivity.this, InitialPageActivity.class));
            }
        });

        // Botão para acessar a conta do usuário (você pode remover isso se não for necessário)
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Account" é clicado
                // Pode ser interessante adicionar uma mensagem indicando que o usuário já está na página de conta
            }
        });

        // Botão para sair do aplicativo
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(AccountActivity.this, LoginActivity.class);
            }
        });
    }
}
