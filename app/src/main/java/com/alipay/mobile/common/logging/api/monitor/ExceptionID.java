package com.alipay.mobile.common.logging.api.monitor;

import java.util.HashMap;
import java.util.Map;

public enum ExceptionID {
    MONITORPOINT_CRASH("MonitorPoint_Crash"),
    MONITORPOINT_IGNORE_CRASH("MonitorPoint_Ignore_Crash"),
    MONITORPOINT_INVALID_CRASH("MonitorPoint_Invalid_Crash"),
    MONITORPOINT_SUB_THREAD_CRASH("MonitorPoint_Sub_Thread_Crash"),
    MONITORPOINT_CLIENTSERR("MonitorPoint_ClientsErr"),
    MONITORPOINT_CONNECTERR("MonitorPoint_ConnectErr"),
    MONITORPOINT_BIZ_EXCEPTION("MonitorPoint_Biz_Exception");
    
    public static final String UNKNOWN_THREAD = "unknown";
    private static final Map<String, ExceptionID> sStringToEnum = null;
    private String desc;

    static {
        int i;
        ExceptionID[] values;
        sStringToEnum = new HashMap();
        for (ExceptionID value : values()) {
            sStringToEnum.put(value.desc, value);
        }
    }

    private ExceptionID(String desc2) {
        this.desc = desc2;
    }

    public final String getDes() {
        return this.desc;
    }

    public static ExceptionID fromString(String symbol) {
        return sStringToEnum.get(symbol);
    }
}
