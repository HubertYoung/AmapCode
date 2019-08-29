package defpackage;

import android.text.TextUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: jr reason: default package */
/* compiled from: LegalAppDownLoadUrlCheck */
public final class jr {
    private static Pattern b = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)$");
    public String a;

    /* renamed from: jr$a */
    /* compiled from: LegalAppDownLoadUrlCheck */
    static class a {
        static jr a = new jr(0);
    }

    /* synthetic */ jr(byte b2) {
        this();
    }

    private jr() {
    }

    public static jr a() {
        return a.a;
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.startsWith("http") && "autonavi.com".equals(b(str))) {
            return true;
        }
        if (this.a == null || !this.a.contains(str)) {
            return false;
        }
        return true;
    }

    private static String b(String str) {
        String str2;
        String str3 = null;
        try {
            str2 = new URL(str).getHost().toLowerCase();
            try {
                Matcher matcher = b.matcher(str2);
                if (matcher.find()) {
                    return matcher.group();
                }
            } catch (MalformedURLException e) {
                MalformedURLException malformedURLException = e;
                str3 = str2;
                e = malformedURLException;
                e.printStackTrace();
                str2 = str3;
                return str2;
            }
        } catch (MalformedURLException e2) {
            e = e2;
            e.printStackTrace();
            str2 = str3;
            return str2;
        }
        return str2;
    }
}
