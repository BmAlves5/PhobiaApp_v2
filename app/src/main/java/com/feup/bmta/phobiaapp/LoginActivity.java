package com.feup.bmta.phobiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        dbHelper = new DBHelper(this);


        usernameEditText = findViewById(R.id.useremailEditText);
        passwordEditText = findViewById(R.id.userpasswordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        // Verifica se o utilizador já está registado
        SharedPreferences sharedPreferences = getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE);
        boolean registrado = sharedPreferences.getBoolean("registrado", false);

        Intent intent;

        if (registrado) {
            // Se registado, vai diretamente para a página inicial
            intent = new Intent(LoginActivity.this, InitialPageActivity.class);
            startActivity(intent);
            finish();  // Finaliza a atividade atual para impedir que o utilizador volte à tela de login
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtem os valores inseridos pelos utilizadores
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Verifica as credenciais e obtem o ID do utilizador
                DBHelper dbHelper = new DBHelper(LoginActivity.this);
                long userId = dbHelper.checkCredentialsAndGetUserId(username, password);

                dbHelper.openDatabase();

                if (userId != -1) {
                    String ecgData = dbHelper.getECGData(userId);


                    Intent intent = new Intent(LoginActivity.this, InitialPageActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);


                    SharedPreferences sharedPreferences = getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("registado", true);
                    editor.apply();

                    // Vai para a página inicial
                    intent = new Intent(LoginActivity.this, InitialPageActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);


                    finish();
                } else {
                    // Credenciais incorretas, exibe mensagem de erro
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