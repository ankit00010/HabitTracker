package com.example.fukc;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


public class CreateYNhabit extends AppCompatActivity {
    ImageView  color;
    EditText habitname,habitque;
    TextView reminderbutton,frequencybutton,savehabit,yes_backtext;
    DBHelper db;
    String colorvalue;
    boolean[] selectedDays;
    String hname,hque,catid,habittype;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    DialogfragmentColorYN dialogFragment = new DialogfragmentColorYN();
    public void selectclr(ImageView ac) {
        if(ac==dialogFragment.cl1){
            color.setImageResource(R.drawable.cl1);
        }else if(ac==dialogFragment.cl2){
            color.setImageResource(R.drawable.cl2);
        }else if(ac==dialogFragment.cl3){
            color.setImageResource(R.drawable.cl3);
        }else if(ac==dialogFragment.cl4){
            color.setImageResource(R.drawable.cl4);
        }else if(ac==dialogFragment.cl5){
            color.setImageResource(R.drawable.cl5);
        }else if(ac==dialogFragment.cl6){
            color.setImageResource(R.drawable.cl6);
        }else if(ac==dialogFragment.cl7){
            color.setImageResource(R.drawable.cl7);
        }else if(ac==dialogFragment.cl8){
            color.setImageResource(R.drawable.cl8);
        }else if(ac==dialogFragment.cl9){
            color.setImageResource(R.drawable.cl9);
        }else if(ac==dialogFragment.cl10){
            color.setImageResource(R.drawable.cl10);
        }else if(ac==dialogFragment.cl11){
            color.setImageResource(R.drawable.cl11);
        }else if(ac==dialogFragment.cl12){
            color.setImageResource(R.drawable.cl12);
        }else if(ac==dialogFragment.cl13){
            color.setImageResource(R.drawable.cl13);
        }else if(ac==dialogFragment.cl14){
            color.setImageResource(R.drawable.cl14);
        }else if(ac==dialogFragment.cl15){
            color.setImageResource(R.drawable.cl15);
        }else if(ac==dialogFragment.cl16){
            color.setImageResource(R.drawable.cl16);
        }else if(ac==dialogFragment.cl17){
            color.setImageResource(R.drawable.cl17);
        }else if(ac==dialogFragment.cl18){
            color.setImageResource(R.drawable.cl18);
        }else if(ac==dialogFragment.cl19){
            color.setImageResource(R.drawable.cl19);
        }else if(ac==dialogFragment.cl20){
            color.setImageResource(R.drawable.cl20);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ynhabit);
        habitname = (EditText) findViewById(R.id.yes_name_edit_text);
        habitque = findViewById(R.id.yes_question_edit_text);
        savehabit= (TextView) findViewById(R.id.yes_create_text);
        yes_backtext =findViewById(R.id.yes_back_text);
        reminderbutton = (TextView) findViewById(R.id.yes_reminder_textview);
        frequencybutton = (TextView) findViewById(R.id.yes_frequency_textview);
        color=(ImageView) findViewById(R.id.yes_color_button);
        selectedDays = new boolean[daysArray.length];
        Intent intent = getIntent();
        catid = intent.getStringExtra("catid");
        habittype =intent.getStringExtra("habittype");
        db = new DBHelper(this);
        yes_backtext.setOnClickListener(new View.OnClickListener() {
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateYNhabit.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateYNhabit.this);
                builder.setTitle("Select Days of the week");
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
                        // use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // remove all selection
                            selectedDays[j] = false;
                            // clear language list
                            daysList.clear();
                            // clear text view value
                            reminderbutton.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
        savehabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(habitname.getText())) {
                    Toast.makeText(CreateYNhabit.this, "Enter a name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(frequencybutton.getText())) {
                    Toast.makeText(CreateYNhabit.this, "Select atleast one frequency", Toast.LENGTH_SHORT).show();
                } else {
                    colorvalue = dialogFragment.colorval;
                    int fcatid = Integer.valueOf(1);
                    int fhabittype = Integer.valueOf(habittype);
                    String frequency = frequencybutton.getText().toString();
                    String reminder = reminderbutton.getText().toString();
                    hname = habitname.getText().toString();
                    hque = habitque.getText().toString();
                    db.insertDatahabit(hname, colorvalue, hque, frequency, reminder, fhabittype, NULL, fcatid);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                dialogFragment.show(fragmentManager, "dialog");

            }
        });

    }
    public void onBackPressed(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(CreateYNhabit.this);
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