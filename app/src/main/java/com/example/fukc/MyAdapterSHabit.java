package com.example.fukc;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterSHabit extends RecyclerView.Adapter<MyAdapterSHabit.MyViewHolder> {

    private Context context;
    private int mData;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;

        public MyViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.checklist_edittext2);
        }
        public String getText() {
            return editText.getText().toString();
        }
    }

    public MyAdapterSHabit(Context con,int num) {
        this.context = con;
        mData = num;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_subtask_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData;
    }
    /*public void addItem() {
        mData.add(" ");
        notifyItemInserted(mData.size() - 1);
    }*/
}
