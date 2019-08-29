package com.autonavi.minimap.bundle.evaluate.handle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticExpandableListListener.StatisticGroupClickListener;

public class DelegateComponent$5 extends StatisticGroupClickListener {
    final /* synthetic */ cwz this$0;
    final /* synthetic */ OnGroupClickListener val$groupClickListener;

    public DelegateComponent$5(cwz cwz, OnGroupClickListener onGroupClickListener) {
        this.this$0 = cwz;
        this.val$groupClickListener = onGroupClickListener;
    }

    public boolean batOnGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
        Handler handler = cxi.a().a;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage();
            obtainMessage.what = 256;
            obtainMessage.arg1 = 263;
            obtainMessage.obj = view;
            Bundle bundle = new Bundle();
            cxk.a();
            bundle.putString("_view_name", cxk.c());
            StringBuilder sb = new StringBuilder(SuperId.BIT_1_NAVI);
            sb.append(i);
            sb.append("$i");
            sb.append(j);
            bundle.putString("_location_property", sb.toString());
            obtainMessage.setData(bundle);
            handler.sendMessage(obtainMessage);
        }
        return this.val$groupClickListener.onGroupClick(expandableListView, view, i, j);
    }
}
