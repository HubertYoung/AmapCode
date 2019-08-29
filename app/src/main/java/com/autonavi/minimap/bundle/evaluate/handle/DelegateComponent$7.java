package com.autonavi.minimap.bundle.evaluate.handle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.bundle.evaluate.delegate.StatisticOnItemClickListener;

public class DelegateComponent$7 extends StatisticOnItemClickListener {
    final /* synthetic */ cwz this$0;
    final /* synthetic */ OnItemClickListener val$itemClickListener;

    public DelegateComponent$7(cwz cwz, OnItemClickListener onItemClickListener) {
        this.this$0 = cwz;
        this.val$itemClickListener = onItemClickListener;
    }

    public void batOnItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        Handler handler = cxi.a().a;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage();
            obtainMessage.what = 256;
            obtainMessage.arg1 = 261;
            obtainMessage.obj = view;
            Bundle bundle = new Bundle();
            cxk.a();
            bundle.putString("_view_name", cxk.c());
            StringBuilder sb = new StringBuilder(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
            sb.append(i);
            sb.append("$i");
            sb.append(j);
            bundle.putString("_location_property", sb.toString());
            obtainMessage.setData(bundle);
            handler.sendMessage(obtainMessage);
        }
        try {
            AccessibilityDelegate accessibilityDelegate = (AccessibilityDelegate) cwz.c.get(view);
            if (accessibilityDelegate == null || (accessibilityDelegate instanceof cwv)) {
                try {
                    view.setAccessibilityDelegate(null);
                    this.val$itemClickListener.onItemClick(adapterView, view, i, j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    view.setAccessibilityDelegate(null);
                    this.val$itemClickListener.onItemClick(adapterView, view, i, j);
                    view.setAccessibilityDelegate(accessibilityDelegate);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
    }
}
