package com.autonavi.widget.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class AlertController$AlertParams$1 implements OnItemClickListener {
    final /* synthetic */ a this$0;
    final /* synthetic */ erm val$alertController;

    AlertController$AlertParams$1(a aVar, erm erm) {
        this.this$0 = aVar;
        this.val$alertController = erm;
    }

    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.this$0.s.onClick(this.val$alertController.b, i);
    }
}
