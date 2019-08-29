package com.uc.crashsdk;

import com.uc.crashsdk.a.b;
import java.io.File;
import java.util.Locale;

/* compiled from: ProGuard */
final class h extends C0106f {
    h(String str, e eVar) {
        super(str, eVar);
    }

    public final boolean a() {
        return b.a(new File(this.b), String.format(Locale.US, "%d %d %d %d", new Object[]{Long.valueOf(this.c.a), Long.valueOf(this.c.b), Integer.valueOf(this.c.c), Integer.valueOf(this.c.d)}).getBytes());
    }
}
