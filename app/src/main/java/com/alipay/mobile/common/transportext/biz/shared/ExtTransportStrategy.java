package com.alipay.mobile.common.transportext.biz.shared;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public final class ExtTransportStrategy {
    public static final String EXT_PROTO_MRPC = "mrpc";
    public static final String EXT_PROTO_SPDY = "spdy";
    private static String SPDY_URL = null;
    public static final String TAG = "ExtTransportStrategy";
    public static final String UNI_DOMAIN_API = "alipay.unidomain";
    private static boolean useSpdyShortReadTimeout = false;

    public static boolean isLongLinkSpdySwitchOn() {
        if (!NetworkTunnelStrategy.getInstance().isCanUseSpdyDataTunel()) {
            return false;
        }
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.SYNC_SPDY_SWITCH, "T");
    }

    public static long getPing2GInterval() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.SECOND_GEN_PING_INTERVAL);
    }

    public static int getConnFailMaxTime() {
        return NetworkTunnelStrategy.getInstance().getConnFailMaxTime();
    }

    public static long getPing3GInterval() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.THIRD_GEN_PING_INTERVAL);
    }

    public static long getPingWifiInterval() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.WIFI_PING_INTERVAL);
    }

    public static long getPingDefaulInterval() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.PING_DEFAULT_INTERVAL);
    }

    public static long getPingTimeOut() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.PING_TIME_OUT);
    }

    public static final long getReconnectionMaxCount() {
        return TransportConfigureManager.getInstance().getLongValue(TransportConfigureItem.RECONN_MAX_COUNT);
    }

    public static final long getPingInterval(Context context) {
        if (context == null) {
            LogCatUtil.error((String) TAG, (String) "context is null. reivew code please !");
            return getPingDefaulInterval();
        }
        int networkType = NetworkUtils.getNetworkType(context);
        LogCatUtil.debug(TAG, "getPingInterval networkType=" + networkType);
        switch (networkType) {
            case 1:
                return getPing2GInterval();
            case 2:
            case 4:
                return getPing3GInterval();
            case 3:
                return getPingWifiInterval();
            default:
                return getPingDefaulInterval();
        }
    }

    @Deprecated
    public static void stopPingSent() {
    }

    public static boolean isDowngradeTLS() {
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.DOWNGRADE_TLS_SWITCH, "T");
    }

    public static boolean isSimpleVerifyAltsMode() {
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.VERIFY_ALTS_MODE, "T");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037 A[Catch:{ Exception -> 0x003d }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void configInit(android.content.Context r5, com.alipay.mobile.common.transport.context.TransportContext r6) {
        /*
            r0 = 1
            r3 = 0
            java.lang.String r2 = r6.api
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy r1 = com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy.getInstance()
            boolean r2 = r1.isCanUseAmnet()
            if (r2 != 0) goto L_0x001b
            boolean r2 = r1.isCanUseSpdy()
            if (r2 == 0) goto L_0x000a
        L_0x001b:
            int r2 = r6.choseExtLinkType     // Catch:{ Exception -> 0x003d }
            r4 = 2
            if (r2 != r4) goto L_0x0046
        L_0x0020:
            java.lang.String r2 = "spdy"
        L_0x0022:
            com.alipay.mobile.common.transport.TransportStrategy.fillCurrentReqInfo(r3, r2, r6)     // Catch:{ Exception -> 0x003d }
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r2 = r6.currentReqInfo     // Catch:{ Exception -> 0x003d }
            java.lang.String r3 = getSpdyUrl(r5)     // Catch:{ Exception -> 0x003d }
            r2.callUrl = r3     // Catch:{ Exception -> 0x003d }
            com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy r2 = com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy.getInstance()     // Catch:{ Exception -> 0x003d }
            boolean r2 = r2.isUseExtTransport(r6)     // Catch:{ Exception -> 0x003d }
            if (r2 == 0) goto L_0x000a
            com.alipay.mobile.common.transport.context.TransportContext$SingleRPCReqConfig r2 = r6.currentReqInfo     // Catch:{ Exception -> 0x003d }
            r3 = 1
            r2.use = r3     // Catch:{ Exception -> 0x003d }
            goto L_0x000a
        L_0x003d:
            r2 = move-exception
            java.lang.String r2 = "ExtTransportStrategy"
            java.lang.String r3 = "网络配置初始化时发生异常"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r2, r3)
            goto L_0x000a
        L_0x0046:
            com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy r2 = com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy.getInstance()     // Catch:{ Exception -> 0x003d }
            boolean r2 = r2.isCanUseAmnet()     // Catch:{ Exception -> 0x003d }
            if (r2 == 0) goto L_0x005b
            boolean r2 = com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper.isAmnetActivite()     // Catch:{ Exception -> 0x003d }
            if (r2 == 0) goto L_0x005b
        L_0x0056:
            if (r0 == 0) goto L_0x0020
            java.lang.String r2 = "mrpc"
            goto L_0x0022
        L_0x005b:
            r0 = r3
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy.configInit(android.content.Context, com.alipay.mobile.common.transport.context.TransportContext):void");
    }

    public static String getSpdyUrl(Context context) {
        if (MiscUtils.isDebugger(context)) {
            return getConfigureManager().getStringValue((ConfigureItem) TransportConfigureItem.SPDY_URL);
        }
        if (!TextUtils.isEmpty(SPDY_URL)) {
            return SPDY_URL;
        }
        SPDY_URL = "https://mobilegw.alipay.com/mgw.htm";
        return "https://mobilegw.alipay.com/mgw.htm";
    }

    public static final TransportConfigureManager getConfigureManager() {
        return TransportConfigureManager.getInstance();
    }

    public static int getSpdyShortTimeout() {
        int spdyShortTime = TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.SPDY_SHORT_TIME_OUT);
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "getSpdyShortTimeout. spdyShortTime=[" + spdyShortTime + "]");
        return spdyShortTime;
    }

    public static boolean isUseSpdyShortReadTimeout() {
        return useSpdyShortReadTimeout;
    }

    public static void setUseSpdyShortReadTimeout(boolean use) {
        useSpdyShortReadTimeout = use;
    }

    public static final boolean isEnableDiagnoseBySystem(String utdid) {
        String grayscaleValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NETWORK_DIAGNOSE_TRACEROUTE_SYS);
        if (MiscUtils.grayscaleUtdid(utdid, grayscaleValue)) {
            LogCatUtil.info(TAG, "isEnableDiagnoseBySystem is true, utdid=" + utdid + ", switch=" + grayscaleValue);
            return true;
        }
        LogCatUtil.info(TAG, "isEnableDiagnoseBySystem is false, utdid=" + utdid + ", switch=" + grayscaleValue);
        return false;
    }

    public static final boolean isEnableDiagnoseByUser(String utdid) {
        String grayscaleValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NETWORK_DIAGNOSE_TRACEROUTE_USR);
        if (MiscUtils.grayscaleUtdid(utdid, grayscaleValue)) {
            LogCatUtil.info(TAG, "isEnableDiagnoseByUser is true, utdid=" + utdid + ", switch=" + grayscaleValue);
            return true;
        }
        LogCatUtil.info(TAG, "isEnableDiagnoseByUser is false, utdid=" + utdid + ", switch=" + grayscaleValue);
        return false;
    }

    public static final boolean isEnableDiagnoseByAuto(String utdid) {
        String grayscaleValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NETWORK_DIAGNOSE_TRACEROUTE_AUTO);
        if (MiscUtils.grayscaleUtdid(utdid, grayscaleValue)) {
            LogCatUtil.info(TAG, "isEnableDiagnoseByAuto is true, utdid=" + utdid + ", switch=" + grayscaleValue);
            return true;
        }
        LogCatUtil.info(TAG, "isEnableDiagnoseByAuto is false, utdid=" + utdid + ", switch=" + grayscaleValue);
        return false;
    }

    public static final boolean isEnableDiagnoseByAuto() {
        return isEnableDiagnoseByAuto(DeviceInfo.createInstance(ExtTransportEnv.getAppContext()).getmDid());
    }

    public static final long getRpcDiagnoseUploadTime() {
        try {
            String stringValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RPC_DIAGNOSE_UPLOAD_TIME);
            LogCatUtil.info(TAG, "getRpcDiagnoseUploadTime, the rdut time is :" + stringValue + "s");
            if (TextUtils.isEmpty(stringValue)) {
                return -1;
            }
            Long lVal = Long.valueOf(Long.parseLong(stringValue));
            if (lVal != null) {
                return lVal.longValue();
            }
            return -1;
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "getRpcDiagnoseUploadTime error: " + e.toString());
            return -1;
        }
    }
}
