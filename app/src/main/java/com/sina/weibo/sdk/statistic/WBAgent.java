package com.sina.weibo.sdk.statistic;

import android.content.Context;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.LogUtil;
import java.util.Map;

public class WBAgent {
    public static final String TAG = "WBAgent";

    public static void openActivityDurationTrack(boolean z) {
        StatisticConfig.ACTIVITY_DURATION_OPEN = z;
    }

    public static void setSessionContinueMillis(long j) {
        StatisticConfig.kContinueSessionMillis = j;
    }

    public static void setAppKey(String str) {
        StatisticConfig.setAppkey(str);
    }

    public static void setChannel(String str) {
        StatisticConfig.setChannel(str);
    }

    public static void setUploadInterval(long j) throws Exception {
        StatisticConfig.setUploadInterval(j);
    }

    public static void setForceUploadInterval(long j) {
        StatisticConfig.setForceUploadInterval(j);
    }

    public static void setNeedGzip(boolean z) {
        StatisticConfig.setNeedGizp(z);
    }

    public static void onPageStart(String str) {
        if (!TextUtils.isEmpty(str)) {
            WBAgentHandler.getInstance().onPageStart(str);
        }
    }

    public static void onPageEnd(String str) {
        if (!TextUtils.isEmpty(str)) {
            WBAgentHandler.getInstance().onPageEnd(str);
        }
    }

    public static void onResume(Context context) {
        if (context == null) {
            LogUtil.e(TAG, "unexpected null context in onResume");
        } else {
            WBAgentHandler.getInstance().onResume(context);
        }
    }

    public static void onPause(Context context) {
        if (context == null) {
            LogUtil.e(TAG, "unexpected null context in onResume");
        } else {
            WBAgentHandler.getInstance().onPause(context);
        }
    }

    public static void onEvent(Object obj, String str) {
        onEvent(obj, str, null);
    }

    public static void onEvent(Object obj, String str, Map<String, String> map) {
        if (obj == null) {
            LogUtil.e(TAG, "unexpected null page or activity in onEvent");
        } else if (str == null) {
            LogUtil.e(TAG, "unexpected null eventId in onEvent");
        } else {
            if (obj instanceof Context) {
                obj = obj.getClass().getName();
            }
            WBAgentHandler.getInstance().onEvent((String) obj, str, map);
        }
    }

    public static void uploadAppLogs(Context context) {
        if (context == null) {
            LogUtil.e(TAG, "unexpected null context in uploadAppLogs");
        } else {
            WBAgentHandler.getInstance().uploadAppLogs(context);
        }
    }

    public static void onStop(Context context) {
        if (context == null) {
            LogUtil.e(TAG, "unexpected null context in onStop");
        } else {
            WBAgentHandler.getInstance().onStop(context);
        }
    }

    public static void onKillProcess() {
        WBAgentHandler.getInstance().onKillProcess();
    }

    public static void setDebugMode(boolean z, boolean z2) {
        LogUtil.sIsLogEnable = z;
        StatisticConfig.setNeedGizp(z2);
    }

    public static void registerApptoAd(Context context, String str, String str2, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            LogUtil.e(TAG, "registerApptoAd appKey is  null  ");
            return;
        }
        setAppKey(str);
        setChannel(str2);
        WBAgentHandler.getInstance().registerApptoAd(context, str, map);
    }
}
