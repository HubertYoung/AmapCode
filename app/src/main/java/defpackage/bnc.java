package defpackage;

import android.text.TextUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* renamed from: bnc reason: default package */
/* compiled from: CheckCrashLimit */
public final class bnc {
    public static int a = 0;
    public static String b = ".record";
    public static String c = "crash.txt";
    public String d;
    public File e;

    public bnc(String str) {
        this.e = new File(str, "crash_limit");
        if (!this.e.exists()) {
            this.e.mkdirs();
        }
        this.d = new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public final boolean a(String str) {
        return a > 0 && a(new File(this.e, str)) + 1 > a;
    }

    public static int a(File file) {
        int i;
        File file2 = new File(file, b);
        if (!file2.exists()) {
            return 0;
        }
        String b2 = bmu.b(file2);
        if (TextUtils.isEmpty(b2)) {
            return 0;
        }
        try {
            i = Integer.valueOf(b2).intValue();
        } catch (Throwable th) {
            th.printStackTrace();
            i = 0;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public static String a() {
        return c;
    }
}
