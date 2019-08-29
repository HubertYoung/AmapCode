package com.alipay.mobile.common.transport;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.context.TransportContext.SingleRPCReqConfig;
import com.alipay.mobile.common.transport.strategy.NetworkTunnelStrategy;
import com.alipay.mobile.common.transport.strategy.StrategyUtil;
import com.alipay.mobile.common.transport.utils.ConnectionUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.transportext.biz.util.NetInfoHelper;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.net.URL;

public class TransportStrategy {
    public static final String SWITCH_OPEN_STR = "T";
    public static final String TAG = "TransportStrategy";
    private static String[] a = {NetInfoHelper.CMWAP_PROXY_HOST, "10.0.0.200"};
    private static Pair<Boolean, Long> b;
    private static int c = 0;

    public static void fillCurrentReqInfo(boolean use, String protocol, TransportContext netContext) {
        SingleRPCReqConfig rpcReqConfig = new SingleRPCReqConfig();
        rpcReqConfig.use = use;
        rpcReqConfig.protocol = protocol;
        netContext.currentReqInfo = rpcReqConfig;
    }

    public static final int getReadTimeout(Context context) {
        TransportConfigureManager mng = TransportConfigureManager.getInstance();
        if (context == null) {
            LogCatUtil.error((String) TAG, (String) "context is null. reivew code please !");
            return mng.getIntValue(TransportConfigureItem.WIFI_READ_TIMEOUT);
        }
        int networkType = NetworkUtils.getNetworkType(context);
        LogCatUtil.debug(TAG, "getReadTimeout networkType=" + networkType);
        switch (networkType) {
            case 1:
                return mng.getIntValue(TransportConfigureItem.SECOND_GEN_READ_TIMEOUT);
            case 2:
            case 4:
                return mng.getIntValue(TransportConfigureItem.THIRD_GEN_READ_TIMEOUT);
            case 3:
                return mng.getIntValue(TransportConfigureItem.WIFI_READ_TIMEOUT);
            default:
                return mng.getIntValue(TransportConfigureItem.WIFI_READ_TIMEOUT);
        }
    }

