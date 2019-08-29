package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ll reason: default package */
/* compiled from: GlobalDBUpdateHandler */
public class ll {
    static String a = "global.db";
    static String b = "newGlobal.db";
    private static volatile ll d;
    public a c;

    /* renamed from: ll$a */
    /* compiled from: GlobalDBUpdateHandler */
    public interface a {
        String a();

        boolean a(String str);
    }

    private ll() {
    }

    public static ll a() {
        if (d == null) {
            synchronized (ll.class) {
                try {
                    if (d == null) {
                        d = new ll();
                    }
                }
            }
        }
        return d;
    }

    public final void b() {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().a(DirType.RESOURCE));
        sb.append(File.separator);
        final String sb2 = sb.toString();
        if (!TextUtils.isEmpty(sb2) && this.c.a(sb2)) {
            final String a2 = this.c.a();
            if (!TextUtils.isEmpty(a2)) {
                ahm.a(new Runnable() {
                    public final void run() {
                        try {
                            JSONObject jSONObject = new JSONObject(a2);
                            int parseInt = Integer.parseInt(jSONObject.optString("version"));
                            String optString = jSONObject.optString("path");
                            String lowerCase = jSONObject.optString("md5").toLowerCase();
                            if (ll.a(parseInt) && ll.a(lowerCase)) {
                                ll.a(optString, sb2, lowerCase);
                            }
                        } catch (JSONException unused) {
                        } catch (NumberFormatException unused2) {
                        }
                    }
                });
            }
        }
    }

    static /* synthetic */ boolean a(int i) {
        return i > lm.a();
    }

    static /* synthetic */ boolean a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(PathManager.a().a(DirType.RESOURCE));
        sb.append(File.separator);
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(a);
            String a2 = agy.a(new File(sb3.toString()), null, true);
            if (!TextUtils.isEmpty(a2) && !a2.toLowerCase().equals(str)) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ void a(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(b);
        bjg bjg = new bjg(sb.toString());
        bjg.setUrl(str);
        bjh.a().a(bjg, (bjf) new lk(str2, str3));
    }
}
