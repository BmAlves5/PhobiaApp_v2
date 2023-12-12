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

import org.w3c.dom.Text;

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



    private DBHelper dbHelper;

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


            // Adicione as tags aqui
            levelButtons[i].setTag(String.valueOf(level));

            levelButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInstructions();
                }
            });
        }

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            int level = intent.getIntExtra("level", 1);
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

        // Inicialize o botão "Next"
        nextButton = findViewById(R.id.nextButton);
        // No método onCreate, após a inicialização do botão "Next"
        nextButton.setVisibility(View.INVISIBLE); // Torna o botão invisível inicialmente

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ação quando o botão "Next" é clicado
                Intent resultIntent = new Intent(SpiderGameActivity.this, ResultActivity.class);
                startActivity(resultIntent);
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
            //button.setEnabled(false);
            button.setVisibility(View.INVISIBLE);
        }

        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setVisibility(View.INVISIBLE);

        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setVisibility(View.INVISIBLE);

        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setVisibility(View.INVISIBLE);

        TextView account =findViewById(R.id.account);
        account.setVisibility(View.INVISIBLE);

        TextView home =findViewById(R.id.home);
        home.setVisibility(View.INVISIBLE);

        TextView exit =findViewById(R.id.exit);
        exit.setVisibility(View.INVISIBLE);

        ImageView baseImageView = findViewById(R.id.base);
        baseImageView.setVisibility(View.INVISIBLE);

        currentLevel = level;

        timer = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (random.nextBoolean()) {
                    showSpiders(currentLevel);
                } else {
                    hideSpiders();
                }
            }

            @Override
            public void onFinish() {
                Toast.makeText(SpiderGameActivity.this, "Time's up! Game over.", Toast.LENGTH_SHORT).show();

                // Mostrar a ImageView com ID "base" após o jogo terminar
                baseImageView.setVisibility(View.VISIBLE);

                // Tornar o botão "Next" visível quando o jogo terminar
                nextButton.setVisibility(View.VISIBLE);

                // Mostrar os botões e o TextView após o jogo terminar


                account.setVisibility(View.VISIBLE);
                home.setVisibility(View.VISIBLE);
                exit.setVisibility(View.VISIBLE);
                accountButton.setVisibility(View.VISIBLE);
                homeButton.setVisibility(View.VISIBLE);
                exitButton.setVisibility(View.VISIBLE);


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
            Toast.makeText(this, "Invalid level: " + level, Toast.LENGTH_SHORT).show();
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


    private void showInstructions() {
        Intent intent = new Intent(SpiderGameActivity.this, BluetoothService.class);
        startActivity(intent);
    }

}