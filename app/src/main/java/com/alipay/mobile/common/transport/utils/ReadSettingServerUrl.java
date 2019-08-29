package com.alipay.mobile.common.transport.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class ReadSettingServerUrl {
    public static final String OFF_SWITCH = "0";
    public static final String ON_SWITCH = "1";
    private static ReadSettingServerUrl a;
    private String b = "https://mobilegw.alipay.com/mgw.htm";
    private String c = "http://amdc.alipay.com/query";
    private String d = null;
    private Boolean e = null;
    private Boolean f = null;
    private String g = null;

    public static synchronized ReadSettingServerUrl getInstance() {
        ReadSettingServerUrl readSettingServerUrl;
        synchronized (ReadSettingServerUrl.class) {
            try {
                if (a == null) {
                    a = new ReadSettingServerUrl();
                }
                readSettingServerUrl = a;
            }
        }
        return readSettingServerUrl;
    }

    public String getmUrl() {
        return this.b;
    }

    public void setmUrl(String mUrl) {
        this.b = mUrl;
    }

    public static boolean isDebug(Context context) {
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isDebug exception.  " + e2.toString());
            return false;
        }
    }

    public final String getGWFURL(Context context) {
        if (!TextUtils.isEmpty(this.d)) {
            return this.d;
        }
        try {
            String mobilegwUrl = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("mobilegw.url");
            if (!TextUtils.isEmpty(mobilegwUrl)) {
                this.d = mobilegwUrl;
                return this.d;
            }
        } catch (Exception e2) {
            LogCatUtil.warn((String) "getGWFURL", (Throwable) e2);
        }
        if (isDebug(context)) {
            String tmpGwf = getValue(context, "content://com.alipay.setting/GWFServerUrl", this.b);
            if (!TextUtils.isEmpty(tmpGwf)) {
                this.d = tmpGwf;
            }
            return this.d;
        }
        this.d = this.b;
        return this.d;
    }

    public final String getHttpdnsServerUrl(Context context) {
        if (isDebug(context)) {
            return getValue(context, "content://com.alipay.setting/httpdns_url", this.c);
        }
        return this.c;
    }

    public static String getValue(Context context, String uri, String defaultVal) {
        LogCatUtil.printInfo("ReadSettingServerUrl", "getValue start.");
        Cursor cursor = context.getContentResolver().query(Uri.parse(uri), null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String ret = cursor.getString(0);
            cursor.close();
            LogCatUtil.printInfo("ReadSettingServerUrl", "getValue.  cursor exist.  uri=[" + uri + "]  ret=[" + ret + "]");
            return TextUtils.isEmpty(ret) ? defaultVal : ret;
        } else if (cursor == null || cursor.isClosed()) {
            return defaultVal;
        } else {
            cursor.close();
            return defaultVal;
        }
    }

    public final boolean isOnline(Context context) {
        if (!isDebug(context) || getGWFURL(context).indexOf(".alipay.net") <= 0) {
            return true;
        }
        return false;
    }

    public final Boolean isEnableAmnetSetting(Context context) {
        if (isDebug(context)) {
            LogCatUtil.info("ReadSettingServerUrl", "isEnableAmnetSetting.  debug is true");
            if (this.e != null) {
                return this.e;
            }
            String val = getValue(context, "content://com.alipay.setting/XmppUseMmtp", null);
            if ("1".equals(val)) {
                this.e = Boolean.TRUE;
            } else if ("0".equals(val)) {
                this.e = Boolean.FALSE;
            }
            return this.e;
        }
        LogCatUtil.info("ReadSettingServerUrl", "isEnableAmnetSetting.  debug is false");
        return null;
    }

    public final Boolean isEnableSpdySetting(Context context) {
        if (!isDebug(context)) {
            LogCatUtil.info("ReadSettingServerUrl", "isEnableSpdySetting.  debug is false");
            return null;
        } else if (this.f != null) {
            return this.f;
        } else {
            LogCatUtil.info("ReadSettingServerUrl", "isEnableSpdySetting.  debug is true");
            String val = getValue(context, "content://com.alipay.setting/XmppUseSpdy", null);
            if ("1".equals(val)) {
                this.f = Boolean.TRUE;
            } else if ("0".equals(val)) {
                this.f = Boolean.FALSE;
            }
            return this.f;
        }
    }

    public final String getAmnetDnsSetting(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "getAmnetDnsSetting. debug is true");
                if (!TextUtils.isEmpty(this.g)) {
                    return this.g;
                }
                String amnetDns = getValue(context, "content://com.alipay.setting/amnet_dns_conf", null);
                StringBuilder sb = new StringBuilder("getAmnetDnsSetting.  amnetDns=[");
                if (TextUtils.isEmpty(amnetDns)) {
                    str = " is null ";
                } else {
                    str = amnetDns;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                if (!TextUtils.isEmpty(amnetDns)) {
                    this.g = amnetDns;
                }
                return this.g;
            }
            LogCatUtil.info("ReadSettingServerUrl", "getAmnetDnsSetting.  debug is false");
            return "";
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "getAmnetDnsSetting exception. " + e2.toString());
            return "";
        }
    }

    public final Boolean isEnabledNbnetDownloadSwitch(Context context) {
        String str;
        try {
            if (!isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "isEnabledNbnetDownloadSwitch.  debug is false, return false.");
                return Boolean.FALSE;
            }
            LogCatUtil.info("ReadSettingServerUrl", "isEnabledNbnetDownloadSwitch. debug is true");
            String enable = getValue(context, "content://com.alipay.setting/nbnet_download_switch", null);
            StringBuilder sb = new StringBuilder("isEnabledNbnetDownloadSwitch.  enable=[");
            if (TextUtils.isEmpty(enable)) {
                str = " is null ";
            } else {
                str = enable;
            }
            LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
            if (enable != null) {
                return Boolean.valueOf(TextUtils.equals(enable, "1"));
            }
            return null;
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isEnabledNbnetDownloadSwitch exception. " + e2.toString());
            return Boolean.valueOf(false);
        }
    }

    public final Boolean isEnabledNbnetUpSwitch(Context context) {
        try {
            if (!isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "isEnabledNbnetUpSwitch.  debug is false, return false.");
                return Boolean.FALSE;
            }
            LogCatUtil.info("ReadSettingServerUrl", "isEnabledNbnetUpSwitch. debug is true");
            String enable = getValue(context, "content://com.alipay.setting/nbnet_up_switch", null);
            LogCatUtil.info("ReadSettingServerUrl", "isEnabledNbnetUpSwitch.  enable=[" + (TextUtils.isEmpty(enable) ? " is null " : enable) + "]");
            if (enable != null) {
                return Boolean.valueOf(TextUtils.equals(enable, "1"));
            }
            return null;
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isEnabledNbnetUpSwitch exception. " + e2.toString());
            return Boolean.valueOf(false);
        }
    }

    public final boolean isEnabledShadowSwitch(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "isEnabledShadowSwitch. debug is true");
                String enable = getValue(context, "content://com.alipay.setting/shadow", "0");
                StringBuilder sb = new StringBuilder("isEnabledShadowSwitch.  enable=[");
                if (TextUtils.isEmpty(enable)) {
                    str = " is null ";
                } else {
                    str = enable;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                return TextUtils.equals(enable, "1");
            }
            LogCatUtil.info("ReadSettingServerUrl", "isEnabledShadowSwitch.  debug is false, return false.");
            return false;
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isEnabledShadowSwitch exception. " + e2.toString());
            return false;
        }
    }

    public final boolean isEnableGlobalNetworkLimit(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "isEnableGlobalNetworkLimit. debug is true");
                String enable = getValue(context, "content://com.alipay.setting/global_network_limit", "0");
                StringBuilder sb = new StringBuilder("isEnableGlobalNetworkLimit.  enable=[");
                if (TextUtils.isEmpty(enable)) {
                    str = " is null ";
                } else {
                    str = enable;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                return TextUtils.equals(enable, "1");
            }
            LogCatUtil.info("ReadSettingServerUrl", "isEnableGlobalNetworkLimit.  debug is false, return false.");
            return false;
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isEnableGlobalNetworkLimit exception. " + e2.toString());
            return false;
        }
    }

    public final boolean isEnableRpcNetworkLimit(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "isEnableRpcNetworkLimit. debug is true");
                String enable = getValue(context, "content://com.alipay.setting/rpc_network_limit", "0");
                StringBuilder sb = new StringBuilder("isEnableRpcNetworkLimit.  enable=[");
                if (TextUtils.isEmpty(enable)) {
                    str = " is null ";
                } else {
                    str = enable;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                return TextUtils.equals(enable, "1");
            }
            LogCatUtil.info("ReadSettingServerUrl", "isEnableRpcNetworkLimit.  debug is false, return false.");
            return false;
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "isEnableRpcNetworkLimit exception. " + e2.toString());
            return false;
        }
    }

    public final String getRpcNameOfRpcNetworkLimit(Context context) {
        String str;
        try {
            if (isDebug(context)) {
                LogCatUtil.info("ReadSettingServerUrl", "getRpcNameOfRpcNetworkLimit. debug is true");
                String rpcName = getValue(context, "content://com.alipay.setting/network_limit_rpc_name", null);
                StringBuilder sb = new StringBuilder("getRpcNameOfRpcNetworkLimit.  rpcName=[");
                if (TextUtils.isEmpty(rpcName)) {
                    str = " is null ";
                } else {
                    str = rpcName;
                }
                LogCatUtil.info("ReadSettingServerUrl", sb.append(str).append("]").toString());
                return rpcName;
            }
            LogCatUtil.info("ReadSettingServerUrl", "getRpcNameOfRpcNetworkLimit.  debug is false");
            return "";
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "ReadSettingServerUrl", "getRpcNameOfRpcNetworkLimit exception. " + e2.toString());
            return "";
        }
    }
}
