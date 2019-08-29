package com.autonavi.minimap.widget.draggable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import java.util.Collections;
import java.util.List;

final class DragHandler extends Callback {
    private OnItemDragedListener mOnItemDragedListener;
    private final List<ViewHolder> mViewHolders;

    public final void onSwiped(ViewHolder viewHolder, int i) {
    }

    DragHandler(@NonNull DraggableListAdapter draggableListAdapter) {
        if (draggableListAdapter != null || !bno.a) {
            this.mViewHolders = draggableListAdapter.c;
            return;
        }
        throw new IllegalArgumentException("Adapter is null");
    }

    public final void setOnItemDragedListener(OnItemDragedListener onItemDragedListener) {
        this.mOnItemDragedListener = onItemDragedListener;
    }

    public final int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        return makeMovementFlags(15, 0);
    }

    public final boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
        int adapterPosition = viewHolder.getAdapterPosition();
        int adapterPosition2 = viewHolder2.getAdapterPosition();
        if (adapterPosition2 == adapterPosition) {
            return false;
        }
        if (adapterPosition < adapterPosition2) {
            int i = adapterPosition;
            while (i < adapterPosition2) {
                int i2 = i + 1;
                Collections.swap(this.mViewHolders, i, i2);
                i = i2;
            }
        } else {
            for (int i3 = adapterPosition; i3 > adapterPosition2; i3--) {
                Collections.swap(this.mViewHolders, i3, i3 - 1);
            }
        }
        recyclerView.getAdapter().notifyItemMoved(adapterPosition, adapterPosition2);
        if (this.mOnItemDragedListener != null) {
            this.mOnItemDragedListener.onItemChanged(viewHolder, adapterPosition, adapterPosition2);
        }
        return true;
    }

    public final ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
        int width = viewHolder.itemView.getWidth() / 2;
        int i3 = i + width;
        int height = viewHolder.itemView.getHeight() / 2;
        int i4 = i2 + height;
        ViewHolder viewHolder2 = null;
        double d = Double.MAX_VALUE;
        for (ViewHolder next : list) {
            int left = (next.itemView.getLeft() + width) - i3;
            int top = (next.itemView.getTop() + height) - i4;
            double d2 = (double) ((left * left) + (top * top));
            if (d2 < d) {
                viewHolder2 = next;
                d = d2;
            }
        }
        return viewHolder2;
    }

    public final void onSelectedChanged(ViewHolder viewHolder, int i) {
        if (i != 2) {
            super.onSelectedChanged(viewHolder, i);
        } else if (this.mOnItemDragedListener != null) {
            this.mOnItemDragedListener.onItemDraged(viewHolder, viewHolder.getAdapterPosition());
        }
    }

    public final void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (this.mOnItemDragedListener != null) {
            this.mOnItemDragedListener.onItemReleased(viewHolder, viewHolder.getAdapterPosition());
        }
    }
}
