package com.autonavi.map.search.view;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import com.autonavi.minimap.R;

public abstract class FloatingBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private final SparseBooleanArray mGroupExpandedMap = new SparseBooleanArray();

    /* access modifiers changed from: protected */
    public abstract View getChildGroupView(int i, boolean z, View view, ViewGroup viewGroup);

    public final View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        if (view != null) {
            Object tag = view.getTag(R.id.fgelv_tag_changed_visibility);
            if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
                view.setVisibility(0);
            }
            view.setTag(R.id.fgelv_tag_changed_visibility, null);
        }
        this.mGroupExpandedMap.put(i, z);
        return getChildGroupView(i, z, view, viewGroup);
    }

    public void onGroupExpanded(int i) {
        this.mGroupExpandedMap.put(i, true);
    }

    public void onGroupCollapsed(int i) {
        this.mGroupExpandedMap.put(i, false);
    }

    public boolean isGroupExpanded(int i) {
        Boolean valueOf = Boolean.valueOf(this.mGroupExpandedMap.get(i));
        if (valueOf != null) {
            return valueOf.booleanValue();
        }
        return false;
    }
}
