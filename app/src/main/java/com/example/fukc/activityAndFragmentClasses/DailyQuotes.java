package com.example.fukc.activityAndFragmentClasses;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.example.fukc.R;

import java.util.Random;

public class DailyQuotes {
    // list of tips and tricks
    private static String[] dailyquotes = {
           " Under the sword lifted high, There is hell making you tremble. But go ahead, And you have the land of bliss.",
            "Know your enemy, know his sword.",
            "I dreamt of worldly success once.",
            "Step by step walk the thousand-mile road.",
            "Develop intuitive judgement and understanding for everything."
    };

    // function to show a random tip or trick
    public static void showqoutes(Context context) {
        // retrieve a random tip or trick
        Random random = new Random();
        int index = random.nextInt(dailyquotes.length);
        String daily = dailyquotes[index];

        // create and show the pop-up window
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Daily Quote");
        builder.setMessage(daily);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
