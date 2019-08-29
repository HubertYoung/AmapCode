package com.taobao.accs.client;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsException;
import com.taobao.accs.IProcessName;
import com.taobao.accs.utl.ALog;

@Deprecated
public class AccsConfig {
    private static final String TAG = "AccsConfig";
    public static Builder mBuilder = null;
    private static boolean mInitConfig = false;

    public enum ACCS_GROUP {
        TAOBAO,
        ALIYUN,
        OPEN
    }

    public enum SECURITY_TYPE {
        SECURITY_TAOBAO,
        SECURITY_OPEN,
        SECURITY_OFF
    }

    public static void setAccsCenterIps(String[] strArr, String[] strArr2, String[] strArr3) {
    }

    public static void setChannelIps(String[] strArr, String[] strArr2, String[] strArr3) {
    }

    public static void setChannelHosts(String str, String str2, String str3) {
        ALog.i(TAG, "env", Integer.valueOf(ACCSManager.mEnv), "setChannelHosts", "releaseHost", str, "prepareHost", str2, "dailyHost", str3);
        switch (ACCSManager.mEnv) {
            case 1:
                getBuilder().setChannelHost(str2);
                return;
            case 2:
                getBuilder().setChannelHost(str3);
                return;
            default:
                getBuilder().setChannelHost(str);
                return;
        }
    }

    public static void build() {
        try {
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(ACCSManager.getDefaultConfig(null));
            if (AccsClientConfig.loadedStaticConfig) {
                if (configByTag != null) {
                    ALog.w(TAG, "default config already exists", new Object[0]);
                    return;
                }
            }
            getBuilder().build();
        } catch (AccsException e) {
            ALog.e(TAG, "build config error", e, new Object[0]);
        }
    }

    private static Builder getBuilder() {
        if (TextUtils.isEmpty(ACCSManager.mDefaultAppkey)) {
            throw new RuntimeException("old interface!!, please AccsManager.setAppkey() first!");
        }
        if (mBuilder == null) {
            mBuilder = new Builder().setAppKey(ACCSManager.mDefaultAppkey).setTag(ACCSManager.getDefaultConfig(null)).setAutoUnit(true);
        }
        return mBuilder;
    }

    public static void setSecurityGuardOff(SECURITY_TYPE security_type) {
        AdapterGlobalClientInfo.mSecurityType = security_type.ordinal();
    }

    public static void setTnetPubkey(int i, int i2) {
        ALog.i(TAG, "setTnetPubkey", "pubKey", Integer.valueOf(i), "channelPubKey", Integer.valueOf(i2));
        getBuilder().setInappPubKey(i).setChannelPubKey(i2);
    }

    public static void setAccsCenterHosts(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            ALog.e(TAG, "setAccsCenterHost null", new Object[0]);
            return;
        }
        ALog.i(TAG, "setAccsCenterHost", "env", Integer.valueOf(ACCSManager.mEnv), "releaseHost", str, "prepareHost", str2, "dailyHost", str3);
        switch (ACCSManager.mEnv) {
            case 1:
                getBuilder().setInappHost(str2);
                return;
            case 2:
                getBuilder().setInappHost(str3);
                return;
            default:
                getBuilder().setInappHost(str);
                return;
        }
    }

    public static void disableInappKeepAlive() {
        getBuilder().setKeepAlive(false);
    }

    public static void setAuthCode(String str) {
        getBuilder().setAutoCode(str);
        AdapterGlobalClientInfo.mAuthCode = str;
    }

    public static void setControlFrameMaxRetry(int i) {
        GlobalConfig.setControlFrameMaxRetry(i);
    }

    public static void setChannelReuse(boolean z, ACCS_GROUP accs_group) {
        GlobalConfig.setChannelReuse(z, accs_group);
    }

    public static void setMainProcessName(String str) {
        GlobalConfig.setMainProcessName(str);
    }

    public static void setChannelProcessName(String str) {
        GlobalConfig.setChannelProcessName(str);
    }

    public static void setCurrProcessNameImpl(IProcessName iProcessName) {
        GlobalConfig.setCurrProcessNameImpl(iProcessName);
    }

    public static void setEnableForground(Context context, boolean z) {
        GlobalConfig.setEnableForground(context, z);
    }
}
