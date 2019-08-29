package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.utils.HexStringUtil;
import com.alipay.mobile.common.utils.MD5Util;
import java.security.MessageDigest;
import org.apache.http.client.HttpClient;

public class EnvSwitcher {
    public static final int ENV_CASE = getEnv(AppUtils.getApplicationContext(), 0);
    public static final int ENV_CASE_DAILY = 2;
    public static final int ENV_CASE_ONLINE = 0;
    public static final int ENV_CASE_PRE = 1;
    private static String a = "aliwallet";

    public static final int getEnv(Context context, int defaultVal) {
        return AppUtils.isDebug(context) ? getValue(context, "content://com.alipay.setting/mtdServiceUrl", defaultVal) : defaultVal;
    }

    /* JADX INFO: finally extract failed */
    public static int getValue(Context context, String uri, int defaultVal) {
        Cursor cursor = null;
        try {
            Cursor cursor2 = context.getContentResolver().query(Uri.parse(uri), null, null, null, null);
            if (cursor2 == null || cursor2.getCount() <= 0) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                Logger.D("EnvSwitcher", "getValue fail return " + defaultVal, new Object[0]);
                return defaultVal;
            }
            cursor2.moveToFirst();
            int ret = cursor2.getInt(0);
            Logger.D("EnvSwitcher", "getValue " + uri + ": " + ret + ", defaultVal: " + defaultVal, new Object[0]);
            if (cursor2 == null) {
                return ret;
            }
            cursor2.close();
            return ret;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static boolean isEnableHost2Ip() {
        return ENV_CASE == 0;
    }

    public static boolean enableSpdyDebug() {
        return isEnableHost2Ip();
    }

    public static Env getCurrentEnv() {
        switch (ENV_CASE) {
            case 0:
                return getOnlineEnv();
            case 1:
                return Env.PRE_RELEASE;
            case 2:
                return Env.DAILY;
            default:
                return getOnlineEnv();
        }
    }

    protected static Env getOnlineEnv() {
        return ConfigManager.getInstance().getCommonConfigItem().net.newDomain == 1 ? Env.NEW_ONLINE : Env.ONLINE;
    }

    public static String getSignature(long timestamp) {
        switch (ENV_CASE) {
            case 0:
            case 1:
                return DjangoUtils.genSignature(getAppKey(), Long.valueOf(timestamp));
            case 2:
                return genSignature(getAppKey(), Long.valueOf(timestamp), "0846ea8b62e145c1a25bbffd490f2901");
            default:
                return DjangoUtils.genSignature(getAppKey(), Long.valueOf(timestamp));
        }
    }

    public static String getAclString(String id, String timestamp, ConnectionManager<HttpClient> connectionManager) {
        return getAclString(id, timestamp, connectionManager, true);
    }

    public static String getAclString(String id, String timestamp, ConnectionManager<HttpClient> connectionManager, boolean needCookie) {
        switch (ENV_CASE) {
            case 2:
                return a(id, timestamp, connectionManager, needCookie);
            default:
                StringBuffer sb = new StringBuffer(id);
                sb.append(timestamp);
                if (needCookie) {
                    sb.append(connectionManager.getUid()).append(connectionManager.getAcl());
                }
                return DjangoUtils.genSignature(connectionManager.getAppKey(), sb.toString());
        }
    }

    private static String a(String id, String timestamp, ConnectionManager<HttpClient> connectionManager, boolean needCookie) {
        StringBuffer sb = new StringBuffer(id);
        sb.append(timestamp);
        if (needCookie) {
            sb.append(connectionManager.getUid());
            sb.append(connectionManager.getAcl());
        }
        sb.append("0846ea8b62e145c1a25bbffd490f2901");
        return MD5Util.getMD5String(sb.toString());
    }

    public static String getAppKey() {
        return a;
    }

    public static String genSignature(String appKey, Long timestamp, String appSecret) {
        MessageDigest md = MD5Util.getMD5Digest();
        md.update(appKey.getBytes());
        md.update(String.valueOf(timestamp).getBytes());
        md.update(appSecret.getBytes());
        return new String(HexStringUtil.encodeHex(md.digest()));
    }

    public static boolean isOnlineEnv() {
        return ENV_CASE == 0;
    }
}
