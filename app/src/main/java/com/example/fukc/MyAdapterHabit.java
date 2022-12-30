package com.example.fukc;




import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class MyAdapterHabit extends RecyclerView.Adapter<MyAdapterHabit.ViewHolder> {
    private Context context;
    private ArrayList name;
    public int[] drawables = {R.drawable.circle,R.drawable.checkedcircle, R.drawable.crossedcircle };
    public int currentDrawableIndex = 0;
    public int[] drawablesm = {R.drawable.checkedcircle, R.drawable.minuscircle};
    int number;

    DBHelper db;

    public MyAdapterHabit(Context context, ArrayList name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.habit_items,parent,false);
        db = new DBHelper(context);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        String habitname =holder.name.getText().toString();
        int habittype = db.getHabittype(habitname);
        int target= db.getHabitTarget(habitname);
        if(habittype==0) {
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentDrawableIndex = (currentDrawableIndex +1) % drawables.length;
                    holder.checkbox.setImageDrawable(context.getDrawable(drawables[currentDrawableIndex]));
                }
            });
        }else if(habittype==1){
            holder.builder.setTitle("Select a value");
            holder.builder.setView(holder.editText);
            holder.builder.setMessage("Target is "+ target);
            holder.builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    number = Integer.valueOf(holder.editText.getText().toString());
                    if(number>=target){
                        holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[0]));
                    }else {
                        holder.checkbox.setImageDrawable(context.getDrawable(drawablesm[1]));
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
        final EditText editText = new EditText(context);
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.habitname);
            checkbox = itemView.findViewById(R.id.empty);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);



        }
    }
}
