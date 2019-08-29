package com.alipay.deviceid.module.senative;

import android.content.Context;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class a {
    private static Context a = null;
    private static String b = "";

    /* renamed from: com.alipay.deviceid.module.senative.a$a reason: collision with other inner class name */
    class C0004a implements FileFilter {
        String a = "";

        public C0004a(String str) {
            this.a = str;
        }

        public final boolean accept(File file) {
            return file.getName().startsWith(this.a);
        }
    }

    public a(Context context) {
        a = context;
    }

    private void a(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File a2 : listFiles) {
                    a(a2);
                }
                file.delete();
            }
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r6v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v1 */
    /* JADX WARNING: type inference failed for: r6v2 */
    /* JADX WARNING: type inference failed for: r0v2, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r6v3, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r7v1, types: [java.io.BufferedOutputStream] */
    /* JADX WARNING: type inference failed for: r0v4, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r7v2 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r7v3 */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v12 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: type inference failed for: r0v14 */
    /* JADX WARNING: type inference failed for: r0v15 */
    /* JADX WARNING: type inference failed for: r0v16 */
    /* JADX WARNING: type inference failed for: r0v17 */
    /* JADX WARNING: type inference failed for: r0v18 */
    /* JADX WARNING: type inference failed for: r0v19 */
    /* JADX WARNING: type inference failed for: r6v13 */
    /* JADX WARNING: type inference failed for: r6v14 */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v16 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0073, code lost:
        if (r0 == 0) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0075, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0078, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0085, code lost:
        if (r0 == 0) goto L_0x0088;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0059 A[SYNTHETIC, Splitter:B:39:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x005e A[Catch:{ IOException -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0063 A[Catch:{ IOException -> 0x0066 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x006b A[SYNTHETIC, Splitter:B:51:0x006b] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0070 A[Catch:{ IOException -> 0x0088 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x007d A[Catch:{ IOException -> 0x0088 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0082 A[Catch:{ IOException -> 0x0088 }] */
    /* JADX WARNING: Unknown variable types count: 7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.io.InputStream r6, java.io.File r7) {
        /*
            r0 = 0
            r1 = 0
            boolean r2 = r7.exists()     // Catch:{ FileNotFoundException -> 0x0079, IOException -> 0x0067, all -> 0x0054 }
            if (r2 == 0) goto L_0x000b
            r7.delete()     // Catch:{ FileNotFoundException -> 0x0079, IOException -> 0x0067, all -> 0x0054 }
        L_0x000b:
            r7.createNewFile()     // Catch:{ FileNotFoundException -> 0x0079, IOException -> 0x0067, all -> 0x0054 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x0079, IOException -> 0x0067, all -> 0x0054 }
            r2.<init>(r6)     // Catch:{ FileNotFoundException -> 0x0079, IOException -> 0x0067, all -> 0x0054 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0052, IOException -> 0x0050, all -> 0x004d }
            r6.<init>(r7)     // Catch:{ FileNotFoundException -> 0x0052, IOException -> 0x0050, all -> 0x004d }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ FileNotFoundException -> 0x007b, IOException -> 0x0069, all -> 0x0047 }
            r7.<init>(r6)     // Catch:{ FileNotFoundException -> 0x007b, IOException -> 0x0069, all -> 0x0047 }
            r0 = 512(0x200, float:7.175E-43)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043, all -> 0x003e }
        L_0x0021:
            int r3 = r2.read(r0)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043, all -> 0x003e }
            r4 = -1
            if (r3 == r4) goto L_0x002c
            r7.write(r0, r1, r3)     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043, all -> 0x003e }
            goto L_0x0021
        L_0x002c:
            r7.flush()     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043, all -> 0x003e }
            r6.flush()     // Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043, all -> 0x003e }
            r0 = 1
            r6.close()     // Catch:{ IOException -> 0x0088 }
            r2.close()     // Catch:{ IOException -> 0x0088 }
            r7.close()     // Catch:{ IOException -> 0x0088 }
            r1 = 1
            return r1
        L_0x003e:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x0057
        L_0x0043:
            r0 = r7
            goto L_0x0069
        L_0x0045:
            r0 = r7
            goto L_0x007b
        L_0x0047:
            r7 = move-exception
            r5 = r0
            r0 = r6
            r6 = r7
            r7 = r5
            goto L_0x0057
        L_0x004d:
            r6 = move-exception
            r7 = r0
            goto L_0x0057
        L_0x0050:
            r6 = r0
            goto L_0x0069
        L_0x0052:
            r6 = r0
            goto L_0x007b
        L_0x0054:
            r6 = move-exception
            r7 = r0
            r2 = r7
        L_0x0057:
            if (r0 == 0) goto L_0x005c
            r0.close()     // Catch:{ IOException -> 0x0066 }
        L_0x005c:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0061:
            if (r7 == 0) goto L_0x0066
            r7.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0066:
            throw r6
        L_0x0067:
            r6 = r0
            r2 = r6
        L_0x0069:
            if (r6 == 0) goto L_0x006e
            r6.close()     // Catch:{ IOException -> 0x0088 }
        L_0x006e:
            if (r2 == 0) goto L_0x0073
            r2.close()     // Catch:{ IOException -> 0x0088 }
        L_0x0073:
            if (r0 == 0) goto L_0x0088
        L_0x0075:
            r0.close()     // Catch:{ IOException -> 0x0088 }
            return r1
        L_0x0079:
            r6 = r0
            r2 = r6
        L_0x007b:
            if (r6 == 0) goto L_0x0080
            r6.close()     // Catch:{ IOException -> 0x0088 }
        L_0x0080:
            if (r2 == 0) goto L_0x0085
            r2.close()     // Catch:{ IOException -> 0x0088 }
        L_0x0085:
            if (r0 == 0) goto L_0x0088
            goto L_0x0075
        L_0x0088:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.senative.a.a(java.io.InputStream, java.io.File):boolean");
    }

    private boolean a(String str) {
        Locale locale;
        String str2;
        Object[] objArr;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_BK");
        String sb2 = sb.toString();
        try {
            File filesDir = a.getFilesDir();
            if (a(filesDir.toString(), sb2, str)) {
                StringBuilder sb3 = new StringBuilder("lib");
                sb3.append(str);
                sb3.append("_");
                sb3.append(b);
                sb3.append(".so");
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(sb2);
                sb5.append(File.separator);
                sb5.append(sb4);
                String sb6 = sb5.toString();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(filesDir.toString());
                sb7.append(File.separator);
                sb7.append(sb6);
                File file = new File(sb7.toString());
                if (file.exists()) {
                    try {
                        System.load(file.toString());
                        return true;
                    } catch (UnsatisfiedLinkError unused) {
                        return false;
                    }
                } else {
                    locale = Locale.ENGLISH;
                    str2 = "error can't find %1$s lib in plugins_lib";
                    objArr = new Object[]{str};
                }
            } else {
                locale = Locale.ENGLISH;
                str2 = "error copy %1$s lib fail";
                objArr = new Object[]{str};
            }
            String.format(locale, str2, objArr);
            return false;
        } catch (FileNotFoundException unused2) {
            return false;
        }
    }

    private static boolean a(String str, String str2, File file) {
        boolean z;
        InputStream resourceAsStream = a.class.getClassLoader().getResourceAsStream(str);
        if (resourceAsStream != null) {
            z = a(resourceAsStream, file);
            try {
                resourceAsStream.close();
                return z;
            } catch (IOException unused) {
            }
        } else {
            StringBuilder sb = new StringBuilder("error: can't find ");
            sb.append(str2);
            sb.append(" in apk");
            z = false;
            return z;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            r6 = this;
            java.lang.String r0 = android.os.Build.CPU_ABI
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "lib"
            r1.<init>(r2)
            r1.append(r9)
            java.lang.String r9 = "_"
            r1.append(r9)
            java.lang.String r9 = b
            r1.append(r9)
            java.lang.String r9 = ".so"
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            java.lang.String r1 = "x86"
            boolean r1 = r1.equals(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0034
            java.lang.String r0 = "lib/x86/"
        L_0x002b:
            java.lang.String r1 = java.lang.String.valueOf(r9)
            java.lang.String r0 = r0.concat(r1)
            goto L_0x0040
        L_0x0034:
            java.lang.String r1 = "armeabi"
            boolean r0 = r0.startsWith(r1)
            if (r0 == 0) goto L_0x003f
            java.lang.String r0 = "lib/armeabi/"
            goto L_0x002b
        L_0x003f:
            r0 = r2
        L_0x0040:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0087 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0087 }
            r3.<init>()     // Catch:{ Exception -> 0x0087 }
            r3.append(r7)     // Catch:{ Exception -> 0x0087 }
            java.lang.String r4 = java.io.File.separator     // Catch:{ Exception -> 0x0087 }
            r3.append(r4)     // Catch:{ Exception -> 0x0087 }
            r3.append(r8)     // Catch:{ Exception -> 0x0087 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0087 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0087 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0085 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0085 }
            r4.<init>()     // Catch:{ Exception -> 0x0085 }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0085 }
            r4.append(r5)     // Catch:{ Exception -> 0x0085 }
            java.lang.String r5 = java.io.File.separator     // Catch:{ Exception -> 0x0085 }
            r4.append(r5)     // Catch:{ Exception -> 0x0085 }
            r4.append(r9)     // Catch:{ Exception -> 0x0085 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0085 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0085 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0089 }
            java.lang.String r4 = "libSE:"
            r2.<init>(r4)     // Catch:{ Exception -> 0x0089 }
            java.lang.String r4 = r3.toString()     // Catch:{ Exception -> 0x0089 }
            r2.append(r4)     // Catch:{ Exception -> 0x0089 }
            goto L_0x0089
        L_0x0085:
            r3 = r2
            goto L_0x0089
        L_0x0087:
            r1 = r2
            r3 = r1
        L_0x0089:
            if (r3 == 0) goto L_0x00b1
            boolean r2 = r3.exists()
            if (r2 == 0) goto L_0x00a6
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "file "
            r7.<init>(r8)
            java.lang.String r8 = r3.toString()
            r7.append(r8)
            java.lang.String r8 = " is exist"
            r7.append(r8)
            r7 = 1
            return r7
        L_0x00a6:
            r6.b(r7, r8)
            r1.mkdirs()
            boolean r7 = a(r0, r9, r3)
            return r7
        L_0x00b1:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.senative.a.a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private void b(String str, String str2) {
        try {
            for (File a2 : new File(str).listFiles(new C0004a(str2))) {
                a(a2);
            }
        } catch (Exception unused) {
        }
    }

    public final boolean a(String str, String str2) {
        b = str2;
        return a(str);
    }
}
