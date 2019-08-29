package defpackage;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: crt reason: default package */
/* compiled from: Save */
public final class crt {
    public static final String a = AMapAppGlobal.getApplication().getString(R.string.useful);
    public static final String b = AMapAppGlobal.getApplication().getString(R.string.home);
    public static final String c = AMapAppGlobal.getApplication().getString(R.string.company);
    public static final String d = AMapAppGlobal.getApplication().getString(R.string.save_home_and_company);
    public static final String e = AMapAppGlobal.getApplication().getString(R.string.save_tag_meishi);
    public static final String f = AMapAppGlobal.getApplication().getString(R.string.save_tag_shenghuo);
    public static final String g = AMapAppGlobal.getApplication().getString(R.string.save_tag_lvyou);
    public static final String h = AMapAppGlobal.getApplication().getString(R.string.save_tag_gongsi);
    public static final String i = AMapAppGlobal.getApplication().getString(R.string.save_tag_qiche);
    public static final String j = AMapAppGlobal.getApplication().getString(R.string.save_tag_chuxing);
    public static final String k = AMapAppGlobal.getApplication().getString(R.string.save_tag_zhuzhai);
    public static final String l = AMapAppGlobal.getApplication().getString(R.string.save_tag_qita);
    public static final String m = AMapAppGlobal.getApplication().getString(R.string.save_tag_jiudian);

    public static boolean a(String str) {
        return !TextUtils.isEmpty(str) && (b.equals(str) || c.equals(str));
    }

    public static void b(String str) {
        AMapAppGlobal.getApplication().getSharedPreferences("save_flag_sp_file", 0).edit().putBoolean("user_has_delete_home_".concat(String.valueOf(str)), true).apply();
    }

    public static void c(String str) {
        AMapAppGlobal.getApplication().getSharedPreferences("save_flag_sp_file", 0).edit().putBoolean("user_has_delete_company_".concat(String.valueOf(str)), true).apply();
    }
}
