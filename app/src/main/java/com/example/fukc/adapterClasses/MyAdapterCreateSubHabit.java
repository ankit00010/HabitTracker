package com.example.fukc.adapterClasses;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fukc.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterCreateSubHabit extends RecyclerView.Adapter<MyAdapterCreateSubHabit.MyViewHolder> {

    ArrayList<String> mData;
    public MyAdapterCreateSubHabit(ArrayList<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_subtask_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.editText.requestFocus();
        holder.editText.setText(mData.get(position));
        holder.delete.setOnClickListener(v -> {
            mData.remove(0);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
   public void addItem(String item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
   }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText editText;
        public ImageView delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.checklist_edittext2);
            delete = itemView.findViewById(R.id.dletesubtask);
        }
        public String getText() {
            return editText.getText().toString();
        }
    }

}
