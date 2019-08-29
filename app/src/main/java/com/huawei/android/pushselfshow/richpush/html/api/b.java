package com.huawei.android.pushselfshow.richpush.html.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.huawei.android.pushagent.a.a.c;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class b {
    public static String a(String str, String str2) {
        if (a(str2)) {
            return str2;
        }
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(File.separator);
            sb.append(str2);
            String sb2 = sb.toString();
            c.e("PushSelfShowLog", "the audio path is ".concat(String.valueOf(sb2)));
            if (sb2.startsWith("file://")) {
                sb2 = sb2.substring(7);
            }
            if (new File(sb2).exists()) {
                return sb2;
            }
        }
        return null;
    }

    public static ArrayList a(Context context, Intent intent) {
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 0);
        if (!(queryIntentActivities == null || queryIntentActivities.size() == 0)) {
            int size = queryIntentActivities.size();
            for (int i = 0; i < size; i++) {
                if (queryIntentActivities.get(i).activityInfo != null) {
                    StringBuilder sb = new StringBuilder("getSupportPackage:");
                    sb.append(queryIntentActivities.get(i).activityInfo.applicationInfo.packageName);
                    c.a("PushSelfShowLog", sb.toString());
                    arrayList.add(queryIntentActivities.get(i).activityInfo.applicationInfo.packageName);
                }
            }
        }
        return arrayList;
    }

    public static boolean a(String str) {
        return str.contains(AjxHttpLoader.DOMAIN_HTTP) || str.contains(AjxHttpLoader.DOMAIN_HTTPS);
    }
}
