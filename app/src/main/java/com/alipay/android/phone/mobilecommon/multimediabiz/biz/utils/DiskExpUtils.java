package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.mobile.common.transport.http.HttpException;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import java.io.IOException;
import java.util.HashMap;

public class DiskExpUtils {
    public static final int CHECK_TIME_INTERVAL = 21600000;
    public static final int DISK_NO_SPACE = 2100;
    public static final int DISK_PERMISSION_DENIED = 2102;
    public static final int DISK_UNWRITEABLE = 2101;
    private static long a = 0;
    private static HashMap<String, String> b = new HashMap<>();

    public static int parseException(Throwable e) {
        int ret = -1;
        if (e != null && (e instanceof HttpException)) {
            int code = ((HttpException) e).getCode();
            if (429 == code) {
                return code;
            }
            if (14 == code) {
                return DjangoConstant.DJANGO_TIMEOUT;
            }
        }
        if (ConfigManager.getInstance().getCommonConfigItem().loadDiskLog == 0) {
            return -1;
        }
        if (e instanceof HttpException) {
            ret = a(((HttpException) e).getCode());
        } else if (e instanceof IOException) {
            ret = a(MiscUtils.getRootCause(e).getMessage());
        }
        return ret;
    }

    private static int a(String exp) {
        if (TextUtils.isEmpty(exp)) {
            return -1;
        }
        if (exp.contains("No space left on device")) {
            return DISK_NO_SPACE;
        }
        if (exp.contains("Permission denied")) {
            return DISK_PERMISSION_DENIED;
        }
        return -1;
    }

    private static int a(int code) {
        switch (code) {
            case 14:
                return DISK_NO_SPACE;
            case 15:
                return DISK_NO_SPACE;
            case 16:
                return DISK_NO_SPACE;
            case 17:
                return DISK_NO_SPACE;
            case 18:
                return DISK_NO_SPACE;
            case 21:
                return 2101;
            case 429:
                return code;
            default:
                return -1;
        }
    }

    public static void putKeyToDiskExpMap(String key) {
        synchronized (b) {
            if (Math.abs(System.currentTimeMillis() - a) > 21600000) {
                b.clear();
                a = System.currentTimeMillis();
            }
            b.put(key, "");
        }
    }

    public static boolean isKeyExistInDiskExpMap(String key) {
        synchronized (b) {
            if (TextUtils.isEmpty(key)) {
                return true;
            }
            boolean containsKey = b.containsKey(key);
            return containsKey;
        }
    }

    public static void UC_MM_47(int ret, String key, boolean bLocal, long size, String id, String biz, String exp) {
        if (ret > 0 && ConfigManager.getInstance().getCommonConfigItem().loadLocalDiskLog == 1 && !isKeyExistInDiskExpMap(key)) {
            putKeyToDiskExpMap(key);
            UCLogUtil.UC_MM_C47(String.valueOf(ret), size, bLocal ? 1 : 0, id, "im", biz, "1", exp, "", "2", false);
        }
    }
}
