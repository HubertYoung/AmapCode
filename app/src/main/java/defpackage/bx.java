package defpackage;

import android.content.Context;
import anet.channel.statist.StrategyStatObject;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/* renamed from: bx reason: default package */
/* compiled from: StrategySerializeHelper */
public final class bx {
    private static File a = null;
    private static volatile boolean b = false;
    private static Comparator<File> c = new Comparator<File>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return (int) (((File) obj2).lastModified() - ((File) obj).lastModified());
        }
    };

    public static void a(Context context) {
        if (context != null) {
            try {
                File file = new File(context.getFilesDir(), "awcn_strategy");
                a = file;
                if (!a(file)) {
                    cl.d("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", a.getAbsolutePath());
                }
                if (!m.b()) {
                    String c2 = m.c();
                    File file2 = new File(a, c2.substring(c2.indexOf(58) + 1));
                    a = file2;
                    if (!a(file2)) {
                        cl.d("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", a.getAbsolutePath());
                    }
                }
                cl.b("awcn.StrategySerializeHelper", "StrateyFolder", null, "path", a.getAbsolutePath());
                if (b) {
                    a();
                    b = false;
                    return;
                }
                c();
            } catch (Throwable unused) {
                cl.e("awcn.StrategySerializeHelper", "StrategySerializeHelper initialize failed!!!", null, new Object[0]);
            }
        }
    }

    private static boolean a(File file) {
        if (file == null || file.exists()) {
            return true;
        }
        return file.mkdir();
    }

    private static File a(String str) {
        a(a);
        return new File(a, str);
    }

    static synchronized void a() {
        synchronized (bx.class) {
            cl.b("awcn.StrategySerializeHelper", "clear start.", null, new Object[0]);
            if (a == null) {
                cl.c("awcn.StrategySerializeHelper", "folder path not initialized, wait to clear", null, new Object[0]);
                b = true;
                return;
            }
            File[] listFiles = a.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
                cl.b("awcn.StrategySerializeHelper", "clear end.", null, new Object[0]);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.io.File[] b() {
        /*
            java.lang.Class<bx> r0 = defpackage.bx.class
            monitor-enter(r0)
            java.io.File r1 = a     // Catch:{ all -> 0x0019 }
            if (r1 != 0) goto L_0x000a
            r1 = 0
            monitor-exit(r0)
            return r1
        L_0x000a:
            java.io.File r1 = a     // Catch:{ all -> 0x0019 }
            java.io.File[] r1 = r1.listFiles()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x0017
            java.util.Comparator<java.io.File> r2 = c     // Catch:{ all -> 0x0019 }
            java.util.Arrays.sort(r1, r2)     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)
            return r1
        L_0x0019:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bx.b():java.io.File[]");
    }

    private static synchronized void c() {
        synchronized (bx.class) {
            File[] b2 = b();
            if (b2 != null) {
                int i = 0;
                for (File file : b2) {
                    if (!file.isDirectory()) {
                        if (System.currentTimeMillis() - file.lastModified() > 172800000) {
                            file.delete();
                        } else if (file.getName().startsWith("WIFI")) {
                            int i2 = i + 1;
                            if (((long) i) > 10) {
                                file.delete();
                            }
                            i = i2;
                        }
                    }
                }
            }
        }
    }

    public static synchronized void a(Serializable serializable, String str, StrategyStatObject strategyStatObject) {
        synchronized (bx.class) {
            cx.a(serializable, a(str), strategyStatObject);
        }
    }

    public static synchronized <T> T a(String str, StrategyStatObject strategyStatObject) {
        T a2;
        synchronized (bx.class) {
            try {
                a2 = cx.a(a(str), strategyStatObject);
            }
        }
        return a2;
    }
}
