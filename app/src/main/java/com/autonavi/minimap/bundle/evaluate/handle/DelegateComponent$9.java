package com.autonavi.minimap.bundle.evaluate.handle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticAdapter.BatExpandableListAdapter;

public class DelegateComponent$9 extends BatExpandableListAdapter {
    final /* synthetic */ cwz this$0;
    final /* synthetic */ Context val$context;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public DelegateComponent$9(cwz cwz, ExpandableListAdapter expandableListAdapter, Context context) {
        // this.this$0 = cwz;
        // this.val$context = context;
        super(expandableListAdapter);
    }

    public void batListGetView(int i, View view, ViewGroup viewGroup) {
        if (view instanceof ViewGroup) {
            this.this$0.a((ViewGroup) view, this.val$context);
        } else {
            this.this$0.a(view);
        }
    }
}
