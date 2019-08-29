package com.autonavi.minimap.bundle.frequentlocation.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.minimap.bundle.frequentlocation.entity.FrequentLocationData;

public class FrequentLocationView$2 implements OnItemClickListener {
    final /* synthetic */ cyl this$0;

    public FrequentLocationView$2(cyl cyl) {
        this.this$0 = cyl;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (!this.this$0.f.getIsEditMode()) {
            Object itemAtPosition = this.this$0.o.getItemAtPosition(i);
            if (itemAtPosition instanceof FrequentLocationData) {
                this.this$0.c.a((FrequentLocationData) itemAtPosition);
            }
        }
    }
}
