package com.autonavi.minimap.basemap.save.controller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SaveSearchUIController$4 implements OnItemClickListener {
    final /* synthetic */ cqz this$0;
    final /* synthetic */ crc val$ld;

    public SaveSearchUIController$4(cqz cqz, crc crc) {
        this.this$0 = cqz;
        this.val$ld = crc;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.val$ld.dismiss();
    }
}
