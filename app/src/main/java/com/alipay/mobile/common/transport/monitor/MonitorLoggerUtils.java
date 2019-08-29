package com.alipay.mobile.common.transport.monitor;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import tv.danmaku.ijk.media.encode.FFmpegSessionConfig;

public class MonitorLoggerUtils {
    public static final String LIB_VERSION = "LIBV";
    public static String LIB_VERSION_BIFROST = "25";
    public static String LIB_VERSION_BIFROST_BDRD = FFmpegSessionConfig.CRF_27;
    public static String LIB_VERSION_BIFROST_HTTP2 = "29";
    public static String LIB_VERSION_BIFROST_HTTP2_BDRD = "31";
    public static String LIB_VERSION_OLD = "24";
    public static String LIB_VERSION_OLD_BDRD = "26";
    public static final String LOG_LEVEL_DEBUG = "DEBUG";
    public static final String LOG_LEVEL_FATAL = "FATAL";
    public static final String LOG_LEVEL_INFO = "INFO";
    public static final String NETTUNNEL = "NETTUNNEL";
    public static final String NETTUNNEL_ULib_h2 = "ULib_h2";
    public static final String REPORT_BIZ_NAME = "network";

    public static String getLogBizType(String subType) {
        try {
            if (TextUtils.equals(subType, "RPC")) {
                return "RPC";
            }
            if (TextUtils.equals(subType, "MMTP") || TextUtils.equals(subType, "TCP_STACK")) {
                return "MMTP";
            }
            if (TextUtils.equals(subType, "MASS") || TextUtils.equals(subType, NetworkServiceTracer.REPORT_SUB_NAME_DJG) || TextUtils.equals(subType, NetworkServiceTracer.REPORT_SUB_NAME_RSRC)) {
                return "MASS";
            }
            if (TextUtils.equals(subType, "H5")) {
                return MonitorItemConstants.PARTITION_NAME;
            }
            if (TextUtils.equals(subType, "HTTPDNS") || TextUtils.equals(subType, "SignalState") || TextUtils.equals(subType, "NetDiago") || TextUtils.equals(subType, "TunnelChange") || TextUtils.equals(subType, "NETQOS") || TextUtils.equals(subType, "NetChange")) {
                return "MISC";
            }
            if (TextUtils.equals(subType, "LOG")) {
                return "mdaplog";
            }
            return "network";
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MonitorLoggerUtils", "getLogBizType,ex:" + ex.toString());
            return "network";
        }
    }

    public static void uploadPerfLog(Performance pf) {
        uploadPerfLog(pf, null);
    }

    public static void uploadPerfLog(Performance pf, TransportContext transportContext) {
        try {
            if (TransportStrategy.isVip() && a()) {
                LoggerFactory.getMonitorLogger().setUploadSize(LogCategory.CATEGORY_NETWORK, b());
            }
            b(pf);
            String bizType = getLogBizType(pf.getSubType());
            pf.setParam1(bizType);
            a(pf, transportContext);
            LoggerFactory.getMonitorLogger().performance(bizType, pf);
        } catch (Throwable ex) {
            LogCatUtil.error("MonitorLoggerUtils", "uploadPerfLog exception", ex);
        }
    }

    private static void a(Performance pf, TransportContext transportContext) {
        if (transportContext == null) {
            a(pf);
            return;
        }
        if (transportContext != null && !TextUtils.isEmpty(transportContext.loggerLevel)) {
            try {
                pf.setLoggerLevel(Integer.parseInt(transportContext.loggerLevel));
                LogCatUtil.info("MonitorLoggerUtils", "[setLogLevel] loggerLevel = " + transportContext.loggerLevel);
                return;
            } catch (Throwable th) {
                LogCatUtil.warn((String) "MonitorLoggerUtils", "[uploadPerfLog] parse logger level fail. loggerLevel = " + transportContext.loggerLevel);
            }
        }
        a(pf);
    }

