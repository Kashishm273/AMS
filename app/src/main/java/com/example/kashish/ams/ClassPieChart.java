package com.example.kashish.ams;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;

public class ClassPieChart extends AppCompatActivity {


    PieChart mChart;
    String json_string, parts[];
    double total, present,absent;

    float[] yValues = new float[2];
    String[] xValues = {"PRESENT", "ABSENT"};
    public static final int[] MY_COLORS = {
            Color.rgb(173, 255, 47), Color.rgb(240, 128, 128), Color.rgb(173, 255, 47),
            Color.rgb(255, 192, 203), Color.rgb(215, 60, 55)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_pie_chart);

        Intent intent=getIntent();
        json_string=intent.getStringExtra("json_string");

        Toast.makeText(this,json_string,Toast.LENGTH_SHORT).show();

        parts = json_string.split("-");
        total=Double.parseDouble(parts[0]);
        present=Double.parseDouble(parts[1]);
        absent=total-present;

        yValues[0]=(float)present;
        yValues[1]=(float)absent;

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setHoleRadius(60f);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setHoleColorTransparent(true);
        mChart.setDescription("TOTAL STUDENTS = " + total);
        mChart.setRotationEnabled(true);
        mChart.setCenterText("ATTENDANCE STATUS");

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(ClassPieChart.this,
                        xValues[e.getXIndex()] + " = " + e.getVal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        yVals1.add(new Entry(yValues[0], 0));
        yVals1.add(new Entry(yValues[1], 1));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(xValues[0]);
        xVals.add(xValues[1]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

        PieData data = new PieData(xVals, dataSet);
        mChart.setData(data);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLUE);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : MY_COLORS)
            colors.add(c);

        dataSet.setColors(colors);
        mChart.animateY(2000);
    }

}

