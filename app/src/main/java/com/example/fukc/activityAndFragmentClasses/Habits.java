package com.example.fukc.activityAndFragmentClasses;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fukc.databaseClass.DBHelper;
import com.example.fukc.adapterClasses.MyAdapterAllHabit;
import com.example.fukc.R;

import java.util.ArrayList;

public class Habits extends Fragment {
    View view;
    RecyclerView recyclerView;
    ArrayList<String> habitname;
    MyAdapterAllHabit adapter;
    DBHelper db;
    SharedPreferences sp;
    int userid;
    public Habits() {
        // Required empty public constructor
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.habits, container, false);
        db = new DBHelper(getActivity().getApplicationContext());
        sp = getContext().getSharedPreferences("login", MODE_PRIVATE);
        userid = sp.getInt("userId", 0);
        habitname = new ArrayList<>();
        Cursor cursor = db.getalldataHabit(userid);
        while (cursor.moveToNext()) {
            habitname.add(cursor.getString(0));
        }
        recyclerView = view.findViewById(R.id.habits);
        adapter = new MyAdapterAllHabit(getActivity(),getActivity().getApplicationContext(), habitname);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
        db.close();
        return view;
    }
}
