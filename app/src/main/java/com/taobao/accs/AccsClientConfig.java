package com.taobao.accs;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccsClientConfig {
    public static final String[] DEFAULT_CENTER_HOSTS = {"msgacs.m.taobao.com", "msgacs.wapa.taobao.com", "msgacs.waptest.taobao.com"};
    /* access modifiers changed from: private */
    public static final String[] DEFAULT_CHANNEL_HOSTS = {"accscdn.m.taobao.com", "acs.wapa.taobao.com", "acs.waptest.taobao.com"};
    public static final String DEFAULT_CONFIGTAG = "default";
    public static final int SECURITY_OFF = 2;
    public static final int SECURITY_OPEN = 1;
    public static final int SECURITY_TAOBAO = 0;
    private static final String TAG = "AccsClientConfig";
    public static boolean loadedStaticConfig = true;
    private static Context mContext;
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mDebugConfigs = new ConcurrentHashMap(1);
    @ENV
    public static int mEnv;
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mPreviewConfigs = new ConcurrentHashMap(1);
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mReleaseConfigs = new ConcurrentHashMap(1);
    /* access modifiers changed from: private */
    public String mAppKey;
    /* access modifiers changed from: private */
    public String mAppSecret;
    /* access modifiers changed from: private */
    public String mAuthCode;
    /* access modifiers changed from: private */
    public boolean mAutoUnit;
    /* access modifiers changed from: private */
    public String mChannelHost;
    /* access modifiers changed from: private */
    public int mChannelPubKey;
    /* access modifiers changed from: private */
    public int mConfigEnv;
    /* access modifiers changed from: private */
    public boolean mDisableChannel;
    /* access modifiers changed from: private */
    public String mInappHost;
    /* access modifiers changed from: private */
    public int mInappPubKey;
    /* access modifiers changed from: private */
    public boolean mKeepalive;
    /* access modifiers changed from: private */
    public int mSecurity;
    /* access modifiers changed from: private */
    public String mTag;

    public static class Builder {
        private String mAppKey;
        private String mAppSecret;
        private String mAuthCode;
        private boolean mAutoUnit = true;
        private String mChannelHost;
        private int mChannelPubKey = -1;
        private int mConfigEnv = -1;
        private boolean mDisableChannel = false;
        private String mInappHost;
        private int mInappPubKey = -1;
        private boolean mKeepalive = true;
        private String mTag;

        public Builder setAppKey(String str) {
            this.mAppKey = str;
            return this;
        }

        public Builder setAppSecret(String str) {
            this.mAppSecret = str;
            return this;
        }

        public Builder setInappHost(String str) {
            this.mInappHost = str;
            return this;
        }

        public Builder setChannelHost(String str) {
            this.mChannelHost = str;
            return this;
        }

        public Builder setAutoCode(String str) {
            this.mAuthCode = str;
            return this;
        }

        public Builder setInappPubKey(int i) {
            this.mInappPubKey = i;
            return this;
        }

        public Builder setChannelPubKey(int i) {
            this.mChannelPubKey = i;
            return this;
        }

        public Builder setKeepAlive(boolean z) {
            this.mKeepalive = z;
            return this;
        }

        public Builder setAutoUnit(boolean z) {
            this.mAutoUnit = z;
            return this;
        }

        public Builder setConfigEnv(@ENV int i) {
            this.mConfigEnv = i;
            return this;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }

        public Builder setDisableChannel(boolean z) {
            this.mDisableChannel = z;
            return this;
        }

        public AccsClientConfig build() throws AccsException {
            Map map;
            if (TextUtils.isEmpty(this.mAppKey)) {
                throw new AccsException((String) "appkey null");
            }
            AccsClientConfig accsClientConfig = new AccsClientConfig();
            accsClientConfig.mAppKey = this.mAppKey;
            accsClientConfig.mAppSecret = this.mAppSecret;
            accsClientConfig.mAuthCode = this.mAuthCode;
            accsClientConfig.mKeepalive = this.mKeepalive;
            accsClientConfig.mAutoUnit = this.mAutoUnit;
            accsClientConfig.mInappPubKey = this.mInappPubKey;
            accsClientConfig.mChannelPubKey = this.mChannelPubKey;
            accsClientConfig.mInappHost = this.mInappHost;
            accsClientConfig.mChannelHost = this.mChannelHost;
            accsClientConfig.mTag = this.mTag;
            accsClientConfig.mConfigEnv = this.mConfigEnv;
            accsClientConfig.mDisableChannel = this.mDisableChannel;
            if (accsClientConfig.mConfigEnv < 0) {
                accsClientConfig.mConfigEnv = AccsClientConfig.mEnv;
            }
            if (TextUtils.isEmpty(accsClientConfig.mAppSecret)) {
                accsClientConfig.mSecurity = 0;
            } else {
                accsClientConfig.mSecurity = 2;
            }
            if (TextUtils.isEmpty(accsClientConfig.mInappHost)) {
                accsClientConfig.mInappHost = AccsClientConfig.DEFAULT_CENTER_HOSTS[accsClientConfig.mConfigEnv];
            }
            if (TextUtils.isEmpty(accsClientConfig.mChannelHost)) {
                accsClientConfig.mChannelHost = AccsClientConfig.DEFAULT_CHANNEL_HOSTS[accsClientConfig.mConfigEnv];
            }
            if (TextUtils.isEmpty(accsClientConfig.mTag)) {
                accsClientConfig.mTag = accsClientConfig.mAppKey;
            }
            switch (accsClientConfig.mConfigEnv) {
                case 1:
                    map = AccsClientConfig.mPreviewConfigs;
                    break;
                case 2:
                    map = AccsClientConfig.mDebugConfigs;
                    break;
                default:
                    map = AccsClientConfig.mReleaseConfigs;
                    break;
            }
            ALog.d(AccsClientConfig.TAG, "build", "config", accsClientConfig);
            AccsClientConfig accsClientConfig2 = (AccsClientConfig) map.get(accsClientConfig.getTag());
            if (accsClientConfig2 != null) {
                ALog.w(AccsClientConfig.TAG, "build conver", "old config", accsClientConfig2);
            }
            map.put(accsClientConfig.getTag(), accsClientConfig);
            return accsClientConfig;
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface ENV {
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface SECURITY_TYPE {
    }

    static {
        String[] strArr;
        int i;
        String str;
        boolean z = true;
        try {
            Bundle metaInfo = Utils.getMetaInfo(getContext());
            if (metaInfo != null) {
                String str2 = null;
                String string = metaInfo.getString("accsConfigTags", null);
                ALog.i(TAG, "init config from xml", "configtags", string);
                if (!TextUtils.isEmpty(string)) {
                    String[] split = string.split("\\|");
                    if (split == null) {
                        split = new String[]{string};
                    }
                    int length = split.length;
                    int i2 = 0;
                    while (i2 < length) {
                        String str3 = split[i2];
                        if (!TextUtils.isEmpty(str3)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str3);
                            sb.append("_accsAppkey");
                            int i3 = metaInfo.getInt(sb.toString(), -1);
                            if (i3 < 0) {
                                str = str2;
                            } else {
                                str = String.valueOf(i3);
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str3);
                            sb2.append("_accsAppSecret");
                            String string2 = metaInfo.getString(sb2.toString());
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str3);
                            sb3.append("_authCode");
                            String string3 = metaInfo.getString(sb3.toString());
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str3);
                            sb4.append("_keepAlive");
                            boolean z2 = metaInfo.getBoolean(sb4.toString(), z);
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str3);
                            sb5.append("_autoUnit");
                            boolean z3 = metaInfo.getBoolean(sb5.toString(), z);
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str3);
                            sb6.append("_inappPubkey");
                            int i4 = metaInfo.getInt(sb6.toString(), -1);
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append(str3);
                            sb7.append("_channelPubkey");
                            int i5 = metaInfo.getInt(sb7.toString(), -1);
                            StringBuilder sb8 = new StringBuilder();
                            sb8.append(str3);
                            sb8.append("_inappHost");
                            String string4 = metaInfo.getString(sb8.toString());
                            StringBuilder sb9 = new StringBuilder();
                            sb9.append(str3);
                            sb9.append("_channelHost");
                            String string5 = metaInfo.getString(sb9.toString());
                            StringBuilder sb10 = new StringBuilder();
                            sb10.append(str3);
                            i = length;
                            sb10.append("_configEnv");
                            int i6 = metaInfo.getInt(sb10.toString(), 0);
                            StringBuilder sb11 = new StringBuilder();
                            sb11.append(str3);
                            strArr = split;
                            sb11.append("_disableChannel");
                            boolean z4 = metaInfo.getBoolean(sb11.toString());
                            if (!TextUtils.isEmpty(str)) {
                                new Builder().setTag(str3).setConfigEnv(i6).setAppKey(str).setAppSecret(string2).setAutoCode(string3).setKeepAlive(z2).setAutoUnit(z3).setInappHost(string4).setInappPubKey(i4).setChannelHost(string5).setChannelPubKey(i5).setDisableChannel(z4).build();
                                ALog.i(TAG, "init config from xml", new Object[0]);
                            }
                        } else {
                            i = length;
                            strArr = split;
                        }
                        i2++;
                        length = i;
                        split = strArr;
                        z = true;
                        str2 = null;
                    }
                }
            }
        } catch (Throwable th) {
            ALog.e(TAG, "init config from xml", th, new Object[0]);
        }
    }

    public static Context getContext() {
        if (mContext != null) {
            return mContext;
        }
        synchronized (AccsClientConfig.class) {
            if (mContext != null) {
                Context context = mContext;
                return context;
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(cls, new Object[0]);
                mContext = (Context) invoke.getClass().getMethod("getApplication", new Class[0]).invoke(invoke, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Context context2 = mContext;
            return context2;
        }
    }

    protected AccsClientConfig() {
    }

    @Deprecated
    public static AccsClientConfig getConfig(String str) {
        Map<String, AccsClientConfig> map;
        switch (mEnv) {
            case 1:
                map = mPreviewConfigs;
                break;
            case 2:
                map = mDebugConfigs;
                break;
            default:
                map = mReleaseConfigs;
                break;
        }
        for (AccsClientConfig next : map.values()) {
            if (next.mAppKey.equals(str) && next.mConfigEnv == mEnv) {
                return next;
            }
        }
        ALog.e(TAG, "getConfigByTag return null", "appkey", str);
        return null;
    }

    public static AccsClientConfig getConfigByTag(String str) {
        Map<String, AccsClientConfig> map;
        switch (mEnv) {
            case 1:
                map = mPreviewConfigs;
                break;
            case 2:
                map = mDebugConfigs;
                break;
            default:
                map = mReleaseConfigs;
                break;
        }
        AccsClientConfig accsClientConfig = map.get(str);
        if (accsClientConfig == null) {
            ALog.e(TAG, "getConfigByTag return null", Constants.KEY_CONFIG_TAG, str);
        }
        return accsClientConfig;
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public String getInappHost() {
        return this.mInappHost;
    }

    public String getChannelHost() {
        return this.mChannelHost;
    }

    public int getSecurity() {
        return this.mSecurity;
    }

    public String getAuthCode() {
        return this.mAuthCode;
    }

    public int getInappPubKey() {
        return this.mInappPubKey;
    }

    public int getChannelPubKey() {
        return this.mChannelPubKey;
    }

    public boolean isKeepalive() {
        return this.mKeepalive;
    }

    public boolean isAutoUnit() {
        return this.mAutoUnit;
    }

    public String getTag() {
        return this.mTag;
    }

    public int getConfigEnv() {
        return this.mConfigEnv;
    }

    public boolean getDisableChannel() {
        return this.mDisableChannel;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AccsClientConfig{Tag=");
        sb.append(this.mTag);
        sb.append(", ConfigEnv=");
        sb.append(this.mConfigEnv);
        sb.append(", AppKey=");
        sb.append(this.mAppKey);
        sb.append(", AppSecret=");
        sb.append(this.mAppSecret);
        sb.append(", InappHost=");
        sb.append(this.mInappHost);
        sb.append(", ChannelHost=");
        sb.append(this.mChannelHost);
        sb.append(", Security=");
        sb.append(this.mSecurity);
        sb.append(", AuthCode=");
        sb.append(this.mAuthCode);
        sb.append(", InappPubKey=");
        sb.append(this.mInappPubKey);
        sb.append(", ChannelPubKey=");
        sb.append(this.mChannelPubKey);
        sb.append(", Keepalive=");
        sb.append(this.mKeepalive);
        sb.append(", AutoUnit=");
        sb.append(this.mAutoUnit);
        sb.append(", DisableChannel=");
        sb.append(this.mDisableChannel);
        sb.append(h.d);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccsClientConfig accsClientConfig = (AccsClientConfig) obj;
        if (!this.mInappHost.equals(accsClientConfig.mInappHost) || this.mInappPubKey != accsClientConfig.mInappPubKey || !this.mChannelHost.equals(accsClientConfig.mChannelHost) || this.mChannelPubKey != accsClientConfig.mChannelPubKey || this.mSecurity != accsClientConfig.mSecurity || this.mConfigEnv != accsClientConfig.mConfigEnv || !this.mAppKey.equals(accsClientConfig.mAppKey) || this.mKeepalive != accsClientConfig.mKeepalive || this.mDisableChannel != accsClientConfig.mDisableChannel) {
            return false;
        }
        if (this.mAuthCode == null ? accsClientConfig.mAuthCode != null : !this.mAuthCode.equals(accsClientConfig.mAuthCode)) {
            return false;
        }
        if (this.mAppSecret == null ? accsClientConfig.mAppSecret == null : this.mAppSecret.equals(accsClientConfig.mAppSecret)) {
            return this.mTag.equals(accsClientConfig.mTag);
        }
        return false;
    }
}
