package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PatientFeedbackActivity extends AppCompatActivity {

    private EditText question1EditText, question2EditText, question3EditText, question4EditText;
    private Button endButton, sendInformationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_feedback);

        // Inicialize as variáveis com base nos IDs definidos no layout XML
        question1EditText = findViewById(R.id.question1EditText);
        question2EditText = findViewById(R.id.question2EditText);
        question3EditText = findViewById(R.id.question3EditText);
        question4EditText = findViewById(R.id.question4EditText);
        endButton = findViewById(R.id.endButton);
        sendInformationButton = findViewById(R.id.sendInformationButton);

        // Configurar a lógica do botão "End"
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.feup.bmta.phobiaapp.PatientFeedbackActivity.this, ResultActivity.class));
            }
        });

        // Configurar a lógica do botão "Send to Health Professional"
        sendInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.feup.bmta.phobiaapp.PatientFeedbackActivity.this, ResultActivity.class));
            }
        });
    }
}