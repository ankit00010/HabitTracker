package com.example.fukc;

import static java.sql.Types.NULL;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Today extends Fragment implements MyAdapterHabit.OnItemClickListener {
    ImageView plus;
    DBHelper db;
    RecyclerView recyclerView;
    ArrayList<String> habitname;
    MyAdapterHabit adapter;
    Calendar calendar = Calendar.getInstance();
    String strday = calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String strDate = dateFormat.format(calendar.getTime());
    View view;
    public Today() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.today, container, false);
        db = new DBHelper(getActivity().getApplicationContext());
        habitname = new ArrayList<>();
        Cursor cursor = db.getdataHabit(strday);
        while (cursor.moveToNext()) {
            habitname.add(cursor.getString(0));
        }
        recyclerView = view.findViewById(R.id.recyclerViewhabit);
        adapter = new MyAdapterHabit(getActivity().getApplicationContext(), habitname);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        MyCallback callback = new MyCallback(adapter, getActivity().getApplicationContext(),  20);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
        int childCount = habitname.size();
        for (int i = 0; i < childCount; i++) {
            String habtname = habitname.get(i);
            int habitid = db.getHabitId(habtname);
            if (db.isRecordPresent(habitid, strDate) == false) {
                db.insertDataRecord(habitid, "S", strDate, NULL,"");
            }
        }
        plus=view.findViewById(R.id.plussign);
        plus.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity().getApplicationContext(),HabitOptions.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        });
        db.close();
        return view;
    }

    @Override
    public void onItemClick(int position) {
        if (isAdded() && isVisible()) {
            int[] drawablesm = {R.drawable.checkedcircle, R.drawable.minuscircle};
            int[] drawables = {R.drawable.crossedcircle,R.drawable.checkedcircle};
            MyAdapterHabit.ViewHolder holder = (MyAdapterHabit.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
            ImageView imageView = holder.checkbox;
            final EditText editText = new EditText(getContext());
            View view = recyclerView.findViewHolderForAdapterPosition(position).itemView;
            int target = (int) view.getTag(R.id.tag_variable);
            int habitid = (int) view.getTag(R.id.tag_variable2);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select a value");
            builder.setView(editText);
            builder.setMessage("Target is " + target);
            builder.setPositiveButton("OK", (dialog, which) -> {
                int number = Integer.valueOf(editText.getText().toString());
                if (number >= target) {
                    Glide.with(getContext()).load(getContext().getDrawable(drawablesm[0])).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
                    db.updateMeasurableRecord(habitid,"Y",strDate,number);
                } else if(number==0){
                    Glide.with(getContext()).load(getContext().getDrawable(drawablesm[0])).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
                    db.updateMeasurableRecord(habitid,"N",strDate,number);
                }else {
                    Glide.with(getContext()).load(getContext().getDrawable(drawablesm[1])).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
                    db.updateMeasurableRecord(habitid,"F",strDate,number);
                }
            });
            builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
