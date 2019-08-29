package com.autonavi.minimap.route.train.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TrainTicketFilterPopup$3 implements OnItemClickListener {
    final /* synthetic */ ejy this$0;

    public TrainTicketFilterPopup$3(ejy ejy) {
        this.this$0 = ejy;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.this$0.b = i;
        this.this$0.h.a(this.this$0.a, i, 0);
        this.this$0.g.setSelection(i);
        this.this$0.e();
    }
}
