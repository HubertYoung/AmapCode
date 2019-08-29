package com.autonavi.minimap.drive.trafficboard.widget;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.bundle.entity.infolite.internal.Condition;

public class FilterPopup$5 implements OnItemClickListener {
    final /* synthetic */ djz this$0;

    public FilterPopup$5(djz djz) {
        this.this$0 = djz;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Condition item = this.this$0.c.getItem(i);
        if (item != null) {
            if (item.subConditions == null || item.subConditions.size() == 0) {
                this.this$0.l.checkedValue = item.value;
                this.this$0.l.name = item.name;
                this.this$0.l.value = item.value;
                if (this.this$0.r != null) {
                    a aVar = this.this$0.r;
                    int i2 = this.this$0.q;
                    djz.a(this.this$0, this.this$0.l);
                    aVar.a(i2, this.this$0.l);
                }
                this.this$0.a();
                return;
            }
            this.this$0.c.setSelection(i);
            this.this$0.c.notifyDataSetChanged();
            this.this$0.d.setData(item.subConditions);
            this.this$0.d.notifyDataSetChanged();
        }
    }
}
