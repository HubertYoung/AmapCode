package com.alibaba.analytics.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.HashMap;
import java.util.Map;

public class AppInfoUtil {
    private static final int IMPORTANCE_BACKGROUND = 400;
    private static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
    private static final String TAG = "AppInfoUtil";
    private static String mAppkey = null;
    private static String mChannle = "";
    static Map<String, String> preInfoMap;

    public static String getUserid() {
        return "";
    }

    public static String getUsernick() {
        return "";
    }

    public static String getLongLoginUsernick() {
        String str = "";
        if (Variables.getInstance().getContext() == null) {
            return str;
        }
        try {
            String string = Variables.getInstance().getContext().getSharedPreferences("UTCommon", 0).getString("_lun", "");
            if (!TextUtils.isEmpty(string)) {
                str = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
            }
        } catch (Exception unused) {
        }
        return str;
    }

    public static String getLongLoingUserid() {
        if (Variables.getInstance().getContext() == null) {
            return "";
        }
        String str = "";
        try {
            String string = Variables.getInstance().getContext().getSharedPreferences("UTCommon", 0).getString("_luid", "");
            if (!TextUtils.isEmpty(string)) {
                str = new String(Base64.decode(string.getBytes(), 2), "UTF-8");
            }
        } catch (Exception unused) {
        }
        return str;
    }

    public static String getChannel() {
        return Variables.getInstance().getChannel();
    }

    public static String getAppkey() {
        return Variables.getInstance().getAppkey();
    }

    public static boolean isAppOnForeground(Context context) {
        if (context == null) {
            Logger.i((String) null, "foreground", Boolean.FALSE);
            return false;
        }
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (next.processName.equals(packageName)) {
                    if (next.importance != 400) {
                        if (next.importance != 125) {
                            if (!powerManager.isScreenOn()) {
                                return false;
                            }
                            Logger.i((String) null, "foreground", Boolean.TRUE);
                            return true;
                        }
                    }
                    Logger.i((String) null, "foreground", Boolean.FALSE);
                    return false;
                }
            }
        } catch (Throwable unused) {
        }
        Logger.i((String) null, Subscribe.THREAD_BACKGROUND, Boolean.FALSE);
        return false;
    }

    public static String getCurProcessName(Context context) {
        if (context == null) {
            return "";
        }
        int myPid = Process.myPid();
        for (RunningAppProcessInfo next : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return null;
    }

    public static String getString(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            Resources resources = context.getResources();
            if (resources == null) {
                return null;
            }
            int identifier = resources.getIdentifier(str, ResUtils.STRING, context.getPackageName());
            if (identifier != 0) {
                return context.getString(identifier);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Map<String, String> getInfoForPreApk(Context context) {
        if (preInfoMap != null) {
            return preInfoMap;
        }
        if (context == null) {
            return null;
        }
        preInfoMap = new HashMap();
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("manufacture_config", 0);
            boolean z = sharedPreferences.getBoolean("preLoad", false);
            String string = sharedPreferences.getString("preLoad_VersionName", "");
            String string2 = sharedPreferences.getString("preLoad_Channel1", "");
            String string3 = sharedPreferences.getString("preLoad_Channel2", "");
            if (z) {
                preInfoMap.put("preLoad", "true");
                preInfoMap.put("preLoad_VersionName", string);
                preInfoMap.put("preLoad_Channel1", string2);
                preInfoMap.put("preLoad_Channel2", string3);
            }
        } catch (Exception unused) {
        }
        return preInfoMap;
    }

    public static String getChannle2ForPreLoadApk(Context context) {
        Map<String, String> infoForPreApk = getInfoForPreApk(context);
        if (infoForPreApk != null) {
            return infoForPreApk.get("preLoad_Channel2");
        }
        return null;
    }
}
