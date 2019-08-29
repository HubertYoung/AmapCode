package com.alibaba.analytics.core.selfmonitor;

import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import com.alibaba.analytics.core.logbuilder.SessionTimeAndIndexMgr;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;

public class ConfigArrivedMonitor implements IKVChangeListener {
    public static final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();
    private String mCurrentValue = null;

    public void start() {
        SystemConfigMgr.getInstance().register("test_config_arrival_rate", this);
    }

    public void end() {
        SystemConfigMgr.getInstance().register("test_config_arrival_rate", this);
    }

    public void onChange(String str, String str2) {
        Logger.d((String) null, "key", str, "value", str2);
        if (str2 != null && !str2.equalsIgnoreCase(this.mCurrentValue)) {
            HashMap hashMap = new HashMap();
            hashMap.put("value", str2);
            hashMap.put("arrival_time", Long.valueOf(System.currentTimeMillis() / 1000));
            hashMap.put("app_start_time", Long.valueOf(SessionTimeAndIndexMgr.getInstance().getSessionTimestamp()));
            mMonitor.onEvent(SelfMonitorEvent.buildCountEvent(SelfMonitorEvent.CONFIG_ARRIVE, JSON.toJSONString(hashMap), Double.valueOf(1.0d)));
            Logger.d((String) null, "json", JSON.toJSONString(hashMap));
        }
        this.mCurrentValue = str2;
    }
}
