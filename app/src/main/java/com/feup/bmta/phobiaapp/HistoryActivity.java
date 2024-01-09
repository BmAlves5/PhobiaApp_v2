package com.feup.bmta.phobiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ECGChartView ecgChartView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_history);

            Intent intent = getIntent();
            if (intent != null) {
                ArrayList<Integer> ecgDataValues = intent.getIntegerArrayListExtra("ECG_DATA_VALUES");
          //      ArrayList<Double> sdnnValues = intent.getExtra("SDNN_VALUES");
                // Obter a instância de ECGChartView da classe Statistics
                Statistics statisticsActivity = new Statistics();
                ecgChartView = statisticsActivity.getEcgChartView();

                // Obter os dados SDNN da classe Statistics
                ArrayList<Double> sdnnValues = statisticsActivity.getSdnnValues();

                // Inicializar o ECGChartView com os dados
                ecgChartView.setValuesToPlot(sdnnValues);

                // Adicionar um texto indicando o primeiro teste realizado
                LinearLayout chartContainer = findViewById(R.id.chartContainer);
                chartContainer.addView(ecgChartView);

                TextView textView = new TextView(this);
                textView.setText("Primeiro teste realizado");
                chartContainer.addView(textView);

            }
        }


    }