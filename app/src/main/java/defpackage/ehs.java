package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: ehs reason: default package */
/* compiled from: ShareBikeSpUtil */
public final class ehs {
    public static void a(boolean z) {
        a((String) "share_bike_icon_down_suc", String.valueOf(z));
    }

    public static void a(String str) {
        a(str, System.currentTimeMillis());
        a((String) "share_bike_order_id", str);
    }

    public static void a(int i) {
        a((String) "share_bike_ofo_loading_time", String.valueOf(i));
    }

    public static int a() {
        String b = b("share_bike_ofo_loading_time");
        if (TextUtils.isEmpty(b)) {
            return 0;
        }
        try {
            return Integer.parseInt(b);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static long b() {
        String b = b("share_bike_ofo_max_loading_time");
        if (TextUtils.isEmpty(b)) {
            return 120;
        }
        try {
            return (long) Integer.parseInt(b);
        } catch (Exception unused) {
            return 120;
        }
    }

    public static synchronized String b(String str) {
        String str2;
        synchronized (ehs.class) {
            str2 = "";
            try {
                if (AMapPageUtil.getAppContext() != null) {
                    str2 = new MapSharePreference((String) "share_bike_sp_data").getStringValue(str, "");
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(", ");
                sb.append(str2);
                eao.b("getStatusFromSp", sb.toString());
            }
        }
        return str2;
    }

    public static synchronized boolean a(String str, String str2) {
        synchronized (ehs.class) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", ");
            sb.append(str2);
            eao.b("setDataToSp", sb.toString());
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "share_bike_sp_data").putStringValue(str, str2);
            return true;
        }
    }

    private static synchronized boolean b(String str, long j) {
        synchronized (ehs.class) {
            if (AMapPageUtil.getAppContext() == null) {
                return false;
            }
            new MapSharePreference((String) "share_bike_sp_data").putLongValue(str, j);
            return true;
        }
    }

    public static synchronized long c(String str) {
        long j;
        synchronized (ehs.class) {
            j = 0;
            if (AMapPageUtil.getAppContext() != null) {
                j = new MapSharePreference((String) "share_bike_sp_data").getLongValue(str, 0);
            }
        }
        return j;
    }

    private static void a(String str, long j) {
        String b = b("share_bike_order_id");
        if (j > 0 && !TextUtils.equals(str, b) && !TextUtils.isEmpty(str)) {
            b("share_bike_riding_start_timestamp", j);
            StringBuilder sb = new StringBuilder("orderId: ");
            sb.append(str);
            sb.append(" startStamp:");
            sb.append(j);
            eao.a((String) "shareRidingStart[tylorvan]", sb.toString());
        }
    }
}
