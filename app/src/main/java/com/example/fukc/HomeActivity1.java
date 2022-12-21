package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity1 extends AppCompatActivity {
FloatingActionButton plus;
ImageView options1,options2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        plus=findViewById(R.id.plussign);
        options1=findViewById(R.id.imageView5);
        options2=findViewById(R.id.imageView6);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            options1.setImageResource(R.mipmap.option2);//image of yes or no by clicking plus button
            options2.setImageResource(R.mipmap.option1);// image of measurable by clicking plus button

            }
        });
        options1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CreateYNhabit.class);
                startActivity(intent);
            }
        });
        options2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CreateMeasurableHabit.class);
                startActivity(intent);
            }
        });
    }
}