package com.example.fukc.otherClasses;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fukc.R;
import com.example.fukc.activityAndFragmentClasses.HomeScreen;
import com.example.fukc.databaseClass.DBHelper;

public class AlaramReciever extends BroadcastReceiver {
    DBHelper db;
    String que="";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent=PendingIntent.getActivity(context,0,i,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"HabitTracker")
        .setSmallIcon(R.drawable.habit_icon)
         .setContentTitle("Habit Tracker")
                .setContentText("Its Time lets go")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentIntent(pendingIntent) ;

            NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123,builder.build());

    }
}
