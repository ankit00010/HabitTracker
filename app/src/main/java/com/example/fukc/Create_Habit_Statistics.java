package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Create_Habit_Statistics extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;
    ArrayList barArraylist,pieArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit_statistics);
         barChart = (BarChart) findViewById(R.id.barChart);
         pieChart=(PieChart) findViewById(R.id.pieChart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArraylist, "Cambo Tutorial");
        PieDataSet dataSet=new PieDataSet(pieArraylist,"Habits Report");


        BarData barData = new BarData(barDataSet);
        PieData pieData=new PieData(dataSet);


        barChart.setData(barData);
        pieChart.setData(pieData);
        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //text color
        barDataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextColor(Color.BLACK);

        //settting text size
        barDataSet.setValueTextSize(16f);
        dataSet.setValueTextSize(16f);

        barChart.getDescription().setEnabled(true);
        pieChart.getDescription().setEnabled(true);

        barChart.animateY(2000);
        pieChart.animateXY(2000,2000);

        barChart.setBackgroundColor(Color.BLACK);
        pieChart.setBackgroundColor(Color.BLACK);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);

    }

    private void getData() {

        //barchart data
        barArraylist = new ArrayList();
        barArraylist.add(new BarEntry(2f, 10));//do the edit according to 12 months
        barArraylist.add(new BarEntry(3f, 20));
        barArraylist.add(new BarEntry(4f, 30));
        barArraylist.add(new BarEntry(5f, 40));
        barArraylist.add(new BarEntry(6f, 50));
        barArraylist.add(new BarEntry(2f, 10));
        barArraylist.add(new BarEntry(3f, 20));
        barArraylist.add(new BarEntry(4f, 30));
        barArraylist.add(new BarEntry(5f, 40));
        barArraylist.add(new BarEntry(6f, 50));
        barArraylist.add(new BarEntry(5f, 40));
        barArraylist.add(new BarEntry(6f, 50));




        //piechart data
        pieArraylist=new ArrayList();
        pieArraylist.add(new PieEntry(32,"Success"));
        pieArraylist.add(new PieEntry(32,"Fail"));
        pieArraylist.add(new PieEntry(32,"Skip"));


    }
}

