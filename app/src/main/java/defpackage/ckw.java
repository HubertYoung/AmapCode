package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import dalvik.system.DexClassLoader;
import java.io.File;

/* renamed from: ckw reason: default package */
/* compiled from: Hotfix */
public final class ckw extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "Hotfix";
    }

    public final void a(Application application) {
        StringBuilder sb = new StringBuilder();
        sb.append(ahk.a(application).getAbsolutePath());
        sb.append("/.crash.tag");
        enh.a(application).d = sb.toString();
        enh.a(application).e = new eng() {
            public final void a(int i) {
                bqw.a(i, "run", "crashed");
            }

            public final void b(int i) {
                bqw.a(i, "run", "fixed");
                bmp.a((String) "SoHotfixSuccessful", i);
            }

            public final void c(int i) {
                bqw.a(i, "install", "success");
            }

            public final void a(int i, int i2) {
                if (i2 != 7) {
                    bqw.a(i, "install", "fail_".concat(String.valueOf(i2)));
                }
            }
        };
        enh.a(application).c();
        bqe a = bqe.a();
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a.b);
            sb2.append("/dex.apk");
            File file = new File(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a.a);
            sb3.append("/dex.apk");
            File file2 = new File(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a.a);
            sb4.append("/dex.dex");
            File file3 = new File(sb4.toString());
            if (file.exists() && !file.isDirectory()) {
                if (file2.exists()) {
                    ahd.a(file2);
                }
                if (file3.exists()) {
                    ahd.a(file3);
                }
                if (!file.renameTo(file2)) {
                    return;
                }
            }
            if (file2.exists()) {
                if (!file2.isDirectory()) {
                    if (!kl.a(file2)) {
                        ahd.a(file2);
                        return;
                    }
                    if (file2.exists()) {
                        if (!file2.isDirectory()) {
                            Class loadClass = new DexClassLoader(file2.getAbsolutePath(), file2.getParent(), null, Application.class.getClassLoader()).loadClass("com.autonavi.minimap.ExeClient");
                            if (!bqe.a(loadClass)) {
                                ahd.a(file2);
                                return;
                            }
                            if (loadClass != null) {
                                Object newInstance = loadClass.newInstance();
                                loadClass.getDeclaredMethod("main", new Class[]{Application.class}).invoke(newInstance, new Object[]{application});
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
