package com.autonavi.map.search.comment.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.search.view.FloatingBaseExpandableListAdapter;
import java.util.ArrayList;
import java.util.List;

public class FloatingExpandableListAdapter<G, C> extends FloatingBaseExpandableListAdapter {
    private int mChildLayoutId;
    public Context mContext;
    private int mGroupLayoutId;
    protected List<GroupList<G, C>> mGroupLists;

    public long getChildId(int i, int i2) {
        return (long) ((i * 1000000) + i2);
    }

    public long getGroupId(int i) {
        return (long) i;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onBindChildViewHolder(int i, int i2, boolean z, bvy bvy) {
    }

    /* access modifiers changed from: protected */
    public void onBindGroupViewHolder(int i, boolean z, bvy bvy) {
    }

    public FloatingExpandableListAdapter(Context context) {
        this(context, 0);
    }

    public FloatingExpandableListAdapter(Context context, int i) {
        this(context, i, 0);
    }

    public FloatingExpandableListAdapter(Context context, int i, int i2) {
        this.mGroupLists = new ArrayList();
        this.mContext = context;
        this.mGroupLayoutId = i;
        this.mChildLayoutId = i2;
    }

    public List<GroupList<G, C>> getGroupLists() {
        return this.mGroupLists;
    }

    public void setGroupLists(List<GroupList<G, C>> list) {
        this.mGroupLists.clear();
        this.mGroupLists.addAll(list);
        notifyDataSetChanged();
    }

    public View getChildGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        bvy onCreateGroupViewHolder = onCreateGroupViewHolder(i, z, view);
        onBindGroupViewHolder(i, z, onCreateGroupViewHolder);
        if (onCreateGroupViewHolder != null) {
            return onCreateGroupViewHolder.a;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public bvy onCreateGroupViewHolder(int i, boolean z, View view) {
        if (this.mGroupLayoutId > 0) {
            return createGroupViewHolder(view, this.mGroupLayoutId);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final bvy createGroupViewHolder(View view, int i) {
        if (view != null) {
            return (bvy) view.getTag();
        }
        bvy bvy = new bvy(this.mContext, i);
        bvy.a.setTag(bvy);
        return bvy;
    }

    public int getGroupCount() {
        return this.mGroupLists.size();
    }

    public int getChildrenCount(int i) {
        return this.mGroupLists.get(i).size();
    }

    public Object getGroup(int i) {
        return this.mGroupLists.get(i);
    }

    public Object getChild(int i, int i2) {
        return this.mGroupLists.get(i).get(i2);
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        bvy onCreateChildViewHolder = onCreateChildViewHolder(i, i2, z, view);
        onBindChildViewHolder(i, i2, z, onCreateChildViewHolder);
        if (onCreateChildViewHolder != null) {
            return onCreateChildViewHolder.a;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public bvy onCreateChildViewHolder(int i, int i2, boolean z, View view) {
        if (this.mChildLayoutId > 0) {
            return createChildViewHolder(view, this.mChildLayoutId);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final bvy createChildViewHolder(View view, int i) {
        if (view != null) {
            return (bvy) view.getTag();
        }
        bvy bvy = new bvy(this.mContext, i);
        bvy.a.setTag(bvy);
        return bvy;
    }
}
