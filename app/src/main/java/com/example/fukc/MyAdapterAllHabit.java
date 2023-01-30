package com.example.fukc;

import static java.sql.Types.NULL;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapterAllHabit extends RecyclerView.Adapter<MyAdapterAllHabit.ViewHolder> {
    private Context context;
    private ArrayList name;
    DBHelper db;
    ArrayList<String> shabitname;
    public MyAdapterAllHabit(Context context, ArrayList name) {
        this.context = context;
        this.name = name;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.all_habits_item,parent,false);
        db = new DBHelper(context);
        shabitname = new ArrayList<>();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        Glide.with(holder.itemView.getContext()).load(R.drawable.stats).into(holder.stats);
        String hname = String.valueOf(name.get(position));
        String frequncy =db.getHabitFrequency(hname);
        String colorcode=db.getHabitColor(hname);
        if(!TextUtils.isEmpty(colorcode)){
            holder.name.setTextColor(Color.parseColor(colorcode));
        }
        String[] days = frequncy.split(",");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < days.length; i++) {
            String shortDay = days[i].substring(0, 3);
            sb.append(shortDay);
            if (i < days.length - 1) {
                sb.append(",");
            }
        }
        String shortS = sb.toString();
        int htype = db.getHabitType(hname);
        if(htype==0){
            holder.htype.setText("Yes/No");
        }else if(htype==1){
            holder.htype.setText("Measurable");
        }else if(htype==2) {
            holder.htype.setText("Checklist");
        }
        holder.frequency.setText(shortS);
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,frequency,htype;
        public ImageView stats;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.habitname);
            frequency= itemView.findViewById(R.id.show_frequency);
            htype= itemView.findViewById(R.id.show_type);
            stats = itemView.findViewById(R.id.stats);
            //Glide.with(itemView.getContext()).load(R.drawable.circle).into(stats);

        }
    }
}