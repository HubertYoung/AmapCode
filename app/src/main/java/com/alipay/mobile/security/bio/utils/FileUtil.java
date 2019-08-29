package com.alipay.mobile.security.bio.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class FileUtil {
    public static final int STREAM_BUFFER_SIZE = 8192;

    public static synchronized byte[] toByteArray(String str) {
        BufferedInputStream bufferedInputStream;
        byte[] byteArray;
        synchronized (FileUtil.class) {
            File file = new File(str);
            if (!file.exists()) {
                throw new FileNotFoundException(str);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) file.length());
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = bufferedInputStream.read(bArr, 0, 1024);
                        if (-1 != read) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            byteArray = byteArrayOutputStream.toByteArray();
                            InputStreamUtils.close(bufferedInputStream);
                            OutputStreamUtils.close(byteArrayOutputStream);
                        }
                    }
                } catch (IOException e) {
                    e = e;
                }
            } catch (IOException e2) {
                e = e2;
                bufferedInputStream = null;
                try {
                    BioLog.e(e.toString());
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    InputStreamUtils.close(bufferedInputStream);
                    OutputStreamUtils.close(byteArrayOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedInputStream = null;
                InputStreamUtils.close(bufferedInputStream);
                OutputStreamUtils.close(byteArrayOutputStream);
                throw th;
            }
        }
        return byteArray;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap, int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, i, byteArrayOutputStream);
        try {
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void bitmap2File(Bitmap bitmap, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            BioLog.w((Throwable) e);
        } catch (IOException e2) {
            BioLog.w((Throwable) e2);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getAssetsData(android.content.Context r3, java.lang.String r4) {
        /*
            r0 = 0
            android.content.res.AssetManager r1 = r3.getAssets()     // Catch:{ IOException -> 0x0011, all -> 0x001a }
            java.io.InputStream r2 = r1.open(r4)     // Catch:{ IOException -> 0x0011, all -> 0x001a }
            byte[] r0 = com.alipay.mobile.security.bio.utils.InputStreamUtils.input2byte(r2)     // Catch:{ IOException -> 0x0023 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
        L_0x0010:
            return r0
        L_0x0011:
            r1 = move-exception
            r2 = r0
        L_0x0013:
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)     // Catch:{ all -> 0x0021 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            goto L_0x0010
        L_0x001a:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x001d:
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            throw r0
        L_0x0021:
            r0 = move-exception
            goto L_0x001d
        L_0x0023:
            r1 = move-exception
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.FileUtil.getAssetsData(android.content.Context, java.lang.String):byte[]");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getAssetsData(android.content.res.Resources r3, java.lang.String r4) {
        /*
            r0 = 0
            android.content.res.AssetManager r1 = r3.getAssets()     // Catch:{ IOException -> 0x0011, all -> 0x001a }
            java.io.InputStream r2 = r1.open(r4)     // Catch:{ IOException -> 0x0011, all -> 0x001a }
            byte[] r0 = com.alipay.mobile.security.bio.utils.InputStreamUtils.input2byte(r2)     // Catch:{ IOException -> 0x0023 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
        L_0x0010:
            return r0
        L_0x0011:
            r1 = move-exception
            r2 = r0
        L_0x0013:
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)     // Catch:{ all -> 0x0021 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            goto L_0x0010
        L_0x001a:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x001d:
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            throw r0
        L_0x0021:
            r0 = move-exception
            goto L_0x001d
        L_0x0023:
            r1 = move-exception
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.FileUtil.getAssetsData(android.content.res.Resources, java.lang.String):byte[]");
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] getRawData(android.content.Context r3, int r4) {
        /*
            r0 = 0
            android.content.res.Resources r1 = r3.getResources()     // Catch:{ IOException -> 0x0011, all -> 0x001e }
            java.io.InputStream r2 = r1.openRawResource(r4)     // Catch:{ IOException -> 0x0011, all -> 0x001e }
            byte[] r0 = com.alipay.mobile.security.bio.utils.InputStreamUtils.input2byte(r2)     // Catch:{ IOException -> 0x0027 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
        L_0x0010:
            return r0
        L_0x0011:
            r1 = move-exception
            r2 = r0
        L_0x0013:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0025 }
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)     // Catch:{ all -> 0x0025 }
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            goto L_0x0010
        L_0x001e:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x0021:
            com.alipay.mobile.security.bio.utils.InputStreamUtils.close(r2)
            throw r0
        L_0x0025:
            r0 = move-exception
            goto L_0x0021
        L_0x0027:
            r1 = move-exception
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.FileUtil.getRawData(android.content.Context, int):byte[]");
    }

    public static synchronized boolean save(File file, byte[] bArr) {
        boolean z;
        OutputStream outputStream;
        BufferedOutputStream bufferedOutputStream;
        OutputStream outputStream2 = null;
        synchronized (FileUtil.class) {
            z = false;
            if (!(file == null || bArr == null)) {
                if (file.exists()) {
                    file.delete();
                } else {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        BioLog.e(e.toString());
                    }
                }
                try {
                    outputStream = new FileOutputStream(file);
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                    } catch (IOException e2) {
                        e = e2;
                        bufferedOutputStream = null;
                        outputStream2 = outputStream;
                        try {
                            BioLog.e(e.toString());
                            OutputStreamUtils.close(bufferedOutputStream);
                            OutputStreamUtils.close(outputStream2);
                            return z;
                        } catch (Throwable th) {
                            th = th;
                            outputStream = outputStream2;
                            OutputStreamUtils.close(bufferedOutputStream);
                            OutputStreamUtils.close(outputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = null;
                        OutputStreamUtils.close(bufferedOutputStream);
                        OutputStreamUtils.close(outputStream);
                        throw th;
                    }
                    try {
                        bufferedOutputStream.write(bArr);
                        bufferedOutputStream.flush();
                        z = true;
                        OutputStreamUtils.close(bufferedOutputStream);
                        OutputStreamUtils.close(outputStream);
                    } catch (IOException e3) {
                        e = e3;
                        outputStream2 = outputStream;
                        BioLog.e(e.toString());
                        OutputStreamUtils.close(bufferedOutputStream);
                        OutputStreamUtils.close(outputStream2);
                        return z;
                    } catch (Throwable th3) {
                        th = th3;
                        OutputStreamUtils.close(bufferedOutputStream);
                        OutputStreamUtils.close(outputStream);
                        throw th;
                    }
                } catch (IOException e4) {
                    e = e4;
                    bufferedOutputStream = null;
                    BioLog.e(e.toString());
                    OutputStreamUtils.close(bufferedOutputStream);
                    OutputStreamUtils.close(outputStream2);
                    return z;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedOutputStream = null;
                    outputStream = null;
                    OutputStreamUtils.close(bufferedOutputStream);
                    OutputStreamUtils.close(outputStream);
                    throw th;
                }
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0055 A[SYNTHETIC, Splitter:B:34:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0065 A[SYNTHETIC, Splitter:B:41:0x0065] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void saveContent(java.io.File r5, java.lang.String r6, boolean r7) {
        /*
            java.lang.Class<com.alipay.mobile.security.bio.utils.FileUtil> r3 = com.alipay.mobile.security.bio.utils.FileUtil.class
            monitor-enter(r3)
            if (r5 != 0) goto L_0x0010
            if (r6 != 0) goto L_0x0010
            com.alipay.mobile.security.bio.exception.BioIllegalArgumentException r0 = new com.alipay.mobile.security.bio.exception.BioIllegalArgumentException     // Catch:{ all -> 0x000d }
            r0.<init>()     // Catch:{ all -> 0x000d }
            throw r0     // Catch:{ all -> 0x000d }
        L_0x000d:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        L_0x0010:
            boolean r0 = r5.exists()     // Catch:{ all -> 0x000d }
            if (r0 != 0) goto L_0x0020
            java.io.File r0 = r5.getParentFile()     // Catch:{ IOException -> 0x0038 }
            r0.mkdirs()     // Catch:{ IOException -> 0x0038 }
            r5.createNewFile()     // Catch:{ IOException -> 0x0038 }
        L_0x0020:
            r2 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            java.io.OutputStreamWriter r0 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            r4.<init>(r5, r7)     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x004a, all -> 0x0062 }
            r1.write(r6)     // Catch:{ Exception -> 0x0075 }
            r1.close()     // Catch:{ IOException -> 0x0041 }
        L_0x0036:
            monitor-exit(r3)
            return
        L_0x0038:
            r0 = move-exception
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x000d }
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)     // Catch:{ all -> 0x000d }
            goto L_0x0020
        L_0x0041:
            r0 = move-exception
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x000d }
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)     // Catch:{ all -> 0x000d }
            goto L_0x0036
        L_0x004a:
            r0 = move-exception
            r1 = r2
        L_0x004c:
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0072 }
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x0036
            r1.close()     // Catch:{ IOException -> 0x0059 }
            goto L_0x0036
        L_0x0059:
            r0 = move-exception
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x000d }
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)     // Catch:{ all -> 0x000d }
            goto L_0x0036
        L_0x0062:
            r0 = move-exception
        L_0x0063:
            if (r2 == 0) goto L_0x0068
            r2.close()     // Catch:{ IOException -> 0x0069 }
        L_0x0068:
            throw r0     // Catch:{ all -> 0x000d }
        L_0x0069:
            r1 = move-exception
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x000d }
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)     // Catch:{ all -> 0x000d }
            goto L_0x0068
        L_0x0072:
            r0 = move-exception
            r2 = r1
            goto L_0x0063
        L_0x0075:
            r0 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.utils.FileUtil.saveContent(java.io.File, java.lang.String, boolean):void");
    }

    public static void recursionDeleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    file.delete();
                    return;
                }
                for (File recursionDeleteFile : listFiles) {
                    recursionDeleteFile(recursionDeleteFile);
                }
                file.delete();
            }
        }
    }

    public static void copyDirectory(String str, String str2) {
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                String str3 = str + File.separator + listFiles[i].getName();
                String str4 = str2 + File.separator + listFiles[i].getName();
                File file2 = new File(str2);
                if (!file2.exists()) {
                    file2.mkdir();
                }
                copyDirectory(str3, str4);
            }
        } else if (file.isFile()) {
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(str)));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(str2)));
            byte[] bArr = new byte[524288];
            while (dataInputStream.read(bArr) != -1) {
                dataOutputStream.write(bArr);
            }
            dataInputStream.close();
            dataOutputStream.close();
        } else {
            BioLog.e((String) "please input correct path!");
        }
    }

    public static File extractAssets(Context context, String str) {
        AssetManager assets = context.getAssets();
        File file = new File(context.getFilesDir().getAbsolutePath());
        file.mkdirs();
        File file2 = new File(file, str);
        try {
            if (assets.list(str) != null) {
                streamToStream(assets.open(str), new FileOutputStream(file2));
            }
        } catch (IOException e) {
            BioLog.w("ZCAuth", "extractAssets: ", e);
        }
        return file2;
    }

    public static boolean streamToStream(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    outputStream.write(bArr, 0, read);
                } else {
                    outputStream.flush();
                    return true;
                }
            }
        } catch (Exception e) {
            BioLog.w("ContentValues", "streamToStream(InputStream, OutputStream): Exception occur.", e);
            return false;
        } finally {
            closeSafely(outputStream);
            closeSafely(inputStream);
        }
    }

    public static void closeSafely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                BioLog.w("ContentValues", "closeSafely(Closeable): Exception occur.", e);
            }
        }
    }

    public static String assetsToString(Context context, String str) {
        try {
            Scanner useDelimiter = new Scanner(context.getAssets().open(str)).useDelimiter("\\A");
            return useDelimiter.hasNext() ? useDelimiter.next() : "";
        } catch (IOException e) {
            BioLog.w("ZCAuth", "extractAssets: ", e);
            return null;
        }
    }
}
