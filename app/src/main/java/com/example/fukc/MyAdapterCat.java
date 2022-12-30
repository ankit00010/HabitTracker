package com.example.fukc;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class MyAdapterCat extends RecyclerView.Adapter<MyAdapterCat.MyViewHolder> {
    private Context context;
    private ArrayList name;
    private ImageView myImageList;
    private int currentpos=-1;
    private String isSelected;
    DBHelper db;

    public MyAdapterCat(Context context, ArrayList name) {
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        db = new DBHelper(context);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        String cname = holder.name.getText().toString();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Category");
                builder.setMessage("Are you sure you want to delete this category?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when the "YES" button is clicked
                        //Toast.makeText(context, cname, Toast.LENGTH_SHORT).show();
                        db.deleteCat(cname);
                        Intent intent = new Intent(context.getApplicationContext(), Category_layout.class);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when the "NO" button is clicked
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //select category
        holder.clickcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scat = holder.name.getText().toString();
                currentpos= db.getCatId(scat);
                String strcurrentpos =String.valueOf(currentpos);
                Intent intent = new Intent(context.getApplicationContext(), HabitOptions.class);
                intent.putExtra("cid", strcurrentpos);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;
        public LinearLayout clickcat;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.deleteicon);
            clickcat=itemView.findViewById(R.id.clickcat);
        }
    }
}
