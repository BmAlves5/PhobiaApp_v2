package com.feup.bmta.phobiaapp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class ECGChartView extends View {

    private ArrayList<Integer> ecgDataValues;
    private Paint paintLine;

    private ArrayList<Double> sdnnValues;

    public ECGChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintLine = new Paint();
        paintLine.setColor(Color.parseColor("#11A4AC"));
        paintLine.setStrokeWidth(3f);
        paintLine.setAntiAlias(true);
        paintLine.setStyle(Paint.Style.STROKE);
    }

    public void setEcgDataValues(ArrayList<Integer> ecgDataValues) {
        this.ecgDataValues = ecgDataValues;
        invalidate(); // Redesenha a view quando os dados são atualizados
    }

    public void setValuesToPlot (ArrayList<Double> sdnnValues){
        this.sdnnValues = sdnnValues;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (sdnnValues == null || sdnnValues.isEmpty()) {
            return;
        }

        int paddingLeft = 100;
        int paddingRight = 50;
        int paddingTop = 50;
        int paddingBottom = 100;

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        Paint paintAxis = new Paint();
        paintAxis.setColor(Color.BLACK);
        paintAxis.setStrokeWidth(2f);

        canvas.drawLine(paddingLeft, getHeight() - paddingBottom, getWidth() - paddingRight, getHeight() - paddingBottom, paintAxis);

        if (Collections.min(sdnnValues) >= 0) {
            paintAxis.setColor(Color.DKGRAY);
            canvas.drawLine(paddingLeft, paddingTop, paddingLeft, getHeight() - paddingBottom, paintAxis);
        }

        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(20);

        paintText.setColor(Color.BLACK);
        paintText.setTextSize(20);

        float minY = Collections.min(sdnnValues).floatValue();
        float maxY = Collections.max(sdnnValues).floatValue();

        float yStep = (maxY - minY) / 10; // Divide o eixo Y em 10 partes

        for (int i = 0; i <= 10; i++) {
            float yPos = getHeight() - paddingBottom - (i * height / 10);
            canvas.drawText(String.valueOf((int) (minY + (i * yStep))), paddingLeft - 40, yPos, paintText);
        }

        float xStep = sdnnValues.size() > 1 ? width / (sdnnValues.size() - 1) : width;

        for (int i = 0; i < sdnnValues.size(); i++) {
            float xPos = paddingLeft + (xStep * i);
            canvas.drawText(String.valueOf(i), xPos, getHeight() - paddingBottom + 40, paintText);
        }

        paintText.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("Time", getWidth() / 2, getHeight() - paddingBottom + 80, paintText);
        canvas.drawText("HRV", paddingLeft - 70, paddingTop + height / 2, paintText);

        Path path = new Path();
        for (int i = 0; i < sdnnValues.size(); i++) {
            float x = paddingLeft + (xStep * i);
            float y = (float) (getHeight() - paddingBottom - (sdnnValues.get(i).floatValue() - minY) * (height / (maxY - minY)));

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        canvas.drawPath(path, paintLine);
    }

}
