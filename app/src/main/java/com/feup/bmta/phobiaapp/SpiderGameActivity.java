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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Random;

import Bio.Library.namespace.BioLib;

public class SpiderGameActivity extends AppCompatActivity {

    private TextView levelTextView;
    private int[] spiderCounts = {1, 2, 3, 4, 5};
    private int currentLevel = 0;
    private Handler handler;
    private Random random;

    private TextView text;

    private BioLib lib = null;

    private Vibrator vibrator;
    private RelativeLayout spidersLayout;
    private Button[] levelButtons;

    private ImageView imageView;

    private CountDownTimer timer;
    private Button nextButton;



    private DBHelper dbHelper;

    // Intervalos para cada nível
    private final int[] levelIntervals = {2000, 2500, 3000, 2000, 2000};

    private boolean gameFinished = false;

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

            levelButtons[i].setTag(String.valueOf(level));

            levelButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGame(level);
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

        // Botão para aceder a conta do utilizador
        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long userId = 1;

                // Usa o DBHelper para obter os detalhes do utilizador
                DBHelper dbHelper = new DBHelper(SpiderGameActivity.this);
                User userAccount = dbHelper.getUserById(userId);

                // Inicia a AccountActivity e passa o ID do utilizador como extra
                Intent intent = new Intent(SpiderGameActivity.this, AccountActivity.class);
                intent.putExtra("USER_ID", userId);

                // Se o utilizador existir, inicia a AccountActivity, caso contrário, mostra um Toast
                if (userAccount != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(SpiderGameActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botão para sair da app
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showExitConfirmationDialog(SpiderGameActivity.this, LoginActivity.class);
            }
        });

        // Inicializa o botão "Next"
        nextButton = findViewById(R.id.nextButton);
        // No método onCreate, após a inicialização do botão "Next"
        nextButton.setVisibility(View.INVISIBLE); // Torna o botão invisível inicialmente

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnect();

                // Ação quando o botão "Next" é clicado
                Intent resultIntent = new Intent(SpiderGameActivity.this, ResultActivity.class);
                startActivity(resultIntent);
            }

            private void disconnect() {
                try {
                    // Reset();

                    if (lib != null) {
                        lib.Disconnect();
                    }


                    if (!gameFinished) {
                        text = findViewById(R.id.disconnect);
                        //text.setText("Bluetooth disconnected");
                    }

                } catch (Exception e) {
                    text.setText("Error during disconnection");
                    e.printStackTrace();
                }
            }
        });
    }



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

        TextView levels =findViewById(R.id.gamelevels);
        levels.setVisibility(View.INVISIBLE);

        TextView phobiaapp =findViewById(R.id.phobia_app);
        phobiaapp.setVisibility(View.INVISIBLE);

        View head =findViewById(R.id.image_head);
        head.setVisibility(View.INVISIBLE);

        ImageView imageBackground = findViewById(R.id.imageViewBackground);
        imageBackground.setVisibility(View.INVISIBLE);

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
                levels.setVisibility(View.VISIBLE);
                phobiaapp.setVisibility(View.VISIBLE);
                head.setVisibility(View.VISIBLE);
                imageBackground.setVisibility(View.VISIBLE);

                hideSpiders();


            }
        };

        timer.start();

    }





    private void showSpiders(int level) {
        if (level >= 1 && level <= spiderImages.length) {
            int resID = spiderImages[level - 1]; // O índice no array é level - 1

            Drawable imagem = getResources().getDrawable(resID);
            imageView.setImageDrawable(imagem);

            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int screenHeight = getResources().getDisplayMetrics().heightPixels;

            float randomX = random.nextFloat() * (screenWidth - imageView.getWidth());
            float randomY = random.nextFloat() * (screenHeight - imageView.getHeight());

            imageView.setX(randomX);
            imageView.setY(randomY);

            float randomScale = random.nextFloat() * (1.5f - 0.5f) + 0.5f; // Entre 0.5 e 1.5

            imageView.setScaleX(randomScale);
            imageView.setScaleY(randomScale);



            vibrateDevice();
        } else {

            Toast.makeText(this, "Invalid dimensions", Toast.LENGTH_SHORT).show();
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
            // Vibra por 500 milissegundos (0,5 segundos)
            vibrator.vibrate(500);
        }
    }


    private void showInstructions() {
        Intent intent = new Intent(SpiderGameActivity.this, BluetoothService.class);
        startActivity(intent);
    }

    private static final int BLUETOOTH_REQUEST_CODE = 100;

    // Método para iniciar a BluetoothService com o nível selecionado
    private void startBluetoothServiceWithLevel(int selectedLevel) {
        Intent intent = new Intent(this, BluetoothService.class);
        intent.putExtra("LEVEL_SELECTED", selectedLevel);
        startActivityForResult(intent, BLUETOOTH_REQUEST_CODE);
    }

    // Método para processar o resultado da BluetoothService
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BLUETOOTH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int levelSelected = data.getIntExtra("LEVEL_SELECTED", 1);
            startBluetoothServiceWithLevel(levelSelected);

        }
    }



}