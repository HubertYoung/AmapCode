package com.alipay.mobile.tinyappcommon.h5plugin;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.nebula.util.H5Log;

public class TinyGetBatteryInfoPlugin extends H5SimplePlugin {
    private static final String GET_BATTERY_INFO = "getBatteryInfo";
    private static final String TAG = "TinyGetBatteryInfoPlugin";
    private volatile boolean isRegisterBatteryReceiver = false;
    private BroadcastReceiver mBatterInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean batteryPlugged = false;
            try {
                int batteryLevel = intent.getIntExtra(H5PermissionManager.level, 0);
                if (intent.getIntExtra("plugged", 0) != 0) {
                    batteryPlugged = true;
                }
                if (TinyGetBatteryInfoPlugin.this.mH5BridgeContext != null) {
                    JSONObject result = new JSONObject();
                    result.put((String) H5PermissionManager.level, (Object) Integer.valueOf(batteryLevel));
                    result.put((String) "isCharging", (Object) Boolean.valueOf(batteryPlugged));
                    TinyGetBatteryInfoPlugin.this.mH5BridgeContext.sendBridgeResult(result);
                    TinyGetBatteryInfoPlugin.this.mH5BridgeContext = null;
                }
            } catch (Throwable e) {
                H5Log.e((String) TinyGetBatteryInfoPlugin.TAG, e);
            }
            TinyGetBatteryInfoPlugin.this.unRegisterBatteryReceiver();
        }
    };
    private IntentFilter mBatteryInfoFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    /* access modifiers changed from: private */
    public volatile H5BridgeContext mH5BridgeContext;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_BATTERY_INFO);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (GET_BATTERY_INFO.equals(event.getAction())) {
            registerBatteryReceiver();
            this.mH5BridgeContext = context;
        }
        return true;
    }

    private void registerBatteryReceiver() {
        try {
            if (!this.isRegisterBatteryReceiver) {
                this.isRegisterBatteryReceiver = true;
                MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                if (microApplicationContext != null) {
                    Application application = microApplicationContext.getApplicationContext();
                    if (application != null) {
                        application.registerReceiver(this.mBatterInfoReceiver, this.mBatteryInfoFilter);
                    } else {
                        H5Log.d(TAG, "register battery receiver failed");
                    }
                } else {
                    H5Log.d(TAG, "register battery receiver failed");
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }

    /* access modifiers changed from: private */
    public void unRegisterBatteryReceiver() {
        try {
            if (this.isRegisterBatteryReceiver) {
                this.isRegisterBatteryReceiver = false;
                MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                if (microApplicationContext != null) {
                    Application application = microApplicationContext.getApplicationContext();
                    if (application != null) {
                        application.unregisterReceiver(this.mBatterInfoReceiver);
                    }
                }
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
        }
    }
}
