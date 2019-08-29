package com.amap.bundle.drive.common.basepage.control.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class StatusBarBatteryStateReceiver extends BroadcastReceiver {
    public a a = null;

    public interface a {
        void onBatteryCharging(int i);

        void onBatteryNormal(int i);
    }

    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "android.intent.action.BATTERY_CHANGED")) {
            int i = (intent.getExtras().getInt(H5PermissionManager.level) * 100) / intent.getExtras().getInt(WidgetType.SCALE);
            int intExtra = intent.getIntExtra("status", -1);
            if (intExtra == 1 || intExtra == 2 || intExtra == 3 || intExtra == 4 || intExtra == 5) {
                if (intExtra == 2 || intExtra == 5) {
                    if (this.a != null) {
                        this.a.onBatteryCharging(i);
                    }
                } else if (this.a != null) {
                    this.a.onBatteryNormal(i);
                }
            }
        }
    }
}
