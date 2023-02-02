package com.example.fukc;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyCallback extends ItemTouchHelper.Callback {

    private final ColorDrawable background = new ColorDrawable();
    private Drawable icon;
    private final int iconMargin;
    private final MyAdapterHabit adapter;
    DBHelper db;
    Context context;

    public MyCallback(MyAdapterHabit adapter, Context context, int iconMargin) {
        this.adapter = adapter;
        this.iconMargin = iconMargin;
        db = new DBHelper(context);
        this.context=context;
        icon = context.getResources().getDrawable(R.drawable.ic_baseline_delete_24);

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            // do something for left swipe, for example show an edit dialog
        } else if (direction == ItemTouchHelper.RIGHT) {
            TextView textView = viewHolder.itemView.findViewById(R.id.habitname);
            String text = textView.getText().toString();
            db.deleteHabit(text);
            adapter.notifyItemRemoved(position);
        }
    }
    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.7f;
    }
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = icon.getIntrinsicWidth();
        int intrinsicHeight = icon.getIntrinsicWidth();

        int iconLeft, iconRight, iconTop, iconBottom;
        if (dX > 0) {
            // Swipe to the right
            background.setColor(context.getResources().getColor(R.color.black));
            icon = context.getResources().getDrawable(R.drawable.ic_baseline_delete_24);
            background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
            iconLeft = itemView.getLeft() + iconMargin;
            iconRight = iconLeft + intrinsicWidth;
            iconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            iconBottom = iconTop + intrinsicHeight;
        } else {
            // Swipe to the left
            background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            iconLeft = itemView.getRight() - iconMargin - intrinsicWidth;
            iconRight = itemView.getRight() - iconMargin;
            iconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            iconBottom = iconTop + intrinsicHeight;
        }
        background.draw(c);
        icon.setBounds(iconLeft, iconTop, iconRight  , iconBottom );
        icon.draw(c);
        c.restore();
    }
}
