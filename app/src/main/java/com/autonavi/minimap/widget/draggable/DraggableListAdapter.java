package com.autonavi.minimap.widget.draggable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public abstract class DraggableListAdapter<E, VH extends ViewHolder> extends Adapter {
    List<VH> c = new ArrayList();
    private final List<E> mEntities;

    public abstract void onBindViewHolderItem(VH vh, E e);

    public abstract VH onCreateViewHolderItem(ViewGroup viewGroup, int i);

    public DraggableListAdapter(List<E> list) {
        this.mEntities = list;
    }

    public final VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        VH onCreateViewHolderItem = onCreateViewHolderItem(viewGroup, i);
        this.c.add(onCreateViewHolderItem);
        return onCreateViewHolderItem;
    }

    public final void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (this.mEntities != null) {
            onBindViewHolderItem(viewHolder, this.mEntities.get(i));
        }
    }

    public List<E> getAllEntity() {
        return this.mEntities;
    }

    public void addEntity(E e) {
        if (this.mEntities != null) {
            this.mEntities.add(e);
            notifyItemRangeInserted(getItemCount() - 1, 1);
        }
    }

    public void removeEntity(E e) {
        if (this.mEntities != null) {
            removeEntity(this.mEntities.indexOf(e));
        }
    }

    public void removeEntity(int i) {
        if (this.mEntities != null) {
            this.mEntities.remove(i);
            notifyItemRemoved(i);
        }
    }

    public final int getItemCount() {
        if (this.mEntities != null) {
            return this.mEntities.size();
        }
        return 0;
    }
}