    public static final int getConnTimeout(Context context) {
        return TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.CONN_TIME_OUT);
    }

    public static boolean isEnabledCacheAddress() {
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.ENABLED_CACHE_ADDRESS, "T");
    }

    public static final String getStrategyVersion() {
        return TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.VERSION);
    }

    public static void configInit(Context context, String api, TransportContext netContext) {
        try {
            LogCatUtil.debug(TAG, "RPC TRANSPORT CONFIG INIT");
            fillNetTypes(context, netContext);
            netContext.logRandom = getConfigureManager().getIntValue(TransportConfigureItem.MDAP_SEED1);
            netContext.reqGzip = a();
            netContext.api = api;
            netContext.setInitd(true);
        } catch (Exception ex) {
            LogCatUtil.error(TAG, "RPC网络配置初始时异常", ex);
        }
    }

    public static boolean loadConfig(Context context) {
        try {
            TransportConfigureManager mConfigMgr = getConfigureManager();
            if (mConfigMgr.isLoadedConfig() || mConfigMgr.updateConfig(context)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return false;
        }
    }

    public static final TransportConfigureManager getConfigureManager() {
        return TransportConfigureManager.getInstance();
    }

    private static boolean a() {
        return TransportConfigureManager.getInstance().equalsString(TransportConfigureItem.GZIP_SWITCH, "T");
    }

    public static void fillNetTypes(Context mContext, TransportContext netContext) {
        netContext.net0 = ConnectionUtil.getConnType(mContext);
        netContext.net1 = ConnectionUtil.getNetworkType(mContext);
    }

    public static final boolean isEnabledRpcV2() {
        return TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.RPCV2_SWITCH), "T");
    }

    public static boolean isAlipayUrl(String urlStr) {
        if (TextUtils.isEmpty(urlStr)) {
            return false;
        }
        return isAlipayHost(new URL(urlStr).getHost());
    }

    public static final boolean isAlipayHost(String hostStr) {
        if (!TextUtils.isEmpty(hostStr) && hostStr.contains(BehavorReporter.PROVIDE_BY_ALIPAY)) {
            return true;
        }
        return false;
    }

    public static final boolean isRpcCdnHost(String rpcCdnHost) {
        boolean z = false;
        String cdnUrl = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.CDN_URL);
        if (TextUtils.isEmpty(cdnUrl)) {
            return z;
        }
        try {
            return TextUtils.equals(new URL(cdnUrl).getHost(), rpcCdnHost);
        } catch (Exception e) {
            return z;
        }
    }

    public static final int getHandshakTimeout() {
        return TransportConfigureManager.getInstance().getIntValue(TransportConfigureItem.HANDSHAK_TIMEOUT);
    }

    public static final boolean isEnabledAmnet(Context context) {
        if (NetworkTunnelStrategy.getInstance().getCurrentDataTunnlType() == 1 || NetworkTunnelStrategy.getInstance().getCurrentDataTunnlType() == 2) {
            return false;
        }
        return true;
    }

    public static final boolean isMobileWapProxyIp(String ip) {
        for (String equals : a) {
            if (equals.equals(ip)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isOpenForceSpdyForSync() {
        return MiscUtils.grayscale(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SYNC_ONLY_SPDY));
    }

    public static final boolean isEnabledDjangoSwitch() {
        return MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.DJG_SWITCH));
    }

    public static final boolean isEnableNBNetDLSwitch() {
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            Boolean isEnabled = ReadSettingServerUrl.getInstance().isEnabledNbnetDownloadSwitch(TransportEnvUtil.getContext());
            if (isEnabled != null) {
                LogCatUtil.info(TAG, "isEnableNBNetDLSwitch Setting's config: " + isEnabled.booleanValue());
                return isEnabled.booleanValue();
            }
        }
        if (MiscUtils.isOversea() && !MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            LogCatUtil.info(TAG, "isEnableNBNetDLSwitch. Current users are overseas user.");
            if (MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NBNET_DL_OVERSEA_SWITCH))) {
                LogCatUtil.warn((String) TAG, (String) "isEnableNBNetDLSwitch. Oversea user disabled nbnet download!");
                return false;
            }
        }
        String switchValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NBNET_DL_SWITCH);
        boolean grayscaleResult = MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), switchValue);
        LogCatUtil.info(TAG, "[isEnableNBNetDLSwitch] grayscaleResult.  switchValue:" + switchValue + ", grayscaleResult:" + grayscaleResult);
        return grayscaleResult;
    }

    public static final boolean isEnableNBNetUPSwitch() {
        if (MiscUtils.isDebugger(TransportEnvUtil.getContext())) {
            Boolean isEnabled = ReadSettingServerUrl.getInstance().isEnabledNbnetUpSwitch(TransportEnvUtil.getContext());
            if (isEnabled != null) {
                LogCatUtil.info(TAG, "[isEnableNBNetUPSwitch] Setting's config = " + isEnabled.booleanValue());
                return isEnabled.booleanValue();
            }
        }
        String switchValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NBNET_UP_SWITCH);
        boolean grayscaleResult = MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), switchValue);
        LogCatUtil.info(TAG, "[isEnableNBNetUPSwitch] grayscaleResult.  switchValue:" + switchValue + ", grayscaleResult:" + grayscaleResult);
        return grayscaleResult;
    }

    public static final boolean isEnableInitMergeSyncSwitch() {
        String switchValue = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.INIT_MERGE_CMD);
        LogCatUtil.debug(TAG, "isEnableInitMergeSyncSwitch=[" + switchValue + "]");
        return MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), switchValue);
    }

    public static boolean isVip() {
        if (TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.WHITE_LIST_USER), "T")) {
            return true;
        }
        return false;
    }

    public static final void incrementRpcErrorCount() {
        LogCatUtil.info(TAG, "incrementRpcErrorCount finish");
        c++;
    }

    public static final void resetRpcErrorCount() {
        if (c > 0) {
            LogCatUtil.info(TAG, "resetRpcErrorCount finish");
        }
        c = 0;
    }

    public static final boolean isDowngradeToHttps() {
        return c <= 3;
    }

    public static boolean isSupportShortLink(String operationType) {
        return isApiInList(operationType, TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SHORTLINK_RPCLIST));
    }

    public static boolean isShortLinkOnly(String operationType) {
        return isApiInList(operationType, TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SHORTLINK_ONLY_RPCLIST));
    }

    public static boolean isSupportZstd(String operationType) {
        try {
            TransportConfigureManager mng = TransportConfigureManager.getInstance();
            if (isApiInList(operationType, mng.getStringValue((ConfigureItem) TransportConfigureItem.ZSTD_BLACK_LIST))) {
                LogCatUtil.warn((String) TAG, operationType + " in zstd black list.");
                return false;
            } else if (mng.getIntValue(TransportConfigureItem.ZSTD_WHITE_LIST_SWITCH) == 0) {
                return true;
            } else {
                if (!isApiInList(operationType, mng.getStringValue((ConfigureItem) TransportConfigureItem.ZSTD_WHITE_LIST))) {
                    return false;
                }
                LogCatUtil.info(TAG, operationType + " in zstd white list.");
                return true;
            }
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "isSupportZstd: " + e.toString());
            return false;
        }
    }

    public static boolean isApiInList(String operationType, String apiList) {
        if (TextUtils.isEmpty(operationType) || TextUtils.isEmpty(apiList)) {
            return false;
        }
        for (String equals : apiList.split(",")) {
            if (TextUtils.equals(equals, operationType)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnableBifrost() {
        try {
            if (!MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.USE_BIFROST)) {
                return false;
            }
            String brand = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BIFROST_BLACK_LIST_BRAND);
            if (!StrategyUtil.isUse4Brand(brand)) {
                LogCatUtil.warn((String) TAG, "isUse4Brand is false. brandBlackList=[" + brand + "]");
                return false;
            }
            String model = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BIFROST_BLACK_LIST_MODEL);
            if (!StrategyUtil.isUse4Model(model)) {
                LogCatUtil.warn((String) TAG, "isUse4Model is false. modelBlackList=[" + model + "]");
                return false;
            }
            String cpuModel = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BIFROST_BLACK_LIST_CPU);
            if (!StrategyUtil.isUse4Hardware(cpuModel)) {
                LogCatUtil.warn((String) TAG, "isUse4Hardware is false. cpuModelBlackList=[" + cpuModel + "]");
                return false;
            }
            String sdkVer = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.BIFROST_BLACK_LIST_SDK);
            if (StrategyUtil.isUse4SdkVersion(sdkVer)) {
                return true;
            }
            LogCatUtil.warn((String) TAG, "isUse4SdkVersion is false. sdkVersionBlackList=[" + sdkVer + "]");
            return false;
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, e);
            return false;
        }
    }

    public static boolean isDisableBifrostRpcDowngrade() {
        try {
            return MiscUtils.grayscaleUtdidForABTest(TransportConfigureItem.BIFROST_DISABLSE_RPC_DOWNGRADE);
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, e);
            return false;
        }
    }

    @TargetApi(5)
    public static final Boolean getEnableOnlyPushStatus() {
        if (b == null || b.first == null || b.second == null || System.currentTimeMillis() >= ((Long) b.second).longValue()) {
            return null;
        }
        return Boolean.valueOf(((Boolean) b.first).booleanValue());
    }

    @TargetApi(5)
    public static final boolean isEnabledOnlyPush() {
        boolean switchStateFromMetaData;
        Boolean status = getEnableOnlyPushStatus();
        if (status != null) {
            return status.booleanValue();
        }
        synchronized (TransportStrategy.class) {
            Boolean status2 = getEnableOnlyPushStatus();
            if (status2 != null) {
                boolean booleanValue = status2.booleanValue();
                return booleanValue;
            }
            Context context = TransportEnvUtil.getContext();
            if (context != null) {
                Object onlyPushSwitch = MiscUtils.getMetaDataVO(context, "only_push_switch");
                if (onlyPushSwitch != null) {
                    try {
                        switchStateFromMetaData = ((Boolean) onlyPushSwitch).booleanValue();
                    } catch (Throwable th) {
                        switchStateFromMetaData = false;
                    }
                    LogCatUtil.info(TAG, "isEnabledOnlyPush. meta data switch is : " + switchStateFromMetaData);
                    a(switchStateFromMetaData);
                    return switchStateFromMetaData;
                }
            } else {
                LogCatUtil.warn((String) TAG, (String) "isEnabledOnlyPush. Oppps, context is null.");
            }
            boolean switchStateFromGrayscale = MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SUPPORT_ONLY_PUSH_SWITCH));
            LogCatUtil.info(TAG, "isEnabledOnlyPush. grayscale switch is: " + switchStateFromGrayscale);
            a(switchStateFromGrayscale);
            return switchStateFromGrayscale;
        }
    }

    @TargetApi(5)
    private static void a(boolean switchStateFromMetaData) {
        b = new Pair<>(Boolean.valueOf(switchStateFromMetaData), Long.valueOf(System.currentTimeMillis() + 60000));
    }

    public static final boolean isEnabledAutoUpgrade() {
        Boolean switchStateFromMetaData = getBooleanFromMetaData(TransportEnvUtil.getContext(), "auto_upgrade_switch", null);
        if (switchStateFromMetaData != null) {
            return switchStateFromMetaData.booleanValue();
        }
        boolean switchStateFromGrayscale = MiscUtils.grayscaleUtdid(DeviceInfoUtil.getDeviceId(), TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SUPPORT_AUTO_UPGRADE_SWITCH));
        LogCatUtil.info(TAG, "isEnabledAutoUpgrade. grayscale switch is: " + switchStateFromGrayscale);
        return switchStateFromGrayscale;
    }

    public static Boolean getBooleanFromMetaData(Context context, String metaDataKey, Boolean defaultValue) {
        boolean switchStateFromMetaData;
        if (context != null) {
            Object metaDataSwitch = MiscUtils.getMetaDataVO(context, metaDataKey);
            if (metaDataSwitch == null) {
                return defaultValue;
            }
            try {
                switchStateFromMetaData = ((Boolean) metaDataSwitch).booleanValue();
            } catch (Throwable th) {
                switchStateFromMetaData = false;
            }
            LogCatUtil.info(TAG, "isEnabledAutoUpgrade. meta data switch is : " + switchStateFromMetaData);
            return Boolean.valueOf(switchStateFromMetaData);
        }
        LogCatUtil.warn((String) TAG, (String) "isEnabledAutoUpgrade. Oppps, context is null.");
        return defaultValue;
    }
}
