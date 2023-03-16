package com.example.fukc.activityAndFragmentClasses;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fukc.otherClasses.AlaramReciever;
import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;


public class CreateYNhabit extends AppCompatActivity {
    ImageView  color;
    EditText habitname,habitque;
    TextView reminderbutton,frequencybutton,savehabit,yes_backtext;
    DBHelper db;
    String colorvalue;
    boolean[] selectedDays;
    String hname,hque,hnameEdit;
    ArrayList<Integer> daysList = new ArrayList<>();
    int selectedHour,selectedMinute;
    Calendar calendar;
    String timer="";

    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    DialogfragmentColorYN dialogFragment = new DialogfragmentColorYN();
    SharedPreferences sp;
    int userid;
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
        setContentView(R.layout.create_ynhabit);
        habitname = (EditText) findViewById(R.id.yes_name_edit_text);
        habitque = findViewById(R.id.yes_question_edit_text);
        hnameEdit="";
        if (!Objects.equals(getIntent().getStringExtra("habitEdit"), ""))
        {
//            Log.d("checkitbruh cuk",hnameEdit);
            hnameEdit = getIntent().getStringExtra("habitEdit");

        }


        savehabit= (TextView) findViewById(R.id.yes_create_text);
        yes_backtext =findViewById(R.id.yes_back_text);
        reminderbutton = (TextView) findViewById(R.id.yes_reminder_textview);

        frequencybutton = (TextView) findViewById(R.id.yes_frequency_textview);
        color=(ImageView) findViewById(R.id.yes_color_button);
        selectedDays = new boolean[daysArray.length];
        calendar=Calendar.getInstance();
        db = new DBHelper(this);
        sp= getSharedPreferences("login", MODE_PRIVATE);
        userid = sp.getInt("userId",0);
        if (hnameEdit != null && hnameEdit.length() > 0)
        {

                Log.d("CHeckthisone","The reminder exception handled ??????????????");
                habitname.setText(hnameEdit);
                frequencybutton.setText((db.getHabitFrequency(hnameEdit)));
                reminderbutton.setText(db.getReminder(hnameEdit));
                habitque.setText(db.getHabitque(hnameEdit));
                savehabit.setText("Update");

        }


        yes_backtext.setOnClickListener(view -> {
            Intent intent;
            if (savehabit.getText().equals("Update")) {
                intent = new Intent(getApplicationContext(), HomeScreen.class);
            } else{
                intent = new Intent(getApplicationContext(), HabitOptions.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        Log.d("reminder",reminderbutton.getText().toString());
        reminderbutton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int mins = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(CreateYNhabit.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, (timePicker, hourOfDay, minute) -> {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                timer = format.format(c.getTime());
                reminderbutton.setText(timer);
                selectedHour=hourOfDay;
                selectedMinute=minute;
            }, hours, mins, false);
            timePickerDialog.show();
        });


        frequencybutton.setOnClickListener(view -> {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateYNhabit.this);
            builder.setTitle("Select Days of the week");
            builder.setCancelable(false);
            daysList.clear();

            builder.setMultiChoiceItems(daysArray, selectedDays, (dialogInterface, i, b) -> {
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
            });

            builder.setPositiveButton("OK", (dialogInterface, i) -> {
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
            });

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                // dismiss dialog
                dialogInterface.dismiss();
            });
            builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
                // use for loop
                for (int j = 0; j < selectedDays.length; j++) {
                    // remove all selection
                    selectedDays[j] = false;
                    // clear language list
                    daysList.clear();
                    // clear text view value
                    frequencybutton.setText(timer);

                }
            });
            // show dialog
            builder.show();
        });

        savehabit.setOnClickListener(view -> {


            if (TextUtils.isEmpty(habitname.getText())) {
                Toast.makeText(CreateYNhabit.this, "Enter a name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(frequencybutton.getText())) {
                Toast.makeText(CreateYNhabit.this, "Select atleast one frequency", Toast.LENGTH_SHORT).show();
            }

            else if (!timer.isEmpty()) {
                if (TextUtils.isEmpty(habitque.getText())) {
                    Toast.makeText(this, "Please enter the question ", Toast.LENGTH_SHORT).show();
                } else {
                    setAlarm();
                    colorvalue = dialogFragment.colorval;
                    final int habittype = 0;
                    String frequency = frequencybutton.getText().toString();
                    hname = habitname.getText().toString();
                    hque = habitque.getText().toString();
                    if (savehabit.getText().equals("Update")) {

                        db.updateEdit(hnameEdit, hname, frequency, timer, colorvalue, hque, habittype, NULL);

                    } else {

                        db.insertDatahabit(hname, colorvalue, hque, frequency, timer, habittype, NULL,userid);

                    }
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            } else {


                colorvalue = dialogFragment.colorval;
                final int habittype = 0;
                String frequency = frequencybutton.getText().toString();
                hname = habitname.getText().toString();
                hque = habitque.getText().toString();
                if (savehabit.getText().equals("Update")) {

                    db.updateEdit(hnameEdit, hname, frequency, timer, colorvalue, hque, habittype, NULL);

                } else {

                    db.insertDatahabit(hname, colorvalue, hque, frequency, timer, habittype, NULL,userid);

                }
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });







        color.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            dialogFragment.show(fragmentManager, "dialog");

        });
        db.close();
    }
    //Method used to set the alaram after the habit is created
    private void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        // Check if the current day is in the selected days list
        if (daysList.contains((currentDay + 5) % 7)) {
            // Set the alarm for the selected time
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlaramReciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Toast.makeText(this, "Alarm set for " + timer, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No alarm set for today", Toast.LENGTH_SHORT).show();
        }
    }







    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateYNhabit.this);
        alertDialog.setTitle("Discard");
        alertDialog.setMessage("Discard the new habit ?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();


    }
}