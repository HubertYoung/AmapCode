package com.alipay.mobile.common.apkutil;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.monitor.ExceptionID;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MiscUtils {
    public static final String TAG = "MiscUtils";

    public MiscUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static ClassLoader getBootClassLoader(ClassLoader classloader) {
        while (!classloader.getClass().getSimpleName().equalsIgnoreCase("BootClassLoader")) {
            classloader = classloader.getParent();
        }
        return classloader;
    }

    public static Object newInstance(String className, ClassLoader loader) {
        try {
            return loader.loadClass(className).newInstance();
        } catch (ClassNotFoundException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
        } catch (InstantiationException e2) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e2);
        } catch (IllegalAccessException e3) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e3);
        }
        return null;
    }

    public static Object callActivityOnMethod(Activity targetActivity, String methodName, Class<?>[] parameterTypes, Object[] values) {
        if (targetActivity == null) {
            return null;
        }
        try {
            Method targetMethod = a((Object) targetActivity, methodName, parameterTypes);
            if (targetMethod == null) {
                return null;
            }
            if (values == null) {
                return targetMethod.invoke(targetActivity, new Object[0]);
            }
            return targetMethod.invoke(targetActivity, values);
        } catch (IllegalArgumentException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return null;
        } catch (IllegalAccessException e2) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e2);
            return null;
        } catch (InvocationTargetException e3) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e3);
            return null;
        }
    }

    private static Method a(Object target, String methodName, Class<?>[] parameterTypes) {
        Method targetMethod = null;
        Class clazz = target.getClass();
        while (true) {
            if (clazz != null) {
                if (parameterTypes != null) {
                    targetMethod = clazz.getDeclaredMethod(methodName, parameterTypes);
                    break;
                }
                try {
                    targetMethod = clazz.getDeclaredMethod(methodName, new Class[0]);
                    break;
                } catch (NoSuchMethodException e) {
                    LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
                    clazz = clazz.getSuperclass();
                }
            } else {
                break;
            }
        }
        targetMethod.setAccessible(true);
        return targetMethod;
    }

    public static boolean fileFromAssets(String assetsFileName, AssetManager assetManager, String destFilePath) {
        if (assetsFileName == null || assetManager == null || destFilePath == null) {
            return false;
        }
        try {
            File destFile = new File(destFilePath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            return a(assetManager.open(assetsFileName), (OutputStream) new FileOutputStream(destFile));
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return false;
        }
    }

    public static boolean copyFile(String srcFilePath, String destFilePath) {
        if (srcFilePath == null || destFilePath == null) {
            return false;
        }
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists()) {
            return false;
        }
        try {
            File destFile = new File(destFilePath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            return a((InputStream) new FileInputStream(srcFile), (OutputStream) new FileOutputStream(destFile));
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return false;
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0043=Splitter:B:20:0x0043, B:13:0x001f=Splitter:B:13:0x001f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.io.InputStream r10, java.io.OutputStream r11) {
        /*
            r7 = 0
            r3 = 0
            r1 = 0
            java.io.BufferedOutputStream r4 = new java.io.BufferedOutputStream     // Catch:{ FileNotFoundException -> 0x0069, IOException -> 0x0042 }
            r4.<init>(r11)     // Catch:{ FileNotFoundException -> 0x0069, IOException -> 0x0042 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x006b, IOException -> 0x0062, all -> 0x005b }
            r2.<init>(r10)     // Catch:{ FileNotFoundException -> 0x006b, IOException -> 0x0062, all -> 0x005b }
            r8 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r8]     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
        L_0x0011:
            int r5 = r10.read(r0)     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
            if (r5 < 0) goto L_0x002f
            r8 = 0
            r4.write(r0, r8, r5)     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
            goto L_0x0011
        L_0x001c:
            r6 = move-exception
            r1 = r2
            r3 = r4
        L_0x001f:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0053 }
            java.lang.String r9 = "MiscUtils"
            r8.warn(r9, r6)     // Catch:{ all -> 0x0053 }
            closeStream(r3)
            closeStream(r1)
        L_0x002e:
            return r7
        L_0x002f:
            r4.flush()     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
            r2.close()     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
            r4.close()     // Catch:{ FileNotFoundException -> 0x001c, IOException -> 0x0065, all -> 0x005e }
            r7 = 1
            closeStream(r4)
            closeStream(r2)
            r1 = r2
            r3 = r4
            goto L_0x002e
        L_0x0042:
            r6 = move-exception
        L_0x0043:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0053 }
            java.lang.String r9 = "MiscUtils"
            r8.warn(r9, r6)     // Catch:{ all -> 0x0053 }
            closeStream(r3)
            closeStream(r1)
            goto L_0x002e
        L_0x0053:
            r8 = move-exception
        L_0x0054:
            closeStream(r3)
            closeStream(r1)
            throw r8
        L_0x005b:
            r8 = move-exception
            r3 = r4
            goto L_0x0054
        L_0x005e:
            r8 = move-exception
            r1 = r2
            r3 = r4
            goto L_0x0054
        L_0x0062:
            r6 = move-exception
            r3 = r4
            goto L_0x0043
        L_0x0065:
            r6 = move-exception
            r1 = r2
            r3 = r4
            goto L_0x0043
        L_0x0069:
            r6 = move-exception
            goto L_0x001f
        L_0x006b:
            r6 = move-exception
            r3 = r4
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.apkutil.MiscUtils.a(java.io.InputStream, java.io.OutputStream):boolean");
    }

    public static boolean copyToFile(InputStream inputStream, File destFile) {
        OutputStream out;
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(buffer);
                if (bytesRead >= 0) {
                    out.write(buffer, 0, bytesRead);
                } else {
                    out.close();
                    return true;
                }
            }
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return false;
        } catch (Throwable th) {
            out.close();
            throw th;
        }
    }

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            }
        }
    }

    public static void reportFail(Context ctx, Throwable ex) {
        if (ex != null) {
            LoggerFactory.getMonitorLogger().exception(ExceptionID.MONITORPOINT_CLIENTSERR, new Exception("NativeApkEngine", ex));
        }
    }

    public static boolean verifyApk(Signature[] hostSignatures, String archiveFilePath) {
        Certificate[] certs = verifyMD5AndLoadCertificates(archiveFilePath);
        if (certs == null) {
            return false;
        }
        Signature[] targetSignatures = null;
        if (certs != null && certs.length > 0) {
            int N = certs.length;
            targetSignatures = new Signature[certs.length];
            for (int i = 0; i < N; i++) {
                try {
                    targetSignatures[i] = new Signature(certs[i].getEncoded());
                } catch (CertificateEncodingException e) {
                    LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
                }
            }
        }
        return a(hostSignatures, targetSignatures);
    }

    public static Certificate[] verifyMD5AndLoadCertificates(String archiveFilePath) {
        Certificate[] certs = null;
        try {
            JarFile jarFile = new JarFile(archiveFilePath);
            JarEntry jarEntry = jarFile.getJarEntry(IBundleOperator.CLASSES_DEX);
            certs = a(jarFile, jarEntry, new byte[8192]);
            if (certs == null) {
                LoggerFactory.getTraceLogger().error((String) "MiscUtils", " Has no certificates at entry " + jarEntry.getName() + "; ignoring!");
            }
            jarFile.close();
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
        }
        return certs;
    }

    private static Certificate[] a(JarFile jarFile, JarEntry je, byte[] readBuffer) {
        try {
            InputStream is = jarFile.getInputStream(je);
            do {
            } while (is.read(readBuffer, 0, 8192) != -1);
            is.close();
            if (je != null) {
                return je.getCertificates();
            }
            return null;
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return null;
        } catch (RuntimeException e2) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e2);
            return null;
        }
    }

    private static boolean a(Signature[] s1, Signature[] s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        HashSet set1 = new HashSet();
        for (Signature sig : s1) {
            set1.add(sig);
        }
        HashSet set2 = new HashSet();
        for (Signature sig2 : s2) {
            set2.add(sig2);
        }
        if (set1.equals(set2)) {
            return true;
        }
        return false;
    }

    public static Signature[] getPackageSignatures(Context context, String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 64).signatures;
        } catch (NameNotFoundException e) {
            LoggerFactory.getTraceLogger().warn((String) "MiscUtils", (Throwable) e);
            return null;
        }
    }
}
