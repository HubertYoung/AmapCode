package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.server.aos.serverkey;
import java.io.File;
import java.nio.charset.Charset;

/* renamed from: wh reason: default package */
/* compiled from: JsAuthCfg */
public final class wh {
    private static final String a;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append("/jsauthwhitelist.dat");
        a = sb.toString();
    }

    private static SharedPreferences f() {
        return AMapAppGlobal.getApplication().getSharedPreferences("jsauth", 0);
    }

    static boolean a() {
        return f().getBoolean("whitelistswitch", true);
    }

    static void a(boolean z) {
        AMapLog.i("jsauth", "[JsAuthCfg] setEnableAccessControl = ".concat(String.valueOf(z)));
        Editor edit = f().edit();
        if (edit != null) {
            edit.putBoolean("whitelistswitch", z);
            edit.apply();
        }
    }

    static boolean a(String str) {
        return ahd.a(new File(a), str);
    }

    static String b() {
        File file = new File(a);
        if (!file.exists()) {
            return null;
        }
        return ahd.d(file);
    }

    static String c() {
        return ahd.a((Context) AMapAppGlobal.getApplication(), (String) "jsauthwhitelist.dat", Charset.defaultCharset());
    }

    static String b(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            str2 = serverkey.amapDecode(str);
        } catch (Exception unused) {
            str2 = null;
        }
        return str2;
    }

    static String d() {
        lo a2 = lo.a();
        if (!a2.e("getModuleVersion() not init")) {
            return "";
        }
        return a2.a.b((String) "h5_white_list");
    }

    static void e() {
        File file = new File(a);
        if (file.exists()) {
            ahd.a(file);
        }
        lo.a().c("h5_white_list");
    }
}
