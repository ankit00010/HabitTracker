package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        TextView option1=(TextView) findViewById(R.id.option1);
        TextView option2= (TextView) findViewById(R.id.option2);
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