package com.alipay.mobile.common.transport.monitor;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.monitor.DeviceTrafficStateInfo.DeviceTrafficStateInfoDelta;
import com.alipay.mobile.common.transport.monitor.lbs.LBSManager;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import java.util.Map;
import java.util.Random;

public class RPCDataParser {
    public static final String BOUND_SYMBOL = ",";
    public static final String PLACE_HOLDER = "-";
    public static final String TIME_MS = "ms";

    public static final void monitorLog(TransportContext netContext) {
        if (netContext != null && netContext.dcList != null && !netContext.dcList.isEmpty()) {
            try {
                parserContext(netContext);
            } catch (Exception ex) {
                LogCatUtil.error("RPCDataParser", "RPC监控日志统计异常", ex);
            }
        }
    }

    public static void parserContext(TransportContext srnc) {
        boolean logup = isLogUp(srnc);
        if (TextUtils.isEmpty(srnc.api)) {
            buildAndWriteLog(srnc, logup, "https");
        } else {
            a(srnc, logup);
        }
    }

    private static void a(TransportContext srnc, boolean logup) {
        try {
            buildAndWriteLog(srnc, logup, ExtTransportStrategy.EXT_PROTO_SPDY);
        } catch (Exception e) {
            LogCatUtil.warn((String) "RPCDataParser", (Throwable) e);
        }
        try {
            buildAndWriteLog(srnc, logup, ExtTransportStrategy.EXT_PROTO_MRPC);
        } catch (Exception e2) {
            LogCatUtil.warn((String) "RPCDataParser", (Throwable) e2);
        }
        try {
            buildAndWriteLog(srnc, logup, "https");
        } catch (Exception e3) {
            LogCatUtil.warn((String) "RPCDataParser", (Throwable) e3);
        }
    }

