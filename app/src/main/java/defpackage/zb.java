package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import java.util.Map;

/* renamed from: zb reason: default package */
/* compiled from: CDNFilter */
public final class zb implements bpd {
    public final bpk a(bpk bpk) {
        return bpk;
    }

    public final bph a(bph bph) {
        if (bph == null) {
            return bph;
        }
        if (ze.a().a) {
            StringBuilder sb = new StringBuilder("url:");
            sb.append(bph.getUrl());
            AMapLog.d("CDNFilter", sb.toString());
        }
        if (zy.a().b()) {
            zx.a().a(bph.getUrl());
        }
        zc.a();
        if (!zc.c()) {
            AMapLog.d("CDNFilter", "CDNFree is close");
            return bph;
        }
        AMapLog.d("CDNFilter", "CDNFree is open");
        Map<String, String> b = zc.b();
        String url = bph.getUrl();
        if (TextUtils.isEmpty(url)) {
            return bph;
        }
        String host = Uri.parse(url).getHost();
        if (TextUtils.isEmpty(host)) {
            return bph;
        }
        String str = b.get(host);
        if (TextUtils.isEmpty(str) || NetworkReachability.a()) {
            return bph;
        }
        String replaceFirst = url.replaceFirst(host, str);
        bph.setUrl(replaceFirst);
        if (ze.a().a) {
            StringBuilder sb2 = new StringBuilder("url: ");
            sb2.append(url);
            sb2.append("  newUrl: ");
            sb2.append(replaceFirst);
            AMapLog.d("CDNFilter", sb2.toString());
        }
        return bph;
    }
}
