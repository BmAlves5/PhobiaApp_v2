package com.feup.bmta.phobiaapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ECGChartAdapter extends RecyclerView.Adapter<ECGChartAdapter.ChartViewHolder> {

    private ArrayList<ArrayList<Integer>> ecgDataValues;

    public ECGChartAdapter(ArrayList<ArrayList<Integer>> ecgDataValues) {
        this.ecgDataValues = ecgDataValues;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_statistics, parent, false);
        return new ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        ArrayList<Integer> dataValues = ecgDataValues.get(position);
        holder.ecgChartView.setEcgDataValues(dataValues);
    }

    @Override
    public int getItemCount() {
        return ecgDataValues.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder {

        ECGChartView ecgChartView;

        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            ecgChartView = itemView.findViewById(R.id.ecgChartView);
        }
    }
}