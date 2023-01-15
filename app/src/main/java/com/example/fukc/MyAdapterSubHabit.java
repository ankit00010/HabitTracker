package com.example.fukc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapterSubHabit extends RecyclerView.Adapter<MyAdapterSubHabit.ViewHolder> {
    private ArrayList name;
    private Context context;
    private int habitid;
    public int[] drawables = {R.drawable.crossedcircle,R.drawable.checkedcircle};
    public String[] record = {"N","Y","N"};
    DBHelper db;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String strDate = dateFormat.format(calendar.getTime());
    public MyAdapterSubHabit(Context con,ArrayList name,int habitid) {
        this.name = name;
        this.context=con;
        this.habitid=habitid;
    }
    @NonNull
    @Override
    public MyAdapterSubHabit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.subhabit_items,parent,false);
        db = new DBHelper(context);
        return new MyAdapterSubHabit.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapterSubHabit.ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        Glide.with(holder.itemView.getContext()).load(R.drawable.circle).into(holder.checkbox);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            int currentDrawableIndex = 0;
            @Override
            public void onClick(View view) {
                currentDrawableIndex = (currentDrawableIndex+1 ) % drawables.length;
                holder.checkbox.setImageDrawable(context.getDrawable(drawables[currentDrawableIndex]));
            }
        });
    }
    @Override
    public int getItemCount() {
        return name.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView checkbox;
        private Calendar mCalendar = Calendar.getInstance();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shhabitname);
            checkbox = itemView.findViewById(R.id.shempty);
        }
        public Calendar getCalendar() {
            return mCalendar;
        }
        public void setCalendar(Calendar calendar) {
            mCalendar = calendar;
        }
    }
}