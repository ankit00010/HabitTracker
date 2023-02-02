package com.example.fukc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterSHabit extends RecyclerView.Adapter<MyAdapterSHabit.MyViewHolder> {

    private final int mData;

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

    public MyAdapterSHabit(int num) {
        mData = num;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_subtask_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData;
    }

}
