package defpackage;

import android.text.TextUtils;
import android.util.Base64;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.model.POI;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* renamed from: azi reason: default package */
/* compiled from: RouteCommutePreferencesUtil */
public final class azi {
    public static long a() {
        return new MapSharePreference((String) "SharedPreferences").getLongValue("sp_home_date", 0);
    }

    public static void b() {
        new MapSharePreference((String) "SharedPreferences").putLongValue("sp_home_date", System.currentTimeMillis());
    }

    public static long c() {
        return new MapSharePreference((String) "SharedPreferences").getLongValue("sp_company_date", 0);
    }

    public static void d() {
        new MapSharePreference((String) "SharedPreferences").putLongValue("sp_company_date", System.currentTimeMillis());
    }

    public static POI e() {
        Object c = c(new MapSharePreference((String) "SharedPreferences").getStringValue("sp_home_info", null));
        if (c == null || !(c instanceof POI)) {
            return null;
        }
        return (POI) c;
    }

    public static void a(POI poi) {
        new MapSharePreference((String) "SharedPreferences").putStringValue("sp_home_info", a((Object) poi));
    }

    public static POI f() {
        Object c = c(new MapSharePreference((String) "SharedPreferences").getStringValue("sp_company_info", null));
        if (c == null || !(c instanceof POI)) {
            return null;
        }
        return (POI) c;
    }

    public static void b(POI poi) {
        new MapSharePreference((String) "SharedPreferences").putStringValue("sp_company_info", a((Object) poi));
    }

    public static String g() {
        return new MapSharePreference((String) "SharedPreferences").getStringValue("sp_buscar_pref", "");
    }

    public static void a(String str) {
        new MapSharePreference((String) "SharedPreferences").putStringValue("sp_buscar_pref", str);
    }

    private static String a(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            String str = new String(Base64.encode(byteArrayOutputStream.toByteArray(), 0), "utf-8");
            objectOutputStream.close();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Base64.decode(str.getBytes("utf-8"), 0)));
            Object readObject = objectInputStream.readObject();
            objectInputStream.close();
            return readObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void h() {
        new MapSharePreference((String) "SharedPreferences").putBooleanValue("is_enter_commute_detail", true);
    }

    public static void i() {
        new MapSharePreference((String) "SharedPreferences").putIntValue("enter_commute_guide_overly_count", j() + 1);
    }

    public static int j() {
        return new MapSharePreference((String) "SharedPreferences").getIntValue("enter_commute_guide_overly_count", 0);
    }

    public static long k() {
        return new MapSharePreference((String) "SharedPreferences").getLongValue("commute_guide_overly_date", 0);
    }

    public static void l() {
        new MapSharePreference((String) "SharedPreferences").putLongValue("commute_guide_overly_date", System.currentTimeMillis());
    }

    public static void b(String str) {
        new MapSharePreference((String) "SharedPreferences").putStringValue("commute_config", str);
    }

    public static String m() {
        return new MapSharePreference((String) "SharedPreferences").getStringValue("commute_config", "");
    }

    public static String n() {
        return new MapSharePreference((String) "SharedPreferences").getStringValue("new_user_pic_zip_url", "");
    }

    public static void a(boolean z) {
        new MapSharePreference((String) "SharedPreferences").putBooleanValue("commute_map_switch", z);
    }

    public static boolean o() {
        return new MapSharePreference((String) "SharedPreferences").getBooleanValue("commute_map_switch", true);
    }

    public static boolean p() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("key_ab_page_switch", q());
    }

    private static boolean q() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("key_redesign_ab_cloud_state", false);
    }
}
