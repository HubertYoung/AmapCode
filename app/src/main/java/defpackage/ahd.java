package defpackage;

import android.content.Context;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

/* renamed from: ahd reason: default package */
/* compiled from: FileUtil */
public final class ahd {
    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v6, types: [java.io.Closeable, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v2
      assigns: []
      uses: []
      mth insns count: 39
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(android.content.Context r1, int r2, java.io.File r3) {
        /*
            r0 = 0
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ Exception -> 0x0025, all -> 0x0022 }
            java.io.InputStream r1 = r1.openRawResource(r2)     // Catch:{ Exception -> 0x0025, all -> 0x0022 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x001e, all -> 0x001c }
            defpackage.ahe.a(r1, r2)     // Catch:{ Exception -> 0x001a, all -> 0x0018 }
            defpackage.ahe.a(r1)
        L_0x0014:
            defpackage.ahe.a(r2)
            return
        L_0x0018:
            r3 = move-exception
            goto L_0x0030
        L_0x001a:
            r3 = move-exception
            goto L_0x0020
        L_0x001c:
            r3 = move-exception
            goto L_0x0031
        L_0x001e:
            r3 = move-exception
            r2 = r0
        L_0x0020:
            r0 = r1
            goto L_0x0027
        L_0x0022:
            r3 = move-exception
            r1 = r0
            goto L_0x0031
        L_0x0025:
            r3 = move-exception
            r2 = r0
        L_0x0027:
            r3.printStackTrace()     // Catch:{ all -> 0x002e }
            defpackage.ahe.a(r0)
            goto L_0x0014
        L_0x002e:
            r3 = move-exception
            r1 = r0
        L_0x0030:
            r0 = r2
        L_0x0031:
            defpackage.ahe.a(r1)
            defpackage.ahe.a(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahd.a(android.content.Context, int, java.io.File):void");
    }

    public static boolean a(String str) {
        FileChannel fileChannel;
        FileChannel fileChannel2;
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2;
        RandomAccessFile randomAccessFile3 = null;
        try {
            RandomAccessFile randomAccessFile4 = new RandomAccessFile(str, "rw");
            try {
                FileChannel channel = randomAccessFile4.getChannel();
                try {
                    FileLock tryLock = channel.tryLock();
                    if (tryLock != null) {
                        if (tryLock != null) {
                            try {
                                tryLock.release();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        ahe.a((Closeable) channel);
                        ahe.a((Closeable) randomAccessFile4);
                        return false;
                    }
                    if (tryLock != null) {
                        try {
                            tryLock.release();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    ahe.a((Closeable) channel);
                    ahe.a((Closeable) randomAccessFile4);
                    return true;
                } catch (Exception e3) {
                    randomAccessFile = randomAccessFile4;
                    fileChannel2 = channel;
                    e = e3;
                    randomAccessFile3 = randomAccessFile;
                    try {
                        e.printStackTrace();
                        ahe.a((Closeable) fileChannel);
                        ahe.a((Closeable) randomAccessFile3);
                        return true;
                    } catch (Throwable th) {
                        th = th;
                        ahe.a((Closeable) fileChannel);
                        ahe.a((Closeable) randomAccessFile3);
                        throw th;
                    }
                } catch (Throwable th2) {
                    randomAccessFile2 = randomAccessFile4;
                    fileChannel = channel;
                    th = th2;
                    randomAccessFile3 = randomAccessFile2;
                    ahe.a((Closeable) fileChannel);
                    ahe.a((Closeable) randomAccessFile3);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                randomAccessFile = randomAccessFile4;
                fileChannel2 = null;
                randomAccessFile3 = randomAccessFile;
                e.printStackTrace();
                ahe.a((Closeable) fileChannel);
                ahe.a((Closeable) randomAccessFile3);
                return true;
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile2 = randomAccessFile4;
                fileChannel = null;
                randomAccessFile3 = randomAccessFile2;
                ahe.a((Closeable) fileChannel);
                ahe.a((Closeable) randomAccessFile3);
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            fileChannel2 = null;
            e.printStackTrace();
            ahe.a((Closeable) fileChannel);
            ahe.a((Closeable) randomAccessFile3);
            return true;
        } catch (Throwable th4) {
            th = th4;
            fileChannel = null;
            ahe.a((Closeable) fileChannel);
            ahe.a((Closeable) randomAccessFile3);
            throw th;
        }
    }

    public static boolean b(String str) {
        if (!TextUtils.isEmpty(str)) {
            return new File(str).exists();
        }
        return false;
    }

    public static boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return a(new File(str));
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (file.isDirectory() && listFiles != null) {
            for (File a : listFiles) {
                if (!a(a)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static byte[] a(Context context, String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        try {
            inputStream = context.getAssets().open(str);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ahe.a(inputStream, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    ahe.a((Closeable) inputStream);
                    ahe.a((Closeable) byteArrayOutputStream);
                    return byteArray;
                } catch (Throwable th) {
                    th = th;
                    try {
                        th.printStackTrace();
                        ahe.a((Closeable) inputStream);
                        ahe.a((Closeable) byteArrayOutputStream);
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        ahe.a((Closeable) inputStream);
                        ahe.a((Closeable) byteArrayOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream = null;
                ahe.a((Closeable) inputStream);
                ahe.a((Closeable) byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th4) {
            byteArrayOutputStream = null;
            th = th4;
            inputStream = null;
            ahe.a((Closeable) inputStream);
            ahe.a((Closeable) byteArrayOutputStream);
            throw th;
        }
    }

    public static byte[] d(String str) {
        Throwable th;
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th2) {
                th = th2;
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) byteArrayOutputStream2);
                throw th;
            }
            try {
                ahe.a(fileInputStream, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) byteArrayOutputStream);
                return byteArray;
            } catch (Throwable th3) {
                th = th3;
                th.printStackTrace();
                ahe.a((Closeable) fileInputStream);
                ahe.a((Closeable) byteArrayOutputStream);
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            ahe.a((Closeable) fileInputStream);
            ahe.a((Closeable) byteArrayOutputStream2);
            throw th;
        }
    }

    public static boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        File file = new File(str);
        File file2 = new File(str2);
        if (file2.exists()) {
            a(file2);
        }
        return file.renameTo(file2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int b(java.io.File r4) {
        /*
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0029 }
            java.lang.String r2 = "chmod 777 "
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ Exception -> 0x0029 }
            java.lang.String r4 = r2.concat(r4)     // Catch:{ Exception -> 0x0029 }
            java.lang.Process r4 = r1.exec(r4)     // Catch:{ Exception -> 0x0029 }
            int r0 = r4.waitFor()     // Catch:{ Exception -> 0x0022, all -> 0x001d }
            if (r4 == 0) goto L_0x001c
            r4.destroy()
        L_0x001c:
            return r0
        L_0x001d:
            r0 = move-exception
            r3 = r0
            r0 = r4
            r4 = r3
            goto L_0x0034
        L_0x0022:
            r0 = move-exception
            r3 = r0
            r0 = r4
            r4 = r3
            goto L_0x002a
        L_0x0027:
            r4 = move-exception
            goto L_0x0034
        L_0x0029:
            r4 = move-exception
        L_0x002a:
            r4.printStackTrace()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0032
            r0.destroy()
        L_0x0032:
            r4 = -1
            return r4
        L_0x0034:
            if (r0 == 0) goto L_0x0039
            r0.destroy()
        L_0x0039:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahd.b(java.io.File):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x006f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r8, java.io.File r9) {
        /*
            boolean r0 = r8.isDirectory()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0033
            boolean r0 = r9.exists()
            if (r0 != 0) goto L_0x0011
            r9.mkdir()
        L_0x0011:
            java.lang.String[] r0 = r8.list()
            if (r0 == 0) goto L_0x0032
            int r3 = r0.length
            r4 = 0
        L_0x0019:
            if (r4 >= r3) goto L_0x0031
            r5 = r0[r4]
            java.io.File r6 = new java.io.File
            r6.<init>(r8, r5)
            java.io.File r7 = new java.io.File
            r7.<init>(r9, r5)
            boolean r5 = a(r6, r7)
            if (r5 != 0) goto L_0x002e
            return r1
        L_0x002e:
            int r4 = r4 + 1
            goto L_0x0019
        L_0x0031:
            return r2
        L_0x0032:
            return r1
        L_0x0033:
            boolean r0 = r9.exists()
            if (r0 == 0) goto L_0x003c
            r9.delete()
        L_0x003c:
            r0 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0055, all -> 0x0052 }
            r3.<init>(r8)     // Catch:{ Exception -> 0x0055, all -> 0x0052 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            r4.<init>(r9)     // Catch:{ Exception -> 0x004f, all -> 0x004d }
            defpackage.ahe.a(r3, r4)     // Catch:{ Exception -> 0x004b }
            goto L_0x005b
        L_0x004b:
            r5 = move-exception
            goto L_0x0058
        L_0x004d:
            r8 = move-exception
            goto L_0x0079
        L_0x004f:
            r5 = move-exception
            r4 = r0
            goto L_0x0058
        L_0x0052:
            r8 = move-exception
            r3 = r0
            goto L_0x0079
        L_0x0055:
            r5 = move-exception
            r3 = r0
            r4 = r3
        L_0x0058:
            r5.printStackTrace()     // Catch:{ all -> 0x0077 }
        L_0x005b:
            defpackage.ahe.a(r3)
            defpackage.ahe.a(r4)
            java.lang.String r8 = defpackage.agy.a(r8, r0, r2)
            java.lang.String r9 = defpackage.agy.a(r9, r0, r2)
            boolean r0 = r8.isEmpty()
            if (r0 != 0) goto L_0x0076
            boolean r8 = r9.equals(r8)
            if (r8 != 0) goto L_0x0076
            return r1
        L_0x0076:
            return r2
        L_0x0077:
            r8 = move-exception
            r0 = r4
        L_0x0079:
            defpackage.ahe.a(r3)
            defpackage.ahe.a(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahd.a(java.io.File, java.io.File):boolean");
    }

    public static boolean c(File file) {
        return a(file);
    }

    public static String a(@NonNull Context context, @NonNull String str, Charset charset) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        InputStream inputStream = null;
        try {
            InputStream inputStream2 = context.getAssets().open(str);
            try {
                String str2 = new String(ahe.a(inputStream2), charset);
                ahe.a((Closeable) inputStream2);
                return str2;
            } catch (Throwable th) {
                th = th;
                ahe.a((Closeable) inputStream2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            ahe.a((Closeable) inputStream);
            return "";
        }
    }

    public static long[] e(String str) {
        long[] jArr = new long[3];
        try {
            StatFs statFs = new StatFs(str);
            long blockSize = (long) statFs.getBlockSize();
            long availableBlocks = (long) statFs.getAvailableBlocks();
            long blockCount = (long) statFs.getBlockCount();
            jArr[0] = availableBlocks * blockSize;
            jArr[1] = (blockCount - availableBlocks) * blockSize;
            jArr[2] = blockCount * blockSize;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return jArr;
    }

    public static String d(File file) {
        Throwable th;
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Exception e) {
                e = e;
                byteArrayOutputStream = null;
                try {
                    e.printStackTrace();
                    ahe.a((Closeable) byteArrayOutputStream);
                    ahe.a((Closeable) fileInputStream);
                    return null;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    th = th3;
                    ahe.a((Closeable) byteArrayOutputStream2);
                    ahe.a((Closeable) fileInputStream);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                ahe.a((Closeable) byteArrayOutputStream2);
                ahe.a((Closeable) fileInputStream);
                throw th;
            }
            try {
                ahe.a(fileInputStream, byteArrayOutputStream);
                String byteArrayOutputStream3 = byteArrayOutputStream.toString("UTF-8");
                ahe.a((Closeable) byteArrayOutputStream);
                ahe.a((Closeable) fileInputStream);
                return byteArrayOutputStream3;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                ahe.a((Closeable) byteArrayOutputStream);
                ahe.a((Closeable) fileInputStream);
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream = null;
            fileInputStream = null;
            e.printStackTrace();
            ahe.a((Closeable) byteArrayOutputStream);
            ahe.a((Closeable) fileInputStream);
            return null;
        } catch (Throwable th5) {
            th = th5;
            fileInputStream = null;
            ahe.a((Closeable) byteArrayOutputStream2);
            ahe.a((Closeable) fileInputStream);
            throw th;
        }
    }

    public static boolean a(File file, String str) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(str.getBytes("UTF-8"));
                fileOutputStream.flush();
                ahe.a((Closeable) fileOutputStream);
                return true;
            } catch (Exception unused) {
                fileOutputStream2 = fileOutputStream;
                ahe.a((Closeable) fileOutputStream2);
                return false;
            } catch (Throwable th) {
                th = th;
                ahe.a((Closeable) fileOutputStream);
                throw th;
            }
        } catch (Exception unused2) {
            ahe.a((Closeable) fileOutputStream2);
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }
}