    static boolean isLogUp(TransportContext srnc) {
        if (!TextUtils.isEmpty(srnc.loggerLevel)) {
            return true;
        }
        if (srnc.logRandom > 0) {
            if (new Random().nextInt(srnc.logRandom) == 0) {
                return true;
            }
            return false;
        } else if (srnc.logRandom == 0) {
            return true;
        } else {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x018c  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x01a5  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0253  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0383  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x03a1  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0469  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x047c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void buildAndWriteLog(com.alipay.mobile.common.transport.context.TransportContext r11, boolean r12, java.lang.String r13) {
        /*
            java.util.Map<java.lang.String, com.alipay.mobile.common.transport.monitor.DataContainer> r8 = r11.dcList
            java.lang.Object r0 = r8.get(r13)
            com.alipay.mobile.common.transport.monitor.DataContainer r0 = (com.alipay.mobile.common.transport.monitor.DataContainer) r0
            if (r0 != 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            r3 = 0
            com.alipay.mobile.common.transport.monitor.TransportPerformance r6 = new com.alipay.mobile.common.transport.monitor.TransportPerformance
            r6.<init>()
            c(r11, r6)
            java.lang.String r8 = r6.getSubType()
            java.lang.String r8 = com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils.getLogBizType(r8)
            r6.setParam1(r8)
            java.lang.String r8 = "INFO"
            r6.setParam2(r8)
            java.lang.String r8 = "https"
            boolean r8 = r8.equals(r13)
            if (r8 == 0) goto L_0x003f
            java.lang.String r8 = r11.url
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x003f
            java.net.URL r8 = new java.net.URL     // Catch:{ Throwable -> 0x042c }
            java.lang.String r9 = r11.url     // Catch:{ Throwable -> 0x042c }
            r8.<init>(r9)     // Catch:{ Throwable -> 0x042c }
            java.lang.String r13 = r8.getProtocol()     // Catch:{ Throwable -> 0x042c }
        L_0x003f:
            r6.setParam3(r13)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "NETTYPE"
            java.lang.String r10 = r11.getNetType()
            r8.put(r9, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "DNS_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "TCP_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SSL_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ALL_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "REQ_SIZE"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RES_SIZE"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "DOWN_TRAFFIC"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "UUID"
            java.lang.String r10 = r11.rpcUUID
            r8.put(r9, r10)
            byte r8 = r11.bizType
            r9 = 2
            if (r8 == r9) goto L_0x00b4
            byte r8 = r11.bizType
            r9 = 3
            if (r8 == r9) goto L_0x00b4
            byte r8 = r11.bizType
            r9 = 4
            if (r8 == r9) goto L_0x00b4
            byte r8 = r11.bizType
            r9 = 5
            if (r8 != r9) goto L_0x0447
        L_0x00b4:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "API"
            java.lang.String r10 = r11.api
            r8.put(r9, r10)
        L_0x00bf:
            a(r11, r6)
        L_0x00c2:
            java.lang.String r8 = "ERROR"
            java.lang.String r8 = r0.getDataItem(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 == 0) goto L_0x0469
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RESULT"
            java.lang.String r10 = "T"
            r8.put(r9, r10)
            b(r0, r6)
        L_0x00dc:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ERROR"
            r10 = 0
            a(r8, r9, r0, r10)
            java.lang.String r8 = "DOWN"
            java.lang.String r8 = r0.getDataItem(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 == 0) goto L_0x047c
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "EXT1"
            java.lang.String r10 = "F"
            r8.put(r9, r10)
        L_0x00fd:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ATLS_DOWN"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "STALLED_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SENT_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "WAIT_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "PROXY"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "TARGET_HOST"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "TARGET_HOST_SHORT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "R_COOKIE"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "READ_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SA_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            com.alipay.mobile.common.transport.config.TransportConfigureManager r8 = com.alipay.mobile.common.transport.config.TransportConfigureManager.getInstance()
            com.alipay.mobile.common.transport.config.TransportConfigureItem r9 = com.alipay.mobile.common.transport.config.TransportConfigureItem.ADVANCED_NET_PERF_PROFILING
            java.lang.String r5 = r8.getStringValue(r9)
            boolean r8 = android.text.TextUtils.isEmpty(r5)
            if (r8 != 0) goto L_0x0179
            java.lang.String r8 = "T"
            boolean r8 = r5.startsWith(r8)
            if (r8 != 0) goto L_0x0489
        L_0x0179:
            java.lang.String r8 = "RPCDataParser"
            java.lang.String r9 = "Advanced NetPerf Profiling is off"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r8, r9)
        L_0x0180:
            java.lang.String r8 = "RETRY"
            java.lang.String r8 = r0.getDataItem(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x0197
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RETRY"
            java.lang.String r10 = "T"
            r8.put(r9, r10)
        L_0x0197:
            java.lang.String r8 = "ORIGHC"
            java.lang.String r8 = r0.getDataItem(r8)
            java.lang.String r9 = "T"
            boolean r8 = android.text.TextUtils.equals(r8, r9)
            if (r8 == 0) goto L_0x01b0
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ORIGHC"
            java.lang.String r10 = "T"
            r8.put(r9, r10)
        L_0x01b0:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "HRC"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "TAG"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "PRIO"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "DT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "VIA"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "EagleId"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RPCID"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ONSHORT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "MTAG"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "QOE_CUR"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "NTCP_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "NSSL_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "CPS"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "IMG_DOWN"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "CANCEL"
            r10 = 0
            a(r8, r9, r0, r10)
            com.alipay.mobile.common.transport.config.TransportConfigureManager r8 = com.alipay.mobile.common.transport.config.TransportConfigureManager.getInstance()
            com.alipay.mobile.common.transport.config.TransportConfigureItem r9 = com.alipay.mobile.common.transport.config.TransportConfigureItem.LOG_PRIO_SWITCH
            int r8 = r8.getIntValue(r9)
            r9 = 3
            if (r8 < r9) goto L_0x02ad
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "JTC_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_QUENE"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_ST"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "CTJ_OUT_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "NT_IO_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "QUEUE_OUT_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_HUNG_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_ENCODE_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_ALL_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
        L_0x02ad:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "CID"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SOFT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "bizId"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "CIP"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "REQ_RAW_SIZE"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "GROUND"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RETRYCOUNT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "QOS"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "IGN_ERR"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "MULTIMAIN"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "DWN_GZIP"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "U_CT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "D_CT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "DJG_BIZ"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "UP_MT"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "NETTUNNEL"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "PROTOCOL"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SUB_TYPE"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "ldcid-level"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "METHOD"
            r10 = 0
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "SLEN"
            r10 = 0
            a(r8, r9, r0, r10)
            boolean r8 = r11.printUrlToMonitorLog
            if (r8 == 0) goto L_0x038d
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "REDIRECT"
            r10 = 0
            a(r8, r9, r0, r10)
        L_0x038d:
            a(r6, r13, r0)
            a(r6)
            b(r11, r6)
            a(r0, r6)
            java.lang.String r8 = r11.targetSpi
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x03ac
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "spi"
            java.lang.String r10 = r11.targetSpi
            r8.put(r9, r10)
        L_0x03ac:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = r6.getSubType()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = "_PERF"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r7 = r8.toString()
            r1 = 0
            java.lang.String r8 = "ERROR"
            java.lang.String r4 = r0.getDataItem(r8)
            java.lang.String r8 = "IGN_ERR"
            java.lang.String r8 = r0.getDataItem(r8)
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x03e2
            boolean r8 = android.text.TextUtils.isEmpty(r4)
            if (r8 != 0) goto L_0x03e2
            r1 = 1
            java.lang.String r8 = "ignErr don't upload"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r7, r8)
        L_0x03e2:
            if (r1 != 0) goto L_0x03eb
            if (r3 != 0) goto L_0x03e8
            if (r12 == 0) goto L_0x03eb
        L_0x03e8:
            com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils.uploadPerfLog(r6, r11)
        L_0x03eb:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r7)
            java.lang.String r9 = ":"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r6.toString()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r11.perfLog = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r9 = ","
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r11.perfLog
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = "\n"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r7, r8)
            goto L_0x000a
        L_0x042c:
            r2 = move-exception
            java.lang.String r8 = "RPCDataParser"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Create URL object exception: "
            r9.<init>(r10)
            java.lang.String r10 = r2.toString()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r8, r9)
            goto L_0x003f
        L_0x0447:
            java.lang.String r8 = r11.api
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x00bf
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "API"
            java.lang.String r10 = r11.api
            r8.put(r9, r10)
            java.lang.String r8 = r11.api
            boolean r8 = com.alipay.mobile.common.transport.utils.MiscUtils.isMdapApi(r8)
            if (r8 == 0) goto L_0x00c2
            java.lang.String r8 = "LOG"
            r6.setSubType(r8)
            goto L_0x00c2
        L_0x0469:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "RESULT"
            java.lang.String r10 = "F"
            r8.put(r9, r10)
            java.lang.String r8 = "FATAL"
            r6.setParam2(r8)
            r3 = 1
            goto L_0x00dc
        L_0x047c:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "EXT1"
            java.lang.String r10 = "T"
            r8.put(r9, r10)
            goto L_0x00fd
        L_0x0489:
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "IPC_TIME1"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "IPC_TIME2"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AW_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AMNET_STALLED_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "AIR_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            java.util.Map r8 = r6.getExtPramas()
            java.lang.String r9 = "UTC_TIME"
            r10 = 1
            a(r8, r9, r0, r10)
            goto L_0x0180
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.monitor.RPCDataParser.buildAndWriteLog(com.alipay.mobile.common.transport.context.TransportContext, boolean, java.lang.String):void");
    }

    private static void a(Performance pf) {
        try {
            b(pf);
            if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
                pf.getExtPramas().put(RPCDataItems.NET_AVAILABLE, "F");
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RPCDataParser", "addGlobalLog ex:" + ex.toString());
        }
    }

    private static void a(TransportContext srnc, Performance pf) {
        if (srnc.printUrlToMonitorLog) {
            pf.getExtPramas().put(MonitorItemConstants.KEY_URL, srnc.url);
        }
    }

    private static void a(Performance pf, String protocol, DataContainer dc) {
        if (ExtTransportStrategy.EXT_PROTO_MRPC.equals(protocol)) {
            String bifrostFlag = dc.getDataItem(MonitorLoggerUtils.LIB_VERSION);
            if (!TextUtils.isEmpty(bifrostFlag)) {
                pf.getExtPramas().put(MonitorLoggerUtils.LIB_VERSION, bifrostFlag);
            }
        }
    }

    private static void b(Performance pf) {
        try {
            String lbsInfo = LBSManager.getInstance().getReportLBSInfo();
            if (!TextUtils.isEmpty(lbsInfo)) {
                pf.getExtPramas().put(RPCDataItems.LBSINFO, lbsInfo);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RPCDataParser", "addLbsLog ex:" + ex.toString());
        }
    }

    private static void b(TransportContext srnc, Performance pf) {
        try {
            DeviceTrafficStateInfo startDeviceTraffic = srnc.deviceTrafficStateInfo;
            if (startDeviceTraffic != null) {
                DeviceTrafficStateInfoDelta delta = AlipayQosService.getInstance().stopTrafficMonitor(startDeviceTraffic);
                pf.getExtPramas().put(RPCDataItems.TRX, String.valueOf(delta.mDiffTotalRxBytes));
                pf.getExtPramas().put(RPCDataItems.TTX, String.valueOf(delta.mDiffTotalTxBytes));
                pf.getExtPramas().put(RPCDataItems.TMRX, String.valueOf(delta.mDiffMobileRxBytes));
                pf.getExtPramas().put(RPCDataItems.TMTX, String.valueOf(delta.mDiffMobileTxBytes));
                pf.getExtPramas().put(RPCDataItems.TTS, String.valueOf(delta.mDeltaTS));
                pf.getExtPramas().put(RPCDataItems.SPEED, String.format("%.4f", new Object[]{Double.valueOf(AlipayQosService.getInstance().getSpeed())}));
                pf.getExtPramas().put(RPCDataItems.BAND_WIDTH_WESTWOOD, String.format("%.4f", new Object[]{Double.valueOf(AlipayQosService.getInstance().getBandwidth())}));
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "RPCDataParser", "addTrafficLog ex:" + ex.toString());
        }
    }

    private static void c(TransportContext srnc, Performance pf) {
        if (srnc.bizType == 2) {
            pf.setSubType("H5");
        } else if (srnc.bizType == 3) {
            pf.setSubType(NetworkServiceTracer.REPORT_SUB_NAME_DJG);
        } else {
            if (srnc.bizType != 4) {
                if (srnc.bizType == 5) {
                    pf.setSubType("LOG");
                    return;
                } else if (!TextUtils.isEmpty(srnc.api)) {
                    pf.setSubType("RPC");
                    return;
                }
            }
            pf.setSubType(NetworkServiceTracer.REPORT_SUB_NAME_RSRC);
        }
    }

    private static void a(DataContainer dc, Performance pf) {
        try {
            String allTime = dc.getDataItem("ALL_TIME");
            String stalledTime = dc.getDataItem(RPCDataItems.STALLED_TIME);
            if (!TextUtils.isEmpty(allTime) && !TextUtils.isEmpty(stalledTime)) {
                pf.getExtPramas().put(RPCDataItems.OLD_RPC_ALL_TIME, (Long.parseLong(allTime) + Long.parseLong(stalledTime)) + TIME_MS);
            }
            String startTime = dc.getDataItem("RPC_ALL_TIME");
            if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(stalledTime)) {
                pf.getExtPramas().put("RPC_ALL_TIME", ((System.currentTimeMillis() - Long.parseLong(startTime)) + Long.parseLong(stalledTime)) + TIME_MS);
            }
        } catch (Exception ex) {
            LogCatUtil.error("RPCDataParser", "addRpcAllTime exception", ex);
        }
    }

    private static void b(DataContainer dc, Performance pf) {
        double allSize = -1.0d;
        try {
            String reqSizeStr = dc.getDataItem("REQ_SIZE");
            if (!TextUtils.isEmpty(reqSizeStr)) {
                allSize = Double.valueOf(reqSizeStr).doubleValue();
            }
            String respSizeStr = dc.getDataItem("RES_SIZE");
            if (!TextUtils.isEmpty(respSizeStr)) {
                allSize += Double.valueOf(respSizeStr).doubleValue();
            }
            if (allSize > 0.0d) {
                String allTimeStr = dc.getDataItem("ALL_TIME");
                if (!TextUtils.isEmpty(allTimeStr)) {
                    Double allTime = Double.valueOf(allTimeStr);
                    if (allTime.doubleValue() > 0.0d) {
                        pf.getExtPramas().put(RPCDataItems.BAND_WIDTH, String.format("%.4f", new Object[]{Double.valueOf((((allSize / (allTime.doubleValue() / 1000.0d)) * 8.0d) / 1024.0d) / 1024.0d)}));
                    }
                }
            }
        } catch (Exception e) {
            LogCatUtil.error((String) "RPCDataParser", (Throwable) e);
        }
    }

    private static void a(Map<String, String> ext, String item, DataContainer dc, boolean time) {
        String itemValue = dc.getDataItem(item);
        if (!TextUtils.isEmpty(itemValue)) {
            if (time) {
                itemValue = itemValue + TIME_MS;
            }
            ext.put(item, itemValue);
        }
    }
}
