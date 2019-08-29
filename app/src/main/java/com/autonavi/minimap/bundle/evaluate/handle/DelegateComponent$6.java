package com.autonavi.minimap.bundle.evaluate.handle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticExpandableListListener.StatisticChildClickListener;

public class DelegateComponent$6 extends StatisticChildClickListener {
    final /* synthetic */ cwz this$0;
    final /* synthetic */ OnChildClickListener val$childClickListener;

    public DelegateComponent$6(cwz cwz, OnChildClickListener onChildClickListener) {
        this.this$0 = cwz;
        this.val$childClickListener = onChildClickListener;
    }

    public boolean batOnChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j) {
        long j2;
        int i3;
        int i4;
        View view2;
        Handler handler = cxi.a().a;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage();
            obtainMessage.what = 256;
            obtainMessage.arg1 = 262;
            view2 = view;
            obtainMessage.obj = view2;
            Bundle bundle = new Bundle();
            cxk.a();
            bundle.putString("_view_name", cxk.c());
            StringBuilder sb = new StringBuilder(SuperId.BIT_1_NAVI);
            i4 = i;
            sb.append(i4);
            sb.append("$c");
            i3 = i2;
            sb.append(i3);
            sb.append("$i");
            j2 = j;
            sb.append(j2);
            bundle.putString("_location_property", sb.toString());
            obtainMessage.setData(bundle);
            handler.sendMessage(obtainMessage);
        } else {
            view2 = view;
            i4 = i;
            i3 = i2;
            j2 = j;
        }
        return this.val$childClickListener.onChildClick(expandableListView, view2, i4, i3, j2);
    }
}
