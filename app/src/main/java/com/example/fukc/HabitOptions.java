package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class HabitOptions extends AppCompatActivity {
    int habittype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_options);
        TextView option1=findViewById(R.id.option1);
        TextView option2= findViewById(R.id.option2);
        TextView option3= findViewById(R.id.option3);
        TextView back=findViewById(R.id.options_back);
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
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habittype = 1;
                String strhabittype =String.valueOf(habittype);
                Intent intent=new Intent(getApplicationContext(),CreateChecklistHabits.class);
                intent.putExtra("catid",catid);
                intent.putExtra("habittype",strhabittype);
                startActivity(intent);
            }
        });
        //back used to navigate back to category layout
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(HabitOptions.this);
        alertDialog.setTitle("Discard");
        alertDialog.setMessage("Discard the new habit ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}