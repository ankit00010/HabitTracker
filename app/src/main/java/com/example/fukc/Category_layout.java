package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Category_layout extends AppCompatActivity {
TextView cancel_txt,next_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_layout);
        cancel_txt=findViewById(R.id.cancel_text);
        next_txt=findViewById(R.id.next_text);
        cancel_txt.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),HomeActivity1.class);
            startActivity(intent);
        });
        next_txt.setOnClickListener(v -> {
            Intent intent=new Intent(getApplicationContext(),HabitOptions.class);
            startActivity(intent);
        });
    }
}