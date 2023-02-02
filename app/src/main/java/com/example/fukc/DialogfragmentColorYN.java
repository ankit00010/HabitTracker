package com.example.fukc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class DialogfragmentColorYN extends DialogFragment {
    String colorval;
    ImageView cl1,cl2,cl3,cl4,cl5,cl6,cl7,cl8,cl9,cl10,cl11,cl12,cl13,cl14,cl15,cl16,cl17,cl18,cl19,cl20;
    CreateYNhabit obj;
    @Override
    public  void onAttach(@NonNull Context context){
        super.onAttach(context);
        obj = (CreateYNhabit) context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.colorpick, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        cl1 = dialogView.findViewById(R.id.cl1);
        cl2 = dialogView.findViewById(R.id.cl2);
        cl3 = dialogView.findViewById(R.id.cl3);
        cl4 = dialogView.findViewById(R.id.cl4);
        cl5 = dialogView.findViewById(R.id.cl5);
        cl6 = dialogView.findViewById(R.id.cl6);
        cl7 = dialogView.findViewById(R.id.cl7);
        cl8 = dialogView.findViewById(R.id.cl8);
        cl9 = dialogView.findViewById(R.id.cl9);
        cl10 = dialogView.findViewById(R.id.cl10);
        cl11 = dialogView.findViewById(R.id.cl11);
        cl12 = dialogView.findViewById(R.id.cl12);
        cl13 = dialogView.findViewById(R.id.cl13);
        cl14 = dialogView.findViewById(R.id.cl14);
        cl15 = dialogView.findViewById(R.id.cl15);
        cl16 = dialogView.findViewById(R.id.cl16);
        cl17 = dialogView.findViewById(R.id.cl17);
        cl18 = dialogView.findViewById(R.id.cl18);
        cl19 = dialogView.findViewById(R.id.cl19);
        cl20 = dialogView.findViewById(R.id.cl20);
        cl1.setOnClickListener(v -> {
            colorval = "#ee9a9a";
            obj.selectclr(cl1);
            dismiss();
        });
        cl2.setOnClickListener(v -> {
            colorval = "#ffab91";
            obj.selectclr(cl2);
            dismiss();
        });
        cl3.setOnClickListener(v -> {
            colorval = "#ffcc80";
            obj.selectclr(cl3);
            dismiss();
        });
        cl4.setOnClickListener(v -> {
            colorval = "#ffecb2";
            obj.selectclr(cl4);
            dismiss();            });
        cl5.setOnClickListener(v -> {
            colorval = "#69f0ae";
            obj.selectclr(cl5);
            dismiss();            });
        cl6.setOnClickListener(v -> {
            colorval = "#c5e1a6";
            obj.selectclr(cl6);
            dismiss();            });
        cl7.setOnClickListener(v -> {
            colorval = "#e6ee9b";
            obj.selectclr(cl7);
            dismiss();            });
        cl8.setOnClickListener(v -> {
            colorval = "#fff59c";
            obj.selectclr(cl8);
            dismiss();            });
        cl9.setOnClickListener(v -> {
            colorval = "#80cbc4";
            obj.selectclr(cl9);
            dismiss();            });
        cl10.setOnClickListener(v -> {
            colorval = "#80deea";
            obj.selectclr(cl10);
            dismiss();            });
        cl11.setOnClickListener(v -> {
            colorval = "#81d5fa";
            obj.selectclr(cl11);
            dismiss();            });
        cl12.setOnClickListener(v -> {
            colorval = "#64b5f6";
            obj.selectclr(cl12);
            dismiss();            });
        cl13.setOnClickListener(v -> {
            colorval = "#f48fb1";
            obj.selectclr(cl13);
            dismiss();            });
        cl14.setOnClickListener(v -> {
            colorval = "#cf93d9";
            obj.selectclr(cl14);
            dismiss();            });
        cl15.setOnClickListener(v -> {
            colorval = "#b39ddb";
            obj.selectclr(cl15);
            dismiss();            });
        cl16.setOnClickListener(v -> {
            colorval = "#9ea8db";
            obj.selectclr(cl16);
            dismiss();            });
        cl17.setOnClickListener(v -> {
            colorval = "#bcaba4";
            obj.selectclr(cl17);
            dismiss();            });
        cl18.setOnClickListener(v -> {
            colorval = "#f5f5f5";
            obj.selectclr(cl18);
            dismiss();            });
        cl19.setOnClickListener(v -> {
            colorval = "#e0e0e0";
            obj.selectclr(cl19);
            dismiss();            });
        cl20.setOnClickListener(v -> {
            colorval = "#9e9e9e";
            obj.selectclr(cl20);
            dismiss();            });
        return builder.create();
    }

}
