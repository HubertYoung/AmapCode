package com.alipay.mobile.common.logging.helper;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.text.SimpleDateFormat;

public class ClientIdHelper {
    public static String a(Context context) {
        String savedClientId = LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_CLIENTID, null);
        String imei = LoggingSPCache.getInstance().getString(LoggingSPCache.STORAGE_CLIENTIMEI, "");
        String imsi = "";
        try {
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
                TelephonyManager telMgr = (TelephonyManager) context.getSystemService("phone");
                imsi = telMgr.getSubscriberId();
                if (TextUtils.isEmpty(imei)) {
                    imei = telMgr.getDeviceId();
                    if (!TextUtils.isEmpty(imei)) {
                        LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CLIENTIMEI, imei);
                    }
                }
            }
        } catch (Throwable t) {
            Log.e("ClientIdHelper", "initClientId", t);
        }
        if (b(savedClientId)) {
            String savedImsi = savedClientId.substring(0, 15);
            if (a(imsi)) {
                String imsiT = d(imsi);
                if (imsiT.length() > 15) {
                    imsiT = imsiT.substring(0, 15);
                }
                if (!savedImsi.startsWith(imsiT)) {
                    savedImsi = imsi;
                }
            }
            String savedImei = savedClientId.substring(savedClientId.length() - 15, savedClientId.length());
            if (a(imei)) {
                String imeiT = d(imei);
                if (imeiT.length() > 15) {
                    imeiT = imeiT.substring(0, 15);
                }
                if (!savedImei.startsWith(imeiT)) {
                    savedImei = imei;
                }
            }
            String newClientId = a(savedImsi, savedImei);
            LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CLIENTID, newClientId);
            return newClientId;
        }
        if (!a(imei)) {
            imei = a();
        }
        if (!a(imsi)) {
            imsi = a();
        }
        String newClientId2 = a(imsi, imei);
        LoggingSPCache.getInstance().putStringApply(LoggingSPCache.STORAGE_CLIENTID, newClientId2);
        return newClientId2;
    }

    private static String a() {
        return new SimpleDateFormat("yyMMddHHmmssSSS").format(Long.valueOf(System.currentTimeMillis()));
    }

    private static boolean a(String imsiOrimei) {
        if (imsiOrimei == null || imsiOrimei.trim().length() == 0) {
            return false;
        }
        String trimS = imsiOrimei.trim();
        if (trimS.equalsIgnoreCase("unknown") || trimS.equalsIgnoreCase("null") || trimS.matches(DeviceInfo.ANY_ZERO_STR) || trimS.length() <= 5) {
            return false;
        }
        return true;
    }

    private static boolean b(String clientID) {
        if (TextUtils.isEmpty(clientID)) {
            return false;
        }
        return clientID.matches("[[a-z][A-Z][0-9]]{15}\\|[[a-z][A-Z][0-9]]{15}");
    }

    private static String a(String imsi, String imei) {
        return c(imsi) + MergeUtil.SEPARATOR_KV + c(imei);
    }

    private static String c(String imeiOrImsi) {
        if (!a(imeiOrImsi)) {
            imeiOrImsi = a();
        }
        return d((imeiOrImsi + "123456789012345").substring(0, 15));
    }

    private static String d(String imei) {
        if (TextUtils.isEmpty(imei)) {
            return imei;
        }
        byte[] byteClientId = imei.getBytes();
        for (int i = 0; i < byteClientId.length; i++) {
            if (!a(byteClientId[i])) {
                byteClientId[i] = 48;
            }
        }
        return new String(byteClientId);
    }

    private static boolean a(byte c) {
        return (c >= 48 && c <= 57) || (c >= 97 && c <= 122) || (c >= 65 && c <= 90);
    }
}
