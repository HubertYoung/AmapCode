package com.alipay.mobile.common.logging.api.monitor;

import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import java.util.HashMap;
import java.util.Map;

public enum PerformanceID {
    MONITORPOINT_NETWORK("network"),
    MONITORPOINT_WEBAPP(H5MonitorLogConfig.WEBAPP_TYPE),
    MONITORPOINT_H5EXCEPTION(H5MonitorLogConfig.H5EXCEPTION_TYPE),
    MONITORPOINT_SDKMONITOR("sdkmonitor"),
    MONITORPOINT_SYNCLINK("synclink"),
    MONITORPOINT_SYNCPROTO("syncproto"),
    MONITORPOINT_PERFORMANCE(AjxDebugConfig.PERFORMANCE),
    MONITORPOINT_FOOTPRINT("footprint"),
    MONITORPOINT_KEYBIZTRACE("keybiztrace");
    
    private static final Map<String, PerformanceID> sStringToEnum = null;
    private String desc;

    static {
        int i;
        PerformanceID[] values;
        sStringToEnum = new HashMap();
        for (PerformanceID value : values()) {
            sStringToEnum.put(value.desc, value);
        }
    }

    private PerformanceID(String desc2) {
        this.desc = desc2;
    }

    public final String getDes() {
        return this.desc;
    }

    public static PerformanceID fromString(String symbol) {
        return sStringToEnum.get(symbol);
    }
}
