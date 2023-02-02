package com.example.fukc.activityAndFragmentClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;
    ArcProgress progressBar;
    ArrayList<BarEntry> barArraylist;
    ArrayList<PieEntry> pieArraylist;
    TextView currentStreak,bestStreak,titleText;
    ImageView back;
    List<Integer> colors;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_statistics);

        //Initialization
        String habitName = getIntent().getStringExtra("habitName");
        db = new DBHelper(this);
        progressBar= findViewById(R.id.progress_bar);
        currentStreak= findViewById(R.id.current_streak_stats);
        bestStreak= findViewById(R.id.best_streak_stats);
        barChart = findViewById(R.id.barChart);
        pieChart= findViewById(R.id.pieChart);
        titleText=findViewById(R.id.titletext_statistics);
        back=findViewById(R.id.back_statistic);
        colors = new ArrayList<>();
        getData(habitName);

        //Top Bar
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        titleText.setText("  "+habitName);
        //Habit score
        int habitScore=25;         //default value to show progress bar
        if((db.getDoneCount(habitName))!=0&&(db.getFailCount(habitName))!=0&&(db.getSkipCount(habitName)!=0)){
            habitScore = ((db.getDoneCount(habitName))/(db.getFailCount(habitName)+db.getSkipCount(habitName)))*100;
        }
        progressBar.setMax(100);
        progressBar.setProgress(habitScore);
        progressBar.setSuffixText("");
        progressBar.setStrokeWidth(20);
        progressBar.setTextColor(Color.WHITE);
        progressBar.setUnfinishedStrokeColor(Color.parseColor("#FF3E3E3E"));
        progressBar.setFinishedStrokeColor(Color.parseColor("#d52059"));

        //Streak
        currentStreak.setText(db.getCurrentStreak(habitName) + " Days");
        bestStreak.setText(db.getBestStreak(habitName) + " Days");

        //PieChart
        pieChart.getLegend().setTextColor(ContextCompat.getColor(this, R.color.white));
        pieChart.setDescription(null);
        PieDataSet dataSet=new PieDataSet(pieArraylist,"");
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSet.setColors(colors);
        PieData pieData=new PieData(dataSet);
        pieChart.setData(pieData);
                //--formatting
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setHoleColor(Color.parseColor("#FF1E1E1E")); //BLACKONE
        pieChart.setDrawEntryLabels(false);
        dataSet.setValueTextSize(16f);
        dataSet.setValueTextColor(Color.WHITE);
        pieChart.animateXY(2000,2000);

        //BarChart
        barChart.getLegend().setTextColor(ContextCompat.getColor(this, R.color.white));
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(barArraylist,"barChart");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
                //--formatting
        Legend barLegend = barChart.getLegend();
        barLegend.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.white));
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(12);
        final String[] months = new String[]{"","Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setValueFormatter(formatter);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.invalidate();
        barDataSet.setColors(Color.parseColor("#d52059"));
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);
        barChart.animateY(2000);
        db.close();
    }


    private void getData(String habitName) {

        //barchart data
        barArraylist = new ArrayList<BarEntry>();
        barArraylist.add(new BarEntry(1f, db.getMonthsCount(habitName,"01")));
        barArraylist.add(new BarEntry(2f, db.getMonthsCount(habitName,"02")));
        barArraylist.add(new BarEntry(3f, db.getMonthsCount(habitName,"03")));
        barArraylist.add(new BarEntry(4f, db.getMonthsCount(habitName,"04")));
        barArraylist.add(new BarEntry(5f, db.getMonthsCount(habitName,"05")));
        barArraylist.add(new BarEntry(6f, db.getMonthsCount(habitName,"06")));
        barArraylist.add(new BarEntry(7f, db.getMonthsCount(habitName,"07")));
        barArraylist.add(new BarEntry(8f, db.getMonthsCount(habitName,"08")));
        barArraylist.add(new BarEntry(9f, db.getMonthsCount(habitName,"09")));
        barArraylist.add(new BarEntry(10f, db.getMonthsCount(habitName,"10")));
        barArraylist.add(new BarEntry(11f, db.getMonthsCount(habitName,"11")));
        barArraylist.add(new BarEntry(12f, db.getMonthsCount(habitName,"12")));

        //piechart data
        pieArraylist=new ArrayList<>();
        pieArraylist.add(new PieEntry(db.getDoneCount(habitName),"Done"));
        pieArraylist.add(new PieEntry(db.getFailCount(habitName),"Fail"));
        pieArraylist.add(new PieEntry(db.getSkipCount(habitName),"Skip"));
            //--pieChart colors
            colors.add(Color.parseColor("#35b35f"));
            colors.add(Color.parseColor("#FF0032"));
            colors.add(Color.parseColor("#FFBE0F"));

    }
}


