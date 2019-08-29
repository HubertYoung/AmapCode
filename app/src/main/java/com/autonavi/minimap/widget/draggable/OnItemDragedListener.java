package com.autonavi.minimap.widget.draggable;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface OnItemDragedListener {
    void onItemChanged(ViewHolder viewHolder, int i, int i2);

    void onItemDraged(ViewHolder viewHolder, int i);

    void onItemReleased(ViewHolder viewHolder, int i);
}
