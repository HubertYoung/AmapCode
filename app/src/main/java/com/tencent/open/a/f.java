package com.tencent.open.a;

import android.os.Environment;
import android.text.TextUtils;
import com.tencent.open.a.d.a;
import com.tencent.open.a.d.b;
import com.tencent.open.a.d.c;
import com.tencent.open.utils.Global;
import java.io.File;

/* compiled from: ProGuard */
public class f {
    public static f a = null;
    protected static final b c;
    private static boolean d = false;
    protected a b = new a(c);

    static {
        b bVar = new b(c(), c.m, c.g, c.h, c.c, (long) c.i, 10, c.e, c.n);
        c = bVar;
    }

    public static f a() {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f();
                    d = true;
                }
            }
        }
        return a;
    }

    private f() {
    }

    /* access modifiers changed from: protected */
    public void a(int i, String str, String str2, Throwable th) {
        if (d) {
            String packageName = Global.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                StringBuilder sb = new StringBuilder();
                sb.append(packageName);
                sb.append(" SDK_VERSION:2.9.4.lite");
                String sb2 = sb.toString();
                if (this.b != null) {
                    String str3 = sb2;
                    e.a.b(32, Thread.currentThread(), System.currentTimeMillis(), "openSDK_LOG", str3, null);
                    this.b.b(32, Thread.currentThread(), System.currentTimeMillis(), "openSDK_LOG", str3, null);
                    d = false;
                } else {
                    return;
                }
            }
        }
        e.a.b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        if (a.a(c.b, i) && this.b != null) {
            this.b.b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        }
    }

    public static final void a(String str, String str2) {
        a().a(1, str, str2, null);
    }

    public static final void b(String str, String str2) {
        a().a(2, str, str2, null);
    }

    public static final void a(String str, String str2, Throwable th) {
        a().a(2, str, str2, th);
    }

    public static final void c(String str, String str2) {
        a().a(4, str, str2, null);
    }

    public static final void d(String str, String str2) {
        a().a(8, str, str2, null);
    }

    public static final void e(String str, String str2) {
        a().a(16, str, str2, null);
    }

    public static final void b(String str, String str2, Throwable th) {
        a().a(16, str, str2, th);
    }

    public static void b() {
        synchronized (f.class) {
            a().d();
            if (a != null) {
                a = null;
            }
        }
    }

    protected static File c() {
        String str = c.d;
        c b2 = b.b();
        if (b2 != null && b2.c() > c.f) {
            return new File(Environment.getExternalStorageDirectory(), str);
        }
        return new File(Global.getFilesDir(), str);
    }

    /* access modifiers changed from: protected */
    public void d() {
        if (this.b != null) {
            this.b.a();
            this.b.b();
            this.b = null;
        }
    }
}
