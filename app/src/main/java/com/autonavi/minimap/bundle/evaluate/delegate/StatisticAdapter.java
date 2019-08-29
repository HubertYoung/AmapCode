package com.autonavi.minimap.bundle.evaluate.delegate;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;

public final class StatisticAdapter {

    public static abstract class BatExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
        private ExpandableListAdapter listAdapter;

        /* access modifiers changed from: protected */
        public abstract void batListGetView(int i, View view, ViewGroup viewGroup);

        public BatExpandableListAdapter(ExpandableListAdapter expandableListAdapter) {
            this.listAdapter = expandableListAdapter;
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.listAdapter != null) {
                this.listAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            if (this.listAdapter != null) {
                this.listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }

        public int getGroupCount() {
            if (this.listAdapter != null) {
                return this.listAdapter.getGroupCount();
            }
            return 0;
        }

        public int getChildrenCount(int i) {
            if (this.listAdapter != null) {
                return this.listAdapter.getChildrenCount(i);
            }
            return 0;
        }

        public Object getGroup(int i) {
            if (this.listAdapter != null) {
                return this.listAdapter.getGroup(i);
            }
            return null;
        }

        public Object getChild(int i, int i2) {
            if (this.listAdapter != null) {
                return this.listAdapter.getChild(i, i2);
            }
            return null;
        }

        public long getGroupId(int i) {
            if (this.listAdapter != null) {
                return this.listAdapter.getGroupId(i);
            }
            return 0;
        }

        public long getChildId(int i, int i2) {
            if (this.listAdapter != null) {
                return this.listAdapter.getChildId(i, i2);
            }
            return 0;
        }

        public boolean hasStableIds() {
            if (this.listAdapter != null) {
                return this.listAdapter.hasStableIds();
            }
            return false;
        }

        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            if (this.listAdapter != null) {
                return this.listAdapter.getGroupView(i, z, view, viewGroup);
            }
            return null;
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            if (this.listAdapter == null) {
                return null;
            }
            View childView = this.listAdapter.getChildView(i, i2, z, view, viewGroup);
            batListGetView(i2, childView, viewGroup);
            return childView;
        }

        public boolean isChildSelectable(int i, int i2) {
            if (this.listAdapter != null) {
                return this.listAdapter.isChildSelectable(i, i2);
            }
            return false;
        }

        public boolean areAllItemsEnabled() {
            if (this.listAdapter != null) {
                return this.listAdapter.areAllItemsEnabled();
            }
            return false;
        }

        public boolean isEmpty() {
            if (this.listAdapter != null) {
                return this.listAdapter.isEmpty();
            }
            return true;
        }

        public void onGroupExpanded(int i) {
            if (this.listAdapter != null) {
                this.listAdapter.onGroupExpanded(i);
            }
        }

        public void onGroupCollapsed(int i) {
            if (this.listAdapter != null) {
                this.listAdapter.onGroupCollapsed(i);
            }
        }

        public long getCombinedChildId(long j, long j2) {
            if (this.listAdapter != null) {
                return this.listAdapter.getCombinedChildId(j, j2);
            }
            return 0;
        }

        public long getCombinedGroupId(long j) {
            if (this.listAdapter != null) {
                return this.listAdapter.getCombinedGroupId(j);
            }
            return 0;
        }
    }

    public static abstract class BatListAdapter extends BaseAdapter implements ListAdapter {
        private ListAdapter listAdapter;

        /* access modifiers changed from: protected */
        public abstract void batListGetView(int i, View view, ViewGroup viewGroup);

        public BatListAdapter(ListAdapter listAdapter2) {
            this.listAdapter = listAdapter2;
        }

        public boolean areAllItemsEnabled() {
            return this.listAdapter.areAllItemsEnabled();
        }

        public boolean isEnabled(int i) {
            return this.listAdapter.isEnabled(i);
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.listAdapter.registerDataSetObserver(dataSetObserver);
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.listAdapter.unregisterDataSetObserver(dataSetObserver);
        }

        public int getCount() {
            return this.listAdapter.getCount();
        }

        public Object getItem(int i) {
            return this.listAdapter.getItem(i);
        }

        public long getItemId(int i) {
            return this.listAdapter.getItemId(i);
        }

        public boolean hasStableIds() {
            return this.listAdapter.hasStableIds();
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = this.listAdapter.getView(i, view, viewGroup);
            batListGetView(i, view2, viewGroup);
            return view2;
        }

        public int getItemViewType(int i) {
            return this.listAdapter.getItemViewType(i);
        }

        public int getViewTypeCount() {
            return this.listAdapter.getViewTypeCount();
        }

        public boolean isEmpty() {
            return this.listAdapter.isEmpty();
        }
    }
}
