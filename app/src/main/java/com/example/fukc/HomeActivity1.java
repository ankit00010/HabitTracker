package com.example.fukc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;


    }
}