package defpackage;

import com.amap.bundle.blutils.FileUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import java.io.File;

/* renamed from: ati reason: default package */
/* compiled from: CarLogoDownloadManager */
public final class ati {
    public static void a(String str, String str2, String str3, String str4, ath ath) {
        if (str != null && str2 != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(a());
            sb.append("/");
            sb.append(a(str, str2));
            String sb2 = sb.toString();
            ath.a = sb2;
            bjg bjg = new bjg(sb2);
            bjg.setUrl(str2);
            if (atg.a(str, sb2)) {
                a(str, sb2, str3, str4);
            } else {
                bjh.a().a(bjg, (bjf) ath);
            }
        }
    }

    private static String a() {
        String externalStroragePath = FileUtil.getExternalStroragePath(AMapAppGlobal.getApplication());
        if (externalStroragePath == null) {
            return null;
        }
        File file = new File(externalStroragePath, "/autonavi/carLogo");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static void a(String str, String str2, String str3, String str4) {
        File file = new File(str2);
        if (file.exists() || "0".equals(str)) {
            String b = b();
            atj atj = new atj();
            atj.a = str;
            atj.b = str4;
            atj.c = b;
            atj.d = false;
            atj.e = true;
            if ("normalType".equals(str4)) {
                atj.g = file.getAbsolutePath();
            } else {
                atj.f = file.getAbsolutePath();
            }
            atg.a(atj);
            if (a(str3)) {
                atg.b(b, str);
            }
        }
    }

    private static String b() {
        String c = c();
        if (c == null) {
            c = "public";
        }
        return agy.a(c);
    }

    private static boolean a(String str) {
        return "1".equals(str);
    }

    private static String a(String str, String str2) {
        if (str2 == null || str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(agy.a(str2));
        return sb.toString();
    }

    private static String c() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (e == null) {
            return "";
        }
        return e.a;
    }
}
