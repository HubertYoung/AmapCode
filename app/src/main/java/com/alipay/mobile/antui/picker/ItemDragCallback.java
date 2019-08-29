package com.alipay.mobile.antui.picker;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter.MyViewHolder;
import com.alipay.mobile.antui.utils.AuiLogger;

public class ItemDragCallback implements OnDragListener {
    /* access modifiers changed from: private */
    public boolean animating = false;
    /* access modifiers changed from: private */
    public onMoveListener onMoveListener;

    public interface onMoveListener {
        void onItemMove(int i, int i2);

        void onItemMoveFinished();
    }

    public ItemDragCallback(onMoveListener onMoveListener2) {
        this.onMoveListener = onMoveListener2;
    }

    public boolean onDrag(View view, DragEvent event) {
        MyViewHolder viewHolder = (MyViewHolder) event.getLocalState();
        switch (event.getAction()) {
            case 1:
                onDragStart(viewHolder);
                break;
            case 2:
                AuiLogger.info("ItemDragCallback", "ItemDragCallback ACTION_DRAG_LOCATION");
                onDragLocation(view, event);
                break;
            case 4:
                AuiLogger.info("ItemDragCallback", "ItemDragCallback ACTION_DRAG_ENDED");
                onDragEnd(viewHolder);
                break;
        }
        return true;
    }

    private void onDragLocation(View view, DragEvent event) {
        RecyclerView recyclerView = (RecyclerView) view;
        if (!recyclerView.getItemAnimator().isRunning()) {
            View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
            if (child != null) {
                ViewHolder toViewHolder = recyclerView.getChildViewHolder(child);
                int fromPosition = ((MyViewHolder) event.getLocalState()).getAdapterPosition();
                if (fromPosition != -1) {
                    int toPosition = toViewHolder.getAdapterPosition();
                    if (!this.animating && toPosition >= 0 && fromPosition != toPosition) {
                        this.animating = true;
                        recyclerView.post(new w(this, fromPosition, toPosition));
                    }
                }
            }
        }
    }

    private void onDragStart(MyViewHolder dragInfo) {
        if (dragInfo != null && dragInfo != null) {
            dragInfo.onViewStateChanged(ImagePickerAdapter.VIEW_STATE_DRAGGED);
        }
    }

    private void onDragEnd(MyViewHolder dragInfo) {
        if (dragInfo != null && dragInfo != null) {
            if (this.onMoveListener != null) {
                this.onMoveListener.onItemMoveFinished();
            }
            ViewCompat.setAlpha(dragInfo.itemView, 1.0f);
            ViewCompat.setScaleX(dragInfo.itemView, 1.0f);
            ViewCompat.setScaleY(dragInfo.itemView, 1.0f);
            dragInfo.onViewStateChanged(ImagePickerAdapter.VIEW_STATA_END);
        }
    }
}
