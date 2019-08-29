package com.autonavi.minimap.bundle.evaluate.handle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticAdapter.BatListAdapter;

public class DelegateComponent$11 extends BatListAdapter {
    final /* synthetic */ cwz this$0;
    final /* synthetic */ Context val$context;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public DelegateComponent$11(cwz cwz, ListAdapter listAdapter, Context context) {
        // this.this$0 = cwz;
        // this.val$context = context;
        super(listAdapter);
    }

    public void batListGetView(int i, View view, ViewGroup viewGroup) {
        if (view instanceof ViewGroup) {
            this.this$0.a((ViewGroup) view, this.val$context);
        } else {
            this.this$0.a(view);
        }
    }
}
