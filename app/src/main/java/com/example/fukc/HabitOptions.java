package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HabitOptions extends AppCompatActivity {
    int habittype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_options);
        TextView option1=findViewById(R.id.option1);
        TextView option2= findViewById(R.id.option2);
        TextView back=findViewById(R.id.back_text);
        Intent intent = getIntent();
        String catid = intent.getStringExtra("cid");
        //YES or NO Create Habit option navigation by clicking
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habittype = 0;
                String strhabittype =String.valueOf(habittype);
                Intent intent=new Intent(getApplicationContext(),CreateYNhabit.class);
                intent.putExtra("catid",catid);
                intent.putExtra("habittype",strhabittype);
                startActivity(intent);
            }
        });
        //Numeric Value i.e Measurable Create Habit option navigation by clicking
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habittype = 1;
                String strhabittype =String.valueOf(habittype);
                Intent intent=new Intent(getApplicationContext(),CreateMeasurableHabit.class);
                intent.putExtra("catid",catid);
                intent.putExtra("habittype",strhabittype);
                startActivity(intent);
            }
        });
        //back used to navigate back to category layout
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Category_layout.class);
                startActivity(intent);
            }
        });
    }
}