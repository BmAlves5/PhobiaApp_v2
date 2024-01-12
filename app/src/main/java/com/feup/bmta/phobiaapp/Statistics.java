package com.feup.bmta.phobiaapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {



    private DBHelper dbHelper;

    private ECGChartView ecgChartView;
    private ArrayList<Double> sdnnValues;

    // Open the database connection    private SQLiteDatabase mDatabase;
    private void openDatabase() {
        if (dbHelper == null) {
            dbHelper = new DBHelper(this);
            dbHelper.openDatabase();
        }
    }

    private ArrayList<Double> calculateSDNNForEach(ArrayList<Integer> ecgDataValues) {
        ArrayList<Double> sdnnValues = new ArrayList<>();

        for (int rrInterval : ecgDataValues) {
            ArrayList<Integer> singleRR = new ArrayList<>();
            singleRR.add(rrInterval);
            double sdnn = calculateSDNN(singleRR);
            sdnnValues.add(sdnn);

        }

        return sdnnValues;
    }

        private double calculateSDNN(ArrayList<Integer> rrIntervals) {
            double meanRR = calculateAverage(rrIntervals);
            double sumSquaredDifferences = 0;

            for (int rrInterval : rrIntervals) {
                double difference = rrInterval - meanRR;
                sumSquaredDifferences += Math.pow(difference, 2);
            }

            double variance = sumSquaredDifferences / rrIntervals.size();
            return Math.sqrt(variance);
        }



    private static double calculateAverage (ArrayList<Integer> ecgDataValues){
        int sum = 0;
        for (int rrInterval : ecgDataValues){
            sum += rrInterval;
        }
        return  (double) sum/ ecgDataValues.size();
    }



    // Método para obter a instância de ECGChartView
    public ECGChartView getEcgChartView() {
        return ecgChartView;
    }

    // Método para obter os dados SDNN
    public ArrayList<Double> getSdnnValues() {

        return sdnnValues;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        openDatabase();

        ECGChartView ecgChartView = findViewById(R.id.ecgChartView);

        // Obtem os valores da coluna ecg_data da base de dados
        ArrayList<Integer> ecgDataValues = getEcgDataValues();

        // Calcula o SDNN para cada conjunto de valores de intervalo RR
        ArrayList<Double> sdnnValues = calculateSDNNForEach(ecgDataValues);

        // Define os valores SDNN no gráfico ECGChartView
        ecgChartView.setValuesToPlot(sdnnValues);

        // Simula dados de um paciente saudável e calmo (substitua pelos seus próprios dados)
        ArrayList<Integer> healthyPatientData = generateHealthyPatientData(ecgDataValues.size());
        ecgChartView.setHealthyPatientData(healthyPatientData);

        // Calcula a média dos valores do intervalo RR
        double averageRR = calculateAverage(ecgDataValues);


        // Calcula o SDNN para cada conjunto de valores de intervalo RR
        ArrayList<Double> sdnnValuesForLast20 = new ArrayList<>();
        for (int i = ecgDataValues.size() - 20; i < ecgDataValues.size() - 1; i++) {
            ArrayList<Integer> rrIntervals = new ArrayList<>();
            rrIntervals.add(ecgDataValues.get(i));
            rrIntervals.add(ecgDataValues.get(i + 1));
            double sdnn = calculateSDNN(rrIntervals);
            sdnnValuesForLast20.add(sdnn);
        }

        // Define os valores SDNN no gráfico ECGChartView
        ecgChartView.setValuesToPlot(sdnnValuesForLast20);


    }

    private ArrayList<Integer> generateHealthyPatientData(int size) {
        // Valores teóricos para um paciente saudável e calmo
        int[] theoreticalValues = {800, 810, 820, 830, 840, 850, 860, 870, 880, 890,
                900, 910, 920, 930, 940, 950, 960, 970, 980, 990};

        ArrayList<Integer> data = new ArrayList<>();

        // Repete os valores teóricos para preencher o array até o tamanho desejado
        for (int i = 0; i < size; i++) {
            data.add(theoreticalValues[i % theoreticalValues.length]);
        }

        return data;
    }


    private ArrayList<Integer> getEcgDataValues() {
        ArrayList<Integer> ecgDataValues = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        try {
            // Verifica se a tabela existe
            if (isTableExists(db, "ecg_data")) {
                Cursor cursor = db.rawQuery("SELECT ecg_data FROM ecg_data ORDER BY _id DESC LIMIT 20", null);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int ecgDataValue = cursor.getInt(cursor.getColumnIndex("ecg_data"));
                        ecgDataValues.add(ecgDataValue);
                    } while (cursor.moveToNext());
                }

                if (cursor != null) {
                    cursor.close();
                }
            } else {
                // Lida com a tabela inexistente
                Toast.makeText(this, "A tabela ECGData não existe", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Lida com exceções, se necessário
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return ecgDataValues;
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


}
