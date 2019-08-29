package defpackage;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/* renamed from: dau reason: default package */
/* compiled from: GifLoader */
public final class dau {
    private static final String a;
    /* access modifiers changed from: private */
    public static final Set<String> b = new HashSet();
    /* access modifiers changed from: private */
    public static final Handler c = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public static final long d = System.currentTimeMillis();

    /* renamed from: dau$a */
    /* compiled from: GifLoader */
    public interface a {
        void a();

        void a(File file);
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append(File.separator);
        sb.append("autonavi/cache/gif/");
        a = sb.toString();
        final File file = new File(a);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            ahl.a(new defpackage.ahl.a<Object>() {
                public final Object doBackground() throws Exception {
                    File[] listFiles;
                    for (File file : file.listFiles()) {
                        if (dau.d - file.lastModified() > 864000000 && !dau.b.contains(file.getName())) {
                            file.delete();
                        }
                    }
                    return null;
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(final android.widget.ImageView r6, java.lang.String r7, final defpackage.dau.a r8) {
        /*
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 == 0) goto L_0x000a
            return
        L_0x000a:
            java.lang.String r0 = a(r7)
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 != 0) goto L_0x0038
            java.io.File r0 = new java.io.File
            java.lang.String r2 = a(r7)
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L_0x0038
            long r2 = d
            long r4 = r0.lastModified()
            long r2 = r2 - r4
            r4 = 864000000(0x337f9800, double:4.26872718E-315)
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 >= 0) goto L_0x0038
            r0 = 1
            goto L_0x0039
        L_0x0038:
            r0 = 0
        L_0x0039:
            if (r0 == 0) goto L_0x003f
            a(r6, r1, r8)
            return
        L_0x003f:
            bjg r0 = new bjg
            java.lang.String r2 = r1.getAbsolutePath()
            r0.<init>(r2)
            r0.setUrl(r7)
            r7 = 10000(0x2710, float:1.4013E-41)
            r0.setTimeout(r7)
            defpackage.yq.a()
            dau$2 r7 = new dau$2
            r7.<init>(r6, r1, r8)
            defpackage.yq.a(r0, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dau.a(android.widget.ImageView, java.lang.String, dau$a):void");
    }

    public static void a(final ImageView imageView, final File file, final a aVar) {
        if (imageView == null || file == null || !file.exists()) {
            if (!(aVar == null || aVar == null)) {
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                    aVar.a();
                    return;
                }
                c.post(new Runnable() {
                    public final void run() {
                        aVar.a();
                    }
                });
            }
            return;
        }
        b.add(file.getName());
        final Uri fromFile = Uri.fromFile(file);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            imageView.setImageURI(fromFile);
            if (aVar != null) {
                aVar.a(file);
            }
        } else {
            c.post(new Runnable() {
                public final void run() {
                    imageView.setImageURI(fromFile);
                    if (aVar != null) {
                        aVar.a(file);
                    }
                }
            });
        }
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String a2 = agy.a(str);
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(a2);
        return sb.toString();
    }
}
