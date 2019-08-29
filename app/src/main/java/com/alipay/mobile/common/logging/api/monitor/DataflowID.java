package com.alipay.mobile.common.logging.api.monitor;

import java.util.HashMap;
import java.util.Map;

public enum DataflowID {
    STATISTIC("STATISTIC"),
    SAMPLE_STATS("STATS"),
    AMNET_SYNC("SYNC"),
    AMNET_PUSH("PUSH"),
    HTTPCLIENT_RPC("HC_RPC"),
    HTTPCLIENT_H5("HC_H5"),
    HTTPCLIENT_DJANGO("HC_DJG"),
    HTTPCLIENT_NBNET("HC_NBN"),
    HTTPCLIENT_DOWNLOAD("HC_DL"),
    HTTPCLIENT_MDAP("HC_MDAP"),
    WEB_SOCKET("WEB_SOC"),
    H5_NATIVE("H5_GG"),
    H5_UCCORE("H5_UC"),
    H5_ASPECT("H5_AOP"),
    MULTIMEDIA_DJANGO("MM_J"),
    MULTIMEDIA_NATIVE("MM_C"),
    ASPECT_V1("AOP1"),
    ASPECT_V2("AOP2"),
    ASPECT_V3("AOP3"),
    MONITOR("DIAG"),
    MDAP_LOG("MDAP"),
    UNKNOWN("NA");
    
    @Deprecated
    public static final String DEFAULT_BUNDLE = "bundle";
    @Deprecated
    public static final String DEFAULT_DIAGNOSE = "diagnose";
    @Deprecated
    public static final String DEFAULT_FALSE = "0";
    @Deprecated
    public static final String DEFAULT_TRUE = "1";
    @Deprecated
    public static final String HOST_NBNET = "__host_nbnet__";
    @Deprecated
    public static final String HOST_PUSH = "__host_push__";
    @Deprecated
    public static final String HOST_SYNC = "__host_sync__";
    private static final Map<String, DataflowID> sStringToEnum = null;
    private String desc;

    static {
        int i;
        DataflowID[] values;
        sStringToEnum = new HashMap();
        for (DataflowID value : values()) {
            sStringToEnum.put(value.desc, value);
        }
    }

    private DataflowID(String desc2) {
        this.desc = desc2;
    }

    public final String getDes() {
        return this.desc;
    }

    public static DataflowID fromString(String symbol) {
        return sStringToEnum.get(symbol);
    }
}
