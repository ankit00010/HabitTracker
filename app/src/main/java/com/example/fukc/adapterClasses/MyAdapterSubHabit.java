package com.example.fukc.adapterClasses;

import static java.sql.Types.NULL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapterSubHabit extends RecyclerView.Adapter<MyAdapterSubHabit.ViewHolder> {
    private final ArrayList name;
    private final Context context;
    private final int habitid;
    public int[] drawables = {R.drawable.crossedcircle,R.drawable.checkedcircle};
    DBHelper db;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String strDate = dateFormat.format(calendar.getTime());
    String hName;
    String sRecord;
    String[] record;

    public MyAdapterSubHabit(Context con,ArrayList name,int habitid,String hName) {
        this.name = name;
        this.context=con;
        this.habitid=habitid;
        this.hName=hName;

    }
    @NonNull
    @Override
    public MyAdapterSubHabit.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.subhabit_items,parent,false);
        db = new DBHelper(context);
        sRecord = db.getSubRecord(hName);
        record= sRecord.split(",");
        return new MyAdapterSubHabit.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapterSubHabit.ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        Glide.with(holder.itemView.getContext()).load(R.drawable.circle).into(holder.checkbox);
        Glide.with(holder.itemView.getContext()).load(R.drawable.subhabit_icon).into(holder.subHabitIcon);
        if(record[holder.getAdapterPosition()].equals("Y")){
            Glide.with(holder.itemView.getContext()).load(R.drawable.checkedcircle).into(holder.checkbox);
        }else if(record[holder.getAdapterPosition()].equals("N")){
            Glide.with(holder.itemView.getContext()).load(R.drawable.crossedcircle).into(holder.checkbox);
        }else if(record[holder.getAdapterPosition()].equals("S")){
            Glide.with(holder.itemView.getContext()).load(R.drawable.circle).into(holder.checkbox);
        }

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            int currentDrawableIndex = -1;
            @Override
            public void onClick(View view) {
                currentDrawableIndex = (currentDrawableIndex+1 ) % drawables.length;
                if (currentDrawableIndex==0) {
                    Glide.with(holder.itemView.getContext()).load(R.drawable.checkedcircle).into(holder.checkbox);
                    record[holder.getAdapterPosition()]="Y";
                } else {
                    Glide.with(holder.itemView.getContext()).load(R.drawable.crossedcircle).into(holder.checkbox);
                    record[holder.getAdapterPosition()]="N";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < record.length; i++) {
                    sb.append(record[i]);
                    if (i < record.length - 1) {
                        sb.append(",");
                    }
                }
                db.updateSubRecord(habitid,sb.toString(),strDate);
                int cnt=0;
                for(int i=0;i<record.length;i++){
                    if(record[i].equals("Y")){
                        cnt++;
                    }
                }
                if(cnt==record.length){
                    db.updateChecklistRecord(habitid, "Y", strDate);
                }else if(cnt<record.length&&cnt>0){
                    db.updateChecklistRecord(habitid, "F", strDate);
                }else if(cnt==0){
                    db.updateChecklistRecord(habitid, "N", strDate);
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
        public TextView name;
        public ImageView checkbox,subHabitIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shhabitname);
            checkbox = itemView.findViewById(R.id.shempty);
            subHabitIcon = itemView.findViewById(R.id.subhabit_icon);
        }
    }
}