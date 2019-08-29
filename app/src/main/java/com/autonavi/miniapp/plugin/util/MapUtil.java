package com.autonavi.miniapp.plugin.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public class MapUtil {
    private static final String TAG = "MapUtil";

    public static void openRoute(double d, double d2, String str) {
        try {
            cgz.a((Context) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext());
            StringBuilder sb = new StringBuilder("amapuri://route/plan/?sid=BGVIS1&did=fBGVIS2&dlat=");
            sb.append(d2);
            sb.append("&dlon=");
            sb.append(d);
            sb.append("&dname=");
            sb.append(str);
            sb.append("&dev=0&t=0");
            String sb2 = sb.toString();
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setAction("com.autonavi.minimap.ACTION");
            intent.setData(Uri.parse(sb2));
            AMapPageUtil.getPageContext().startScheme(intent);
        } catch (Exception e) {
            H5Log.e(TAG, "openLocation exception.", e);
        }
    }
}
