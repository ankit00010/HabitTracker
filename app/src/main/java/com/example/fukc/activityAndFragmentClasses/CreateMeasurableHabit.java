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
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;
import com.example.fukc.otherClasses.AlaramReciever;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


public class CreateMeasurableHabit extends AppCompatActivity {

    ImageView color;
    EditText habitname,habitque,target;
    TextView reminderbox,frequency_edittext,backtext,savehabit;
    boolean[] selectedDays;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    String hname,hque,hnameEdit;
    DBHelper db;
    String colorvalue;
    AlarmManager alaramManager;
    PendingIntent pendingIntent;
    Calendar calendar;
    DialogfragmentColorM dialogFragment = new DialogfragmentColorM();
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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_measurable_habit);
        backtext=findViewById(R.id.measurable_back_text);
        reminderbox =findViewById(R.id.measurable_reminder_edit_text);
        String remainder = reminderbox.getText().toString().trim();

        frequency_edittext=findViewById(R.id.measurable_frequency_edit_text);
        habitname = findViewById(R.id.measurable_name_text);
        habitque = findViewById(R.id.measurable_question_edit_text);
        savehabit= findViewById(R.id.measurable_create_text);
        target= findViewById(R.id.measurable_target_text1);

        color=findViewById(R.id.measurable_color_button);
        calendar=Calendar.getInstance();
        if (getIntent().getStringExtra("habitEdit") != "")
        {
//            Log.d("checkitbruh cuk",hnameEdit);
            hnameEdit = getIntent().getStringExtra("habitEdit");

        }

        db = new DBHelper(this);
        reminderbox.setOnClickListener(view -> {
            Calendar calendar= Calendar.getInstance();
            int hours=calendar.get(Calendar.HOUR_OF_DAY);
            int mins=calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog=new TimePickerDialog(CreateMeasurableHabit.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, (timePicker, hourOfDay, minute) -> {
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                c.set(Calendar.MINUTE,minute);
                c.setTimeZone(TimeZone.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("k:mm a");
                String time=format.format(c.getTime());
                reminderbox.setText(time);


            },hours,mins,false);
            timePickerDialog.show();

        });
        //backtext to navigate to habit options back
        backtext.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(),HabitOptions.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        //code for frequency
        frequency_edittext.setOnClickListener(view -> {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateMeasurableHabit.this);
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
                frequency_edittext.setText(stringBuilder.toString());
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
                    frequency_edittext.setText("");
                }
            });
            // show dialog
            builder.show();
        });
        if (hnameEdit != null && hnameEdit.length() > 0)
        {
            if (remainder.isEmpty())
            {   Log.d("CHeckthisone","The reminder exception handled");
                habitname.setText(db.getHabitName(hnameEdit));
                frequency_edittext.setText((db.getHabitFrequency(hnameEdit)));
                String targetString = ""+db.getHabitTarget(hnameEdit);
                target.setText(targetString);
                Log.d("Here it is bruh", targetString);


                savehabit.setText("Update");
            }
            else {
                Log.d("CHeckthisone","The reminder exception handled ??????????????");

                habitname.setText(db.getHabitName(hnameEdit));
                frequency_edittext.setText((db.getHabitFrequency(hnameEdit)));
                reminderbox.setText(db.getReminder(hnameEdit));
                habitque.setText(db.getHabitque(hnameEdit));
                target.setText(db.getHabitTarget(hnameEdit));
                savehabit.setText("Update");
                //check this logic should be written outside or inside savehabit
            }
        }

        savehabit.setOnClickListener(view -> {
            if(TextUtils.isEmpty(habitname.getText()))//validation for  habitname field is empty
            {
                Toast.makeText(CreateMeasurableHabit.this, "Enter a name", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(target.getText()))//validation for  Target field is empty
            {
                Toast.makeText(CreateMeasurableHabit.this, "Enter a target greater than zero", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(frequency_edittext.getText()))//validation for  Frequency field is empty
            {
                Toast.makeText(CreateMeasurableHabit.this, "Select atleast one frequency", Toast.LENGTH_SHORT).show();
            }
            //save the data
            else {

                colorvalue = dialogFragment.colorval;
                final int habittype = 1;
                String frequency = frequency_edittext.getText().toString();
                String reminder = reminderbox.getText().toString();
                int targetval = Integer.parseInt(target.getText().toString());
                hname = habitname.getText().toString();
                hque = habitque.getText().toString();
                if (savehabit.getText().equals("Update")) {
                    Log.d("Heereeeeeeeee","11111111111111111111111111111111");

                    db.updateEdit(hnameEdit,hname,frequency,reminder,colorvalue,hque,habittype,targetval);
                } else{
                    Log.d("Heereeeeeeeee","22222222222222222222222222222222");

                    db.insertDatahabit(hname, colorvalue, hque, frequency, reminder, habittype, targetval);

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
    private void setAlaram() {
        Toast.makeText(this, "naah bro ", Toast.LENGTH_SHORT).show();
        alaramManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, AlaramReciever.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        alaramManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    public void onBackPressed(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(CreateMeasurableHabit.this);
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

}