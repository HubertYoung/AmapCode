package defpackage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.text.SimpleDateFormat;

/* renamed from: emu reason: default package */
/* compiled from: AmapVersionInfoUtil */
public final class emu {
    public static boolean a() {
        boolean z = false;
        try {
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext == null) {
                return false;
            }
            PackageInfo packageInfo = appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0);
            if (packageInfo.firstInstallTime == packageInfo.lastUpdateTime) {
                z = true;
            }
            return z;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static emt a(Context context) {
        emt emt = new emt();
        emt.a = b();
        emt.c = lb.a();
        emt.b = NetworkParam.getDic();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            emt.d = a(packageInfo.firstInstallTime);
            emt.e = a(packageInfo.lastUpdateTime);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return emt;
    }

    public static emt a(String str) {
        return emt.a(new MapSharePreference(SharePreferenceName.SharedPreferences).getStringValue(str, ""));
    }

    public static void a(String str, String str2) {
        StringBuilder sb = new StringBuilder("updateVerInfo2Sp: versionKey = ");
        sb.append(str);
        sb.append(",versionInfo = ");
        sb.append(str2);
        if (!TextUtils.isEmpty(str2)) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putStringValue(str, str2);
        }
    }

    private static String b() {
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext == null) {
            return "";
        }
        try {
            return appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String a(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(j));
    }
}
