package com.example.fukc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity1 extends AppCompatActivity {
    FloatingActionButton plus;
    SharedPreferences sp;
    DBHelper db;
    RecyclerView recyclerView;
    ArrayList<String> habitname;
    MyAdapterHabit adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        db = new DBHelper(this);
        habitname = new ArrayList<>();
        View view = getLayoutInflater().inflate(R.layout.category_item, null);
        ImageView imageList=view.findViewById(R.id.deleteicon);
        imageList.setImageResource(R.drawable.deleteicon);;
        recyclerView = findViewById(R.id.recyclerViewhabit);
        adapter = new MyAdapterHabit(this, habitname);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add data to adapter
        Cursor cursor = db.getdataHabit();
        while (cursor.moveToNext()) {
            habitname.add(cursor.getString(1));
        }
        plus=findViewById(R.id.plussign);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HabitOptions.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                sp = getSharedPreferences("login",MODE_PRIVATE);
                sp.edit().putBoolean("logged",false).apply();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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