package com.standardar.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.List;

public class Util {
    private static boolean DEBUG = false;
    public static final int SLAM_TAG = 417;
    private static final String TAG = "StandardAR";
    public static final int TOF_TAG = 418;
    public static final int VERSION_1 = 4081;
    public static final int VERSION_2 = 4082;
    public static final int VERSION_3 = 4083;
    private static long time;

    public static void LOGD(String str) {
    }

    public static void LOGE(String str) {
    }

    public static void LOGI(String str) {
    }

    public static void LOGW(String str) {
    }

    public static void timeStart() {
        time = SystemClock.elapsedRealtimeNanos();
    }

    public static void timeEnd(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" cost:");
        sb.append(((float) (SystemClock.elapsedRealtimeNanos() - time)) / 1000000.0f);
        sb.append(" ms");
        LOGI(sb.toString());
    }

    public static boolean checkPackageInstalled(Context context, String str) {
        if (context == null || str == null) {
            return false;
        }
        List<PackageInfo> installedPackages = context.getPackageManager().getInstalledPackages(0);
        if (installedPackages == null) {
            return false;
        }
        for (PackageInfo packageInfo : installedPackages) {
            if (packageInfo.packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAndroidExceed(int i) {
        return VERSION.SDK_INT > i;
    }

    public static int getLandscapeDisplayWidth(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.x > point.y ? point.x : point.y;
    }

    public static int getLandscapeDisplayHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        return point.x > point.y ? point.y : point.x;
    }
}
