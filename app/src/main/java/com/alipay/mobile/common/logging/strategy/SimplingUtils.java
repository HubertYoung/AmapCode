package com.alipay.mobile.common.logging.strategy;

import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import java.util.Calendar;
import java.util.TimeZone;

public class SimplingUtils {
    public static final String TAG = "SimplingUtils";
    public static final int maxRate = 1000;

    public static boolean isHitTest(int rate, String utdid) {
        if (LoggingUtil.isOfflineMode() || rate > 999 || rate < 0) {
            return true;
        }
        if (rate == 0) {
            return false;
        }
        int bucketId = 0;
        if (TextUtils.isEmpty(utdid) || utdid.length() < 2) {
            bucketId = SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR;
        } else {
            try {
                bucketId = ((int) IntUtil.a(utdid.substring(utdid.length() - 2, utdid.length()))) % 1000;
            } catch (Throwable th) {
            }
        }
        if ((getOffset(rate) + bucketId) % 1000 >= rate) {
            return false;
        }
        return true;
    }

    public static int getOffset(int rate) {
        return ((int) (getCurrentDay() % ((long) (1000 / rate)))) * rate;
    }

    public static long getCurrentDay() {
        return ((((Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai")).getTimeInMillis() + 28800000) / 1000) / 60) / 60) / 24;
    }
}
