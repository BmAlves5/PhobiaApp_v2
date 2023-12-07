package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SpiderGameActivity extends AppCompatActivity {

    private TextView levelTextView;
    private int[] spiderCounts = {1, 2, 3, 4, 5}; // Quantidade de aranhas por nível
    private int currentLevel = 0;
    private Handler handler;
    private Random random;
    private RelativeLayout spidersLayout;
    private Button[] levelButtons;
    private Button nextButton;

    // Intervalos para cada nível
    private final int[] levelIntervals = {2000, 2500, 3000, 2000, 2000}; // Adicione mais intervalos conforme necessário

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider_game);
        levelTextView = findViewById(R.id.levelTextView);
        spidersLayout = findViewById(R.id.spidersLayout);
        handler = new Handler();
        random = new Random();

        // Configurar botões para cada nível
        levelButtons = new Button[5];
        for (int i = 0; i < 5; i++) {
            int buttonId = getResources().getIdentifier("levelButton" + (i + 1), "id", getPackageName());
            levelButtons[i] = findViewById(buttonId);

            final int level = i;
            levelButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInstructions();
                }
            });
        }

        // Mantenha apenas a chamada dentro do bloco if
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("level")) {
            int selectedLevel = intent.getIntExtra("level", 0);
            startGame(selectedLevel);
        }

        // Botão para ir para a página inicial
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
                // Ação quando o botão "Exit" é clicado
                finish(); // Fecha a atividade atual
            }
        });
    }

    private void startGame(int level) {
        currentLevel = level;
        levelTextView.setText(getString(R.string.level, currentLevel + 1));

        // Torna todos os botões invisíveis
        for (Button button : levelButtons) {
            button.setVisibility(View.GONE);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSpiders();
            }
        }, getInterval());
    }

    private void showSpiders() {
        int maxSpiderCount = Math.min(spiderCounts[currentLevel], 2);

        spidersLayout.removeAllViews(); // Limpa as aranhas existentes

        int totalDuration = 0;

        for (int i = 0; i < maxSpiderCount; i++) {
            final ImageView spiderImageView = new ImageView(SpiderGameActivity.this);
            spiderImageView.setImageResource(R.drawable.aranha);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            int maxWidth = spidersLayout.getWidth() - spiderImageView.getWidth();
            int maxHeight = spidersLayout.getHeight() - spiderImageView.getHeight();

            if (maxWidth > 0 && maxHeight > 0) {
                layoutParams.topMargin = Math.min(random.nextInt(maxHeight), maxHeight - spiderImageView.getHeight());
                layoutParams.leftMargin = Math.min(random.nextInt(maxWidth), maxWidth - spiderImageView.getWidth());
            }

            spiderImageView.setLayoutParams(layoutParams);

            // Define o índice Z para evitar sobreposição
            spiderImageView.setZ(i);

            spidersLayout.addView(spiderImageView);

            // Adiciona um efeito de aparecer e desaparecer
            spiderImageView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    spiderImageView.setVisibility(View.INVISIBLE);
                }
            }, 4000); // Tempo em milissegundos antes de desaparecer (4 segundos)

            int spiderInterval = random.nextInt(4000) + 1000; // Intervalo entre 1 e 4 segundos
            totalDuration += spiderInterval;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Remove a aranha após o intervalo específico
                    spidersLayout.removeView(spiderImageView);
                }
            }, totalDuration);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentLevel < 4) {
                    startGame(currentLevel + 1);
                } else {
                    showNextButton();  // Mostrar o botão "Next" quando o jogo for concluído
                }
            }
        }, Math.min(getIntervalForLevel(currentLevel), 20000)); // Limite de 20 segundos por nível
    }

    // Defina a lógica para obter o intervalo desejado com base no nível
    private int getIntervalForLevel(int level) {
        return levelIntervals[level];
    }

    private void nextLevel() {
        if (currentLevel < 4) { // Se não estiver no último nível
            currentLevel++;
            startGame(currentLevel);
        } else {
            // Jogo concluído
            levelTextView.setText(getString(R.string.game_completed));
            showNextButton(); // Mostrar o botão "Next"
        }
    }

    private int getInterval() {
        return random.nextInt(3000) + 1000; // Intervalo aleatório entre 1 e 4 segundos
    }

    private void showNextButton() {
        nextButton.setVisibility(View.VISIBLE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultActivity();
            }
        });
    }

    private void showResultActivity() {
        Intent intent = new Intent(SpiderGameActivity.this, ResultActivity.class);
        startActivity(intent);
    }

    private void showInstructions() {
        Intent intent = new Intent(SpiderGameActivity.this, BluetoothService.class);
        startActivity(intent);
    }


}
