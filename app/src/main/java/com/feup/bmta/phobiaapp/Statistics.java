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
            dbHelper = new DBHelper(this); // Certifique-se de que 'this' seja uma referência válida ao contexto
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


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

       openDatabase();



               // Obter o contêiner de dados
        LinearLayout dataContainer = findViewById(R.id.dataContainer);

        // Chame o método para obter os valores da coluna ecg_data da base de dados
        ArrayList<Integer> ecgDataValues = getEcgDataValues();

        ArrayList<Integer> sdnnvalues = calculateSDNNForAll(ecgDataValues);

        ECGChartView ecgChartView = findViewById(R.id.ecgChartView);
        ecgChartView.setSDNNValues(sdnnValues);

        //ecgChartView.setEcgDataValues(ecgDataValues);

        // Preencher dinamicamente as TextViews com os valores de ecg_data
        for (int i = 0; i < ecgDataValues.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText("Valor ECG " + (i + 1) + ": " + ecgDataValues.get(i));
            dataContainer.addView(textView);
        }
    }*/

    // Método para obter a instância de ECGChartView
    public ECGChartView getEcgChartView() {
        return ecgChartView;
    }

    // Método para obter os dados SDNN
    public ArrayList<Double> getSdnnValues() {
        // Suponha que sdnnValues seja uma variável de classe
        return sdnnValues;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        openDatabase();

        ECGChartView ecgChartView = findViewById(R.id.ecgChartView);

        // Obter os valores da coluna ecg_data da base de dados
        ArrayList<Integer> ecgDataValues = getEcgDataValues();

        // Calcular a média dos valores do intervalo RR
        double averageRR = calculateAverage(ecgDataValues);

       // Exibir o valor médio em uma TextView
        TextView averageTextView = findViewById(R.id.averageTextView);
        averageTextView.setText("Average RR values: " + averageRR);

        // Calcular o SDNN para cada conjunto de valores de intervalo RR
        ArrayList<Double> sdnnValues = new ArrayList<>();
        for (int i = ecgDataValues.size() - 20; i < ecgDataValues.size() - 1; i++) {
            ArrayList<Integer> rrIntervals = new ArrayList<>();
            rrIntervals.add(ecgDataValues.get(i));
            rrIntervals.add(ecgDataValues.get(i + 1));
            double sdnn = calculateSDNN(rrIntervals);
            sdnnValues.add(sdnn);
        }

        // Definir os valores SDNN no gráfico ECGChartView
        ecgChartView.setValuesToPlot(sdnnValues);

        /*Intent intent = new Intent(Statistics.this, HistoryActivity.class);
        intent.putIntegerArrayListExtra("ECG_DATA_VALUES", ecgDataValues);

        // Certifique-se de que sdnnValues é uma ArrayList<Double>
        // Se for outra coisa, como ArrayList<Integer>, pode causar erro ao tentar passar DoubleArrayListExtra
        intent.putExtra("SDNN_VALUES", sdnnValues);
        startActivity(intent);*/
    }

    private ArrayList<Integer> getEcgDataValues() {
        ArrayList<Integer> ecgDataValues = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        try {
            // Verificar se a tabela existe
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
                // Lidar com a tabela inexistente
                Toast.makeText(this, "A tabela ECGData não existe", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // Lide com exceções, se necessário
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
