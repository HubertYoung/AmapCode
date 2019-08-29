package com.autonavi.map.template;

import android.view.View;
import android.view.ViewGroup;
import com.autonavi.map.template.AbstractViewHolderAdapter.a;

public abstract class AbstractViewHolderAdapter<VH extends a> extends AbstractBaseAdapter {

    public static class a {
        public final View itemView;
        private int mPosition;

        public a(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        /* access modifiers changed from: 0000 */
        public final void setPosition(int i) {
            this.mPosition = i;
        }

        public final int getPosition() {
            return this.mPosition;
        }
    }

    public Object getChild(int i, int i2) {
        return null;
    }

    public long getChildId(int i, int i2) {
        return 0;
    }

    public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
        return null;
    }

    public int getChildrenCount(int i) {
        return 0;
    }

    public Object getGroup(int i) {
        return null;
    }

    public long getGroupId(int i) {
        return 0;
    }

    public int getItemViewHolderType(int i) {
        return 0;
    }

    public int getViewHolderTypeCount() {
        return 1;
    }

    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    public abstract void onBindViewHolder(VH vh, int i, int i2);

    public abstract View onCreateView(ViewGroup viewGroup, int i);

    public abstract VH onCreateViewHolder(View view, ViewGroup viewGroup, int i);

    public final View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        int itemViewType = getItemViewType(i);
        if (view == null) {
            view = onCreateView(viewGroup, itemViewType);
            aVar = onCreateViewHolder(view, viewGroup, itemViewType);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.setPosition(i);
        onBindViewHolder(aVar, i, itemViewType);
        return view;
    }

    public final int getItemViewType(int i) {
        return getItemViewHolderType(i);
    }

    public final int getViewTypeCount() {
        return getViewHolderTypeCount();
    }

    public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
        a aVar;
        int itemViewType = getItemViewType(i);
        if (view == null) {
            view = onCreateView(viewGroup, itemViewType);
            aVar = onCreateViewHolder(view, viewGroup, itemViewType);
            aVar.setPosition(i);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        onBindViewHolder(aVar, i, itemViewType);
        return view;
    }
}
