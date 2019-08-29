package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import java.util.Map;

/* renamed from: zd reason: default package */
/* compiled from: CdnFilterForUrl */
public final class zd {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (zy.a().b()) {
            zx.a().a(str);
        }
        if (ze.a().a) {
            AMapLog.d("CDNFilter", "url: ".concat(String.valueOf(str)));
        }
        zc.a();
        if (!zc.c()) {
            return str;
        }
        Map<String, String> b = zc.b();
        String host = Uri.parse(str).getHost();
        if (TextUtils.isEmpty(host)) {
            return str;
        }
        String str2 = b.get(host);
        if (TextUtils.isEmpty(str2) || NetworkReachability.a()) {
            return str;
        }
        String replaceFirst = str.replaceFirst(host, str2);
        if (ze.a().a) {
            StringBuilder sb = new StringBuilder("url: ");
            sb.append(str);
            sb.append("  newUrl: ");
            sb.append(replaceFirst);
            AMapLog.d("CDNFilter", sb.toString());
        }
        return replaceFirst;
    }
}
