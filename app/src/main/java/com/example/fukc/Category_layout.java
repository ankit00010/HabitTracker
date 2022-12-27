package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category_layout extends AppCompatActivity {
TextView cancel_txt,next_txt;
CardView createcat;
DBHelper db;
RecyclerView recyclerView;
ArrayList<String> catname;
MyAdapterCat adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_layout);
        cancel_txt = findViewById(R.id.cancel_text);
        next_txt = findViewById(R.id.next_text);
        createcat = findViewById(R.id.createcat);
        db = new DBHelper(this);
        catname = new ArrayList<>();
        View view = getLayoutInflater().inflate(R.layout.category_item, null);
        ImageView imageList=view.findViewById(R.id.deleteicon);
        imageList.setImageResource(R.drawable.deleteicon);;
        recyclerView = findViewById(R.id.recyclerv);
        adapter = new MyAdapterCat(this, catname);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //add data to adapter
        Cursor cursor = db.getdata();
        while (cursor.moveToNext()) {
            catname.add(cursor.getString(1));
        }


        cancel_txt.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
            startActivity(intent);
        });
        next_txt.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HabitOptions.class);
            startActivity(intent);
        });
        createcat.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Create Category");

            LayoutInflater inflater = getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.create_category, null))

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText editTextName = (EditText) ((AlertDialog) dialog).findViewById(R.id.edit_text_name);
                            String name = editTextName.getText().toString();
                            db.insertDataCategory(name);
                            Intent intent = new Intent(getApplicationContext(), Category_layout.class);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        });




    }
}