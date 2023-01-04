package com.example.fukc;

import static java.sql.Types.NULL;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class CreateChecklistHabits extends AppCompatActivity {
    ImageView color;
    LinearLayout subhabitlist;
    EditText habitname,habitque,subhabit1;
    TextView reminderbutton,frequencybutton,addsubtask,savehabit,backarrow,create;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    List<EditText> editTextList = new ArrayList<>();
    List<String> inputList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    int numberOfEditTexts=0;
    LinearLayout parentLayout;
    MyAdapterSHabit adapter;
    RecyclerView recyclerView;
    DBHelper db;
    int habittype = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_checklist_habits);
        habitname = findViewById(R.id.name_edittext);
        subhabit1 = findViewById(R.id.checklist_edittext);
        habitque = findViewById(R.id.question_edittext);
        savehabit= findViewById(R.id.create_text);
        backarrow =findViewById(R.id.back_text);
        reminderbutton = findViewById(R.id.reminder_textview);
        frequencybutton = findViewById(R.id.frequency_textview);
        addsubtask = findViewById(R.id.additem_text);
        create = findViewById(R.id.create_text);
        subhabitlist = findViewById(R.id.linearLayout);
        db = new DBHelper(this);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HabitOptions.class);
                startActivity(intent);
                finish();

            }
        });
        reminderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateChecklistHabits.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        reminderbutton.setText(time);


                    }
                }, hours, mins, false);
                timePickerDialog.show();

            }
        });
        frequencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateChecklistHabits.this);
                builder.setTitle("Select Days of the week");
                builder.setCancelable(false);
                daysList.clear();

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
                        frequencybutton.setText(stringBuilder.toString());
                    }
                });

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
                        daysList.clear();
                        frequencybutton.setText("");
                    }
                });
                // show dialog
                builder.show();
            }
        });
        addsubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfEditTexts =numberOfEditTexts+1;
                adapter = new MyAdapterSHabit(getApplicationContext(),numberOfEditTexts);
                recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setVisibility(View.VISIBLE);

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sHabitId;
                String value="";
                String hname= habitname.getText().toString();
                String frequency =frequencybutton.getText().toString();
                String reminder = reminderbutton.getText().toString();
                String hque= habitque.getText().toString();
                String subH1=subhabit1.getText().toString();
                inputList.add(subH1);
                if(numberOfEditTexts>0) {
                    for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                        MyAdapterSHabit.MyViewHolder viewHolder = (MyAdapterSHabit.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (viewHolder != null) {
                            String text = viewHolder.getText();
                            inputList.add(text);
                        }
                    }
                }
                String delimiter = ",";
                String result = String.join(delimiter, inputList);
                if(db.insertDataSubhabits(result)){
                    Cursor cursor = db.getdata();
                    if (cursor.moveToLast()) {
                        value = cursor.getString(0);
                    }
                }
                sHabitId=Integer.valueOf(value);
                db.insertDatahabit(hname,"no color",hque,frequency,reminder,habittype,NULL,sHabitId);
                Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
