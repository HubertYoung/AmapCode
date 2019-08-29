package com.autonavi.common.tool;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.appmonitor.offline.TempEvent;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class SoCollector {
    public static final String LIBART_PATH = "/system/lib/libart.so";
    public static final String LIBDVM_PATH = "/system/lib/libdvm.so";

    public static class a {
        public String a;
        public File b;
        public int c;
        public int d;

        public final boolean equals(Object obj) {
            if (!(obj instanceof SoCollector)) {
                return false;
            }
            return this.a.equals(((a) obj).a);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SoFile:");
            sb.append(this.b);
            sb.append("\tSoFileLength:");
            sb.append(this.c);
            sb.append("\tSoFileCrc32:");
            sb.append(this.d);
            return sb.toString();
        }
    }

    private static Map<String, a> dumpLibs(Application application, String str) {
        HashMap hashMap = new HashMap();
        dumpMainAppSoLibs(application, str, hashMap);
        CrashLogUtil.getControler();
        return hashMap;
    }

    private static void dumpMainAppSoLibs(Application application, String str, Map<String, a> map) {
        try {
            File[] listFiles = new File(str).listFiles(new FileFilter() {
                public final boolean accept(File file) {
                    return file.getName().endsWith(".so");
                }
            });
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file != null && file.exists()) {
                        a aVar = new a();
                        aVar.b = file;
                        aVar.a = file.getName();
                        map.put(aVar.a, aVar);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dumpModuleSoLibs(Application application, String str, Map<String, a> map) {
        File dir = application.getDir(TempEvent.TAG_MODULE, 0);
        if (dir != null && dir.exists()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file != null && file.isDirectory()) {
                        dumpEachModuleLibs(map, file);
                    }
                }
            }
        }
    }

    private static void dumpEachModuleLibs(Map<String, a> map, File file) {
        dumpEachModuleVersion(map, file);
    }

    private static void dumpEachModuleVersion(Map<String, a> map, File file) {
        try {
            File[] listFiles = file.listFiles(new FileFilter() {
                public final boolean accept(File file) {
                    return file.getName().endsWith(".so");
                }
            });
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2 != null && file2.exists()) {
                        a aVar = new a();
                        aVar.b = file2;
                        aVar.a = file2.getName();
                        map.put(aVar.a, aVar);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getAbi() {
        String L = CrashLogUtil.getControler().L();
        if (!TextUtils.isEmpty(L)) {
            return L;
        }
        String str = Build.CPU_ABI;
        if (TextUtils.isEmpty(str)) {
            return "armeabi";
        }
        return str;
    }

    /* access modifiers changed from: private */
    public static Map<String, a> getSoInApk(File file) {
        HashMap hashMap = new HashMap();
        String[] strArr = new String[70];
        int[] iArr = new int[70];
        int[] iArr2 = new int[70];
        String absolutePath = file.getAbsolutePath();
        StringBuilder sb = new StringBuilder("lib/");
        sb.append(getAbi());
        int apkFileInfos = dumpcrash.getApkFileInfos(absolutePath, sb.toString(), strArr, iArr, iArr2);
        for (int i = 0; i < apkFileInfos; i++) {
            a aVar = new a();
            aVar.b = new File(strArr[i]);
            aVar.a = aVar.b.getName();
            aVar.c = iArr[i];
            aVar.d = iArr2[i];
            hashMap.put(aVar.a, aVar);
        }
        return hashMap;
    }

    public static void saveFileToUpload(File file) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream2;
        try {
            File file2 = new File(CrashLogUtil.getControler().t(), file.getName());
            fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    fileOutputStream2 = new FileOutputStream(file2, false);
                    bmu.a(fileInputStream, fileOutputStream2);
                    bmu.a((Closeable) fileInputStream);
                    bmu.a((Closeable) fileOutputStream2);
                } catch (Throwable th) {
                    th = th;
                    bmu.a((Closeable) fileInputStream);
                    bmu.a((Closeable) fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
                bmu.a((Closeable) fileInputStream);
                bmu.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Throwable unused) {
        }
    }

    private static Map<String, a> calcCrc32(Map<String, a> map) {
        if (map == null || map.isEmpty()) {
            return map;
        }
        ArrayList arrayList = new ArrayList();
        for (final a next : map.values()) {
            arrayList.add(new Callable<Integer>() {
                public final /* synthetic */ Object call() throws Exception {
                    if (next.c == 0) {
                        next.c = (int) next.b.length();
                    }
                    if (next.d == 0) {
                        next.d = bmx.b(next.b);
                    }
                    return null;
                }
            });
        }
        bmx.a(arrayList, 0, 2);
        return map;
    }

    private static Map<String, a> dumpInApkLibs(Application application) {
        HashMap hashMap = new HashMap();
        try {
            ArrayList<File> arrayList = new ArrayList<>();
            arrayList.add(new File(application.getApplicationInfo().sourceDir));
            CrashLogUtil.getControler();
            ArrayList arrayList2 = new ArrayList();
            for (final File file : arrayList) {
                arrayList2.add(new Callable<Map<String, a>>() {
                    public final /* synthetic */ Object call() throws Exception {
                        return SoCollector.getSoInApk(file);
                    }
                });
            }
            List<Future> a2 = bmx.a(arrayList2, 0, 2);
            if (a2 != null) {
                for (Future future : a2) {
                    try {
                        hashMap.putAll((Map) future.get());
                    } catch (Throwable unused) {
                    }
                }
            }
        } catch (Throwable unused2) {
        }
        return hashMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x0161  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0167  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getSoInfo(java.lang.Throwable r9, android.app.Application r10, java.lang.String r11, boolean[] r12) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 1
            r2 = 0
            if (r9 == 0) goto L_0x0024
            java.lang.Class<java.lang.UnsatisfiedLinkError> r3 = java.lang.UnsatisfiedLinkError.class
            boolean r3 = defpackage.bmx.a(r9, r3)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            if (r3 != 0) goto L_0x0024
            java.lang.Class<java.lang.NoClassDefFoundError> r3 = java.lang.NoClassDefFoundError.class
            boolean r9 = defpackage.bmx.a(r9, r3)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            if (r9 != 0) goto L_0x0024
            java.lang.String r9 = ""
            if (r12 == 0) goto L_0x001f
            r12[r2] = r2
        L_0x001f:
            return r9
        L_0x0020:
            r9 = move-exception
            r3 = 0
            goto L_0x015f
        L_0x0024:
            java.lang.String r9 = "\nSOCrc32:\n"
            r0.append(r9)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            java.util.Map r9 = dumpLibs(r10, r11)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            java.util.Map r9 = calcCrc32(r9)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            java.util.Map r10 = dumpInApkLibs(r10)     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            java.util.Collection r11 = r10.values()     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ Throwable -> 0x0164, all -> 0x0020 }
            r3 = 0
            r4 = 0
        L_0x003f:
            boolean r5 = r11.hasNext()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r5 == 0) goto L_0x0112
            java.lang.Object r5 = r11.next()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            com.autonavi.common.tool.SoCollector$a r5 = (com.autonavi.common.tool.SoCollector.a) r5     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r6 = r5.a     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            boolean r6 = r9.containsKey(r6)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r6 == 0) goto L_0x00e5
            java.lang.String r6 = r5.a     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.Object r6 = r9.get(r6)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            com.autonavi.common.tool.SoCollector$a r6 = (com.autonavi.common.tool.SoCollector.a) r6     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r7 = r5.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r8 = r6.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r7 != r8) goto L_0x0090
            int r7 = r5.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r8 = r6.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r7 == r8) goto L_0x0068
            goto L_0x0090
        L_0x0068:
            java.lang.String r5 = "\tSORight.file="
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.io.File r5 = r6.b     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = "\t len="
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r5 = r6.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = "\t crc32="
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r5 = r6.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = defpackage.bmx.a(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = "\n"
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            goto L_0x003f
        L_0x0090:
            java.lang.String r7 = "\tSOCorrupt.file="
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.io.File r7 = r6.b     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r7 = "\t rightLen="
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r7 = r5.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r7 = "\t curLen="
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r7 = r6.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r7 = "\t rightCrc32="
            r0.append(r7)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r5 = r5.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = defpackage.bmx.a(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = "\t curCrc32="
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r5 = r6.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = defpackage.bmx.a(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r5 = "\n"
            r0.append(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.io.File r5 = r6.b     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            saveFileToUpload(r5)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r4 != 0) goto L_0x00e1
            bmt r4 = com.autonavi.common.tool.CrashLogUtil.getControler()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.io.File r4 = r4.w()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            saveFileToUpload(r4)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
        L_0x00e1:
            r3 = 1
            r4 = 1
            goto L_0x003f
        L_0x00e5:
            java.lang.String r3 = "\tSOLose.file="
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            java.io.File r3 = r5.b     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            java.lang.String r3 = "\t len="
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            int r3 = r5.c     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            java.lang.String r3 = "\t crc32="
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            int r3 = r5.d     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            java.lang.String r3 = defpackage.bmx.a(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            java.lang.String r3 = "\n"
            r0.append(r3)     // Catch:{ Throwable -> 0x0165, all -> 0x010f }
            r3 = 1
            goto L_0x003f
        L_0x010f:
            r9 = move-exception
            r3 = 1
            goto L_0x015f
        L_0x0112:
            java.util.Collection r9 = r9.values()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
        L_0x011a:
            boolean r11 = r9.hasNext()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r11 == 0) goto L_0x0156
            java.lang.Object r11 = r9.next()     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            com.autonavi.common.tool.SoCollector$a r11 = (com.autonavi.common.tool.SoCollector.a) r11     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r1 = r11.a     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            boolean r1 = r10.containsKey(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            if (r1 != 0) goto L_0x011a
            java.lang.String r1 = "\tSOExtra.file="
            r0.append(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.io.File r1 = r11.b     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r1 = "\t len="
            r0.append(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r1 = r11.c     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r1 = "\t crc32="
            r0.append(r1)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            int r11 = r11.d     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r11 = defpackage.bmx.a(r11)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            r0.append(r11)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            java.lang.String r11 = "\n"
            r0.append(r11)     // Catch:{ Throwable -> 0x015d, all -> 0x015b }
            goto L_0x011a
        L_0x0156:
            if (r12 == 0) goto L_0x0169
            r12[r2] = r3
            goto L_0x0169
        L_0x015b:
            r9 = move-exception
            goto L_0x015f
        L_0x015d:
            r1 = r3
            goto L_0x0165
        L_0x015f:
            if (r12 == 0) goto L_0x0163
            r12[r2] = r3
        L_0x0163:
            throw r9
        L_0x0164:
            r1 = 0
        L_0x0165:
            if (r12 == 0) goto L_0x0169
            r12[r2] = r1
        L_0x0169:
            java.lang.String r9 = getSysLibSoInfo()
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.SoCollector.getSoInfo(java.lang.Throwable, android.app.Application, java.lang.String, boolean[]):java.lang.String");
    }

    public static String getSysLibSoInfo() {
        StringBuilder sb = new StringBuilder();
        File file = new File(LIBDVM_PATH);
        if (file.exists()) {
            try {
                long length = file.length();
                String a2 = bmx.a(bmx.b(file));
                sb.append("\tfile=");
                sb.append(file.getAbsolutePath());
                sb.append("\tlen=");
                sb.append(length);
                sb.append("\tcrc_value=");
                sb.append(a2);
                sb.append("\n");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        File file2 = new File(LIBART_PATH);
        if (file2.exists()) {
            try {
                String a3 = bmx.a(bmx.b(file2));
                sb.append("\tfile=");
                sb.append(file2.getAbsolutePath());
                sb.append("\tlen=");
                sb.append(file2.length());
                sb.append("\tcrc_value=");
                sb.append(a3);
                sb.append("\n");
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
        return sb.toString();
    }
}
