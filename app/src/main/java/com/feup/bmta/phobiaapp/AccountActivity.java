package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        dbHelper = new DBHelper(this);



        // Assuming userId is obtained from the Intent
        Intent intent = getIntent();
        long userId = intent.getLongExtra("USER_ID", -1);

        // Get user information from the database
        User userAccount = dbHelper.getUserById(userId);

        if (userAccount != null) {
            // Update UI with user information
            updateUI(userAccount);
        } else {
            // Handle the case where the user is not found
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
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

        // Botão para sair do aplicativo
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(AccountActivity.this, LoginActivity.class);
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
                DBHelper dbHelper = new DBHelper(AccountActivity.this);
                User userAccount = dbHelper.getUserById(userId);

                // Inicia a AccountActivity e passa o ID do usuário como extra
                Intent intent = new Intent(AccountActivity.this, AccountActivity.class);
                intent.putExtra("USER_ID", userId);

                // Se o usuário existir, inicia a AccountActivity, caso contrário, mostra um Toast
                if (userAccount != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(AccountActivity.this, "User not found", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    private void updateUI(User user) {
        // Update UI elements with user information
        EditText fullNameEditText = findViewById(R.id.fullNameEditText);
        EditText dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        EditText genderEditText = findViewById(R.id.genderEditText);
        EditText idCardEditText = findViewById(R.id.idCardEditText);
        EditText usernameEditText = findViewById(R.id.usernameEditText);

        if (user != null) {
            fullNameEditText.setText(user.getFullName());
            dateOfBirthEditText.setText(user.getDateOfBirth());
            genderEditText.setText(user.getGender());
            idCardEditText.setText(user.getIdCardNumber());
            usernameEditText.setText(user.getUsername());
            // Set other EditTexts with the corresponding information
        } else {
            // Handle the case where the user is not found
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        }
    }
}
