package com.autonavi.minimap.drive.trafficboard.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.bundle.entity.infolite.internal.Condition;

public class FilterPopup$6 implements OnItemClickListener {
    final /* synthetic */ djz this$0;

    public FilterPopup$6(djz djz) {
        this.this$0 = djz;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Condition item = this.this$0.d.getItem(i);
        if (item != null) {
            this.this$0.l.checkedValue = item.value;
            this.this$0.l.name = item.name;
            this.this$0.l.value = item.value;
            if (this.this$0.r != null) {
                a aVar = this.this$0.r;
                int i2 = this.this$0.q;
                djz.a(this.this$0, this.this$0.l);
                aVar.a(i2, this.this$0.l);
            }
        }
        this.this$0.a();
    }
}
