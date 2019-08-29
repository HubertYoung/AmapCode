package com.autonavi.minimap.ajx3.util;

import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AjxLogUtil {
    private static String dataPath;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static final Executor singleExecutor = Executors.newSingleThreadExecutor();

    public static void recordLogToTagFile(String str, String str2) {
    }

    public static String getCurrentTime() {
        return lf.a(new Date(System.currentTimeMillis()));
    }

    /* access modifiers changed from: private */
    public static String assertFileName(String str) {
        if (TextUtils.isEmpty(dataPath)) {
            dataPath = PathManager.a().a(DirType.LOG);
        }
        if (dataPath != null && dataPath.contains("autonavi/log")) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().toString());
            sb.append("/");
            String str2 = dataPath;
            sb.append(str2.substring(str2.indexOf("autonavi/log")));
            dataPath = sb.toString();
        }
        try {
            File file = new File(dataPath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception unused) {
        }
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(dataPath);
            sb2.append("/");
            sb2.append(str);
            sb2.append(".txt");
            return sb2.toString();
        }
        StringBuilder sb3 = new StringBuilder("client-");
        sb3.append(sdf.format(Long.valueOf(System.currentTimeMillis())));
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(dataPath);
        sb5.append("/");
        sb5.append(sb4);
        sb5.append(".txt");
        return sb5.toString();
    }
}
