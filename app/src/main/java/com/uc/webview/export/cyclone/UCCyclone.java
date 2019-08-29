package com.uc.webview.export.cyclone;

import android.content.Context;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.ZipFile;

@Constant
/* compiled from: ProGuard */
public class UCCyclone {
    static final String LOG_TAG = "cyclone";
    static final boolean RELEASE_BUILD = false;
    private static final String TEMP_DEC_DIR_PREFIX = "temp_dec_";
    public static String dataFolder = "cyclone";
    public static boolean enableDebugLog = true;
    public static ValueCallback<String> loadLibraryCallback = null;
    public static String logExtraTag = "cyclone.";
    @Constant
    public static ConcurrentLinkedQueue<File> sInusedFiles = null;
    public static final UCClassLoaderOnCreate sUcClassLoaderOnCreate = new UCClassLoaderOnCreate();
    public static String serverZipTag = "7z";
    public static ValueCallback<Pair<String, HashMap<String, String>>> statCallback;

    /* compiled from: ProGuard */
    public static class DecFileOrign {
        public static String DecFileOrignFlag = "_dec_ori_";
        public static int Other = 1;
        public static int Sdcard = 2;
        public static int Sdcard_Share_Core = 3;
        public static int Update = 1;
    }

    /* compiled from: ProGuard */
    public enum MessageDigestType {
        MD5,
        SHA1,
        SHA256
    }

    public static File expectCreateDirFile(File file) {
        int i = 3;
        while (!file.exists() && !file.mkdirs()) {
            int i2 = i - 1;
            if (i <= 0) {
                throw new UCKnownException(1003, String.format("Directory [%s] mkdir failed.", new Object[]{file.getAbsolutePath()}));
            }
            i = i2;
        }
        return file;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    static Object invoke(Class<?> cls, String str, Class[] clsArr, Object[] objArr) throws Exception {
        return invoke(null, cls, str, clsArr, objArr);
    }

    @Constant
    public static Object invoke(Object obj, Class<?> cls, String str, Class[] clsArr, Object[] objArr) throws Exception {
        Method method;
        try {
            method = cls.getDeclaredMethod(str, clsArr);
        } catch (Throwable unused) {
            method = cls.getMethod(str, clsArr);
        }
        return invoke(obj, cls, method, objArr);
    }

    @Constant
    public static Object invoke(Object obj, Class<?> cls, Method method, Object[] objArr) throws Exception {
        method.setAccessible(true);
        try {
            return method.invoke(obj, objArr);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof Exception) {
                throw ((Exception) targetException);
            } else if (targetException instanceof Error) {
                throw ((Error) targetException);
            } else {
                throw new RuntimeException(targetException);
            }
        }
    }

    @Constant
    public static void stat(String str, HashMap<String, String> hashMap) {
        ValueCallback<Pair<String, HashMap<String, String>>> valueCallback = statCallback;
        if (valueCallback != null) {
            try {
                valueCallback.onReceiveValue(new Pair(str, hashMap));
            } catch (Exception unused) {
            }
        }
    }

    @Constant
    static void getFile(byte[][] bArr, File file) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            expectCreateDirFile(file.getParentFile());
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
            try {
                for (byte[] write : bArr) {
                    bufferedOutputStream2.write(write);
                }
                close(bufferedOutputStream2);
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = bufferedOutputStream2;
                close(bufferedOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            close(bufferedOutputStream);
            throw th;
        }
    }

    public static long getFolderSize(File file) {
        return getFolderSize(file, -1);
    }

