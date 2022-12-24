package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class HabitOptions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_options);
       ImageView option1=(ImageView)findViewById(R.id.option1);
       ImageView option2= (ImageView)findViewById(R.id.option2);
        TextView back=(TextView) findViewById(R.id.back_text);

        //YES or NO Create Habit option navigation by clicking
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CreateYNhabit.class);
                startActivity(intent);
            }
        });
        //Numeric Value i.e Measurable Create Habit option navigation by clicking
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CreateMeasurableHabit.class);
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