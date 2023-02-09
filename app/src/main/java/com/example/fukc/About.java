package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.fukc.activityAndFragmentClasses.HomeScreen;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        finish();
        super.onBackPressed();
    }
}