    public static long getFolderSize(File file, long j) {
        Stack stack = new Stack();
        if (file.exists()) {
            stack.push(file);
        }
        long j2 = 0;
        while (!stack.empty()) {
            File[] listFiles = ((File) stack.pop()).listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    try {
                        String name = file2.getName();
                        if (!name.equals(".") && !name.equals("..") && !name.equals("./")) {
                            if (!name.equals("../")) {
                                if (file2.isDirectory() && file2.exists()) {
                                    stack.push(file2);
                                } else if (file2.exists()) {
                                    j2 += file2.length();
                                    if (j >= 0 && j2 > j) {
                                        return j2;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
                continue;
            }
        }
        return j2;
    }

    @Constant
    public static File getDataFolder(Context context) throws UCKnownException {
        UCLogger uCLogger = null;
        try {
            if (dataFolder == null) {
                dataFolder = LOG_TAG;
            }
            File dir = context.getDir(dataFolder, 0);
            if (sInusedFiles == null) {
                synchronized (UCCyclone.class) {
                    if (sInusedFiles == null) {
                        sInusedFiles = new ConcurrentLinkedQueue<>();
                    }
                }
            }
            if (enableDebugLog) {
                uCLogger = UCLogger.create("i", LOG_TAG);
            }
            if (uCLogger != null) {
                uCLogger.print("getDataFolder: ok.", new Throwable[0]);
            }
            return dir;
        } catch (Throwable th) {
            if (enableDebugLog) {
                uCLogger = UCLogger.create("e", LOG_TAG);
            }
            if (uCLogger != null) {
                uCLogger.print("getDataFolder: from dir app_* Exception:", th);
            }
            throw new UCKnownException(1003, th);
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001a */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0031 A[Catch:{ Throwable -> 0x003a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void deleteUnusedFiles(android.content.Context r5) {
        /*
            r0 = 0
            java.io.File r1 = getDataFolder(r5)     // Catch:{ Throwable -> 0x001a }
            java.util.concurrent.ConcurrentLinkedQueue<java.io.File> r2 = sInusedFiles     // Catch:{ Throwable -> 0x001a }
            if (r2 == 0) goto L_0x0015
            java.util.concurrent.ConcurrentLinkedQueue<java.io.File> r2 = sInusedFiles     // Catch:{ Throwable -> 0x001a }
            r3 = 5
            java.io.File[] r3 = new java.io.File[r3]     // Catch:{ Throwable -> 0x001a }
            java.lang.Object[] r2 = r2.toArray(r3)     // Catch:{ Throwable -> 0x001a }
            java.io.File[] r2 = (java.io.File[]) r2     // Catch:{ Throwable -> 0x001a }
            goto L_0x0016
        L_0x0015:
            r2 = r0
        L_0x0016:
            r3 = 1
            recursiveDelete(r1, r3, r2)     // Catch:{ Throwable -> 0x001a }
        L_0x001a:
            java.io.File r5 = r5.getCacheDir()     // Catch:{ Throwable -> 0x003a }
            com.uc.webview.export.cyclone.UCCyclone$1 r1 = new com.uc.webview.export.cyclone.UCCyclone$1     // Catch:{ Throwable -> 0x003a }
            r1.<init>()     // Catch:{ Throwable -> 0x003a }
            java.io.File[] r5 = r5.listFiles(r1)     // Catch:{ Throwable -> 0x003a }
            if (r5 == 0) goto L_0x0039
            int r1 = r5.length     // Catch:{ Throwable -> 0x003a }
            if (r1 <= 0) goto L_0x0039
            int r1 = r5.length     // Catch:{ Throwable -> 0x003a }
            r2 = 0
            r3 = 0
        L_0x002f:
            if (r3 >= r1) goto L_0x0039
            r4 = r5[r3]     // Catch:{ Throwable -> 0x003a }
            recursiveDelete(r4, r2, r0)     // Catch:{ Throwable -> 0x003a }
            int r3 = r3 + 1
            goto L_0x002f
        L_0x0039:
            return
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCCyclone.deleteUnusedFiles(android.content.Context):void");
    }

    public static File expectFile(File file, String str) throws UCKnownException {
        return expectFile(new File(file, str));
    }

    public static File expectFile(String str) throws UCKnownException {
        return expectFile(new File(str));
    }

    public static File expectFile(File file) throws UCKnownException {
        if (!file.exists()) {
            throw new UCKnownException(1001, String.format("File [%s] not exists.", new Object[]{file.getAbsolutePath()}));
        } else if (file.canRead()) {
            return file;
        } else {
            throw new UCKnownException(1012, String.format("File [%s] cannot read.", new Object[]{file.getAbsolutePath()}));
        }
    }

    @Constant
    public static String getDecompressFileHash(File file) {
        return getDecompressSourceHash(file.getAbsolutePath(), file.length(), file.lastModified(), false);
    }

    public static String getDecompressSourceHash(String str, long j, long j2, boolean z) {
        StringBuilder sb = new StringBuilder();
        if (!z) {
            str = getSourceHash(str);
        }
        sb.append(str);
        sb.append("_");
        sb.append(getSourceHash(j, j2));
        return sb.toString();
    }

    public static String getSourceHash(String str) {
        return String.valueOf(str.hashCode()).replace('-', '_');
    }

    public static String getSourceHash(long j, long j2) {
        StringBuilder sb = new StringBuilder();
        sb.append(j);
        sb.append("_");
        sb.append(j2);
        return sb.toString();
    }

    public static synchronized boolean decompressIfNeeded(Context context, String str, File file, File file2, FilenameFilter filenameFilter, boolean z) throws UCKnownException {
        boolean decompressIfNeeded;
        synchronized (UCCyclone.class) {
            try {
                decompressIfNeeded = decompressIfNeeded(context, str, file.getAbsolutePath(), file.length(), file.lastModified(), file, file2, filenameFilter, z, DecFileOrign.Other);
            } catch (Throwable th) {
                throw th;
            }
        }
        return decompressIfNeeded;
    }

    public static synchronized boolean decompressIfNeeded(Context context, boolean z, File file, File file2, FilenameFilter filenameFilter, boolean z2) throws UCKnownException {
        boolean decompressIfNeeded;
        synchronized (UCCyclone.class) {
            try {
                decompressIfNeeded = decompressIfNeeded(context, z, file.getAbsolutePath(), file.length(), file.lastModified(), file, file2, filenameFilter, z2, DecFileOrign.Other);
            } catch (Throwable th) {
                throw th;
            }
        }
        return decompressIfNeeded;
    }

    public static synchronized boolean decompressIfNeeded(Context context, boolean z, File file, File file2, FilenameFilter filenameFilter, boolean z2, int i) throws UCKnownException {
        boolean decompressIfNeeded;
        synchronized (UCCyclone.class) {
            try {
                decompressIfNeeded = decompressIfNeeded(context, z, file.getAbsolutePath(), file.length(), file.lastModified(), file, file2, filenameFilter, z2, i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return decompressIfNeeded;
    }

    public static boolean isDecompressFinished(File file) {
        String[] split = file.getName().split("_");
        if (split.length == 2) {
            return isDecompressFinished(file.getParentFile().getName(), Long.valueOf(split[0]).longValue(), Long.valueOf(split[1]).longValue(), file, true);
        }
        return false;
    }

    static boolean isDecompressFinished(String str, long j, long j2, File file, boolean z) {
        return getDecompressStopFlgFile(str, j, j2, file, z).exists() && !getDecompressStartFlgFile(str, j, j2, file, z).exists();
    }

    public static boolean detect7zFromFileName(String str) {
        return str.endsWith(".7z") || str.contains("_7z_") || str.contains("_7z");
    }

    public static boolean detectZipByFileType(String str) {
        try {
            new ZipFile(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    static void expectMakeDecompressNotFinished(String str, long j, long j2, File file) throws UCKnownException {
        try {
            File decompressStartFlgFile = getDecompressStartFlgFile(str, j, j2, file, false);
            if (!decompressStartFlgFile.exists() && !decompressStartFlgFile.createNewFile()) {
                throw new Exception("createNewFile return false");
            }
        } catch (Throwable th) {
            throw new UCKnownException(1006, th);
        }
    }

    static void decompress(boolean z, Context context, String str, String str2, FilenameFilter filenameFilter) throws UCKnownException {
        decompress(z, context, str, str2, "", filenameFilter);
    }

    /* JADX INFO: used method not loaded: com.uc.webview.export.cyclone.UCKnownException.<init>(int, java.lang.String):null, types can be incorrect */
    /* JADX INFO: used method not loaded: com.uc.webview.export.cyclone.UCKnownException.<init>(int, java.lang.Throwable):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0034, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        close(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x010a, code lost:
        if (r10 != null) goto L_0x0116;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0115, code lost:
        throw new com.uc.webview.export.cyclone.UCKnownException(2002, (java.lang.String) "No entry exists in zip file. Make sure specify a valid zip file url.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0116, code lost:
        stat("sdk_decz_s", null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0121, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        close(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0125, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0126, code lost:
        stat("sdk_dec_e", null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x012d, code lost:
        if ((r8 instanceof com.uc.webview.export.cyclone.UCKnownException) != false) goto L_0x012f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0131, code lost:
        throw ((com.uc.webview.export.cyclone.UCKnownException) r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0139, code lost:
        throw new com.uc.webview.export.cyclone.UCKnownException(2005, r8);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:3:0x000a, B:16:0x004f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void decompress(boolean r8, android.content.Context r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.io.FilenameFilter r13) throws com.uc.webview.export.cyclone.UCKnownException {
        /*
            java.lang.String r0 = "sdk_dec"
            r1 = 0
            stat(r0, r1)
            if (r8 == 0) goto L_0x0037
            java.lang.Class<com.uc.webview.export.cyclone.service.UCUnSevenZip> r8 = com.uc.webview.export.cyclone.service.UCUnSevenZip.class
            com.uc.webview.export.cyclone.service.UCServiceInterface r8 = com.uc.webview.export.cyclone.UCService.initImpl(r8)     // Catch:{ Throwable -> 0x0034 }
            com.uc.webview.export.cyclone.service.UCUnSevenZip r8 = (com.uc.webview.export.cyclone.service.UCUnSevenZip) r8     // Catch:{ Throwable -> 0x0034 }
            if (r8 == 0) goto L_0x002a
            int r8 = r8.deccompress(r9, r10, r11, r12)     // Catch:{ Throwable -> 0x0034 }
            if (r8 == 0) goto L_0x011b
            com.uc.webview.export.cyclone.UCKnownException r9 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x0034 }
            r10 = 2001(0x7d1, float:2.804E-42)
            java.lang.String r11 = "Error on 7z decoding: "
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x0034 }
            java.lang.String r8 = r11.concat(r8)     // Catch:{ Throwable -> 0x0034 }
            r9.<init>(r10, r8)     // Catch:{ Throwable -> 0x0034 }
            throw r9     // Catch:{ Throwable -> 0x0034 }
        L_0x002a:
            com.uc.webview.export.cyclone.UCKnownException r8 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x0034 }
            r9 = 2015(0x7df, float:2.824E-42)
            java.lang.String r10 = "Error on 7z decoding: no impl found."
            r8.<init>(r9, r10)     // Catch:{ Throwable -> 0x0034 }
            throw r8     // Catch:{ Throwable -> 0x0034 }
        L_0x0034:
            r8 = move-exception
            goto L_0x0126
        L_0x0037:
            java.lang.String r8 = "sdk_decz"
            stat(r8, r1)     // Catch:{ Throwable -> 0x0034 }
            java.util.zip.ZipInputStream r8 = new java.util.zip.ZipInputStream     // Catch:{ Throwable -> 0x0034 }
            java.io.BufferedInputStream r9 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0034 }
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0034 }
            r12.<init>(r10)     // Catch:{ Throwable -> 0x0034 }
            r9.<init>(r12)     // Catch:{ Throwable -> 0x0034 }
            r8.<init>(r9)     // Catch:{ Throwable -> 0x0034 }
            r9 = 0
            r10 = r1
            r12 = 0
            r0 = 0
        L_0x004f:
            java.util.zip.ZipEntry r2 = r8.getNextEntry()     // Catch:{ all -> 0x0121 }
            if (r2 == 0) goto L_0x0107
            r3 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r3]     // Catch:{ all -> 0x0121 }
            java.lang.String r2 = r2.getName()     // Catch:{ all -> 0x0121 }
            java.lang.String r5 = ".."
            boolean r5 = r2.contains(r5)     // Catch:{ all -> 0x0121 }
            if (r5 == 0) goto L_0x0078
            com.uc.webview.export.cyclone.UCKnownException r10 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x0121 }
            r11 = 2012(0x7dc, float:2.82E-42)
            java.lang.String r12 = "Zip entry [%s] not valid."
            r13 = 1
            java.lang.Object[] r13 = new java.lang.Object[r13]     // Catch:{ all -> 0x0121 }
            r13[r9] = r2     // Catch:{ all -> 0x0121 }
            java.lang.String r9 = java.lang.String.format(r12, r13)     // Catch:{ all -> 0x0121 }
            r10.<init>(r11, r9)     // Catch:{ all -> 0x0121 }
            throw r10     // Catch:{ all -> 0x0121 }
        L_0x0078:
            if (r13 == 0) goto L_0x008d
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0121 }
            r5.<init>(r2)     // Catch:{ all -> 0x0121 }
            java.io.File r6 = r5.getParentFile()     // Catch:{ all -> 0x0121 }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x0121 }
            boolean r5 = r13.accept(r6, r5)     // Catch:{ all -> 0x0121 }
            if (r5 == 0) goto L_0x004f
        L_0x008d:
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x0121 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0121 }
            r6.<init>()     // Catch:{ all -> 0x0121 }
            r6.append(r11)     // Catch:{ all -> 0x0121 }
            java.lang.String r7 = "/"
            r6.append(r7)     // Catch:{ all -> 0x0121 }
            r6.append(r2)     // Catch:{ all -> 0x0121 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0121 }
            r5.<init>(r6)     // Catch:{ all -> 0x0121 }
            java.lang.String r6 = "/"
            boolean r6 = r2.endsWith(r6)     // Catch:{ all -> 0x0121 }
            if (r6 != 0) goto L_0x0102
            java.lang.String r6 = "\\"
            boolean r2 = r2.endsWith(r6)     // Catch:{ all -> 0x0121 }
            if (r2 == 0) goto L_0x00b7
            goto L_0x0102
        L_0x00b7:
            java.io.File r10 = new java.io.File     // Catch:{ all -> 0x0121 }
            java.lang.String r2 = r5.getParent()     // Catch:{ all -> 0x0121 }
            r10.<init>(r2)     // Catch:{ all -> 0x0121 }
            expectCreateDirFile(r10)     // Catch:{ all -> 0x0121 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ all -> 0x0121 }
            r10.<init>(r5)     // Catch:{ all -> 0x0121 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0121 }
            r2.<init>(r10, r3)     // Catch:{ all -> 0x0121 }
        L_0x00cd:
            int r10 = r8.read(r4, r9, r3)     // Catch:{ all -> 0x0121 }
            r5 = -1
            if (r10 == r5) goto L_0x00e6
            r2.write(r4, r9, r10)     // Catch:{ all -> 0x0121 }
            int r12 = r12 + r10
            r10 = 536870912(0x20000000, float:1.0842022E-19)
            if (r12 <= r10) goto L_0x00cd
            com.uc.webview.export.cyclone.UCKnownException r9 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x0121 }
            r10 = 2010(0x7da, float:2.817E-42)
            java.lang.String r11 = "Zip contents is too big."
            r9.<init>(r10, r11)     // Catch:{ all -> 0x0121 }
            throw r9     // Catch:{ all -> 0x0121 }
        L_0x00e6:
            r2.flush()     // Catch:{ all -> 0x0121 }
            r2.close()     // Catch:{ all -> 0x0121 }
            r8.closeEntry()     // Catch:{ all -> 0x0121 }
            int r0 = r0 + 1
            r10 = 1024(0x400, float:1.435E-42)
            if (r0 <= r10) goto L_0x00ff
            com.uc.webview.export.cyclone.UCKnownException r9 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x0121 }
            r10 = 2011(0x7db, float:2.818E-42)
            java.lang.String r11 = "Too many files to unzip."
            r9.<init>(r10, r11)     // Catch:{ all -> 0x0121 }
            throw r9     // Catch:{ all -> 0x0121 }
        L_0x00ff:
            r10 = r2
            goto L_0x004f
        L_0x0102:
            expectCreateDirFile(r5)     // Catch:{ all -> 0x0121 }
            goto L_0x004f
        L_0x0107:
            close(r8)     // Catch:{ Throwable -> 0x0034 }
            if (r10 != 0) goto L_0x0116
            com.uc.webview.export.cyclone.UCKnownException r8 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x0034 }
            r9 = 2002(0x7d2, float:2.805E-42)
            java.lang.String r10 = "No entry exists in zip file. Make sure specify a valid zip file url."
            r8.<init>(r9, r10)     // Catch:{ Throwable -> 0x0034 }
            throw r8     // Catch:{ Throwable -> 0x0034 }
        L_0x0116:
            java.lang.String r8 = "sdk_decz_s"
            stat(r8, r1)     // Catch:{ Throwable -> 0x0034 }
        L_0x011b:
            java.lang.String r8 = "sdk_dec_s"
            stat(r8, r1)
            return
        L_0x0121:
            r9 = move-exception
            close(r8)     // Catch:{ Throwable -> 0x0034 }
            throw r9     // Catch:{ Throwable -> 0x0034 }
        L_0x0126:
            java.lang.String r9 = "sdk_dec_e"
            stat(r9, r1)
            boolean r9 = r8 instanceof com.uc.webview.export.cyclone.UCKnownException
            if (r9 == 0) goto L_0x0132
            com.uc.webview.export.cyclone.UCKnownException r8 = (com.uc.webview.export.cyclone.UCKnownException) r8
            throw r8
        L_0x0132:
            com.uc.webview.export.cyclone.UCKnownException r9 = new com.uc.webview.export.cyclone.UCKnownException
            r10 = 2005(0x7d5, float:2.81E-42)
            r9.<init>(r10, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCCyclone.decompress(boolean, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.io.FilenameFilter):void");
    }

    public static synchronized boolean decompressIfNeeded(Context context, boolean z, String str, long j, long j2, File file, File file2, FilenameFilter filenameFilter, boolean z2, int i) throws UCKnownException {
        String str2;
        boolean decompressIfNeeded;
        synchronized (UCCyclone.class) {
            if (z) {
                try {
                    str2 = serverZipTag;
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                str2 = "";
            }
            decompressIfNeeded = decompressIfNeeded(context, str2, str, j, j2, file, file2, filenameFilter, z2, i);
        }
        return decompressIfNeeded;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0157, code lost:
        return true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00cb A[Catch:{ all -> 0x009d, all -> 0x0173 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0158 A[SYNTHETIC, Splitter:B:56:0x0158] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean decompressIfNeeded(android.content.Context r14, java.lang.String r15, java.lang.String r16, long r17, long r19, java.io.File r21, java.io.File r22, java.io.FilenameFilter r23, boolean r24, int r25) throws com.uc.webview.export.cyclone.UCKnownException {
        /*
            r1 = r15
            r9 = r22
            java.lang.Class<com.uc.webview.export.cyclone.UCCyclone> r10 = com.uc.webview.export.cyclone.UCCyclone.class
            monitor-enter(r10)
            r8 = 0
            r2 = r16
            r3 = r17
            r5 = r19
            r7 = r9
            boolean r2 = isDecompressFinished(r2, r3, r5, r7, r8)     // Catch:{ all -> 0x0173 }
            r8 = 0
            if (r2 == 0) goto L_0x0017
            monitor-exit(r10)
            return r8
        L_0x0017:
            boolean r2 = r21.exists()     // Catch:{ all -> 0x0173 }
            r11 = 1
            if (r2 != 0) goto L_0x0034
            com.uc.webview.export.cyclone.UCKnownException r1 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x0173 }
            r2 = 1001(0x3e9, float:1.403E-42)
            java.lang.String r3 = "File [%s] not exists."
            java.lang.Object[] r4 = new java.lang.Object[r11]     // Catch:{ all -> 0x0173 }
            java.lang.String r5 = r21.getAbsolutePath()     // Catch:{ all -> 0x0173 }
            r4[r8] = r5     // Catch:{ all -> 0x0173 }
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x0173 }
            r1.<init>(r2, r3)     // Catch:{ all -> 0x0173 }
            throw r1     // Catch:{ all -> 0x0173 }
        L_0x0034:
            java.io.File r2 = r14.getCacheDir()     // Catch:{ all -> 0x0173 }
            java.lang.String r3 = r22.getAbsolutePath()     // Catch:{ all -> 0x0173 }
            java.io.File r4 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ all -> 0x0173 }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x0173 }
            boolean r3 = r3.startsWith(r4)     // Catch:{ all -> 0x0173 }
            if (r3 == 0) goto L_0x0051
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x0173 }
            java.lang.String r3 = ".cache"
            r2.<init>(r9, r3)     // Catch:{ all -> 0x0173 }
        L_0x0051:
            java.io.File r12 = new java.io.File     // Catch:{ all -> 0x0173 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0173 }
            java.lang.String r4 = "temp_dec_"
            r3.<init>(r4)     // Catch:{ all -> 0x0173 }
            int r4 = android.os.Process.myPid()     // Catch:{ all -> 0x0173 }
            r3.append(r4)     // Catch:{ all -> 0x0173 }
            java.lang.String r4 = "_"
            r3.append(r4)     // Catch:{ all -> 0x0173 }
            int r4 = android.os.Process.myTid()     // Catch:{ all -> 0x0173 }
            r3.append(r4)     // Catch:{ all -> 0x0173 }
            java.lang.String r4 = "_"
            r3.append(r4)     // Catch:{ all -> 0x0173 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0173 }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0173 }
            r3.append(r4)     // Catch:{ all -> 0x0173 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0173 }
            r12.<init>(r2, r3)     // Catch:{ all -> 0x0173 }
            boolean r2 = r12.exists()     // Catch:{ all -> 0x0173 }
            if (r2 != 0) goto L_0x016e
            expectCreateDirFile(r12)     // Catch:{ all -> 0x0173 }
            r13 = 0
            if (r1 == 0) goto L_0x00a1
            int r2 = r1.length()     // Catch:{ all -> 0x009d }
            if (r2 <= 0) goto L_0x00a1
            java.lang.String r2 = serverZipTag     // Catch:{ all -> 0x009d }
            boolean r1 = r2.equalsIgnoreCase(r1)     // Catch:{ all -> 0x009d }
            goto L_0x00aa
        L_0x009d:
            r0 = move-exception
            r1 = r0
            goto L_0x016a
        L_0x00a1:
            java.lang.String r1 = r21.getAbsolutePath()     // Catch:{ all -> 0x009d }
            boolean r1 = detectZipByFileType(r1)     // Catch:{ all -> 0x009d }
            r1 = r1 ^ r11
        L_0x00aa:
            java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x009d }
            java.lang.String r2 = r21.getAbsolutePath()     // Catch:{ all -> 0x009d }
            java.lang.String r3 = r12.getAbsolutePath()     // Catch:{ all -> 0x009d }
            r4 = r14
            r5 = r23
            decompress(r1, r4, r2, r3, r5)     // Catch:{ all -> 0x009d }
            r1 = r16
            r2 = r17
            r4 = r19
            r6 = r9
            expectMakeDecompressNotFinished(r1, r2, r4, r6)     // Catch:{ all -> 0x009d }
            java.io.File[] r1 = r12.listFiles()     // Catch:{ all -> 0x009d }
            if (r1 == 0) goto L_0x0158
            int r2 = r1.length     // Catch:{ all -> 0x009d }
            r3 = 0
        L_0x00cd:
            if (r3 >= r2) goto L_0x0142
            r4 = r1[r3]     // Catch:{ all -> 0x009d }
            java.lang.String r5 = r4.getName()     // Catch:{ all -> 0x009d }
            java.lang.String r6 = "."
            java.lang.String r7 = ""
            java.lang.String r5 = r5.replace(r6, r7)     // Catch:{ all -> 0x009d }
            java.lang.String r6 = "/"
            java.lang.String r7 = ""
            java.lang.String r5 = r5.replace(r6, r7)     // Catch:{ all -> 0x009d }
            java.lang.String r6 = " "
            java.lang.String r7 = ""
            java.lang.String r5 = r5.replace(r6, r7)     // Catch:{ all -> 0x009d }
            int r5 = r5.length()     // Catch:{ all -> 0x009d }
            if (r5 == 0) goto L_0x013f
            java.io.File r5 = new java.io.File     // Catch:{ all -> 0x009d }
            java.lang.String r6 = r4.getName()     // Catch:{ all -> 0x009d }
            r5.<init>(r9, r6)     // Catch:{ all -> 0x009d }
            boolean r6 = r5.exists()     // Catch:{ all -> 0x009d }
            if (r6 == 0) goto L_0x0124
            boolean r6 = r5.isDirectory()     // Catch:{ all -> 0x009d }
            if (r6 == 0) goto L_0x010c
            recursiveDelete(r5, r8, r13)     // Catch:{ all -> 0x009d }
            goto L_0x0124
        L_0x010c:
            boolean r6 = r5.delete()     // Catch:{ all -> 0x009d }
            if (r6 != 0) goto L_0x0124
            com.uc.webview.export.cyclone.UCKnownException r1 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x009d }
            r2 = 1004(0x3ec, float:1.407E-42)
            java.lang.String r3 = "File [%s] delete failed."
            java.lang.Object[] r4 = new java.lang.Object[r11]     // Catch:{ all -> 0x009d }
            r4[r8] = r5     // Catch:{ all -> 0x009d }
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x009d }
            r1.<init>(r2, r3)     // Catch:{ all -> 0x009d }
            throw r1     // Catch:{ all -> 0x009d }
        L_0x0124:
            boolean r6 = r4.renameTo(r5)     // Catch:{ all -> 0x009d }
            if (r6 != 0) goto L_0x013f
            com.uc.webview.export.cyclone.UCKnownException r1 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x009d }
            r2 = 1005(0x3ed, float:1.408E-42)
            java.lang.String r3 = "File [%s] renameTo [%s] failed."
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x009d }
            r6[r8] = r4     // Catch:{ all -> 0x009d }
            r6[r11] = r5     // Catch:{ all -> 0x009d }
            java.lang.String r3 = java.lang.String.format(r3, r6)     // Catch:{ all -> 0x009d }
            r1.<init>(r2, r3)     // Catch:{ all -> 0x009d }
            throw r1     // Catch:{ all -> 0x009d }
        L_0x013f:
            int r3 = r3 + 1
            goto L_0x00cd
        L_0x0142:
            r1 = r16
            r2 = r17
            r4 = r19
            r6 = r9
            r7 = r25
            expectMakeDecompressFinished(r1, r2, r4, r6, r7)     // Catch:{ all -> 0x009d }
            recursiveDelete(r12, r8, r13)     // Catch:{ all -> 0x0173 }
            if (r24 == 0) goto L_0x0156
            deleteFile(r21)     // Catch:{ all -> 0x0173 }
        L_0x0156:
            monitor-exit(r10)
            return r11
        L_0x0158:
            com.uc.webview.export.cyclone.UCKnownException r1 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ all -> 0x009d }
            r2 = 2008(0x7d8, float:2.814E-42)
            java.lang.String r3 = "Zip [%s] decompress success but no items found."
            java.lang.Object[] r4 = new java.lang.Object[r11]     // Catch:{ all -> 0x009d }
            r4[r8] = r21     // Catch:{ all -> 0x009d }
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x009d }
            r1.<init>(r2, r3)     // Catch:{ all -> 0x009d }
            throw r1     // Catch:{ all -> 0x009d }
        L_0x016a:
            recursiveDelete(r12, r8, r13)     // Catch:{ all -> 0x0173 }
            throw r1     // Catch:{ all -> 0x0173 }
        L_0x016e:
            r4 = r14
            r5 = r23
            goto L_0x0034
        L_0x0173:
            r0 = move-exception
            r1 = r0
            monitor-exit(r10)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCCyclone.decompressIfNeeded(android.content.Context, java.lang.String, java.lang.String, long, long, java.io.File, java.io.File, java.io.FilenameFilter, boolean, int):boolean");
    }

    static File getDecompressStopFlgFile(String str, long j, long j2, File file, boolean z) {
        return new File(file, getDecompressSourceHash(str, j, j2, z));
    }

    static File getDecompressStartFlgFile(String str, long j, long j2, File file, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDecompressSourceHash(str, j, j2, z));
        sb.append("_start");
        return new File(file, sb.toString());
    }

    static File getDecompressOrignFlgFile(String str, long j, long j2, File file, boolean z, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDecompressSourceHash(str, j, j2, z));
        sb.append(DecFileOrign.DecFileOrignFlag);
        sb.append(Integer.toString(i));
        return new File(file, sb.toString());
    }

    static void expectMakeDecompressFinished(String str, long j, long j2, File file, int i) throws UCKnownException {
        try {
            File decompressStopFlgFile = getDecompressStopFlgFile(str, j, j2, file, false);
            if (decompressStopFlgFile.exists() || decompressStopFlgFile.createNewFile()) {
                try {
                    File decompressStartFlgFile = getDecompressStartFlgFile(str, j, j2, file, false);
                    if (!decompressStartFlgFile.exists() || decompressStartFlgFile.delete()) {
                        int i2 = i;
                        if (i2 == DecFileOrign.Sdcard_Share_Core) {
                            try {
                                File decompressOrignFlgFile = getDecompressOrignFlgFile(str, j, j2, file, false, i2);
                                if (!decompressOrignFlgFile.exists() && !decompressOrignFlgFile.createNewFile()) {
                                    throw new Exception("createNewFile return false");
                                }
                            } catch (Throwable th) {
                                throw new UCKnownException(1006, th);
                            }
                        }
                    } else {
                        throw new Exception("delete File return false");
                    }
                } catch (Throwable th2) {
                    throw new UCKnownException(1004, th2);
                }
            } else {
                throw new Exception("createNewFile return false");
            }
        } catch (Throwable th3) {
            throw new UCKnownException(1006, th3);
        }
    }

    public static void recursiveDelete(File file, boolean z, Object obj) {
        ArrayList arrayList;
        if (file.exists()) {
            if (obj != null) {
                arrayList = new ArrayList(2);
                if (obj instanceof File) {
                    arrayList.add((File) obj);
                } else if (obj instanceof File[]) {
                    Collections.addAll(arrayList, (File[]) obj);
                } else {
                    throw new UCKnownException(1010, String.format("File or File[] argument expected, but get [%s].", new Object[]{obj.getClass().getName()}));
                }
            } else {
                arrayList = null;
            }
            ArrayList arrayList2 = new ArrayList(20);
            File[] listFiles = (!z || !file.isDirectory()) ? new File[]{file} : file.listFiles();
            int i = -1;
            do {
                for (File file2 : listFiles) {
                    if (arrayList == null || !arrayList.contains(file2)) {
                        if (!file2.isDirectory()) {
                            file2.delete();
                        } else if (file2.getName().replace(".", "").replace("/", "").replace(Token.SEPARATOR, "").length() != 0) {
                            arrayList2.add(file2);
                        }
                    }
                }
                i++;
                listFiles = i < arrayList2.size() ? ((File) arrayList2.get(i)).listFiles() : null;
                if (listFiles == null) {
                    break;
                }
            } while (i < 256);
            for (int size = arrayList2.size() - 1; size >= 0; size--) {
                ((File) arrayList2.get(size)).delete();
            }
        }
    }

    static void deleteFile(File file) throws UCKnownException {
        if (!file.delete()) {
            throw new UCKnownException(1004, String.format("File [%s] delete failed.", new Object[]{file.getAbsolutePath()}));
        }
    }

    public static File optimizedFileFor(String str, String str2) {
        String name = new File(str).getName();
        if (!name.endsWith(".dex")) {
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(name);
                sb.append(".dex");
                name = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder(lastIndexOf + 4);
                sb2.append(name, 0, lastIndexOf);
                sb2.append(".dex");
                name = sb2.toString();
            }
        }
        return new File(str2, name);
    }

    public static String hashFileContents(File file, MessageDigestType messageDigestType) {
        String str;
        String str2;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        if (!file.isFile()) {
            return null;
        }
        if (messageDigestType == MessageDigestType.MD5) {
            str = "MD5";
            str2 = "%032x";
        } else if (messageDigestType == MessageDigestType.SHA1) {
            str = "SHA-1";
            str2 = "%040x";
        } else if (messageDigestType != MessageDigestType.SHA256) {
            return null;
        } else {
            str = "SHA-256";
            str2 = "%064x";
        }
        byte[] bArr = new byte[131072];
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            while (true) {
                try {
                    int read = bufferedInputStream.read(bArr, 0, 131072);
                    if (read != -1) {
                        instance.update(bArr, 0, read);
                    } else {
                        close(bufferedInputStream);
                        BigInteger bigInteger = new BigInteger(1, instance.digest());
                        return String.format(Locale.CHINA, str2, new Object[]{bigInteger});
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream2 = bufferedInputStream;
                    close(bufferedInputStream2);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            close(bufferedInputStream2);
            throw th;
        }
    }

    public static File genFile(Context context, String str, String str2, String str3, long j, String str4, byte[][] bArr, Object... objArr) throws UCKnownException, IOException {
        File dataFolder2 = getDataFolder(context);
        if (str != null && str.length() > 0) {
            dataFolder2 = expectCreateDirFile(new File(dataFolder2, str));
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str3 == null) {
            str3 = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(String.valueOf(j));
        sb.append(str3);
        String sb2 = sb.toString();
        File file = new File(dataFolder2, sb2);
        if (!file.canRead()) {
            file.delete();
        }
        long codeLength = (long) getCodeLength(bArr);
        if (!file.exists() || file.length() != codeLength || !str4.equals(hashFileContents(file, MessageDigestType.MD5))) {
            getFile(bArr, file);
            UCLogger create = !enableDebugLog ? null : UCLogger.create("d", LOG_TAG);
            if (create != null) {
                StringBuilder sb3 = new StringBuilder("genFile Extract new ");
                sb3.append(sb2);
                sb3.append(" to ");
                sb3.append(dataFolder2);
                create.print(sb3.toString(), new Throwable[0]);
            }
        }
        if (sInusedFiles != null) {
            sInusedFiles.add(file);
        }
        return file;
    }

    private static int getCodeLength(byte[][] bArr) {
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        return i;
    }

    public static void copy(File file, File file2) throws IOException {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            fileOutputStream = new FileOutputStream(file2);
            byte[] bArr = new byte[8192];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    fileInputStream.close();
                    return;
                }
            }
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
    }
}
