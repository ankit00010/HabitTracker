package com.example.fukc.activityAndFragmentClasses;

import static java.sql.Types.NULL;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.adapterClasses.MyAdapterCreateSubHabit;
import com.example.fukc.R;
import com.example.fukc.otherClasses.AlaramReciever;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class CreateChecklistHabits extends AppCompatActivity {
    ImageView color;
    LinearLayout subhabitlist;
    EditText habitname,habitque,subhabit1;
    TextView reminderbutton,frequencybutton,addsubtask,backarrow,create;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    List<String> inputList = new ArrayList<>();
    ArrayList<String> mData = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    MyAdapterCreateSubHabit adapter;
    RecyclerView recyclerView;
    int selectedHour,selectedMinute;
    DBHelper db;
    String timer="";
    DialogfragmentColor dialogFragment = new DialogfragmentColor();
    int habittype = 2;
    String colorvalue,hnameEdit;
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
        setContentView(R.layout.create_checklist_habits);
        habitname = findViewById(R.id.name_edittext);
        subhabit1 = findViewById(R.id.checklist_edittext);
        habitque = findViewById(R.id.question_edittext);
        backarrow =findViewById(R.id.back_text);
        reminderbutton = findViewById(R.id.reminder_textview);
        frequencybutton = findViewById(R.id.frequency_textview);
        addsubtask = findViewById(R.id.additem_text);
        create = findViewById(R.id.create_text);
        subhabitlist = findViewById(R.id.linearLayout);
        recyclerView = findViewById(R.id.recyclerview);
        color=findViewById(R.id.color_icon);
        db = new DBHelper(this);
        adapter = new MyAdapterCreateSubHabit(mData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        hnameEdit="";
        sp= getSharedPreferences("login", MODE_PRIVATE);
        userid = sp.getInt("userId",0);
        if (getIntent().getStringExtra("habitEdit") != "")
        {
            hnameEdit = getIntent().getStringExtra("habitEdit");
        }
        if (hnameEdit != null && hnameEdit.length() > 0)
        {
                habitname.setText(hnameEdit);
                frequencybutton.setText((db.getHabitFrequency(hnameEdit)));
                create.setText("Update");
                reminderbutton.setText(db.getReminder(hnameEdit));
                habitque.setText(db.getHabitque(hnameEdit));
                String subHabitList = db.getSubHabit(hnameEdit);
                String[] arrSubHabit = subHabitList.split(",");
                for(int i = 0;i< arrSubHabit.length;i++){
                    subhabit1.setText(arrSubHabit[0]);
                    if (i > 0) {
                        adapter.addItem(arrSubHabit[i]);
                        if(recyclerView.getVisibility() != View.VISIBLE) {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        }
        backarrow.setOnClickListener(view -> {
            Intent intent;
            if (create.getText().equals("Update")) {
                intent = new Intent(getApplicationContext(), HomeScreen.class);
            } else{
                intent = new Intent(getApplicationContext(), HabitOptions.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        reminderbutton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int mins = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(CreateChecklistHabits.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, (timePicker, hourOfDay, minute) -> {
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
            createNotificationChannel();

        });
        frequencybutton.setOnClickListener(view -> {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateChecklistHabits.this);
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
            if (getIntent().getStringExtra("habitEdit") != "")
            {
//            Log.d("checkitbruh cuk",hnameEdit);
                hnameEdit = getIntent().getStringExtra("habitEdit");

            }

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
                daysList.clear();
                frequencybutton.setText("");
            });
            // show dialog
            builder.show();
        });
        addsubtask.setOnClickListener(view -> {
            subhabit1.requestFocus();
            adapter.addItem("");
            if(recyclerView.getVisibility() != View.VISIBLE) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
        create.setOnClickListener(view -> {
            if(TextUtils.isEmpty(habitname.getText()))
            {
                Toast.makeText(CreateChecklistHabits.this, "Enter a name", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(frequencybutton.getText()))
            {
                Toast.makeText(CreateChecklistHabits.this, "Select at least one frequency", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(subhabit1.getText())){
                Toast.makeText(CreateChecklistHabits.this, "Enter at least two sub habit", Toast.LENGTH_SHORT).show();
            }
            else if (!timer.isEmpty()) {
                if (TextUtils.isEmpty(habitque.getText())) {
                    Toast.makeText(this, "Please enter the question ", Toast.LENGTH_SHORT).show();
                }
                else
                {   setAlarm();
                    colorvalue = dialogFragment.colorval;
                    String hname = habitname.getText().toString();
                    String frequency = frequencybutton.getText().toString();
                    String hque = habitque.getText().toString();
                    String subH1 = subhabit1.getText().toString();
                    inputList.add(subH1);
                    if (recyclerView.getChildCount() > 0) {
                        for (int i = 0; i < Objects.requireNonNull(recyclerView.getAdapter()).getItemCount(); i++) {
                            MyAdapterCreateSubHabit.MyViewHolder viewHolder = (MyAdapterCreateSubHabit.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                            if (viewHolder != null) {
                                String text = viewHolder.getText();
                                inputList.add(text);
                            }
                        }
                    }
                    String delimiter = ",";
                    String resultSubHabit = String.join(delimiter, inputList);

                    if (create.getText().equals("Update")) {
                        db.updateEdit(hnameEdit, hname, frequency, timer, colorvalue, hque, habittype, NULL);
                        int habitid=db.getHabitId(hname);
                        db.updateSubHabit(resultSubHabit,habitid);
                    } else {
                        db.insertDatahabit(hname, colorvalue, hque, frequency, timer, habittype, NULL,userid);
                        int habitid=db.getHabitId(hname);
                        db.insertDataSubhabits(resultSubHabit,habitid);
                    }
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
            }
            else {
                colorvalue = dialogFragment.colorval;
                String hname = habitname.getText().toString();
                String frequency = frequencybutton.getText().toString();
                String reminder = reminderbutton.getText().toString();
                String hque = habitque.getText().toString();
                String subH1 = subhabit1.getText().toString();
                inputList.add(subH1);
                if (recyclerView.getChildCount() > 0) {
                    for (int i = 0; i < Objects.requireNonNull(recyclerView.getAdapter()).getItemCount(); i++) {
                        MyAdapterCreateSubHabit.MyViewHolder viewHolder = (MyAdapterCreateSubHabit.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (viewHolder != null) {
                            String text = viewHolder.getText();
                            inputList.add(text);
                        }
                    }
                }
                String delimiter = ",";
                String resultSubHabit = String.join(delimiter, inputList);
                if (create.getText().equals("Update")) {
                    db.updateEdit(hnameEdit, hname, frequency, timer, colorvalue, hque, habittype, NULL);
                    int habitid=db.getHabitId(hname);
                    db.updateSubHabit(resultSubHabit,habitid);
                } else {
                    db.insertDatahabit(hname, colorvalue, hque, frequency, timer, habittype, NULL,userid);
                    int habitid=db.getHabitId(hname);
                    db.insertDataSubhabits(resultSubHabit,habitid);
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
    public void onBackPressed(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(CreateChecklistHabits.this);
        alertDialog.setTitle("Discard");
        alertDialog.setMessage("Discard the new habit ?");
        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent=new Intent(getApplicationContext(), HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="HabitTrackerChannel"; ///channel for habit tracker
            String description="Channel for Alaram Manager";
            int importance= NotificationManager.IMPORTANCE_HIGH;//It will appear on screen of a user
            NotificationChannel channel=new NotificationChannel("HabitTracker",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
