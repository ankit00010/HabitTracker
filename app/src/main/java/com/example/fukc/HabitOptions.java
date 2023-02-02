package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
        option1.setOnClickListener(v -> {
            habittype = 0;
            String strhabittype =String.valueOf(habittype);
            Intent intent1 =new Intent(getApplicationContext(),CreateYNhabit.class);
            intent1.putExtra("catid",catid);
            intent1.putExtra("habittype",strhabittype);
            startActivity(intent1);
        });
        //Numeric Value i.e Measurable Create Habit option navigation by clicking
        option2.setOnClickListener(v -> {
            habittype = 1;
            String strhabittype =String.valueOf(habittype);
            Intent intent12 =new Intent(getApplicationContext(),CreateMeasurableHabit.class);
            intent12.putExtra("catid",catid);
            intent12.putExtra("habittype",strhabittype);
            startActivity(intent12);
        });
        option3.setOnClickListener(v -> {
            habittype = 1;
            String strhabittype =String.valueOf(habittype);
            Intent intent14 =new Intent(getApplicationContext(),CreateChecklistHabits.class);
            intent14.putExtra("catid",catid);
            intent14.putExtra("habittype",strhabittype);
            startActivity(intent14);
        });
        //back used to navigate back to category layout
        back.setOnClickListener(v -> {
            Intent intent13 =new Intent(getApplicationContext(),HomeActivity1.class);
            startActivity(intent13);
        });
    }
    public void onBackPressed(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(HabitOptions.this);
        alertDialog.setTitle("Discard");
        alertDialog.setMessage("Discard the new habit ?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }
}