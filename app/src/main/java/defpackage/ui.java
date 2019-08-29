package defpackage;

import android.text.TextUtils;
import java.io.File;

/* renamed from: ui reason: default package */
/* compiled from: DumpCrashConfig */
public final class ui {
    File a = null;
    String b = null;
    private uc c;

    ui(uc ucVar) {
        this.c = ucVar;
    }

    public static String a() {
        String b2 = aaf.b("crash_log_url");
        return !TextUtils.isEmpty(b2) ? b2 : "";
    }
}