    private static void a(Performance pf) {
        String logLevel = pf.getParam2();
        if (TextUtils.equals(logLevel, "DEBUG")) {
            pf.setLoggerLevel(3);
        } else if (TextUtils.equals(logLevel, "INFO")) {
            pf.setLoggerLevel(2);
        } else if (TextUtils.equals(logLevel, "FATAL")) {
            pf.setLoggerLevel(1);
        }
    }

    public static void uploadDiagLog(Performance pf) {
        try {
            pf.getExtPramas().put("SUBTYPE", "DIAG");
            String bizType = getLogBizType(pf.getSubType());
            pf.setParam1(bizType);
            LoggerFactory.getMonitorLogger().performance(bizType, pf);
            LoggerFactory.getLogContext().flush(LogCategory.CATEGORY_NETWORK, true);
            LoggerFactory.getLogContext().upload(LogCategory.CATEGORY_NETWORK);
        } catch (Throwable ex) {
            LogCatUtil.error("MonitorLoggerUtils", "uploadDiagLog exception", ex);
        }
    }

    public static void uploadAutoDiagLog(Performance pf) {
        try {
            b(pf);
            pf.getExtPramas().put("SUBTYPE", "DIAG");
            if (TransportStrategy.isVip() && a()) {
                LoggerFactory.getMonitorLogger().setUploadSize(LogCategory.CATEGORY_FOOTPRINT, b());
                LoggerFactory.getMonitorLogger().footprint("network", pf.getSubType(), pf.getParam1(), pf.getParam2(), pf.getParam3(), pf.getExtPramas());
            }
        } catch (Throwable ex) {
            LogCatUtil.error("MonitorLoggerUtils", "uploadAutoDiagLog exception", ex);
        }
    }

    private static void b(Performance pf) {
        c(pf);
        d(pf);
    }

    private static void c(Performance pf) {
        try {
            String libVersion = pf.getExtPramas().get(LIB_VERSION);
            if (TransportStrategy.isEnableBifrost()) {
                if (TextUtils.isEmpty(libVersion)) {
                    pf.getExtPramas().put(LIB_VERSION, LIB_VERSION_OLD);
                }
            } else if (!TextUtils.isEmpty(libVersion) && TextUtils.equals(libVersion, LIB_VERSION_OLD)) {
                pf.getExtPramas().remove(LIB_VERSION);
            }
        } catch (Throwable e) {
            LogCatUtil.error("MonitorLoggerUtils", "setAmnetLibVersion exception", e);
        }
    }

    private static void d(Performance pf) {
        try {
            String libVersion = pf.getExtPramas().get(LIB_VERSION);
            if (TextUtils.isEmpty(libVersion) || !TransportStrategy.isDisableBifrostRpcDowngrade()) {
                return;
            }
            if (TextUtils.equals(LIB_VERSION_OLD, libVersion)) {
                pf.getExtPramas().remove(LIB_VERSION);
                pf.getExtPramas().put(LIB_VERSION, LIB_VERSION_OLD_BDRD);
            } else if (TextUtils.equals(LIB_VERSION_BIFROST, libVersion)) {
                pf.getExtPramas().remove(LIB_VERSION);
                pf.getExtPramas().put(LIB_VERSION, LIB_VERSION_BIFROST_BDRD);
            } else if (TextUtils.equals(LIB_VERSION_BIFROST_HTTP2, libVersion)) {
                pf.getExtPramas().remove(LIB_VERSION);
                pf.getExtPramas().put(LIB_VERSION, LIB_VERSION_BIFROST_HTTP2_BDRD);
            }
        } catch (Throwable e) {
            LogCatUtil.error("MonitorLoggerUtils", "updateAmnetLibVersion exception", e);
        }
    }

    private static boolean a() {
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.UPLOAD_ATONCE), "T")) {
            return true;
        }
        return false;
    }

    private static int b() {
        return TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.LOG_UPLOAD_SIZE, 10);
    }
}
