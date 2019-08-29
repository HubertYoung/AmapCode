package defpackage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.server.aos.serverkey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: abj reason: default package */
/* compiled from: PreferencesCookieStore */
public class abj implements abi {
    private static volatile abj c;
    public final ConcurrentHashMap<String, a> a = new ConcurrentHashMap<>();
    public final SharedPreferences b;

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004c, code lost:
        r5 = com.autonavi.server.aos.serverkey.amapDecode(r5.substring(13));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private abj(android.content.Context r9) {
        /*
            r8 = this;
            r8.<init>()
            java.lang.String r0 = "CookiePrefsFile"
            r1 = 0
            android.content.SharedPreferences r9 = r9.getSharedPreferences(r0, r1)
            r8.b = r9
            java.util.concurrent.ConcurrentHashMap r9 = new java.util.concurrent.ConcurrentHashMap
            r9.<init>()
            r8.a = r9
            android.content.SharedPreferences r9 = r8.b
            java.lang.String r0 = "names"
            r2 = 0
            java.lang.String r9 = r9.getString(r0, r2)
            if (r9 == 0) goto L_0x006f
            java.lang.String r0 = ","
            java.lang.String[] r9 = android.text.TextUtils.split(r9, r0)
            int r0 = r9.length
            r3 = 0
        L_0x0026:
            if (r3 >= r0) goto L_0x006c
            r4 = r9[r3]
            android.content.SharedPreferences r5 = r8.b
            java.lang.String r6 = "cookie_"
            java.lang.String r7 = java.lang.String.valueOf(r4)
            java.lang.String r6 = r6.concat(r7)
            java.lang.String r5 = r5.getString(r6, r2)
            if (r5 == 0) goto L_0x0054
            java.lang.String r6 = "[=ServerKey=]"
            boolean r6 = r5.startsWith(r6)
            if (r6 == 0) goto L_0x0054
            int r6 = r5.length()
            r7 = 13
            if (r6 <= r7) goto L_0x0054
            java.lang.String r5 = r5.substring(r7)
            java.lang.String r5 = com.autonavi.server.aos.serverkey.amapDecode(r5)
        L_0x0054:
            if (r5 == 0) goto L_0x0069
            java.util.List r5 = c(r5)
            int r6 = r5.size()
            if (r6 <= 0) goto L_0x0069
            java.util.concurrent.ConcurrentHashMap<java.lang.String, abi$a> r6 = r8.a
            java.lang.Object r5 = r5.get(r1)
            r6.put(r4, r5)
        L_0x0069:
            int r3 = r3 + 1
            goto L_0x0026
        L_0x006c:
            r8.c()
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.abj.<init>(android.content.Context):void");
    }

    @Deprecated
    public static abj a(Context context) {
        if (c == null) {
            synchronized (abj.class) {
                if (c == null) {
                    c = new abj(context);
                }
            }
        }
        return c;
    }

    public static abj a() {
        if (c == null) {
            synchronized (abj.class) {
                try {
                    if (c == null) {
                        c = new abj(aaf.a());
                    }
                }
            }
        }
        return c;
    }

    public final void a(String str) {
        for (a a2 : c(str)) {
            a(a2);
        }
    }

    private boolean c() {
        Editor edit = this.b.edit();
        boolean z = false;
        for (Entry next : this.a.entrySet()) {
            String str = (String) next.getKey();
            a aVar = (a) next.getValue();
            if (aVar.a()) {
                StringBuilder sb = new StringBuilder("cookie expired: ");
                sb.append(str);
                sb.append("=");
                sb.append(aVar.b);
                AMapLog.d("CookieStore", sb.toString());
                this.a.remove(str);
                edit.remove("cookie_".concat(String.valueOf(str)));
                z = true;
            }
        }
        if (z) {
            edit.putString("names", TextUtils.join(",", this.a.keySet()));
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        }
        return z;
    }

    private List<a> d() {
        c();
        return new ArrayList(this.a.values());
    }

    public final a b(String str) {
        c();
        return this.a.get(str);
    }

    public final String b() {
        c();
        StringBuffer stringBuffer = new StringBuffer("");
        for (a b2 : d()) {
            stringBuffer.append(b2.b());
        }
        String stringBuffer2 = stringBuffer.toString();
        if (!TextUtils.isEmpty(stringBuffer2) && stringBuffer2.endsWith(";")) {
            stringBuffer2 = stringBuffer2.substring(0, stringBuffer2.length() - 1);
        }
        AMapLog.d("CookieStore", "cookie: ".concat(String.valueOf(stringBuffer2)));
        return stringBuffer2;
    }

    private static List<a> c(String str) {
        ArrayList<a> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(";");
            if (split != null && split.length > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                Date date = null;
                String str2 = null;
                String str3 = null;
                for (String str4 : split) {
                    if (str4.contains("=")) {
                        String[] split2 = str4.split("=");
                        if (split2.length == 2) {
                            String trim = split2[0].trim();
                            String trim2 = split2[1].trim();
                            if (trim.equalsIgnoreCase("expires")) {
                                if (date == null) {
                                    try {
                                        date = simpleDateFormat.parse(trim2.replace("-", Token.SEPARATOR));
                                    } catch (Throwable th) {
                                        AMapLog.d("CookieStore", th.getMessage());
                                    }
                                }
                            } else if (trim.equalsIgnoreCase("domain")) {
                                str2 = trim2;
                            } else if (trim.equalsIgnoreCase("path")) {
                                str3 = trim2;
                            } else if (trim.equalsIgnoreCase("Max-Age")) {
                                try {
                                    date = new Date(System.currentTimeMillis() + (Long.parseLong(trim2) * 1000));
                                } catch (Throwable th2) {
                                    AMapLog.d("CookieStore", th2.getMessage());
                                }
                            } else {
                                a aVar = new a();
                                aVar.a = trim;
                                aVar.b = trim2;
                                arrayList.add(aVar);
                            }
                        }
                    }
                }
                if (!(date == null && str2 == null && str3 == null)) {
                    for (a aVar2 : arrayList) {
                        aVar2.e = date;
                        aVar2.c = str2;
                        aVar2.d = str3;
                    }
                }
            }
        }
        return arrayList;
    }

    private void a(a aVar) {
        String str = aVar.a;
        if (!aVar.a()) {
            this.a.put(str, aVar);
        } else {
            this.a.remove(str);
        }
        Editor edit = this.b.edit();
        edit.putString("names", TextUtils.join(",", this.a.keySet()));
        String concat = "cookie_".concat(String.valueOf(str));
        StringBuilder sb = new StringBuilder("[=ServerKey=]");
        sb.append(serverkey.amapEncode(aVar.toString()));
        edit.putString(concat, sb.toString());
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }
}
