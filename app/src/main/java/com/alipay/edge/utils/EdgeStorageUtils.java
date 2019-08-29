package com.alipay.edge.utils;

import android.content.Context;
import com.alipay.edge.impl.EdgeSwitchManager;
import com.alipay.security.mobile.module.commonutils.FileUtil;
import com.alipay.security.mobile.module.localstorage.SharePreferenceStorage;
import java.io.File;

public class EdgeStorageUtils {
    private static String a = "sc_edge";
    private static String b = "edge_config_data";

    public static String a(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/");
        sb.append(a);
        String sb2 = sb.toString();
        FileUtil.createDirs(sb2);
        return new File(sb2).exists() ? sb2 : "";
    }

    public static void b(Context context) {
        FileUtil.deleteFile(new File(a(context)));
        EdgeSwitchManager.a(context).d();
        if (context != null) {
            SharePreferenceStorage.writeDataToSharePreference(context, b, "edge_rules", "");
        }
    }

    public static int c(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append("/");
        sb.append(a);
        sb.append("/DATA0.db");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(context.getFilesDir());
        sb3.append("/");
        sb3.append(a);
        sb3.append("/DATA1.db");
        String sb4 = sb3.toString();
        boolean fileExists = FileUtil.fileExists(sb2);
        return FileUtil.fileExists(sb4) ? fileExists | true ? 1 : 0 : fileExists ? 1 : 0;
    }
}
