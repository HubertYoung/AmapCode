package com.autonavi.minimap.bundle.evaluate.delegate;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public final class StatisticExpandableListListener {

    public static abstract class StatisticChildClickListener implements OnChildClickListener, b {
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
            return batOnChildClick(expandableListView, view, i, i2, j);
        }
    }

    public static abstract class StatisticGroupClickListener implements OnGroupClickListener, c {
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
            return batOnGroupClick(expandableListView, view, i, j);
        }
    }
}
