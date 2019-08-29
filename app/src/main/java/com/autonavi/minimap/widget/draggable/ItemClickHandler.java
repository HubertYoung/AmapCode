package com.autonavi.minimap.widget.draggable;

import android.os.Vibrator;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

class ItemClickHandler extends SimpleOnGestureListener {
    private OnItemClickListener mOnItemClickListener;
    private final DraggableRecyclerView mRecyclerView;

    public ItemClickHandler(DraggableRecyclerView draggableRecyclerView) {
        this.mRecyclerView = draggableRecyclerView;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return super.onDown(motionEvent);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        View findChildViewUnder = this.mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (findChildViewUnder == null || this.mOnItemClickListener == null) {
            return false;
        }
        try {
            ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(findChildViewUnder);
            if (childViewHolder == null) {
                return false;
            }
            this.mOnItemClickListener.onItemClick(childViewHolder, childViewHolder.getAdapterPosition());
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void onLongPress(MotionEvent motionEvent) {
        try {
            if (this.mRecyclerView.isDraggable()) {
                ((Vibrator) this.mRecyclerView.getContext().getSystemService("vibrator")).vibrate(150);
            }
        } catch (Throwable unused) {
        }
        View findChildViewUnder = this.mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewUnder == null || this.mOnItemClickListener == null)) {
            try {
                ViewHolder childViewHolder = this.mRecyclerView.getChildViewHolder(findChildViewUnder);
                if (childViewHolder != null) {
                    this.mOnItemClickListener.onItemLongClick(childViewHolder, childViewHolder.getAdapterPosition());
                }
            } catch (Exception unused2) {
            }
        }
    }
}
