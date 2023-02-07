package com.example.fukc.adapterClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fukc.activityAndFragmentClasses.CreateChecklistHabits;
import com.example.fukc.activityAndFragmentClasses.CreateMeasurableHabit;
import com.example.fukc.activityAndFragmentClasses.CreateYNhabit;
import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;
import com.example.fukc.activityAndFragmentClasses.Statistics;

import java.util.ArrayList;


public class MyAdapterAllHabit extends RecyclerView.Adapter<MyAdapterAllHabit.ViewHolder> {
    private final Context context;
    private final ArrayList name;
    DBHelper db;
    ArrayList<String> shabitname;
    public MyAdapterAllHabit(Context context, ArrayList name) {
        this.context = context;
        this.name = name;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.all_habits_item,parent,false);
        db = new DBHelper(context);
        shabitname = new ArrayList<>();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        Glide.with(holder.itemView.getContext()).load(R.drawable.stat).into(holder.stats);
        Glide.with(holder.itemView.getContext()).load(R.drawable.ic_baseline_edit_24).into(holder.edit);
        Glide.with(holder.itemView.getContext()).load(R.drawable.deleteit).into(holder.delete);


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
        holder.stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Statistics.class);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("habitName", hname);
                context.startActivity(intent);
            }

        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int category =db.getHabitType(hname);
                if (category==0){
                    Intent intent=new Intent(context.getApplicationContext(), CreateYNhabit.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("hname",hname);
                    intent.putExtra("habitEdit", hname);
                    context.getApplicationContext().startActivity(intent);
                }
                else if (category==1)
                {
                    Intent intent=new Intent(context, CreateMeasurableHabit.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("habitName", hname);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
                else if (category==2){
                    Intent intent=new Intent(context, CreateChecklistHabits.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("habitName", hname);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            }
        });
        db.close();
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,frequency,htype;
        public ImageView stats,edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.habitname);
            frequency= itemView.findViewById(R.id.show_frequency);
            htype= itemView.findViewById(R.id.show_type);
            stats = itemView.findViewById(R.id.stats);
            edit =itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);
            //Glide.with(itemView.getContext()).load(R.drawable.circle).into(stats);

        }
    }
}