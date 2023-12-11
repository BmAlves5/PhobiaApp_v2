package com.feup.bmta.phobiaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SpiderGameActivity extends AppCompatActivity {

    private TextView levelTextView;
    private int[] spiderCounts = {1, 2, 3, 4, 5}; // Quantidade de aranhas por nível
    private int currentLevel = 0;
    private Handler handler;
    private Random random;

    private Vibrator vibrator;
    private RelativeLayout spidersLayout;
    private Button[] levelButtons;

    private ImageView imageView;

    private CountDownTimer timer;
    private Button nextButton;

    // Intervalos para cada nível
    private final int[] levelIntervals = {2000, 2500, 3000, 2000, 2000}; // Adicione mais intervalos conforme necessário



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider_game);

        levelButtons = new Button[]{
                findViewById(R.id.levelButton1),
                findViewById(R.id.levelButton2),
                findViewById(R.id.levelButton3),
                findViewById(R.id.levelButton4),
                findViewById(R.id.levelButton5),
        };
        imageView = findViewById(R.id.imageView);

        random = new Random();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        for (int i = 0; i < levelButtons.length; i++) {
            final int level = i + 1;
            levelButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInstructions();
                }
            });
        }

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            int level = intent.getIntExtra("level", 1); // O segundo parâmetro é o valor padrão se "level" não estiver presente
            showSpiders(level);
            startGame(level);
        }





        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Home" é clicado
                startActivity(new Intent(SpiderGameActivity.this, InitialPageActivity.class));
            }
        });

        // Botão para acessar a conta do usuário
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ação quando o botão "Account" é clicado
                startActivity(new Intent(SpiderGameActivity.this, AccountActivity.class));
            }
        });

        // Botão para sair do aplicativo
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(SpiderGameActivity.this, LoginActivity.class);
            }
        });
    }



   /* private void setButtonVisibility(int visibility) {
        Button homeButton = findViewById(R.id.homeButton);
        Button accountButton = findViewById(R.id.accountButton);
        Button exitButton = findViewById(R.id.exitButton);

        homeButton.setVisibility(visibility);
        accountButton.setVisibility(visibility);
        exitButton.setVisibility(visibility);
    }*/

    public void startGame(int level) {
        for (Button button : levelButtons) {
            button.setEnabled(false);
        }

        currentLevel = level;

        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (random.nextBoolean()) { // Probabilidade de 50%
                    showSpiders(currentLevel);
                } else {
                    hideSpiders();
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(SpiderGameActivity.this, "Tempo esgotado! Jogo terminado.", Toast.LENGTH_SHORT).show();
                restartGame();
            }
        };

        timer.start();
    }



    private void showSpiders(int level) {
        if (level >= 1 && level <= spiderImages.length) {
            int resID = spiderImages[level - 1]; // O índice no array é level - 1

            Drawable imagem = getResources().getDrawable(resID);
            imageView.setImageDrawable(imagem);
            vibrateDevice();
        } else {
            // Trate o caso em que o nível é inválido
            Toast.makeText(this, "Nível inválido: " + level, Toast.LENGTH_SHORT).show();
        }
    }

    private int[] spiderImages = {
            R.drawable.spiderlevel1,
            R.drawable.spiderlevel2,
            R.drawable.spiderlevel3,
            R.drawable.spiderlevel4,
            R.drawable.spiderlevel5
    };

    private void hideSpiders() {
        imageView.setImageDrawable(null);
    }

    private void vibrateDevice() {
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibre por 500 milissegundos (0,5 segundos)
            vibrator.vibrate(500);
        }
    }
    private void restartGame() {
        for (Button button : levelButtons) {
            button.setEnabled(true);
        }

        imageView.setImageDrawable(null); // Limpa a imagem
        timer.cancel(); // Para o temporizador, se estiver em execução
    }

    private void showInstructions() {
        Intent intent = new Intent(SpiderGameActivity.this, BluetoothService.class);
        startActivity(intent);
    }


}