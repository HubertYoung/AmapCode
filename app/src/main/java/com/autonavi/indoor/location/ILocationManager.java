package com.autonavi.indoor.location;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.indoor.provider.BLEHelper;
import com.autonavi.indoor.util.L;
import java.util.ArrayList;

public class ILocationManager {
    public String mBuildingID = "";
    public Configuration mConfiguration;
    public volatile boolean mIsInited = false;
    public volatile boolean mIsLocating = false;
    public HandlerThread mLocationThread = null;
    public ArrayList<Handler> mOutterHandlers = new ArrayList<>();

    public boolean isPdrEnable() {
        return true;
    }

    public void recordLocationData(double d, double d2, int i) {
    }

    public void init(String str, Configuration configuration, Handler handler) {
        throw new RuntimeException("Subclass should implement this method");
    }

    public void requestLocationUpdates(Handler handler) {
        throw new RuntimeException("Subclass should implement this method");
    }

    public void removeUpdates(Handler handler) {
        throw new RuntimeException("Subclass should implement this method");
    }

    public boolean isInited() {
        return this.mConfiguration != null && this.mIsInited;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public void destroy() {
        throw new RuntimeException("Subclass should implement this method");
    }

    /* access modifiers changed from: protected */
    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.BLUETOOTH"})
    public boolean checkSensor(Configuration configuration, Handler handler) {
        if (configuration.isUsingBLE()) {
            try {
                if (!new BLEHelper(configuration.context).hasBluetooth()) {
                    handler.sendEmptyMessage(MessageCode.MSG_BLE_NOT_SUPPORT);
                    return false;
                }
            } catch (SecurityException e) {
                if (L.isLogging) {
                    L.d((Throwable) e);
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isLocating() {
        return this.mIsLocating;
    }

    /* access modifiers changed from: protected */
    public void checkInit() {
        if (!isInited()) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("checkInit. mConfiguration is ");
                sb.append(this.mConfiguration == null ? MiscUtil.NULL_STR : "not null");
                sb.append(", mIsInited:");
                sb.append(this.mIsInited);
                L.d(sb.toString());
            }
            throw new IllegalStateException("OnlineLocator must be init with mConfiguration before using");
        }
    }

    /* access modifiers changed from: protected */
    public boolean isSimulating(Configuration configuration) {
        return !TextUtils.isEmpty(configuration.mSimulateFile);
    }

    public void setPdr(int i, float f) {
        throw new RuntimeException("Subclass should implement this method");
    }
}
