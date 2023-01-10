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
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.text.InputType;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyAdapterHabit extends RecyclerView.Adapter<MyAdapterHabit.ViewHolder> {
    private Context context;
    private ArrayList name;
    public int[] drawables = {R.drawable.circle,R.drawable.checkedcircle, R.drawable.crossedcircle };
    public int[] drawablesm = {R.drawable.checkedcircle, R.drawable.minuscircle};
    public String[] recordYN = {"N","Y","N"};
    int number;
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
        //
        /*if (!holder.itemStoredToday) {
            db.insertDataRecord(habitid,"N",strDate,NULL);
            holder.itemStoredToday = true;
        }*/
        //
        if (habittype == 0) {

            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                int currentDrawableIndex = 0;
                @Override
                public void onClick(View view) {
                    currentDrawableIndex = (currentDrawableIndex + 1) % drawables.length;
                    holder.checkbox.setImageDrawable(context.getDrawable(drawables[currentDrawableIndex]));
                    //Log.d("xyz", "Inserting record into database: " + habitid + " " + "N" + " " + strDate + " " + NULL);
                    db.updateYNrecord(habitid,recordYN[currentDrawableIndex],strDate);
                    //Log.d("xyz", "Inserting record into database: " + habitid + " " + recordYN[currentDrawableIndex] + " " + strDate + " " + NULL);
                }
            });
        }else if (habittype == 1) {
            holder.builder.setTitle("Select a value");
            holder.builder.setView(holder.editText);
            holder.builder.setMessage("Target is " + target);
            holder.builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    number = Integer.valueOf(holder.editText.getText().toString());
                    if (number >= target) {
                        holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[0]));
                        db.updateMeasurableRecord(habitid,"Y",strDate,number);
                    } else if(number<=0){
                        holder.checkbox.setImageDrawable(context.getDrawable(drawables[0]));
                        db.updateMeasurableRecord(habitid,"N",strDate,number);
                    }else {
                        holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[1]));
                        db.updateMeasurableRecord(habitid,"F",strDate,number);
                    }
                }
            });
            holder.builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = holder.builder.create();
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show();
                }
            });
        } else if (habittype == 2) {
            String shlst= db.getdataSHabit(habitid);
            String[] stringArray = shlst.split(",");
            ArrayList<String> shabitname = new ArrayList<>(Arrays.asList(stringArray));
            holder.recyclerView.setVisibility(View.VISIBLE);
            adapter = new MyAdapterSubHabit(context,shabitname,habitid);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setAdapter(adapter);
            int iconn=0;
            int childCount = holder.recyclerView.getChildCount();
            Drawable desiredDrawabl2 = context.getResources().getDrawable(R.drawable.checkedcircle);
            for (int i = 0; i < childCount; i++) {
                View childView = holder.recyclerView.getChildAt(i);
                ImageView imageView = childView.findViewById(R.id.shempty);
                if (imageView.getDrawable() == desiredDrawabl2) {
                    iconn = iconn+1;
                    break;
                }
            }
            if(iconn==0){
                holder.checkbox.setImageDrawable(context.getDrawable(drawables[0]));
            }else if(iconn>0 && iconn<childCount){
                holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[1]));
            }else if(iconn==childCount){
                holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[0]));
            }
           // Log.d("CompositeAdapter", "id "  );
        }
    }
    @Override
    public int getItemCount() {
        return name.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView checkbox;
        public AlertDialog.Builder builder = new AlertDialog.Builder(context);
        RecyclerView recyclerView;
        final EditText editText = new EditText(context);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.habitname);
            checkbox = itemView.findViewById(R.id.empty);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            recyclerView = itemView.findViewById(R.id.child_rv);
        }


    }
}

