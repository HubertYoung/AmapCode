package com.autonavi.minimap.widget.draggable;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface OnItemClickListener {
    void onItemClick(ViewHolder viewHolder, int i);

    void onItemLongClick(ViewHolder viewHolder, int i);
}
