package com.amap.bundle.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.Iterator;

public class AmapBatteryManager$4 extends BroadcastReceiver {
    final /* synthetic */ afq a;

    public AmapBatteryManager$4(afq afq) {
        this.a = afq;
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
            float intExtra = ((float) intent.getIntExtra(H5PermissionManager.level, 0)) / (((float) intent.getIntExtra(WidgetType.SCALE, 1)) * 1.0f);
            if (bno.a) {
                AMapLog.debug("paas.tools", "AmapBatteryManager", "scale:".concat(String.valueOf(intExtra)));
            }
            synchronized (this.a.j) {
                Iterator it = this.a.j.iterator();
                while (it.hasNext()) {
                    ((a) it.next()).a();
                }
            }
        }
    }
}
