package defpackage;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

/* renamed from: fay reason: default package */
/* compiled from: SdcardCache */
final class fay implements fai {
    private static final String a;
    private static final String b;
    private static String c = "SdcardCache";
    private File d;

    fay() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(".vivo/pushsdk/config");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(a);
        sb2.append(File.separator);
        sb2.append("config.txt");
        b = sb2.toString();
    }

    public final boolean a(Context context) {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return false;
        }
        File file = new File(a);
        boolean mkdirs = !file.exists() ? file.mkdirs() : true;
        if (mkdirs) {
            this.d = new File(b);
            if (!this.d.exists()) {
                try {
                    this.d.createNewFile();
                    mkdirs = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    mkdirs = false;
                }
            }
        }
        return mkdirs;
    }

    public final String a(String str, String str2) {
        return a().getProperty(str, str2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0028 A[SYNTHETIC, Splitter:B:19:0x0028] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0030 A[SYNTHETIC, Splitter:B:25:0x0030] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            java.util.Properties r0 = a()
            java.lang.String r1 = b
            r2 = 0
            r0.setProperty(r4, r5)     // Catch:{ Exception -> 0x0022 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0022 }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0022 }
            r0.store(r4, r2)     // Catch:{ Exception -> 0x001d, all -> 0x001a }
            r4.flush()     // Catch:{ Exception -> 0x001d, all -> 0x001a }
            r4.close()     // Catch:{ Exception -> 0x0019 }
            return
        L_0x0019:
            return
        L_0x001a:
            r5 = move-exception
            r2 = r4
            goto L_0x002e
        L_0x001d:
            r5 = move-exception
            r2 = r4
            goto L_0x0023
        L_0x0020:
            r5 = move-exception
            goto L_0x002e
        L_0x0022:
            r5 = move-exception
        L_0x0023:
            r5.printStackTrace()     // Catch:{ all -> 0x0020 }
            if (r2 == 0) goto L_0x002d
            r2.close()     // Catch:{ Exception -> 0x002c }
            goto L_0x002d
        L_0x002c:
            return
        L_0x002d:
            return
        L_0x002e:
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0033:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fay.b(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0029 A[SYNTHETIC, Splitter:B:16:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002f A[SYNTHETIC, Splitter:B:21:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Properties a() {
        /*
            java.util.Properties r0 = new java.util.Properties
            r0.<init>()
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0023 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0023 }
            java.lang.String r4 = b     // Catch:{ Exception -> 0x0023 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0023 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0023 }
            r0.load(r2)     // Catch:{ Exception -> 0x001c, all -> 0x0019 }
            r2.close()     // Catch:{ IOException -> 0x002c }
            goto L_0x002c
        L_0x0019:
            r0 = move-exception
            r1 = r2
            goto L_0x002d
        L_0x001c:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            goto L_0x0024
        L_0x0021:
            r0 = move-exception
            goto L_0x002d
        L_0x0023:
            r2 = move-exception
        L_0x0024:
            r2.printStackTrace()     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x002c
            r1.close()     // Catch:{ IOException -> 0x002c }
        L_0x002c:
            return r0
        L_0x002d:
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ IOException -> 0x0032 }
        L_0x0032:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fay.a():java.util.Properties");
    }
}
