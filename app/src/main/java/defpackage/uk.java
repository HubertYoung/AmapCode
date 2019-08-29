package defpackage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: uk reason: default package */
/* compiled from: MultidexCrashHandler */
public final class uk {
    static StringBuffer d = new StringBuffer();
    Context a;
    StringBuffer b = new StringBuffer();
    SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    uk(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: 0000 */
    public final ApplicationInfo a() {
        ApplicationInfo applicationInfo;
        Exception e;
        try {
            applicationInfo = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), 128);
            try {
                this.b.append("ApplicationInfo = ");
                this.b.append(applicationInfo);
                c();
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            Exception exc = e3;
            applicationInfo = null;
            e = exc;
            this.b.append("getApplicationInfo error: ");
            this.b.append(e.getMessage());
            c();
            this.b.append(a((Throwable) e));
            return applicationInfo;
        }
        return applicationInfo;
    }

    /* access modifiers changed from: 0000 */
    public final ClassLoader b() {
        ClassLoader classLoader;
        Exception e;
        try {
            classLoader = this.a.getClassLoader();
            try {
                this.b.append("loader = ");
                this.b.append(classLoader);
                c();
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            Exception exc = e3;
            classLoader = null;
            e = exc;
            this.b.append("checkClassLoader error: ");
            this.b.append(e.getMessage());
            c();
            this.b.append(a((Throwable) e));
            return classLoader;
        }
        return classLoader;
    }

    /* access modifiers changed from: 0000 */
    public final void a(ApplicationInfo applicationInfo) {
        File[] listFiles;
        try {
            File file = new File(applicationInfo.dataDir, "code_cache");
            File file2 = new File(file, "secondary-dexes");
            if (!file.exists() || !file2.exists()) {
                this.b.append("code_cache exist: ");
                this.b.append(file.exists());
                this.b.append(", secondary-dexes exist: ");
                this.b.append(file2.exists());
                this.b.append("。");
                c();
                return;
            }
            this.b.append("code_cache：");
            this.b.append(file.canWrite());
            this.b.append(" - ");
            this.b.append(file.canRead());
            this.b.append(" - ");
            this.b.append(file.canExecute());
            c();
            this.b.append("secondary-dexes：");
            this.b.append(file2.canWrite());
            this.b.append(" - ");
            this.b.append(file2.canRead());
            this.b.append(" - ");
            this.b.append(file2.canExecute());
            c();
            this.b.append("dex list：");
            c();
            for (File file3 : file2.listFiles()) {
                this.b.append("  file： ");
                this.b.append(file3.length());
                this.b.append(" - ");
                this.b.append(this.c.format(new Date(file3.lastModified())));
                this.b.append(" - ");
                this.b.append(file3.getName());
                c();
                this.b.append("  info： ");
                this.b.append(file3.canWrite());
                this.b.append(" - ");
                this.b.append(file3.canRead());
                this.b.append(" - ");
                this.b.append(file3.canExecute());
                c();
            }
        } catch (Exception e) {
            this.b.append("getFileInfo error: ");
            this.b.append(e.getMessage());
            c();
            this.b.append(a((Throwable) e));
        }
    }

    static Field a(Object obj, String str) throws NoSuchFieldException {
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
                cls = cls.getSuperclass();
            }
        }
        StringBuilder sb = new StringBuilder("Field ");
        sb.append(str);
        sb.append(" not found in ");
        sb.append(obj.getClass());
        throw new NoSuchFieldException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        this.b.append(MultipartUtility.LINE_FEED);
    }

    static String d() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append(":");
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0020  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String a(java.lang.Throwable r4) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ Throwable -> 0x0024, all -> 0x001d }
            r2.<init>()     // Catch:{ Throwable -> 0x0024, all -> 0x001d }
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x0024, all -> 0x001d }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0024, all -> 0x001d }
            r4.printStackTrace(r3)     // Catch:{ Throwable -> 0x001b, all -> 0x0018 }
            java.lang.String r4 = r2.toString()     // Catch:{ Throwable -> 0x001b, all -> 0x0018 }
            r3.close()
            goto L_0x002a
        L_0x0018:
            r4 = move-exception
            r1 = r3
            goto L_0x001e
        L_0x001b:
            r1 = r3
            goto L_0x0024
        L_0x001d:
            r4 = move-exception
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r1.close()
        L_0x0023:
            throw r4
        L_0x0024:
            if (r1 == 0) goto L_0x0029
            r1.close()
        L_0x0029:
            r4 = r0
        L_0x002a:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.uk.a(java.lang.Throwable):java.lang.String");
    }
}
