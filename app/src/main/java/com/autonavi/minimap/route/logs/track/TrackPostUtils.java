package com.autonavi.minimap.route.logs.track;

import android.os.Environment;
import com.autonavi.jni.eyrie.amap.tracker.TrackType;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.io.File;

public final class TrackPostUtils {
    private static String[] a = {"busnavi", "rtbt", "wtbt", "helride", "helrun", "sharebike", "elebike"};

    enum ERouteTag {
        TYPE_ROUTE_DEFAULT,
        TYPE_WALK_LEAST,
        TYPE_LAZY_MODEL,
        TYPE_ROAD_FIRST,
        TYPE_ROUTE_2
    }

    public static String a(TrackType trackType, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/autonavi/a_routelog/");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(a[trackType.ordinal()]);
        String sb4 = sb3.toString();
        if (z) {
            File file = new File(sb4);
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(sb4, ReportManager.LOG_PATH);
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        return sb4;
    }

    public static void a(String str) {
        eao.a((String) "Tracker", str);
    }
}
