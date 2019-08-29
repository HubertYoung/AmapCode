package com.autonavi.minimap.basemap.save.controller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SaveSearchUIController$7 implements OnItemClickListener {
    final /* synthetic */ cqz this$0;
    final /* synthetic */ String[] val$F_SELECT_ACTION;

    public SaveSearchUIController$7(cqz cqz, String[] strArr) {
        this.this$0 = cqz;
        this.val$F_SELECT_ACTION = strArr;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.this$0.c = this.val$F_SELECT_ACTION[i];
        if (this.this$0.g != null) {
            this.this$0.g.a(this.this$0.c);
        }
    }
}
