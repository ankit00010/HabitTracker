package com.example.fukc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


public class CreateMeasurableHabit extends AppCompatActivity {



ImageView backarrow;
TextView ReminderBox,frequency_edittext;
boolean[] selectedDays;
ArrayList<Integer> daysList = new ArrayList<>();
String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_measurable_habit);
        backarrow=findViewById(R.id.backbutton);
        ReminderBox=findViewById(R.id.ReminderButton);
        frequency_edittext=findViewById(R.id.frequency_edittext);
        ReminderBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(CreateMeasurableHabit.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format=new SimpleDateFormat("k:mm:a");
                        String time=format.format(c.getTime());
                        ReminderBox.setText(time);


                    }
                },hours,mins,false);
                timePickerDialog.show();

            }
        });
        //backarrow to navigate to habit options back
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HabitOptions.class);
                startActivity(intent);
            }
        });
        //code for frequency
        frequency_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeasurableHabit.this);
                builder.setTitle("Select Days of the weeek");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(daysArray, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            daysList.add(i);
                            Collections.sort(daysList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            daysList.remove(Integer.valueOf(i));
                        }
                    }
                });
                //code for frequency
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < daysList.size(); j++) {
                            // concat array value
                            stringBuilder.append(daysArray[daysList.get(j)]);
                            // check condition
                            if (j != daysList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        frequency_edittext.setText(stringBuilder.toString());
                    }
                });
                ///code for frequency
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // remove all selection
                            selectedDays[j] = false;
                            // clear language list
                            daysList.clear();
                            // clear text view value
                            frequency_edittext.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });//end of frequency


    }

}