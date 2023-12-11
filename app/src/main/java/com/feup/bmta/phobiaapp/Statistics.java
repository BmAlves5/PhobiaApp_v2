package com.feup.bmta.phobiaapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        dbHelper = new DBHelper(this);

        // Obter o contêiner de dados
        LinearLayout dataContainer = findViewById(R.id.dataContainer);

        // Chame o método para obter os valores da coluna ecg_data da base de dados
        ArrayList<Integer> ecgDataValues = getEcgDataValues();

        // Preencher dinamicamente as TextViews com os valores de ecg_data
        for (int i = 0; i < ecgDataValues.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText("Valor ECG " + (i + 1) + ": " + ecgDataValues.get(i));
            dataContainer.addView(textView);
        }
    }

    private ArrayList<Integer> getEcgDataValues() {
        ArrayList<Integer> ecgDataValues = new ArrayList<>();

        // Abra o banco de dados e consulte os dados da coluna ecg_data
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ecg_data FROM ECGData.db", null);

        // Iterar através do cursor e adicionar os valores de ecg_data à lista
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ecgDataValue = cursor.getInt(cursor.getColumnIndex("ecg_data"));
                ecgDataValues.add(ecgDataValue);
            } while (cursor.moveToNext());

            // Fechar o cursor após a leitura dos dados
            cursor.close();
        }

        // Fechar o banco de dados
        db.close();

        return ecgDataValues;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Statistics.class);
    }
}
