package com.alipay.mobile.common.logging.api.monitor;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BatteryModel {
    private boolean a;
    public String bundle;
    public String diagnose;
    public Map<String, String> params;
    public long power;
    public long time;
    public BatteryID type;

    public static BatteryModel obtain(BatteryID type2, long power2, String bundle2, String diagnose2) {
        return obtain(type2, 0, diagnose2);
    }

    public static BatteryModel obtain(BatteryID type2, long time2, String diagnose2) {
        BatteryModel batteryModel = a();
        batteryModel.recycle();
        batteryModel.type = type2;
        batteryModel.time = time2;
        batteryModel.diagnose = diagnose2;
        batteryModel.a = true;
        return batteryModel;
    }

    private static BatteryModel a() {
        return new BatteryModel();
    }

    private BatteryModel() {
    }

    public BatteryModel cloneMirror() {
        BatteryModel mirror = obtain(this.type, this.time, this.diagnose);
        mirror.power = this.power;
        mirror.bundle = this.bundle;
        mirror.a = this.a;
        if (this.params != null) {
            mirror.params = new HashMap();
            for (Entry entry : this.params.entrySet()) {
                mirror.params.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return mirror;
    }

    public void recycle() {
        this.a = false;
        this.type = BatteryID.UNKNOWN;
        this.time = 0;
        this.power = 0;
        this.bundle = null;
        this.diagnose = null;
        this.params = null;
    }

    public boolean isInUse() {
        return this.a;
    }

    public void report() {
        LoggerFactory.getMonitorLogger().battery(this);
    }

    public String getParam(String key) {
        if (this.params == null) {
            return null;
        }
        return this.params.get(key);
    }

    public BatteryModel putParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap();
        }
        this.params.put(key, value);
        return this;
    }

    public BatteryModel removeParam(String key) {
        if (this.params != null) {
            this.params.remove(key);
        }
        return this;
    }
}
