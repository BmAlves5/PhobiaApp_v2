package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 10000; // 10 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar o ícone e o título da aplicação
        ImageView appIcon = findViewById(R.id.appIcon);
        TextView appTitle = findViewById(R.id.appTitle);

        appIcon.setImageResource(R.mipmap.ic_launcher);  // Substitua "ic_launcher" pelo nome do seu ícone
        appTitle.setText("PhobiaApp");

        // Delay de 10 segundos antes de ir para a LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToLoginActivity();
            }
        }, DELAY_TIME);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();  // Encerrar a atividade atual para evitar empilhamento desnecessário
    }
}
