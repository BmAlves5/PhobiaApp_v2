package com.feup.bmta.phobiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this); // Inicialize o DBHelper aqui


        usernameEditText = findViewById(R.id.useremailEditText);
        passwordEditText = findViewById(R.id.userpasswordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenha os valores inseridos pelos usuários
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Verifique as credenciais e obtenha o ID do usuário
                DBHelper dbHelper = new DBHelper(LoginActivity.this);
                long userId = dbHelper.checkCredentialsAndGetUserId(username, password);

                dbHelper.openDatabase();

                if (userId != -1) {
                    String ecgData = dbHelper.getECGData(userId);

                    Toast.makeText(LoginActivity.this, "ECG Date: " + ecgData, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, InitialPageActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                    finish();
                } else {
                    // Credenciais incorretas, exiba mensagem de erro
                    Toast.makeText(LoginActivity.this, "Incorrect credentials. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abra a atividade de registro
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}