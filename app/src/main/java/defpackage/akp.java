package defpackage;

import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/* renamed from: akp reason: default package */
/* compiled from: DebugFileUtils */
public final class akp {
    public static final String a;
    private static final String b;
    private static boolean c = b();

    /* renamed from: akp$a */
    /* compiled from: DebugFileUtils */
    static class a implements FilenameFilter {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final boolean accept(File file, String str) {
            return str.endsWith(".so");
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getAppSDCardFileDir());
        sb.append("/AE8/lib");
        b = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtil.getAppSDCardFileDir());
        sb2.append("/flag.txt");
        a = sb2.toString();
    }

    public static String a() throws IOException {
        return a(b);
    }

    public static String a(String str) throws IOException {
        String[] list;
        File file = new File(str);
        if (file.exists()) {
            if (file.list(new a(0)).length > 0) {
                String str2 = !c ? "A" : DiskFormatter.B;
                StringBuilder sb = new StringBuilder();
                sb.append(c());
                sb.append(str2);
                String sb2 = sb.toString();
                File file2 = new File(sb2);
                if (!file2.exists()) {
                    file2.mkdir();
                }
                String str3 = "";
                for (String str4 : file.list(new a(0))) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append("/");
                    sb3.append(str4);
                    String sb4 = sb3.toString();
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb2);
                    sb5.append("/");
                    sb5.append(str4);
                    a(sb4, sb5.toString());
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(str3);
                    sb6.append(str4);
                    sb6.append(";");
                    str3 = sb6.toString();
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str);
                    sb7.append("/");
                    sb7.append(str4);
                    new File(sb7.toString()).delete();
                    AMapLog.d("jiawu", "copyLibsFromSdcardToapppkg ".concat(String.valueOf(str4)));
                }
                File file3 = new File(a);
                if (!c) {
                    if (file3.exists()) {
                        file3.delete();
                    }
                } else if (!file3.exists()) {
                    file3.createNewFile();
                }
                return str3;
            }
        }
        return null;
    }

    public static boolean b() {
        return !new File(a).exists();
    }

    public static String c() {
        StringBuilder sb = new StringBuilder("/data/data/");
        sb.append(AMapAppGlobal.getApplication().getPackageName());
        sb.append("/ae8libs");
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x006e A[SYNTHETIC, Splitter:B:42:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0076 A[Catch:{ IOException -> 0x0072 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x007b A[Catch:{ IOException -> 0x0072 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0086 A[SYNTHETIC, Splitter:B:54:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x008e A[Catch:{ IOException -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0093 A[Catch:{ IOException -> 0x008a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r6, java.lang.String r7) {
        /*
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x0066, all -> 0x0062 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0066, all -> 0x0062 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0066, all -> 0x0062 }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0066, all -> 0x0062 }
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            r2.<init>(r7)     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            boolean r7 = r2.exists()     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            if (r7 != 0) goto L_0x001a
            r2.createNewFile()     // Catch:{ IOException -> 0x005f, all -> 0x005c }
        L_0x001a:
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            r7.<init>(r2)     // Catch:{ IOException -> 0x005f, all -> 0x005c }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0056, all -> 0x0051 }
            r2.<init>()     // Catch:{ IOException -> 0x0056, all -> 0x0051 }
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]     // Catch:{ IOException -> 0x004c, all -> 0x004a }
        L_0x0028:
            int r3 = r6.read(r1)     // Catch:{ IOException -> 0x004c, all -> 0x004a }
            r4 = -1
            if (r3 == r4) goto L_0x0033
            r2.write(r1, r0, r3)     // Catch:{ IOException -> 0x004c, all -> 0x004a }
            goto L_0x0028
        L_0x0033:
            byte[] r1 = r2.toByteArray()     // Catch:{ IOException -> 0x004c, all -> 0x004a }
            r7.write(r1)     // Catch:{ IOException -> 0x004c, all -> 0x004a }
            r7.close()     // Catch:{ IOException -> 0x0044 }
            r2.close()     // Catch:{ IOException -> 0x0044 }
            r6.close()     // Catch:{ IOException -> 0x0044 }
            goto L_0x0048
        L_0x0044:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0048:
            r6 = 1
            return r6
        L_0x004a:
            r0 = move-exception
            goto L_0x0053
        L_0x004c:
            r1 = move-exception
            r5 = r1
            r1 = r7
            r7 = r5
            goto L_0x0069
        L_0x0051:
            r0 = move-exception
            r2 = r1
        L_0x0053:
            r1 = r7
            r7 = r0
            goto L_0x0084
        L_0x0056:
            r2 = move-exception
            r5 = r1
            r1 = r7
            r7 = r2
            r2 = r5
            goto L_0x0069
        L_0x005c:
            r7 = move-exception
            r2 = r1
            goto L_0x0084
        L_0x005f:
            r7 = move-exception
            r2 = r1
            goto L_0x0069
        L_0x0062:
            r7 = move-exception
            r6 = r1
            r2 = r6
            goto L_0x0084
        L_0x0066:
            r7 = move-exception
            r6 = r1
            r2 = r6
        L_0x0069:
            r7.printStackTrace()     // Catch:{ all -> 0x0083 }
            if (r1 == 0) goto L_0x0074
            r1.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0074
        L_0x0072:
            r6 = move-exception
            goto L_0x007f
        L_0x0074:
            if (r2 == 0) goto L_0x0079
            r2.close()     // Catch:{ IOException -> 0x0072 }
        L_0x0079:
            if (r6 == 0) goto L_0x0082
            r6.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0082
        L_0x007f:
            r6.printStackTrace()
        L_0x0082:
            return r0
        L_0x0083:
            r7 = move-exception
        L_0x0084:
            if (r1 == 0) goto L_0x008c
            r1.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x008c
        L_0x008a:
            r6 = move-exception
            goto L_0x0097
        L_0x008c:
            if (r2 == 0) goto L_0x0091
            r2.close()     // Catch:{ IOException -> 0x008a }
        L_0x0091:
            if (r6 == 0) goto L_0x009a
            r6.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x009a
        L_0x0097:
            r6.printStackTrace()
        L_0x009a:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akp.a(java.lang.String, java.lang.String):boolean");
    }
}
