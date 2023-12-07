package com.feup.bmta.phobiaapp;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.useremailEditText);
        passwordEditText = findViewById(R.id.userpasswordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aqui você deve adicionar lógica para verificar o login
                // Se o login for bem-sucedido, você pode abrir a próxima atividade
                Intent intent = new Intent(LoginActivity.this, InitialPageActivity.class);
                startActivity(intent);
                finish();
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