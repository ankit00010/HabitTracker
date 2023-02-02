package com.example.fukc.adapterClasses;


import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class MyAdapterHabit extends RecyclerView.Adapter<MyAdapterHabit.ViewHolder> {
    private final Context context;
    private final ArrayList name;
    public int[] drawables = {R.drawable.crossedcircle,R.drawable.checkedcircle};
    public String[] recordYN = {"N","Y"};
    DBHelper db;
    ArrayList<String> shabitname;
    MyAdapterSubHabit adapter;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String strDate = dateFormat.format(calendar.getTime());
    public MyAdapterHabit(Context context, ArrayList name) {
        this.context = context;
        this.name = name;

    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.habit_items,parent,false);
        db = new DBHelper(context);
        shabitname = new ArrayList<>();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        String habitname = holder.name.getText().toString();
        int habittype = db.getHabittype(habitname);
        int target = db.getHabitTarget(habitname);
        int habitid= db.getHabitId(habitname);
        String colorcode=db.getHabitColor(habitname);

        if(db.getRecord(habitname).equals("Y")){
            Glide.with(context).load(R.drawable.checkedcircle).into(holder.checkbox);
        }else if(db.getRecord(habitname).equals("N")){
            Glide.with(context).load(R.drawable.crossedcircle).into(holder.checkbox);
        }else if(db.getRecord(habitname).equals("S")){
            Glide.with(context).load(R.drawable.circle).into(holder.checkbox);
        }else if(db.getRecord(habitname).equals("F")){
            //holder.checkbox.setImageResource(R.drawable.minuscircle);
            Glide.with(context).load(R.drawable.minuscircle).into(holder.checkbox);
        }

        if(!TextUtils.isEmpty(colorcode)){
            holder.name.setTextColor(Color.parseColor(colorcode));
        }
        if (habittype == 0) {
            holder.icon.setImageResource(R.drawable.yn_icon);
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                int currentDrawableIndex = 0;
                @Override
                public void onClick(View view) {
                    currentDrawableIndex = (currentDrawableIndex + 1) % drawables.length;
                    Glide.with(context).load(context.getDrawable(drawables[currentDrawableIndex])).transition(DrawableTransitionOptions.withCrossFade()).into(holder.checkbox);
                    db.updateYNrecord(habitid,recordYN[currentDrawableIndex],strDate);
                }
            });
        }else if (habittype == 1) {
            holder.icon.setImageResource(R.drawable.measurable_icon);
            holder.itemView.setTag(R.id.tag_variable, target);
            holder.itemView.setTag(R.id.tag_variable2, habitid);
            holder.checkbox.setOnClickListener(view -> {
                Log.d("dialog today","before this");
                if (listener != null) {
                    int position1 = holder.getAdapterPosition();
                    if (position1 != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position1);
                    }
                }
            });
        }else if (habittype == 2) {
            RotateAnimation rotateLeft = new RotateAnimation(-90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            RotateAnimation rotateRight = new RotateAnimation(0, -90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateLeft.setDuration(100);
            rotateLeft.setInterpolator(new LinearInterpolator());
            rotateLeft.setFillAfter(true);
            rotateRight.setDuration(100);
            rotateRight.setInterpolator(new LinearInterpolator());
            rotateRight.setFillAfter(true);


            holder.dropdown.setImageResource(R.drawable.dropdown_icon);
            holder.icon.setImageResource(R.drawable.checklist_icon);
            String shlst= db.getdataSHabit(habitid);
            String[] stringArray = shlst.split(",");
            ArrayList<String> shabitname = new ArrayList<>(Arrays.asList(stringArray));
            adapter = new MyAdapterSubHabit(context,shabitname,habitid,habitname);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerView.setHasFixedSize(true);
            String[] sRecord = new String[shabitname.size()];
            if (db.isSubRecordPresent(habitid, strDate) == false) {
                for(int i = 0;i<shabitname.size();i++){
                    sRecord[i]="S";
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < stringArray.length; i++) {
                    sb.append(stringArray[i]);
                    if (i < stringArray.length - 1) {
                        sb.append(",");
                    }
                }
                db.updateSubRecord(habitid,sb.toString(),strDate);
            }
            holder.recyclerView.setAdapter(adapter);
            holder.itemView.setOnClickListener(view -> {
                if (holder.recyclerView.getVisibility() == View.GONE) {
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.dropdown.startAnimation(rotateRight);

                } else {
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.dropdown.startAnimation(rotateLeft);
                }
            });

        }
        db.close();
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView checkbox,icon,dropdown;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.habitname);
            checkbox = itemView.findViewById(R.id.empty);
            recyclerView = itemView.findViewById(R.id.child_rv);
            icon = itemView.findViewById(R.id.habit_list_icon);
            dropdown = itemView.findViewById(R.id.dropdown_icon);
            Glide.with(itemView.getContext()).load(R.drawable.circle).into(checkbox);
        }
    }
}