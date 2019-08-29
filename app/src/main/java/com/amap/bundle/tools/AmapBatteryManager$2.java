package com.amap.bundle.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.amap.bundle.logs.AMapLog;

public class AmapBatteryManager$2 extends BroadcastReceiver {
    final /* synthetic */ afq a;

    public AmapBatteryManager$2(afq afq) {
        this.a = afq;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                this.a.g.writeLock().lock();
                this.a.f = null;
            } catch (Exception e) {
                if (bno.a) {
                    AMapLog.debug("paas.tools", "AmapBatteryManager", Log.getStackTraceString(e));
                }
            } catch (Throwable th) {
                this.a.g.writeLock().unlock();
                throw th;
            }
            this.a.g.writeLock().unlock();
            String action = intent.getAction();
            if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
                if (bno.a) {
                    AMapLog.debug("paas.tools", "AmapBatteryManager", "POWER_CONNECTED");
                }
                afq.a(this.a, true);
                return;
            }
            if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
                if (bno.a) {
                    AMapLog.debug("paas.tools", "AmapBatteryManager", "POWER_DISCONNECTED");
                }
                afq.a(this.a, false);
            }
        }
    }
}
