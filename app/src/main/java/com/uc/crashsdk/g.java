package com.uc.crashsdk;

import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.b;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: ProGuard */
final class g extends C0106f {
    final /* synthetic */ File a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    g(String str, e eVar, File file) {
        // this.a = file;
        super(str, eVar);
    }

    public final boolean a() {
        String a2 = b.a(this.a, 64, true);
        if (a2 == null) {
            return false;
        }
        try {
            Matcher matcher = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)").matcher(a2);
            if (matcher.find()) {
                long parseLong = Long.parseLong(matcher.group(1));
                if (System.currentTimeMillis() - parseLong < 86400000) {
                    this.c.b = Long.parseLong(matcher.group(2));
                    this.c.c = Integer.parseInt(matcher.group(3));
                    this.c.d = Integer.parseInt(matcher.group(4));
                    this.c.a = parseLong;
                }
            }
        } catch (Throwable th) {
            a.a(th, false);
        }
        return true;
    }
}
