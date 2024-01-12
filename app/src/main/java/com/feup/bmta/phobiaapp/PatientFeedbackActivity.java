package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientFeedbackActivity extends AppCompatActivity {

    private EditText answer1EditText, answer2EditText, answer3EditText, answer4EditText;

    private TextView question1Text, question2Text, question3Text, question4Text;
    private Button endButton, sendInformationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_feedback);

        // Inicializa as variáveis com base nos IDs definidos no layout XML
        answer1EditText = findViewById(R.id.answer1EditText);
        answer2EditText = findViewById(R.id.answer2EditText);
        answer3EditText = findViewById(R.id.answer3EditText);
        answer4EditText = findViewById(R.id.answer4EditText);

        // Inicializa as variáveis com base nos IDs definidos no layout XML
        question1Text = findViewById(R.id.question1Text);
        question2Text = findViewById(R.id.question2Text);
        question3Text = findViewById(R.id.question3Text);
        question4Text = findViewById(R.id.question4Text);

        endButton = findViewById(R.id.endButton);
        sendInformationButton = findViewById(R.id.sendInformationButton);

        // Configura a lógica do botão "End"
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.feup.bmta.phobiaapp.PatientFeedbackActivity.this, ResultActivity.class));
            }
        });

        // Configura a lógica do botão "Send to Health Professional"
        sendInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar mensagem usando um Toast
                Toast.makeText(PatientFeedbackActivity.this, "Your data has been sent to a health professional", Toast.LENGTH_SHORT).show();
            }
        });
    }
}